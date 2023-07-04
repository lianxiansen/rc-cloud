package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.appearance.req.*;
import com.rc.cloud.app.distributor.appearance.resp.DistributorDetailRespVO;
import com.rc.cloud.app.distributor.appearance.vo.DistributorContactBaseVO;
import com.rc.cloud.app.distributor.application.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.application.convert.DistributorConvert;
import com.rc.cloud.app.distributor.application.convert.DistributorDetailConvert;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorExcelVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorRespVO;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
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
public class DistributorController {

    @Resource
    private DistributorService service;

    @Resource
    private DistributorContactService contactService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商")

    public CodeResult<Long> create(@Valid @RequestBody DistributorCreateReqVO createReqVO) {
        return CodeResult.ok(service.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商")
    public CodeResult<Boolean> update(@Valid @RequestBody DistributorUpdateReqVO updateReqVO) {
        service.update(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> delete(@RequestParam("id") Long id) {
        service.delete(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorRespVO> get(@RequestParam("id") Long id) {
        DistributorPO distributorPO = service.get(id);
        return CodeResult.ok(DistributorConvert.INSTANCE.convert(distributorPO));
    }

    @GetMapping("/getDetail")
    @Operation(summary = "获得经销商详细信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorDetailRespVO> getDetail(@RequestParam("id") Long id) {
        DistributorDetailPO distributorDetailPO = service.getDetail(id);
        return CodeResult.ok(DistributorDetailConvert.INSTANCE.convert(distributorDetailPO));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<DistributorRespVO>> getList(@RequestParam("ids") Collection<Long> ids) {
        List<DistributorPO> list = service.getList(ids);
        return CodeResult.ok(DistributorConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商分页")

    public CodeResult<PageResult<DistributorRespVO>> getPage(@Valid DistributorPageReqVO pageVO) {
        PageResult<DistributorPO> pageResult = service.getPage(pageVO);
        return CodeResult.ok(DistributorConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出经销商 Excel")
    public void exportExcel(@Valid DistributorExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DistributorPO> list = service.getList(exportReqVO);
        // 导出 Excel
        List<DistributorExcelVO> datas = DistributorConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "经销商.xls", "数据", DistributorExcelVO.class, datas);
    }

    @PostMapping("/updateContactPassword")
    @Operation(summary = "更新联系人密码")
    public CodeResult<Boolean> updateContactPassword(@Valid @RequestBody DistributorContactUpdatePasswordReqVO updatePasswordReqVO) {
        contactService.updatePassword(updatePasswordReqVO);
        return CodeResult.ok(true);
    }

    @PostMapping("/resetContactPassword")
    @Operation(summary = "重置联系人密码")
    public CodeResult<Boolean> resetContactPassword(@RequestParam("id") Long id) {
        contactService.resetPassword(id);
        return CodeResult.ok(true);
    }

    @PostMapping("/getContacts")
    @Operation(summary = "获取经销商联系人")
    public CodeResult<List<DistributorContactBaseVO>> getContacts(@RequestParam("id") Long id) {
        return CodeResult.ok(DistributorContactConvert.INSTANCE.convertList3(contactService.getByDistributorId(id)));
    }
}