package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorReputationRespVO;
import com.rc.cloud.app.distributor.application.convert.DistributorReputationConvert;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationDO;
import com.rc.cloud.app.distributor.application.service.DistributorReputationService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationCreateReqVO;
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
public class AppDistributorReputationController {

    @Resource
    private DistributorReputationService reputationService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商客户信誉")

    public CodeResult<Integer> createReputation(@Valid @RequestBody AppDistributorReputationCreateReqVO createReqVO) {
        return CodeResult.ok(reputationService.createReputation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商客户信誉")

    public CodeResult<Boolean> updateReputation(@Valid @RequestBody AppDistributorReputationUpdateReqVO updateReqVO) {
        reputationService.updateReputation(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商客户信誉")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteReputation(@RequestParam("id") Integer id) {
        reputationService.deleteReputation(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商客户信誉")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<AppDistributorReputationRespVO> getReputation(@RequestParam("id") Integer id) {
        DistributorReputationDO reputation = reputationService.getReputation(id);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convert(reputation));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商客户信誉列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<AppDistributorReputationRespVO>> getReputationList(@RequestParam("ids") Collection<Integer> ids) {
        List<DistributorReputationDO> list = reputationService.getReputationList(ids);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商客户信誉分页")

    public CodeResult<PageResult<AppDistributorReputationRespVO>> getReputationPage(@Valid AppDistributorReputationPageReqVO pageVO) {
        PageResult<DistributorReputationDO> pageResult = reputationService.getReputationPage(pageVO);
        return CodeResult.ok(DistributorReputationConvert.INSTANCE.convertPage(pageResult));
    }
}
