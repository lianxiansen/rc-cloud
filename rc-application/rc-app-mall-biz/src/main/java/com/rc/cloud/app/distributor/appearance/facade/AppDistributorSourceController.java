package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.application.convert.DistributorSourceConvert;
import com.rc.cloud.app.distributor.application.service.DistributorSourceService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorSourceRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourceDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import java.util.*;

@Tag(name = "用户 APP - 经销商来源")
@RestController
@RequestMapping("/distributor/source")
@Validated
public class AppDistributorSourceController {

    @Resource
    private DistributorSourceService sourceService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商来源")

    public CodeResult<Long> createSource(@Valid @RequestBody AppDistributorSourceCreateReqVO createReqVO) {
        return CodeResult.ok(sourceService.createSource(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商来源")

    public CodeResult<Boolean> updateSource(@Valid @RequestBody AppDistributorSourceUpdateReqVO updateReqVO) {
        sourceService.updateSource(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商来源")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteSource(@RequestParam("id") Long id) {
        sourceService.deleteSource(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商来源")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<AppDistributorSourceRespVO> getSource(@RequestParam("id") Long id) {
        DistributorSourceDO source = sourceService.getSource(id);
        return CodeResult.ok(DistributorSourceConvert.INSTANCE.convert(source));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商来源列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<AppDistributorSourceRespVO>> getSourceList(@RequestParam("ids") Collection<Long> ids) {
        List<DistributorSourceDO> list = sourceService.getSourceList(ids);
        return CodeResult.ok(DistributorSourceConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商来源分页")

    public CodeResult<PageResult<AppDistributorSourceRespVO>> getSourcePage(@Valid AppDistributorSourcePageReqVO pageVO) {
        PageResult<DistributorSourceDO> pageResult = sourceService.getSourcePage(pageVO);
        return CodeResult.ok(DistributorSourceConvert.INSTANCE.convertPage(pageResult));
    }

}
