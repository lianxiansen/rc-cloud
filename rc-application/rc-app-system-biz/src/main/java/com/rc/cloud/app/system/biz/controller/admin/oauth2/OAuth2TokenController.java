package com.rc.cloud.app.system.biz.controller.admin.oauth2;

import com.rc.cloud.app.system.biz.convert.auth.OAuth2TokenConvert;
import com.rc.cloud.app.system.biz.model.oauth2.OAuth2AccessTokenDO;
import com.rc.cloud.app.system.biz.service.auth.AdminAuthService;
import com.rc.cloud.app.system.biz.service.oauth2.OAuth2TokenService;
import com.rc.cloud.app.system.biz.vo.oauth2.token.OAuth2AccessTokenPageReqVO;
import com.rc.cloud.app.system.biz.vo.oauth2.token.OAuth2AccessTokenRespVO;
import com.rc.cloud.app.system.enums.login.LoginLogTypeEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台 - OAuth2.0 令牌")
@RestController
@RequestMapping("/system/oauth2-token")
public class OAuth2TokenController {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private AdminAuthService authService;

    @GetMapping("/page")
    @Operation(summary = "获得访问令牌分页", description = "只返回有效期内的")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-token:page')")
    public CodeResult<PageResult<OAuth2AccessTokenRespVO>> getAccessTokenPage(@Valid OAuth2AccessTokenPageReqVO reqVO) {
        PageResult<OAuth2AccessTokenDO> pageResult = oauth2TokenService.getAccessTokenPage(reqVO);
        return CodeResult.ok(OAuth2TokenConvert.INSTANCE.convert(pageResult));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
//    @PreAuthorize("@ss.hasPermission('system:oauth2-token:delete')")
    public CodeResult<Boolean> deleteAccessToken(@RequestParam("accessToken") String accessToken) {
        authService.logout(accessToken, LoginLogTypeEnum.LOGOUT_DELETE.getType());
        return CodeResult.ok(true);
    }

}
