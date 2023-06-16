package com.rc.cloud.ops.auth.controller;

import cn.hutool.json.JSONObject;
import com.rc.cloud.common.core.constant.AuthConstants;
import com.rc.cloud.common.core.util.RequestUtils;
import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.common.redis.util.RedisUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@Api(tags = "注销")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class LogoutController {

    @PostMapping("/logout")
    public CodeResult logout() {
        JSONObject jsonObject = RequestUtils.getJwtPayload();
        String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
        long exp = jsonObject.getLong(AuthConstants.JWT_EXP);

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) {
            return CodeResult.ok();
        }
        RedisUtils.setCacheObject(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return CodeResult.ok();
    }

}
