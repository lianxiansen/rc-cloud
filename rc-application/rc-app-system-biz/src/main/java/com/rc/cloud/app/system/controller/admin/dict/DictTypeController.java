package com.rc.cloud.app.system.controller.admin.dict;

import com.rc.cloud.app.system.convert.dict.DictTypeConvert;
import com.rc.cloud.app.system.model.dict.SysDictTypeDO;
import com.rc.cloud.app.system.service.dict.DictTypeService;
import com.rc.cloud.app.system.vo.dict.SysDictVO;
import com.rc.cloud.app.system.vo.dict.type.*;
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
 * @description 字典类型管理
 */
@Tag(name = "管理后台 - 字典类型")
@RestController
@RequestMapping("/sys/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    /**
     * 创建字典类型
     *
     * @param reqVO 字典类型信息
     * @return 字典类型编号
     */
    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
    @PreAuthorize("@pms.hasPermission('sys:dict:create')")
    public CodeResult<String> createDictType(@Valid @RequestBody DictTypeCreateReqVO reqVO) {
        String dictTypeId = dictTypeService.createDictType(reqVO);
        return CodeResult.ok(dictTypeId);
    }

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改字典类型")
    @PreAuthorize("@pms.hasPermission('sys:dict:update')")
    public CodeResult<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateReqVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return CodeResult.ok(true);
    }

    /**
     * 删除字典类型
     *
     * @param idList 字典类型编号列表
     * @return 是否成功
     */
    @DeleteMapping()
    @Operation(summary = "删除字典类型")
    @Parameter(name = "idList", description = "编号列表", required = true, example = "[1024,1025]")
    @PreAuthorize("@pms.hasPermission('sys:dict:delete')")
    public CodeResult<Boolean> deleteDictType(@RequestBody List<String> idList) {
        dictTypeService.deleteDictTypes(idList);
        return CodeResult.ok(true);
    }

    /**
     * 获得字典类型的分页列表
     *
     * @param reqVO 查询条件
     * @return 字典类型分页列表
     */
    @Operation(summary = "/获得字典类型的分页列表")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
    public CodeResult<PageResult<DictTypeRespVO>> pageDictTypes(@Valid DictTypePageReqVO reqVO) {
        return CodeResult.ok(DictTypeConvert.INSTANCE.convertPage(dictTypeService.getDictTypePage(reqVO)));
    }

    /**
     * 获得字典类型详细
     *
     * @param id 字典类型编号
     * @return 字典类型详细
     */
    @Operation(summary = "/查询字典类型详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
    public CodeResult<DictTypeRespVO> getDictType(@PathVariable("id") String id) {
        return CodeResult.ok(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictTypeById(id)));
    }

    /**
     * 获得全部字典类型列表
     *
     * @return 全部字典类型列表
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典类型列表", description = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public CodeResult<List<DictTypeSimpleRespVO>> getSimpleDictTypeList() {
        List<SysDictTypeDO> list = dictTypeService.getDictTypeList();
        return CodeResult.ok(DictTypeConvert.INSTANCE.convertList(list));
    }

    /**
     * 获得全部字典类型列表
     *
     * @return 全部字典类型列表
     */
    @GetMapping("/dict/all")
    @Operation(summary = "全部字典数据")
    public CodeResult<List<SysDictVO>> all() {
        List<SysDictVO> dictList = dictTypeService.getDictList();
        return CodeResult.ok(dictList);
    }

//    @Operation(summary = "导出数据类型")
//    @GetMapping("/export")
//    @PreAuthorize("@pms.hasPermission('sys:dict:query')")
////    @OperateLog(type = EXPORT)
//    public void export(HttpServletResponse response, @Valid DictTypeExportReqVO reqVO) throws IOException {
//        List<SysDictTypeDO> list = dictTypeService.getDictTypeList(reqVO);
//        List<DictTypeExcelVO> data = DictTypeConvert.INSTANCE.convertList02(list);
//        // 输出
//        ExcelUtils.write(response, "字典类型.xls", "类型列表", DictTypeExcelVO.class, data);
//    }

}
