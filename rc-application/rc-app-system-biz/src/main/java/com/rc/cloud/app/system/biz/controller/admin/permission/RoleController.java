package com.rc.cloud.app.system.biz.controller.admin.permission;

import com.rc.cloud.app.system.biz.convert.permission.RoleConvert;
import com.rc.cloud.app.system.biz.model.permission.RoleDO;
import com.rc.cloud.app.system.biz.service.permission.RoleService;
import com.rc.cloud.app.system.biz.vo.permission.role.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.excel.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


import static java.util.Collections.singleton;

@Tag(name = "管理后台 - 角色")
@RestController
@RequestMapping("/system/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/create")
    @Operation(summary = "创建角色")
//    @PreAuthorize("@ss.hasPermission('system:role:create')")
    public CodeResult<Long> createRole(@Valid @RequestBody RoleCreateReqVO reqVO) {
        return CodeResult.ok(roleService.createRole(reqVO, null));
    }

    @PutMapping("/update")
    @Operation(summary = "修改角色")
//    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public CodeResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO reqVO) {
        roleService.updateRole(reqVO);
        return CodeResult.ok(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改角色状态")
//    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public CodeResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:role:delete')")
    public CodeResult<Boolean> deleteRole(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得角色信息")
//    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CodeResult<RoleRespVO> getRole(@RequestParam("id") Long id) {
        RoleDO role = roleService.getRole(id);
        return CodeResult.ok(RoleConvert.INSTANCE.convert(role));
    }

    @GetMapping("/page")
    @Operation(summary = "获得角色分页")
//    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public CodeResult<PageResult<RoleDO>> getRolePage(RolePageReqVO reqVO) {
        return CodeResult.ok(roleService.getRolePage(reqVO));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取角色精简信息列表", description = "只包含被开启的角色，主要用于前端的下拉选项")
    public CodeResult<List<RoleSimpleRespVO>> getSimpleRoleList() {
        // 获得角色列表，只要开启状态的
        List<RoleDO> list = roleService.getRoleListByStatus(singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(RoleDO::getSort));
        return CodeResult.ok(RoleConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/export")
//    @OperateLog(type = EXPORT)
//    @PreAuthorize("@ss.hasPermission('system:role:export')")
    public void export(HttpServletResponse response, @Validated RoleExportReqVO reqVO) throws IOException {
        List<RoleDO> list = roleService.getRoleList(reqVO);
        List<RoleExcelVO> data = RoleConvert.INSTANCE.convertList03(list);
        // 输出
        ExcelUtils.write(response, "角色数据.xls", "角色列表", RoleExcelVO.class, data);
    }

}
