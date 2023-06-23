/**
 * @author oliveoil
 * date 2023-06-23 09:12
 */
package com.rc.cloud.ops.auth.common.dto;

import com.rc.cloud.app.system.api.user.dto.UserInfo;
import lombok.Data;

import java.util.List;

@Data
public class LoginReturnDTO {
    private String sub;
    private String clientId;
    private String iss;
    private String token_type;
    private String access_token;
    private String refresh_token;
    private String aud;
    private String license;
    private String nbf;
//    private UserInfo user_info;
    private List<String> scope;
    private Double exp;
    private Double expires_in;
    private Double iat;
    private String jti;
}
