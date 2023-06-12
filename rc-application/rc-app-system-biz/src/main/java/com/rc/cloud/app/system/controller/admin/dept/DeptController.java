package com.rc.cloud.app.system.controller.admin.dept;

import com.rc.cloud.app.system.convert.dept.DeptConvert;
import com.rc.cloud.app.system.model.dept.DeptDO;
import com.rc.cloud.app.system.service.dept.DeptService;
import com.rc.cloud.app.system.vo.dept.dept.*;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
//    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public CodeResult<Long> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        Long deptId = deptService.createDept(reqVO);
        return CodeResult.ok(deptId);
    }

    @PutMapping("update")
    @Operation(summary = "更新部门")
//    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CodeResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    public CodeResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取部门列表")
//    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CodeResult<List<DeptRespVO>> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> list = deptService.getDeptList(reqVO);
        list.sort(Comparator.comparing(DeptDO::getSort));
        return CodeResult.ok(DeptConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取部门精简信息列表", description = "只包含被开启的部门，主要用于前端的下拉选项")
    public CodeResult<List<DeptSimpleRespVO>> getSimpleDeptList() {
        // 获得部门列表，只要开启状态的
        DeptListReqVO reqVO = new DeptListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<DeptDO> list = deptService.getDeptList(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(DeptDO::getSort));
        return CodeResult.ok(DeptConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/get")
    @Operation(summary = "获得部门信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CodeResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        return CodeResult.ok(DeptConvert.INSTANCE.convert(deptService.getDept(id)));
    }

}