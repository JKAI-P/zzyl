package com.zzyl.nursing.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.common.exception.ServiceException;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.*;
import com.zzyl.nursing.dto.CheckInApplyDto;
import com.zzyl.nursing.mapper.*;
import com.zzyl.nursing.service.ICheckInService;
import com.zzyl.nursing.utils.CodeGenerator;
import com.zzyl.nursing.utils.IDCardUtils;
import com.zzyl.nursing.vo.CheckInDetailVo;
import com.zzyl.nursing.vo.CheckInConfigVo;
import com.zzyl.nursing.vo.CheckInElderVo;
import com.zzyl.nursing.vo.ElderFamilyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 入住Service业务层处理
 * 
 * @author liuyp
 * @date 2026-05-13
 */
@Service
@Slf4j
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements ICheckInService {
    @Autowired
    private CheckInMapper checkInMapper;
    @Autowired
    private ElderMapper elderMapper;
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private CheckInConfigMapper checkInConfigMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private FloorMapper floorMapper;

    /**
     * 查询入住
     * 
     * @param id 入住主键
     * @return 入住
     */
    @Override
    public CheckIn selectCheckInById(Long id) {
        return getById(id);
    }

    /**
     * 查询入住列表
     *
     * @param checkIn 入住
     * @return 入住
     */
    @Override
    public List<CheckInDetailVo> selectCheckInList(CheckIn checkIn) {
        return checkInMapper.selectCheckInList(checkIn);
    }

    /**
     * 新增入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    @Override
    public int insertCheckIn(CheckIn checkIn) {
        return save(checkIn) ? 1 : 0;
    }

    /**
     * 修改入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    @Override
    public int updateCheckIn(CheckIn checkIn) {
        return updateById(checkIn) ? 1 : 0;
    }

    /**
     * 批量删除入住
     * 
     * @param ids 需要删除的入住主键
     * @return 结果
     */
    @Override
    public int deleteCheckInByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除入住信息
     * 
     * @param id 入住主键
     * @return 结果
     */
    @Override
    public int deleteCheckInById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    /**
     * 申请入住
     *
     * @param dto 入住申请DTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apply(CheckInApplyDto dto) {
        //1. 通过身份证号查询老人信息，如果是已入住状态，则抛出异常
        String idCardNo = dto.getCheckInElderDto().getIdCardNo();
        Elder elderDb = elderMapper.selectOne(Wrappers.<Elder>lambdaQuery().eq(Elder::getIdCardNo, idCardNo));
        if (elderDb != null) {
            Integer status = elderDb.getStatus();
            if (status == 2 || status == 3 || status == 4) {
                throw new ServiceException("该老人已入住");
            }
        }
        log.info("【{}入住申请】老人申请入住", idCardNo);

        //2. 更新床位状态为已入住
        int updated = bedMapper.updateBedStatus(dto.getCheckInConfigDto().getBedId());
        if (updated == 0) {
            throw new ServiceException("床位已占用，请选择其它床位");
        }
        log.info("【{}入住申请】床位{}状态已更新", idCardNo, dto.getCheckInConfigDto().getBedId());

        //3. 新增或更新老人信息
        Elder elder = BeanUtil.toBean(dto.getCheckInElderDto(), Elder.class);
        elder.setStatus(1);
        Bed bed = bedMapper.selectById(dto.getCheckInConfigDto().getBedId());
        if (elderDb == null) {
            //新增老人信息
            elder.setBedNumber(bed.getBedNumber());
            elderMapper.insert(elder);
            log.info("【{}入住申请】新增老人信息成功", idCardNo);
        }else{
            //修改老人信息
            elder.setId(elderDb.getId());
            elderMapper.updateById(elder);
            log.info("【{}入住申请】修改老人信息成功", idCardNo);
        }

        //4. 新增入住信息
        CheckIn checkIn = BeanUtil.toBean(dto.getCheckInConfigDto(), CheckIn.class);
        checkIn.setElderId(elder.getId());
        checkIn.setElderName(elder.getName());
        checkIn.setIdCardNo(elder.getIdCardNo());
        checkIn.setBedNumber(bed.getBedNumber());
        checkIn.setStatus(0);
        checkIn.setRemark(JSONUtil.toJsonStr(dto.getElderFamilyDtoList()));
        
        // 设置入住开始和结束时间（从 DTO 中获取）
        checkIn.setStartDate(dto.getCheckInConfigDto().getStartDate());
        checkIn.setEndDate(dto.getCheckInConfigDto().getEndDate());
        
        save(checkIn);
        log.info("【{}入住申请】新增入住信息成功", idCardNo);

        //5. 新增签约办理
        Contract contract = BeanUtil.toBean(dto.getCheckInContractDto(), Contract.class);
        contract.setCheckInId(checkIn.getId());
        contract.setElderId(elder.getId());
        contract.setContractNumber(CodeGenerator.generateContractNumber());
        contract.setElderName(elder.getName());
        
        // 设置必填字段的默认值，避免数据库报错
        if (contract.getAgreementPath() == null || contract.getAgreementPath().isEmpty()) {
            contract.setAgreementPath("");
        }
        if (contract.getThirdPartyName() == null || contract.getThirdPartyName().isEmpty()) {
            contract.setThirdPartyName("");
        }
        if (contract.getThirdPartyPhone() == null || contract.getThirdPartyPhone().isEmpty()) {
            contract.setThirdPartyPhone("");
        }
        if (contract.getContractName() == null || contract.getContractName().isEmpty()) {
            contract.setContractName("");
        }
        
        LocalDateTime startDate = dto.getCheckInConfigDto().getStartDate();
        LocalDateTime endDate = dto.getCheckInConfigDto().getEndDate();
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) {
            //如果现在还没有到 入住开始时间，设置状态为0待入住
            contract.setStatus(0);
        } else if (now.isAfter(endDate)) {
            //如果现在已经过了 入住结束时间，无意义，直接抛出异常
            throw new ServiceException("入住结束日期要大于当前日期");
        } else{
            //如果现在在 入住开始~入住结束之间，设置状态为1已入住
            contract.setStatus(1);
        }
        contract.setSignDate(LocalDateTime.now());
        contractMapper.insert(contract);
        log.info("【{}入住申请】新增签约合同信息成功", idCardNo);

        //6. 新增入住配置
        CheckInConfig checkInConfig = BeanUtil.toBean(dto.getCheckInConfigDto(), CheckInConfig.class);
        checkInConfig.setCheckInId(checkIn.getId());
        checkInConfigMapper.insert(checkInConfig);
        log.info("【{}入住申请】新增入住配置成功", idCardNo);
    }

    /**
     * 查询入住详情
     *
     * @param id 入住ID
     * @return 入住详情
     */
    @Override
    public CheckInDetailVo detail(Long id) {
        // 1. 单次JOIN查询入住详情（老人、床位、楼层、房间、入住配置）
        CheckInDetailVo vo = checkInMapper.selectCheckInDetail(id);
        if (vo == null) {
            throw new ServiceException("入住信息不存在");
        }

        // 2. 构建嵌套的老人信息对象
        CheckInElderVo elderVo = new CheckInElderVo();
        elderVo.setName(vo.getElderName());
        elderVo.setIdCardNo(vo.getIdCardNo());
        elderVo.setSex(vo.getSex());
        elderVo.setPhone(vo.getPhone());
        elderVo.setAddress(vo.getAddress());
        elderVo.setImage(vo.getImage());
        // 计算年龄
        try {
            elderVo.setAge(IDCardUtils.getAgeByIdCard(vo.getIdCardNo()));
        } catch (Exception e) {
            elderVo.setAge(null);
        }
        // 查询老人详细信息（出生日期、身份证图片等）
        Elder elder = elderMapper.selectById(vo.getElderId());
        if (elder != null) {
            elderVo.setBirthday(elder.getBirthday());
            elderVo.setIdCardPortraitImg(elder.getIdCardPortraitImg());
            elderVo.setIdCardNationalEmblemImg(elder.getIdCardNationalEmblemImg());
        }
        vo.setCheckInElderVo(elderVo);

        // 3. 构建嵌套的入住配置对象
        CheckInConfigVo configVo = new CheckInConfigVo();
        configVo.setStartDate(vo.getStartDate());
        configVo.setEndDate(vo.getEndDate());
        configVo.setFeeStartDate(vo.getFeeStartDate());
        configVo.setFeeEndDate(vo.getFeeEndDate());
        configVo.setNursingLevelName(vo.getNursingLevelName());
        configVo.setBedFee(vo.getBedFee() != null ? new java.math.BigDecimal(vo.getBedFee()) : null);
        configVo.setNursingFee(vo.getNursingFee() != null ? new java.math.BigDecimal(vo.getNursingFee()) : null);
        configVo.setDeposit(vo.getDeposit() != null ? new java.math.BigDecimal(vo.getDeposit()) : null);
        configVo.setGovernmentSubsidy(vo.getGovernmentSubsidy() != null ? new java.math.BigDecimal(vo.getGovernmentSubsidy()) : null);
        configVo.setInsurancePayment(vo.getInsurancePayment() != null ? new java.math.BigDecimal(vo.getInsurancePayment()) : null);
        configVo.setOtherFees(vo.getOtherFees() != null ? new java.math.BigDecimal(vo.getOtherFees()) : null);
        configVo.setBedNumber(vo.getBedNumber());
        configVo.setFloorName(vo.getFloorName());
        configVo.setCode(vo.getRoomCode());
        // 查询入住配置中的护理等级ID
        CheckInConfig checkInConfig = checkInConfigMapper.selectOne(
            Wrappers.<CheckInConfig>lambdaQuery().eq(CheckInConfig::getCheckInId, id)
        );
        if (checkInConfig != null) {
            configVo.setNursingLevelId(checkInConfig.getNursingLevelId());
        }
        // 查询床位和房间信息
        if (vo.getBedNumber() != null && !vo.getBedNumber().isEmpty()) {
            Bed bed = bedMapper.selectOne(
                Wrappers.<Bed>lambdaQuery().eq(Bed::getBedNumber, vo.getBedNumber())
            );
            if (bed != null) {
                configVo.setBedId(bed.getId());
                configVo.setRoomId(bed.getRoomId());
                Room room = roomMapper.selectById(bed.getRoomId());
                if (room != null) {
                    configVo.setFloorId(room.getFloorId());
                }
            }
        }
        vo.setCheckInConfigVo(configVo);

        // 4. 查询合同信息
        Contract contract = contractMapper.selectOne(
            Wrappers.<Contract>lambdaQuery().eq(Contract::getCheckInId, id)
        );
        vo.setContract(contract);

        // 5. 解析家属信息（从check_in.remark字段）
        CheckIn checkIn = checkInMapper.selectById(id);
        if (checkIn != null && checkIn.getRemark() != null && !checkIn.getRemark().isEmpty()) {
            try {
                java.util.List<ElderFamilyVo> familyList = JSONUtil.toList(
                    checkIn.getRemark(), ElderFamilyVo.class
                );
                vo.setElderFamilyVoList(familyList);
            } catch (Exception e) {
                vo.setElderFamilyVoList(new java.util.ArrayList<>());
            }
        } else {
            vo.setElderFamilyVoList(new java.util.ArrayList<>());
        }

        return vo;
    }
}
