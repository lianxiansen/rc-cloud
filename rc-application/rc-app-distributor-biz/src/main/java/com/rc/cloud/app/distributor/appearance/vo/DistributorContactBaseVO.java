package com.rc.cloud.app.distributor.appearance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-06-27 10:39
 * @description TODO
 */

@Data
public class DistributorContactBaseVO {

    @Schema(description = "联系人名称")
    private String name;
    @Schema(description = "联系人手机")
    private String mobile;

}
