package com.rc.cloud.app.system.vo.user.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 用户创建 Request VO
 */
@Schema(description = "管理后台 - 用户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateReqVO extends UserBaseVO {

    //CHECKSTYLE:OFF: checkstyle:magicnumber
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
