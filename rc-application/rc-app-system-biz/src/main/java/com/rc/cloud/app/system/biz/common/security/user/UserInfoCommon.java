/**
 * @author oliveoil
 * date 2023-04-22 11:13
 */
package com.rc.cloud.app.system.biz.common.security.user;

import lombok.Data;

@Data
public class UserInfoCommon {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String realName;
    private String avatar;
    private Integer gender;
    private String email;
    private String mobile;
    private Long orgId;
    private Integer status;
    private Integer superAdmin;
}
