package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorLevelConvert;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.application.service.DistributorLevelService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorLevelRespVO;
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

@Tag(name = "用户 APP - 经销商客户等级")
@RestController
@RequestMapping("/distributor/level")
@Validated
public class AppDistributorLevelController {

    @Resource
    private DistributorLevelService levelService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商客户等级")

    public CodeResult<Long> createLevel(@Valid @RequestBody AppDistributorLevelCreateReqVO createReqVO) {
        return CodeResult.ok(levelService.createLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商客户等级")

    public CodeResult<Boolean> updateLevel(@Valid @RequestBody AppDistributorLevelUpdateReqVO updateReqVO) {
        levelService.updateLevel(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商客户等级")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteLevel(@RequestParam("id") Long id) {
        levelService.deleteLevel(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商客户等级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<AppDistributorLevelRespVO> getLevel(@RequestParam("id") Long id) {
        DistributorLevelPO level = levelService.getLevel(id);
        return CodeResult.ok(DistributorLevelConvert.INSTANCE.convert(level));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商客户等级列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<AppDistributorLevelRespVO>> getLevelList(@RequestParam("ids") Collection<Long> ids) {
        List<DistributorLevelPO> list = levelService.getLevelList(ids);
        return CodeResult.ok(DistributorLevelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商客户等级分页")

    public CodeResult<PageResult<AppDistributorLevelRespVO>> getLevelPage(@Valid AppDistributorLevelPageReqVO pageVO) {
        PageResult<DistributorLevelPO> pageResult = levelService.getLevelPage(pageVO);
        return CodeResult.ok(DistributorLevelConvert.INSTANCE.convertPage(pageResult));
    }

}
