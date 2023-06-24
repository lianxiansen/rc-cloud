package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.application.convert.DistributorChannelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorChannelRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
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


@Tag(name = "用户 APP - 经销商渠道")
@RestController
@RequestMapping("/distributor/channel")
@Validated
public class AppDistributorChannelController {

    @Resource
    private DistributorChannelService channelService;

    @PostMapping("/create")
    @Operation(summary = "创建经销商渠道")

    public CodeResult<Integer> createChannel(@Valid @RequestBody AppDistributorChannelCreateReqVO createReqVO) {
        return CodeResult.ok(channelService.createChannel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商渠道")

    public CodeResult<Boolean> updateChannel(@Valid @RequestBody AppDistributorChannelUpdateReqVO updateReqVO) {
        channelService.updateChannel(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商渠道")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteChannel(@RequestParam("id") Integer id) {
        channelService.deleteChannel(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商渠道")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<AppDistributorChannelRespVO> getChannel(@RequestParam("id") Integer id) {
        DistributorChannelDO channel = channelService.getChannel(id);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convert(channel));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商渠道列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<AppDistributorChannelRespVO>> getChannelList(@RequestParam("ids") Collection<Integer> ids) {
        List<DistributorChannelDO> list = channelService.getChannelList(ids);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商渠道分页")

    public CodeResult<PageResult<AppDistributorChannelRespVO>> getChannelPage(@Valid AppDistributorChannelPageReqVO pageVO) {
        PageResult<DistributorChannelDO> pageResult = channelService.getChannelPage(pageVO);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convertPage(pageResult));
    }
}
