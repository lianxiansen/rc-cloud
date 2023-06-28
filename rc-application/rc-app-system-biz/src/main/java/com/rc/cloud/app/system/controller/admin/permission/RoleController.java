package com.rc.cloud.app.system.controller.admin.permission;

import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.convert.permission.RoleConvert;
import com.rc.cloud.app.system.service.permission.RoleService;
import com.rc.cloud.app.system.vo.permission.role.*;
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
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.singleton;

@Tag(name = "管理后台 - 角色")
@RestController
@RequestMapping("/sys/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/create")
    @Operation(summary = "创建角色")
    @PreAuthorize("@pms.hasPermission('sys:role:create')")
    public CodeResult<Long> createRole(@Valid @RequestBody RoleCreateReqVO reqVO) {
        return CodeResult.ok(roleService.createRole(reqVO, null));
    }

    @PutMapping("/update")
    @Operation(summary = "修改角色")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO reqVO) {
        roleService.updateRole(reqVO);
        return CodeResult.ok(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改角色状态")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    public CodeResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO reqVO) {
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return CodeResult.ok(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    public CodeResult<Boolean> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获得角色信息")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<RoleRespVO> getRole(@PathVariable("id") Long id) {
        SysRoleDO role = roleService.getRole(id);
        return CodeResult.ok(RoleConvert.INSTANCE.convert(role));
    }

    @GetMapping("/page")
    @Operation(summary = "获得角色分页")
    @PreAuthorize("@pms.hasPermission('sys:role:query')")
    public CodeResult<PageResult<SysRoleDO>> getRolePage(RolePageReqVO reqVO) {
        return CodeResult.ok(roleService.getRolePage(reqVO));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取角色精简信息列表", description = "只包含被开启的角色，主要用于前端的下拉选项")
    public CodeResult<List<RoleSimpleRespVO>> getSimpleRoleList() {
        // 获得角色列表，只要开启状态的
        List<SysRoleDO> list = roleService.getRoleListByStatus(singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysRoleDO::getSort));
        return CodeResult.ok(RoleConvert.INSTANCE.convertList02(list));
    }

//    @GetMapping("/export")
////    @OperateLog(type = EXPORT)
//    @PreAuthorize("@pms.hasPermission('sys:role:export')")
//    public void export(HttpServletResponse response, @Validated RoleExportReqVO reqVO) throws IOException {
//        List<SysRoleDO> list = roleService.getRoleList(reqVO);
//        List<RoleExcelVO> data = RoleConvert.INSTANCE.convertList03(list);
//        // 输出
//        ExcelUtils.write(response, "角色数据.xls", "角色列表", RoleExcelVO.class, data);
//    }

}
