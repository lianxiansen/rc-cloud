package com.rc.cloud.app.system.controller.admin.tenant;

import com.rc.cloud.app.system.api.tenant.entity.SysTenantDO;
import com.rc.cloud.app.system.convert.tenant.TenantConvert;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantCreateReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantPageReqVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantRespVO;
import com.rc.cloud.app.system.vo.tenant.tenant.TenantUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;


@Tag(name = "管理后台 - 租户")
@RestController
@RequestMapping("/sys/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @GetMapping("/get-id-by-name")
    @PermitAll
    @Operation(summary = "使用租户名，获得租户编号", description = "登录界面，根据用户的租户名，获得租户编号")
    @Parameter(name = "name", description = "租户名", required = true, example = "1024")
    public CodeResult<String> getTenantIdByName(@RequestParam("name") String name) {
        SysTenantDO tenantDO = tenantService.getTenantByName(name);
        return CodeResult.ok(tenantDO != null ? tenantDO.getId() : null);
    }

    @PostMapping("/create")
    @Operation(summary = "创建租户")
    @PreAuthorize("@pms.hasPermission('sys:tenant:create')")
    public CodeResult<String> createTenant(@Valid @RequestBody TenantCreateReqVO createReqVO) {
        return CodeResult.ok(tenantService.createTenant(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户")
    @PreAuthorize("@pms.hasPermission('sys:tenant:update')")
    public CodeResult<Boolean> updateTenant(@Valid @RequestBody TenantUpdateReqVO updateReqVO) {
        tenantService.updateTenant(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:tenant:delete')")
    public CodeResult<Boolean> deleteTenant(@PathVariable("id") String id) {
        tenantService.deleteTenant(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "获得租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:tenant:query')")
    public CodeResult<TenantRespVO> getTenant(@PathVariable("id") String id) {
        SysTenantDO tenant = tenantService.getTenant(id);
        return CodeResult.ok(TenantConvert.INSTANCE.convert(tenant));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租户分页")
    @PreAuthorize("@pms.hasPermission('sys:tenant:query')")
    public CodeResult<PageResult<TenantRespVO>> getTenantPage(@Valid TenantPageReqVO pageVO) {
        PageResult<SysTenantDO> pageResult = tenantService.getTenantPage(pageVO);
        return CodeResult.ok(TenantConvert.INSTANCE.convertPage(pageResult));
    }

//    @GetMapping("/export-excel")
//    @Operation(summary = "导出租户 Excel")
//    @PreAuthorize("@ss.hasPermission('system:tenant:export')")
////    @OperateLog(type = EXPORT)
//    public void exportTenantExcel(@Valid TenantExportReqVO exportReqVO,
//                                  HttpServletResponse response) throws IOException {
//        List<SysTenantDO> list = tenantService.getTenantList(exportReqVO);
//        // 导出 Excel
//        List<TenantExcelVO> datas = TenantConvert.INSTANCE.convertList02(list);
//        ExcelUtils.write(response, "租户.xls", "数据", TenantExcelVO.class, datas);
//    }


}
