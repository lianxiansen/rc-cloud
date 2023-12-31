package com.rc.cloud.app.distributor.appearance.facade.admin;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.resp.DistributorReputationRespVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorReputationConvert;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorReputationCreateReqVO;
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

@Tag(name = "用户 APP - 经销商客户信誉")
@RestController
@RequestMapping("/distributor/reputation")
@Validated
public class DistributorReputationController {

    @Resource
    private DistributorReputationService reputationService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商客户信誉")

    public CodeResult<String> createReputation(@Valid @RequestBody DistributorReputationCreateReqVO createReqVO) {
        return CodeResult.ok(reputationService.createReputation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商客户信誉")

    public CodeResult<Boolean> updateReputation(@Valid @RequestBody DistributorReputationUpdateReqVO updateReqVO) {
        reputationService.updateReputation(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商客户信誉")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteReputation(@RequestParam("id") String id) {
        reputationService.deleteReputation(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商客户信誉")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorReputationRespVO> getReputation(@RequestParam("id") String id) {
        DistributorReputationPO reputation = reputationService.getReputation(id);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convert(reputation));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得所有经销商信誉")
    public CodeResult<List<DistributorReputationRespVO>> getAll() {
        List<DistributorReputationPO> poList = reputationService.getAll();
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convertList(poList));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商客户信誉列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<DistributorReputationRespVO>> getReputationList(@RequestParam("ids") Collection<String> ids) {
        List<DistributorReputationPO> list = reputationService.getReputationList(ids);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商客户信誉分页")

    public CodeResult<PageResult<DistributorReputationRespVO>> getReputationPage(@Valid DistributorReputationPageReqVO pageVO) {
        PageResult<DistributorReputationPO> pageResult = reputationService.getReputationPage(pageVO);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convertPage(pageResult));
    }
}
