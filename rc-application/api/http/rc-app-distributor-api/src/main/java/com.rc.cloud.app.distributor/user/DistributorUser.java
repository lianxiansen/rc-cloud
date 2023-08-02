package com.rc.cloud.app.distributor.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-11 10:30
 * @description TODO
 */
@Data
public class DistributorUser {
    @Schema(description = "用户联系人")
    private String id;
    @Schema(description = "用户联系人")
    private String name;
    @Schema(description = "用户手机号")
    private String mobile;
    @Schema(description = "密码")
    private String password;
}
