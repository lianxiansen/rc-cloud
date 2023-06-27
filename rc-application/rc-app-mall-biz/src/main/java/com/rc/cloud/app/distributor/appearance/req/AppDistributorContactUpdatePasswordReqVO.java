package com.rc.cloud.app.distributor.appearance.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-06-27 14:39
 * @description TODO
 */
@Data
public class AppDistributorContactUpdatePasswordReqVO {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "密码")
    private String password;
}
