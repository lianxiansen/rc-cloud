package com.rc.cloud.app.system.biz.vo.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户Token
 *
 * @author oliveoil
 */
@Data
@AllArgsConstructor
@Schema(description = "用户登录")
public class TokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "access_token")
    private String access_token;

    @Schema(description = "refresh_token")
    private String refresh_token;
}
