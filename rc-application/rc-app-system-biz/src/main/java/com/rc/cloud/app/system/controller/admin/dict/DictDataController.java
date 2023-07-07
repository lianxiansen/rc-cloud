package com.rc.cloud.app.system.controller.admin.dict;

import com.rc.cloud.app.system.model.dict.SysDictDataDO;
import com.rc.cloud.app.system.convert.dict.DictDataConvert;
import com.rc.cloud.app.system.service.dict.DictDataService;
import com.rc.cloud.app.system.vo.dict.data.*;
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

@Tag(name = "管理后台 - 字典数据")
@RestController
@RequestMapping("/sys/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
    @Operation(summary = "新增字典数据")
    @PreAuthorize("@pms.hasPermission('sys:dict:create')")
    public CodeResult<String> createDictData(@Valid @RequestBody DictDataCreateReqVO reqVO) {
        String dictDataId = dictDataService.createDictData(reqVO);
        return CodeResult.ok(dictDataId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典数据")
    @PreAuthorize("@pms.hasPermission('sys:dict:update')")
    public CodeResult<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateReqVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping()
    @Operation(summary = "删除字典数据")
    @Parameter(name = "idList", description = "编号列表", required = true, example = "[1024,1025]")
    @PreAuthorize("@pms.hasPermission('sys:dict:delete')")
    public CodeResult<Boolean> deleteDictData(@RequestBody List<String> idList) {
        dictDataService.deleteDictDatas(idList);
        return CodeResult.ok(true);
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典数据列表", description = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public CodeResult<List<DictDataSimpleRespVO>> getSimpleDictDataList() {
        List<SysDictDataDO> list = dictDataService.getDictDataList();
        return CodeResult.ok(DictDataConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "/获得字典类型的分页列表")
    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
    public CodeResult<PageResult<DictDataRespVO>> getDictTypePage(@Valid DictDataPageReqVO reqVO) {
        return CodeResult.ok(DictDataConvert.INSTANCE.convertPage(dictDataService.getDictDataPage(reqVO)));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "/查询字典数据详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
    public CodeResult<DictDataRespVO> getDictData(@PathVariable("id") String id) {
        return CodeResult.ok(DictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
    }

//    @GetMapping("/export")
//    @Operation(summary = "导出字典数据")
//    @PreAuthorize("@pms.hasPermission('sys:dict:export')")
////    @OperateLog(type = EXPORT)
//    public void export(HttpServletResponse response, @Valid DictDataExportReqVO reqVO) throws IOException {
//        List<SysDictDataDO> list = dictDataService.getDictDataList(reqVO);
//        List<DictDataExcelVO> data = DictDataConvert.INSTANCE.convertList02(list);
//        // 输出
//        ExcelUtils.write(response, "字典数据.xls", "数据列表", DictDataExcelVO.class, data);
//    }

}
