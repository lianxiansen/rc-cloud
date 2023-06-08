package com.rc.cloud.app.system.biz.controller.admin.oauth2;

import com.rc.cloud.app.system.biz.convert.auth.OAuth2ClientConvert;
import com.rc.cloud.app.system.biz.model.oauth2.OAuth2ClientDO;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2ClientService;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientCreateReqVO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientPageReqVO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientRespVO;
import com.rc.cloud.app.system.biz.vo.oauth2.client.OAuth2ClientUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台 - OAuth2 客户端")
@RestController
@RequestMapping("/sys/oauth2-client")
@Validated
public class OAuth2ClientController {

    @Resource
    private OAuth2ClientService oAuth2ClientService;

    @PostMapping("/create")
    @Operation(summary = "创建 OAuth2 客户端")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-client:create')")
    public CodeResult<Long> createOAuth2Client(@Valid @RequestBody OAuth2ClientCreateReqVO createReqVO) {
        return CodeResult.ok(oAuth2ClientService.createOAuth2Client(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新 OAuth2 客户端")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-client:update')")
    public CodeResult<Boolean> updateOAuth2Client(@Valid @RequestBody OAuth2ClientUpdateReqVO updateReqVO) {
        oAuth2ClientService.updateOAuth2Client(updateReqVO);
        return CodeResult.ok(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 OAuth2 客户端")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('system:oauth2-client:delete')")
    public CodeResult<Boolean> deleteOAuth2Client(@RequestParam("id") Long id) {
        oAuth2ClientService.deleteOAuth2Client(id);
        return CodeResult.ok(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得 OAuth2 客户端")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-client:query')")
    public CodeResult<OAuth2ClientRespVO> getOAuth2Client(@RequestParam("id") Long id) {
        OAuth2ClientDO oAuth2Client = oAuth2ClientService.getOAuth2Client(id);
        return CodeResult.ok(OAuth2ClientConvert.INSTANCE.convert(oAuth2Client));
    }

    @GetMapping("/page")
    @Operation(summary = "获得OAuth2 客户端分页")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-client:query')")
    public CodeResult<PageResult<OAuth2ClientRespVO>> getOAuth2ClientPage(@Valid OAuth2ClientPageReqVO pageVO) {
        PageResult<OAuth2ClientDO> pageResult = oAuth2ClientService.getOAuth2ClientPage(pageVO);
        return CodeResult.ok(OAuth2ClientConvert.INSTANCE.convertPage(pageResult));
    }

}
