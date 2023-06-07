package com.rc.cloud.app.system.biz.controller.admin.captcha;

import com.rc.cloud.app.system.biz.service.captcha.CaptchaService;
import com.rc.cloud.app.system.biz.vo.captcha.CaptchaVO;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 验证码
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 验证码")
@RestController("adminCaptchaController")
@RequestMapping("/sys/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("get")
    @Operation(summary = "验证码")
    public CodeResult<CaptchaVO> captcha() {
        CaptchaVO captchaVO = captchaService.generate();
        return CodeResult.ok(captchaVO);
    }
}
