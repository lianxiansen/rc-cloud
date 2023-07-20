package com.rc.cloud.app.distributor.appearance.facade.admin.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-06-27 14:39
 * @description TODO
 */
@Data
public class DistributorContactUpdatePasswordReqVO {
    @Schema(description = "id")
    private String id;
    @Schema(description = "密码")
    private String password;
}
