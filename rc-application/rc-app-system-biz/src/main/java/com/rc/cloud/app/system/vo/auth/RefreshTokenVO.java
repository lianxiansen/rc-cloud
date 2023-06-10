/**
 * @author oliveoil
 * date 2023-06-09 16:19
 */
package com.rc.cloud.app.system.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenVO {

    @NotEmpty(message = "refreshToken 不能为空")
    private String refreshToken;
}
