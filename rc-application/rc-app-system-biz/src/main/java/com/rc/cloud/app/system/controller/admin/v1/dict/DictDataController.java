package com.rc.cloud.app.system.controller.admin.v1.dict;

import com.rc.cloud.app.system.convert.dict.DictDataConvert;
import com.rc.cloud.app.system.model.dict.SysDictDataPO;
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

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 字典数据管理
 */
@Tag(name = "管理后台 - 字典数据")
@RestController
@RequestMapping("/admin/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    /**
     * 创建字典数据
     *
     * @param reqVO 字典数据信息
     * @return 字典数据编号
     */
    @PostMapping("/create")
    @Operation(summary = "新增字典数据")
    @PreAuthorize("@pms.hasPermission('sys:dict:create')")
    public CodeResult<String> createDictData(@Valid @RequestBody DictDataCreateReqVO reqVO) {
        String dictDataId = dictDataService.createDictData(reqVO);
        return CodeResult.ok(dictDataId);
    }

    /**
     * 更新字典数据
     *
     * @param reqVO 字典数据信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改字典数据")
    @PreAuthorize("@pms.hasPermission('sys:dict:update')")
    public CodeResult<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateReqVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 批量删除字典数据
     *
     * @param idList 字典数据编号列表
     * @return 是否成功
     */
    @DeleteMapping()
    @Operation(summary = "删除字典数据")
    @Parameter(name = "idList", description = "编号列表", required = true, example = "[1024,1025]")
    @PreAuthorize("@pms.hasPermission('sys:dict:delete')")
    public CodeResult<Boolean> deleteDictData(@RequestBody List<String> idList) {
        dictDataService.deleteDictDatas(idList);
        return CodeResult.ok(true);
    }

    /**
     * 获得精简字典数据列表
     *
     * @return 字典数据列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典数据列表", description = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public CodeResult<List<DictDataSimpleRespVO>> getSimpleDictDataList() {
        List<SysDictDataPO> list = dictDataService.getDictDataList();
        return CodeResult.ok(DictDataConvert.INSTANCE.convertList(list));
    }

    /**
     * 分页获得字典数据列表
     *
     * @param reqVO 查询条件
     * @return 字典数据列表
     */
    @GetMapping("/page")
    @Operation(summary = "/获得字典类型的分页列表")
    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
    public CodeResult<PageResult<DictDataRespVO>> getDictTypePage(@Valid DictDataPageReqVO reqVO) {
        return CodeResult.ok(DictDataConvert.INSTANCE.convertPage(dictDataService.getDictDataPage(reqVO)));
    }

    /**
     * 获得字典数据详细
     *
     * @param id 字典数据编号
     * @return 字典数据详细
     */
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
