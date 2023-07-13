package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.application.convert.DistributorChannelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorChannelRespVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
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
public class DistributorChannelController {

    /** @经销商渠道服务 */
    @Resource
    private DistributorChannelService channelService;

    /** @创建经销商渠道 */
    @PostMapping("/create")
    @Operation(summary = "创建经销商渠道")
    public CodeResult<String> createChannel(@Valid @RequestBody final DistributorChannelCreateReqVO createReqVO) {
        return CodeResult.ok(channelService.createChannel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新经销商渠道")

    public CodeResult<Boolean> updateChannel(@Valid @RequestBody DistributorChannelUpdateReqVO updateReqVO) {
        channelService.updateChannel(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除经销商渠道")
    @Parameter(name = "id", description = "编号", required = true)

    public CodeResult<Boolean> deleteChannel(@RequestParam("id") String id) {
        channelService.deleteChannel(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得经销商渠道")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorChannelRespVO> getChannel(@RequestParam("id") String id) {
        DistributorChannelPO channel = channelService.getChannel(id);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convert(channel));
    }

    @GetMapping("/list")
    @Operation(summary = "获得经销商渠道列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")

    public CodeResult<List<DistributorChannelRespVO>> getChannelList(@RequestParam("ids") Collection<String> ids) {
        List<DistributorChannelPO> list = channelService.getChannelList(ids);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得所有经销商渠道")
    public CodeResult<List<DistributorChannelRespVO>> getAll() {
        List<DistributorChannelPO> poList = channelService.getAll();
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convertList(poList));
    }

    @GetMapping("/page")
    @Operation(summary = "获得经销商渠道分页")

    public CodeResult<PageResult<DistributorChannelRespVO>> getChannelPage(@Valid DistributorChannelPageReqVO pageVO) {
        PageResult<DistributorChannelPO> pageResult = channelService.getChannelPage(pageVO);
        return CodeResult.ok(DistributorChannelConvert.INSTANCE.convertPage(pageResult));
    }
}
