package com.rc.cloud.app.system.controller.admin.tenant;

import com.rc.cloud.app.system.api.tenant.entity.SysTenantPackageDO;
import com.rc.cloud.app.system.convert.tenant.TenantPackageConvert;
import com.rc.cloud.app.system.service.tenant.TenantPackageService;
import com.rc.cloud.app.system.vo.tenant.packages.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台 - 租户套餐")
@RestController
@RequestMapping("/sys/tenant-package")
@Validated
public class TenantPackageController {

    @Resource
    private TenantPackageService tenantPackageService;

    @PostMapping("/create")
    @Operation(summary = "创建租户套餐")
    @PreAuthorize("@pms.hasPermission('sys:tenant-package:create')")
    public CodeResult<String> createTenantPackage(@Valid @RequestBody TenantPackageCreateReqVO createReqVO) {
        return CodeResult.ok(tenantPackageService.createTenantPackage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户套餐")
    @PreAuthorize("@pms.hasPermission('sys:tenant-package:update')")
    public CodeResult<Boolean> updateTenantPackage(@Valid @RequestBody TenantPackageUpdateReqVO updateReqVO) {
        tenantPackageService.updateTenantPackage(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户套餐")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@pms.hasPermission('sys:tenant-package:delete')")
    public CodeResult<Boolean> deleteTenantPackage(@PathVariable("id") String id) {
        tenantPackageService.deleteTenantPackage(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "获得租户套餐")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:tenant-package:query')")
    public CodeResult<TenantPackageRespVO> getTenantPackage(@PathVariable("id") String id) {
        SysTenantPackageDO tenantPackage = tenantPackageService.getTenantPackage(id);
        return CodeResult.ok(TenantPackageConvert.INSTANCE.convert(tenantPackage));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租户套餐分页")
    @PreAuthorize("@pms.hasPermission('sys:tenant-package:query')")
    public CodeResult<PageResult<TenantPackageRespVO>> getTenantPackagePage(@Valid TenantPackagePageReqVO pageVO) {
        PageResult<SysTenantPackageDO> pageResult = tenantPackageService.getTenantPackagePage(pageVO);
        return CodeResult.ok(TenantPackageConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get-simple-list")
    @Operation(summary = "获取租户套餐精简信息列表", description = "只包含被开启的租户套餐，主要用于前端的下拉选项")
    public CodeResult<List<TenantPackageSimpleRespVO>> getTenantPackageList() {
        // 获得角色列表，只要开启状态的
        List<SysTenantPackageDO> list = tenantPackageService.getTenantPackageListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return CodeResult.ok(TenantPackageConvert.INSTANCE.convertList02(list));
    }

}
