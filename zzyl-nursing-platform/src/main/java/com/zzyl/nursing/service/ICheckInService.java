package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.vo.CheckInDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 入住Service接口
 *
 * @author liuyp
 * @date 2026-05-13
 */
public interface ICheckInService extends IService<CheckIn> {
    /**
     * 查询入住
     *
     * @param id 入住主键
     * @return 入住
     */
    CheckIn selectCheckInById(Long id);

    /**
     * 查询入住列表（含楼层、房间、合同状态）
     *
     * @param checkIn 入住
     * @return 入住集合
     */
    List<CheckInDetailVo> selectCheckInList(CheckIn checkIn);

    /**
     * 新增入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    int insertCheckIn(CheckIn checkIn);

    /**
     * 修改入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    int updateCheckIn(CheckIn checkIn);

    /**
     * 批量删除入住
     * 
     * @param ids 需要删除的入住主键集合
     * @return 结果
     */
    int deleteCheckInByIds(Long[] ids);

    /**
     * 删除入住信息
     * 
     * @param id 入住主键
     * @return 结果
     */
    int deleteCheckInById(Long id);

    /**
     * 申请入住
     * @param checkInApplyDto
     */
    void apply(com.zzyl.nursing.dto.CheckInApplyDto checkInApplyDto);

    /**
     * 查询入住详情
     * @param id 入住ID
     * @return 入住详情
     */
    com.zzyl.nursing.vo.CheckInDetailVo detail(Long id);
}
