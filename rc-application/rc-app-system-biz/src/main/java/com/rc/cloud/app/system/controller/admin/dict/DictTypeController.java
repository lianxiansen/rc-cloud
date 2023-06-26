package com.rc.cloud.app.system.controller.admin.dict;

import com.rc.cloud.app.system.api.dict.entity.SysDictTypeDO;
import com.rc.cloud.app.system.convert.dict.DictTypeConvert;
import com.rc.cloud.app.system.service.dict.DictTypeService;
import com.rc.cloud.app.system.vo.dict.SysDictVO;
import com.rc.cloud.app.system.vo.dict.type.*;
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
import java.util.List;


@Tag(name = "管理后台 - 字典类型")
@RestController
@RequestMapping("/sys/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
//    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CodeResult<Long> createDictType(@Valid @RequestBody DictTypeCreateReqVO reqVO) {
        Long dictTypeId = dictTypeService.createDictType(reqVO);
        return CodeResult.ok(dictTypeId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典类型")
//    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CodeResult<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateReqVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除字典类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CodeResult<Boolean> deleteDictType(@PathVariable Long id) {
        dictTypeService.deleteDictType(id);
        return CodeResult.ok(true);
    }

    @Operation(summary = "/获得字典类型的分页列表")
    @GetMapping("/page")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CodeResult<PageResult<DictTypeRespVO>> pageDictTypes(@Valid DictTypePageReqVO reqVO) {
        return CodeResult.ok(DictTypeConvert.INSTANCE.convertPage(dictTypeService.getDictTypePage(reqVO)));
    }

    @Operation(summary = "/查询字典类型详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @GetMapping(value = "/{id}")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CodeResult<DictTypeRespVO> getDictType(@PathVariable("id") Long id) {
        return CodeResult.ok(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictType(id)));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典类型列表", description = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public CodeResult<List<DictTypeSimpleRespVO>> getSimpleDictTypeList() {
        List<SysDictTypeDO> list = dictTypeService.getDictTypeList();
        return CodeResult.ok(DictTypeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/dict/all")
    @Operation(summary = "全部字典数据")
    public CodeResult<List<SysDictVO>> all() {
        List<SysDictVO> dictList = dictTypeService.getDictList();
        return CodeResult.ok(dictList);
    }

    @Operation(summary = "导出数据类型")
    @GetMapping("/export")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
//    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid DictTypeExportReqVO reqVO) throws IOException {
        List<SysDictTypeDO> list = dictTypeService.getDictTypeList(reqVO);
        List<DictTypeExcelVO> data = DictTypeConvert.INSTANCE.convertList02(list);
        // 输出
        ExcelUtils.write(response, "字典类型.xls", "类型列表", DictTypeExcelVO.class, data);
    }

}
