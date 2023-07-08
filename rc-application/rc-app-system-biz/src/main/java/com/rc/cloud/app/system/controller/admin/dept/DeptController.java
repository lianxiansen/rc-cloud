package com.rc.cloud.app.system.controller.admin.dept;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.rc.cloud.app.system.model.dept.SysDeptDO;
import com.rc.cloud.app.system.convert.dept.DeptConvert;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.vo.dept.dept.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import com.sun.org.apache.xerces.internal.dom.ParentNode;
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


@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/sys/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @PostMapping("create")
    @Operation(summary = "创建部门")
    @PreAuthorize("@pms.hasPermission('sys:dept:create')")
    public CodeResult<String> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        String deptId = deptService.createDept(reqVO);
        return CodeResult.ok(deptId);
    }

    @PutMapping("update")
    @Operation(summary = "更新部门")
    @PreAuthorize("@pms.hasPermission('sys:dept:update')")
    public CodeResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping()
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:dept:delete')")
    public CodeResult<Boolean> deleteDept(@RequestParam("id") String id) {
        deptService.deleteDept(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取部门列表")
    @PreAuthorize("@pms.hasPermission('sys:dept:query')")
    public CodeResult<List<DeptRespVO>> getDeptList(DeptListReqVO reqVO) {
        List<SysDeptDO> list = deptService.getDeptList(reqVO);
        list.sort(Comparator.comparing(SysDeptDO::getSort));
        List<DeptRespVO> deptRespVOS = DeptConvert.INSTANCE.convertList(list);
        return CodeResult.ok(TreeUtil.build(deptRespVOS));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取部门精简信息列表", description = "只包含被开启的部门，主要用于前端的下拉选项")
    public CodeResult<List<DeptSimpleRespVO>> getSimpleDeptList() {
        // 获得部门列表，只要开启状态的
        DeptListReqVO reqVO = new DeptListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SysDeptDO> list = deptService.getDeptList(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysDeptDO::getSort));
        return CodeResult.ok(DeptConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获得部门信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:dept:query')")
    public CodeResult<DeptRespVO> getDept(@PathVariable("id") String id) {
        DeptRespVO deptRespVO = DeptConvert.INSTANCE.convert(deptService.getDept(id));
        String parentId = deptRespVO.getParentId();
        if (parentId == null || StringUtils.isEmpty(parentId) || "0".equals(parentId)) {
            deptRespVO.setParentName(null);
            return CodeResult.ok(deptRespVO);
        }
        SysDeptDO parent = deptService.getDept(parentId);
        deptRespVO.setParentName(parent == null ? null : parent.getName());
        return CodeResult.ok(deptRespVO);
    }
}
