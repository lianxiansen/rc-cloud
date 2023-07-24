package com.rc.cloud.app.system.controller.admin.v1.dept;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.rc.cloud.app.system.convert.dept.DeptConvert;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.vo.dept.dept.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.tree.TreeUtil;
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

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门管理
 */
@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/admin/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 创建部门
     *
     * @param reqVO 部门信息
     * @return 部门编号
     */
    @PostMapping("create")
    @Operation(summary = "创建部门")
    @PreAuthorize("@pms.hasPermission('sys:dept:create')")
    public CodeResult<DeptRespVO> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        String deptId = deptService.createDept(reqVO);
        return CodeResult.ok(DeptConvert.INSTANCE.convert(deptService.getDept(deptId)));
    }

    /**
     * 更新部门
     *
     * @param reqVO 部门信息
     * @return 是否成功
     */
    @PutMapping("update")
    @Operation(summary = "更新部门")
    @PreAuthorize("@pms.hasPermission('sys:dept:update')")
    public CodeResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return 是否成功
     */
    @DeleteMapping()
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:dept:delete')")
    public CodeResult<Boolean> deleteDept(@RequestParam("id") String id) {
        deptService.deleteDept(id);
        return CodeResult.ok(true);
    }

    /**
     * 获取部门列表
     *
     * @param reqVO DeptListReqVO
     * @return 部门列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取部门列表")
    @PreAuthorize("@pms.hasPermission('sys:dept:query')")
    public CodeResult<List<DeptRespVO>> getDeptList(DeptListReqVO reqVO) {
        List<SysDeptPO> list = deptService.getDeptList(reqVO);
        list.sort(Comparator.comparing(SysDeptPO::getSort));
        List<DeptRespVO> deptRespVOS = DeptConvert.INSTANCE.convertList(list);
        return CodeResult.ok(TreeUtil.build(deptRespVOS));
    }

    /**
     * 获取部门精简信息列表
     *
     * @return 部门精简信息列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获取部门精简信息列表", description = "只包含被开启的部门，主要用于前端的下拉选项")
    public CodeResult<List<DeptSimpleRespVO>> getSimpleDeptList() {
        // 获得部门列表，只要开启状态的
        DeptListReqVO reqVO = new DeptListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SysDeptPO> list = deptService.getDeptList(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SysDeptPO::getSort));
        return CodeResult.ok(TreeUtil.build(DeptConvert.INSTANCE.convertList02(list)));
    }

    /**
     * 通过id获得部门信息
     *
     * @param id 部门ID
     * @return 部门信息
     */
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
        SysDeptPO parent = deptService.getDept(parentId);
        deptRespVO.setParentName(parent == null ? null : parent.getName());
        return CodeResult.ok(deptRespVO);
    }
}
