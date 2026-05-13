package com.zzyl.nursing.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.dto.NursingElderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingElderMapper;
import com.zzyl.nursing.domain.NursingElder;
import com.zzyl.nursing.service.INursingElderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 护理员老人关联Service业务层处理
 *
 */
@Service
public class NursingElderServiceImpl extends ServiceImpl<NursingElderMapper, NursingElder> implements INursingElderService
{
    @Autowired
    private NursingElderMapper nursingElderMapper;

    /**
     * 查询护理员老人关联
     * 
     * @param id 护理员老人关联主键
     * @return 护理员老人关联
     */
    @Override
    public NursingElder selectNursingElderById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询护理员老人关联列表
     * 
     * @param nursingElder 护理员老人关联
     * @return 护理员老人关联
     */
    @Override
    public List<NursingElder> selectNursingElderList(NursingElder nursingElder)
    {
        return nursingElderMapper.selectNursingElderList(nursingElder);
    }

    /**
     * 新增护理员老人关联
     * 
     * @param nursingElder 护理员老人关联
     * @return 结果
     */
    @Override
    public int insertNursingElder(NursingElder nursingElder)
    {
        return save(nursingElder) ? 1 : 0;
    }

    /**
     * 修改护理员老人关联
     * 
     * @param nursingElder 护理员老人关联
     * @return 结果
     */
    @Override
    public int updateNursingElder(NursingElder nursingElder)
    {
        return updateById(nursingElder) ? 1 : 0;
    }

    /**
     * 批量删除护理员老人关联
     * 
     * @param ids 需要删除的护理员老人关联主键
     * @return 结果
     */
    @Override
    public int deleteNursingElderByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除护理员老人关联信息
     * 
     * @param id 护理员老人关联主键
     * @return 结果
     */
    @Override
    public int deleteNursingElderById(Long id)
    {
        return removeById(id) ? 1 : 0;
    }

    /**
     * 设置护理员
     *
     * @param nursingElderDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean setNursingElder(List<NursingElderDto> nursingElderDtos) {
        //重新添加
        List<NursingElder> list = new ArrayList<>();
        List<Long> elderIds = new ArrayList<>();
        nursingElderDtos.forEach(nursingElderDto -> {
            Long elderId = nursingElderDto.getElderId();
            elderIds.add(elderId);
            nursingElderDto.getNursingIds().forEach(nursingId -> {
                NursingElder nursingElder = new NursingElder();
                nursingElder.setNursingId(nursingId);
                nursingElder.setElderId(elderId);
                list.add(nursingElder);
            });
        });

        //删除所有的对应的关系
        LambdaQueryWrapper<NursingElder> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(NursingElder::getElderId, elderIds);
        nursingElderMapper.delete(wrapper);
        //批量新增
        return saveBatch(list);
    }
}
