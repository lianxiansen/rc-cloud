package com.rc.cloud.app.distributor.appearance.facade;

import com.rc.cloud.app.distributor.appearance.resp.DistributorContactRespVO;
import com.rc.cloud.app.distributor.application.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getById/{id}")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public CodeResult<DistributorContactRespVO> getById(@RequestParam("id") String id) {
        DistributorContactPO user = contactService.getById(id);
        DistributorContactRespVO respVOS = DistributorContactConvert.INSTANCE.convert(user);
        return CodeResult.ok(respVOS);
    }

    @GetMapping("/getByMobile/{mobile}")
    @Operation(summary = "获得经销商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CodeResult<DistributorContactRespVO> getByMobile(@RequestParam("mobile") String mobile) {
        DistributorContactPO user = contactService.getByMobile(mobile);
        DistributorContactRespVO respVOS = DistributorContactConvert.INSTANCE.convert(user);
        return CodeResult.ok(respVOS);
    }
}
