package com.rc.cloud.app.product.appearance.admin.v1;


import com.rc.cloud.app.dubbo.application.service.AuthenticationService;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "test")
@RestController
@RequestMapping("/admin/test")
@Validated
public class TestController {
    @DubboReference
    private AuthenticationService authenticationService;
    @PostMapping("index")
    @Operation(summary = "index")
    public CodeResult<String> index(String uuid, String captchaCode) {
        String result=authenticationService.validateCaptcha(uuid,captchaCode);
        return CodeResult.ok(result);
    }


}
