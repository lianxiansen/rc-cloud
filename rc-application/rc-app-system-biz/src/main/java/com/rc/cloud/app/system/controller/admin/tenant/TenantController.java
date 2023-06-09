package com.rc.cloud.app.system.controller.admin.tenant;

import com.rc.cloud.app.system.convert.tenant.TenantConvert;
import com.rc.cloud.app.system.model.tenant.TenantDO;
import com.rc.cloud.app.system.service.tenant.TenantService;
import com.rc.cloud.app.system.vo.tenant.tenant.*;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.excel.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


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
    public CodeResult<Long> getTenantIdByName(@RequestParam("name") String name) {
        TenantDO tenantDO = tenantService.getTenantByName(name);
        return CodeResult.ok(tenantDO != null ? tenantDO.getId() : null);
    }

    @PostMapping("/create")
    @Operation(summary = "创建租户")
//    @PreAuthorize("@ss.hasPermission('system:tenant:create')")
    public CodeResult<Long> createTenant(@Valid @RequestBody TenantCreateReqVO createReqVO) {
        return CodeResult.ok(tenantService.createTenant(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户")
//    @PreAuthorize("@ss.hasPermission('system:tenant:update')")
    public CodeResult<Boolean> updateTenant(@Valid @RequestBody TenantUpdateReqVO updateReqVO) {
        tenantService.updateTenant(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:tenant:delete')")
    public CodeResult<Boolean> deleteTenant(@RequestParam("id") Long id) {
        tenantService.deleteTenant(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CodeResult<TenantRespVO> getTenant(@RequestParam("id") Long id) {
        TenantDO tenant = tenantService.getTenant(id);
        return CodeResult.ok(TenantConvert.INSTANCE.convert(tenant));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租户分页")
//    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CodeResult<PageResult<TenantRespVO>> getTenantPage(@Valid TenantPageReqVO pageVO) {
        PageResult<TenantDO> pageResult = tenantService.getTenantPage(pageVO);
        return CodeResult.ok(TenantConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租户 Excel")
//    @PreAuthorize("@ss.hasPermission('system:tenant:export')")
//    @OperateLog(type = EXPORT)
    public void exportTenantExcel(@Valid TenantExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<TenantDO> list = tenantService.getTenantList(exportReqVO);
        // 导出 Excel
        List<TenantExcelVO> datas = TenantConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "租户.xls", "数据", TenantExcelVO.class, datas);
    }


}
