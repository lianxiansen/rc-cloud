package com.rc.cloud.app.operate.appearance.admin.v1;

import com.rc.cloud.api.product.service.AuthenticationService;
import com.rc.cloud.common.core.web.CodeResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author liandy
 * @Date 2023/8/10 09:14
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @DubboReference
    private AuthenticationService authenticationService;
    @GetMapping("index")
    public CodeResult<String> index(String var1, String var2) {
        return CodeResult.ok( authenticationService.validateCaptcha(var1,var2));
    }
}
