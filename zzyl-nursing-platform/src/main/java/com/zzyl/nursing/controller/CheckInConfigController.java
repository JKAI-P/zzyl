package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
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
import com.zzyl.nursing.domain.CheckInConfig;
import com.zzyl.nursing.service.ICheckInConfigService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 入住配置Controller
 * 
 * @author liuyp
 * @date 2026-05-13
 */
@RestController
@RequestMapping("/nursing/config")
@Api(tags = "入住配置相关接口")
public class CheckInConfigController extends BaseController {
    @Autowired
    private ICheckInConfigService checkInConfigService;

    /**
     * 查询入住配置列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取入住配置列表")
    public TableDataInfo<CheckInConfig> list(CheckInConfig checkInConfig) {
        startPage();
        List<CheckInConfig> list = checkInConfigService.selectCheckInConfigList(checkInConfig);
        return getDataTable(list);
    }

    /**
     * 导出入住配置列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:export')")
    @Log(title = "入住配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出入住配置列表")
    public void export(HttpServletResponse response, CheckInConfig checkInConfig) {
        List<CheckInConfig> list = checkInConfigService.selectCheckInConfigList(checkInConfig);
        ExcelUtil<CheckInConfig> util = new ExcelUtil<CheckInConfig>(CheckInConfig.class);
        util.exportExcel(response, list, "入住配置数据");
    }

    /**
     * 获取入住配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取入住配置详细信息")
    public R<CheckInConfig> getInfo(@ApiParam(value = "入住配置ID", required = true)
            @PathVariable("id") Long id) {
        return R.ok(checkInConfigService.selectCheckInConfigById(id));
    }

    /**
     * 新增入住配置
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:add')")
    @Log(title = "入住配置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增入住配置")
    public AjaxResult add(@ApiParam(value = "入住配置实体", required = true) @RequestBody CheckInConfig checkInConfig) {
        return toAjax(checkInConfigService.insertCheckInConfig(checkInConfig));
    }

    /**
     * 修改入住配置
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:edit')")
    @Log(title = "入住配置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改入住配置")
    public AjaxResult edit(@ApiParam(value = "入住配置实体", required = true) @RequestBody CheckInConfig checkInConfig) {
        return toAjax(checkInConfigService.updateCheckInConfig(checkInConfig));
    }

    /**
     * 删除入住配置
     */
    @PreAuthorize("@ss.hasPermi('nursing:config:remove')")
    @Log(title = "入住配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除入住配置")
    public AjaxResult remove(@ApiParam(value = "要删除的数据id的数组") @PathVariable Long[] ids) {
        return toAjax(checkInConfigService.deleteCheckInConfigByIds(ids));
    }
}
