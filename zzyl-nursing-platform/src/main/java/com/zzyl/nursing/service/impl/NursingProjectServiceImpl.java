package com.zzyl.nursing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.mapper.NursingProjectMapper;
import com.zzyl.nursing.vo.NursingProjectVo;
import com.zzyl.nursing.service.INursingProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 护理项目Service业务层处理
 *
 */
@Service
public class NursingProjectServiceImpl extends ServiceImpl<NursingProjectMapper, NursingProject> implements INursingProjectService {
    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    /**
     * 查询护理项目
     *
     * @param id 护理项目主键
     * @return 护理项目
     */
    @Override
    public NursingProject selectNursingProjectById(Long id) {
        return nursingProjectMapper.selectById(id);
    }

    /**
     * 查询护理项目列表
     *
     * @param nursingProject 护理项目
     * @return 护理项目
     */
    @Override
    public List<NursingProject> selectNursingProjectList(NursingProject nursingProject) {
        return nursingProjectMapper.selectNursingProjectList(nursingProject);
    }

    /**
     * 新增护理项目
     *
     * @param nursingProject 护理项目
     * @return 结果
     */
    @Override
    public int insertNursingProject(NursingProject nursingProject) {
        return nursingProjectMapper.insert(nursingProject);
    }

    /**
     * 修改护理项目
     *
     * @param nursingProject 护理项目
     * @return 结果
     */
    @Override
    public int updateNursingProject(NursingProject nursingProject) {
        return nursingProjectMapper.updateById(nursingProject);
    }

    /**
     * 批量删除护理项目
     *
     * @param ids 需要删除的护理项目主键
     * @return 结果
     */
    @Override
    public int deleteNursingProjectByIds(Long[] ids) {
        return nursingProjectMapper.deleteBatchIds(List.of(ids));
    }

    /**
     * 删除护理项目信息
     *
     * @param id 护理项目主键
     * @return 结果
     */
    @Override
    public int deleteNursingProjectById(Long id) {
        return nursingProjectMapper.deleteById(id);
    }

    @Override
    public List<NursingProjectVo> selectAll() {
        return nursingProjectMapper.selectAll();
    }

    /**
     * 分页查询护理项目（小程序端）
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示记录数
     * @param name     护理项目名称
     * @param status   状态
     * @return 分页数据
     */
    @Override
    public TableDataInfo<NursingProject> pageByNameAndStatus(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<NursingProject> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<NursingProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, NursingProject::getName, name)
            .eq(status != null, NursingProject::getStatus, status);
        page = page(page, queryWrapper);

        TableDataInfo<NursingProject> tableDataInfo = new TableDataInfo<>();
        tableDataInfo.setRows(page.getRecords());
        tableDataInfo.setTotal(page.getTotal());
        tableDataInfo.setCode(200);
        tableDataInfo.setMsg("查询成功");
        return tableDataInfo;
    }

}
