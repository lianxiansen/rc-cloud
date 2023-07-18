package com.rc.cloud.app.distributor.appearance.facade.admin;

import com.rc.cloud.app.distributor.appearance.resp.DistributorContactRespVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorContactUserRespVO;
import com.rc.cloud.app.distributor.application.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author WJF
 * @create 2023-07-11 10:34
 * @description TODO
 */
@Tag(name = "用户 APP - 经销商")
@RestController
@RequestMapping("/distributor/user")
@Validated
public class DistributorUserController {
    @Resource
    private DistributorContactService contactService;

    @Inner
    @GetMapping("/getById/{id}")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorContactRespVO> getById(@PathVariable("id") String id) {
        DistributorContactPO user = contactService.getById(id);
        DistributorContactRespVO respVOS = DistributorContactConvert.INSTANCE.convert(user);
        return CodeResult.ok(respVOS);
    }

    @Inner
    @GetMapping("/getByMobile/{mobile}")
    @Operation(summary = "获得经销商")
    @Parameter(name = "mobile", description = "编号", required = true, example = "1024")
    public CodeResult<DistributorContactUserRespVO> getByMobile(@PathVariable("mobile") String mobile) {
        DistributorContactPO user = contactService.getByMobile(mobile);
        DistributorContactUserRespVO respVOS = DistributorContactConvert.INSTANCE.convert2(user);
        return CodeResult.ok(respVOS);
    }
}
