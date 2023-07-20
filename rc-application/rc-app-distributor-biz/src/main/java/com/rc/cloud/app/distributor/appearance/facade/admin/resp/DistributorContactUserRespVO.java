package com.rc.cloud.app.distributor.appearance.facade.admin.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-11 10:38
 * @description TODO
 */

@Data
public class DistributorContactUserRespVO {
    @Schema(description = "联系人id")
    private String id;
    @Schema(description = "联系人名称")
    private String name;
    @Schema(description = "联系人手机")
    private String mobile;
    @Schema(description = "密码")
    private String password;
}
