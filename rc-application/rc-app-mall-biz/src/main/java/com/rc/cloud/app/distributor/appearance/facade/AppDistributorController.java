package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.application.convert.DistributorConvert;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorExcelVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorRespVO;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.excel.util.ExcelUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

@Tag(name = "用户 APP - 经销商")
@RestController
@RequestMapping("/distributor/")
@Validated
public class AppDistributorController {

    @Resource
    private DistributorService Service;

    @PostMapping("/create")
    @Operation(summary = "创建经销商")

    public CodeResult<Integer> create(@Valid @RequestBody AppDistributorCreateReqVO createReqVO) {
        return CodeResult.ok(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商")

    public CodeResult<Boolean> update(@Valid @RequestBody AppDistributorUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> delete(@RequestParam("id") Integer id) {
        Service.delete(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<AppDistributorRespVO> get(@RequestParam("id") Integer id) {
        DistributorDO distributorDO= Service.get(id);
        return CodeResult.ok(DistributorConvert.INSTANCE.convert(distributorDO));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<AppDistributorRespVO>> getList(@RequestParam("ids") Collection<Integer> ids) {
        List<DistributorDO> list = Service.getList(ids);
        return CodeResult.ok(DistributorConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商分页")

    public CodeResult<PageResult<AppDistributorRespVO>> getPage(@Valid AppDistributorPageReqVO pageVO) {
        PageResult<DistributorDO> pageResult = Service.getPage(pageVO);
        return CodeResult.ok(DistributorConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出经销商 Excel")
    public void exportExcel(@Valid AppDistributorExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DistributorDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<AppDistributorExcelVO> datas = DistributorConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "经销商.xls", "数据", AppDistributorExcelVO.class, datas);
    }

}
