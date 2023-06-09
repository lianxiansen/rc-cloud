package com.rc.cloud.app.system.vo.auth;

import cn.hutool.core.util.StrUtil;
import com.rc.cloud.app.system.enums.social.SocialTypeEnum;
import com.rc.cloud.common.core.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "管理后台 - 账号密码登录 Request VO，如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReqVO {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "rouchuan")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    // ========== 图片验证码相关 ==========
    @Schema(description = "验证码唯一key", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "90561a88-ceb8-4369-bd99-c72d79036211")
    private String key;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1B3C5")
    private String captcha;
}
