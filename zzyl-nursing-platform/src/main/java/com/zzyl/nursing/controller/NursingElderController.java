package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.NursingElderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.NursingElder;
import com.zzyl.nursing.service.INursingElderService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理员老人关联Controller
 */
@RestController
@RequestMapping("/elder/nursingElder")
@Api(tags = "护理员老人关联相关接口")
public class NursingElderController extends BaseController {
    @Autowired
    private INursingElderService nursingElderService;

    /**
     * 查询护理员老人关联列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取护理员老人关联列表")
    public TableDataInfo<List<NursingElder>> list(NursingElder nursingElder) {
        startPage();
        List<NursingElder> list = nursingElderService.selectNursingElderList(nursingElder);
        return getDataTable(list);
    }

    /**
     * 导出护理员老人关联列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:export')")
    @Log(title = "护理员老人关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出护理员老人关联列表")
    public void export(HttpServletResponse response, NursingElder nursingElder) {
        List<NursingElder> list = nursingElderService.selectNursingElderList(nursingElder);
        ExcelUtil<NursingElder> util = new ExcelUtil<NursingElder>(NursingElder.class);
        util.exportExcel(response, list, "护理员老人关联数据");
    }

    /**
     * 获取护理员老人关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取护理员老人关联详细信息")
    public R<NursingElder> getInfo(@ApiParam(value = "护理员老人关联ID", required = true)
                                   @PathVariable("id") Long id) {
        return R.ok(nursingElderService.selectNursingElderById(id));
    }

    /**
     * 新增护理员老人关联
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:add')")
    @Log(title = "护理员老人关联", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增护理员老人关联")
    public AjaxResult add(@ApiParam(value = "护理员老人关联实体", required = true) @RequestBody NursingElder nursingElder) {
        return toAjax(nursingElderService.insertNursingElder(nursingElder));
    }

    /**
     * 修改护理员老人关联
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:edit')")
    @Log(title = "护理员老人关联", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改护理员老人关联")
    public AjaxResult edit(@ApiParam(value = "护理员老人关联实体", required = true) @RequestBody NursingElder nursingElder) {
        return toAjax(nursingElderService.updateNursingElder(nursingElder));
    }

    /**
     * 删除护理员老人关联
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingElder:remove')")
    @Log(title = "护理员老人关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除护理员老人关联")
    public AjaxResult remove(@ApiParam(value = "要删除的数据id的数组") @PathVariable Long[] ids) {
        return toAjax(nursingElderService.deleteNursingElderByIds(ids));
    }

    @PostMapping("/setNursing")
    @ApiOperation("设置护理员")
    public AjaxResult setNursingElder(@RequestBody List<NursingElderDto> nursingElderDtos) {
        return AjaxResult.success(nursingElderService.setNursingElder(nursingElderDtos));
    }
}
