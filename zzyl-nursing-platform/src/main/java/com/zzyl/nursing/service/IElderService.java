package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.Elder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 老人Service接口
 * 
 * @author liuyp
 * @date 2026-05-13
 */
public interface IElderService extends IService<Elder> {
    /**
     * 查询老人
     * 
     * @param id 老人主键
     * @return 老人
     */
    Elder selectElderById(Long id);

    /**
     * 查询老人列表
     * 
     * @param elder 老人
     * @return 老人集合
     */
    List<Elder> selectElderList(Elder elder);

    /**
     * 新增老人
     * 
     * @param elder 老人
     * @return 结果
     */
    int insertElder(Elder elder);

    /**
     * 修改老人
     * 
     * @param elder 老人
     * @return 结果
     */
    int updateElder(Elder elder);

    /**
     * 批量删除老人
     * 
     * @param ids 需要删除的老人主键集合
     * @return 结果
     */
    int deleteElderByIds(Long[] ids);

    /**
     * 删除老人信息
     * 
     * @param id 老人主键
     * @return 结果
     */
    int deleteElderById(Long id);
}
