package com.rc.cloud.app.system.vo.user.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 用户个人信息更新 Request VO
 */
@Schema(description = "管理后台 - 用户个人信息更新 Request VO")
@Data
public class UserProfileUpdateReqVO {

    @SuppressWarnings("checkstyle:magicnumber")
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "RC")
    @Size(max = 30, message = "用户昵称长度不能超过 30 个字符")
    private String nickname;

    @SuppressWarnings("checkstyle:magicnumber")
    @Schema(description = "用户邮箱", example = "RC@RC.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @Schema(description = "手机号码", example = "15601691377")
    private String mobile;

    @Schema(description = "用户性别-参见 SexEnum 枚举类", example = "1")
    private Integer sex;

}
