package com.rc.cloud.app.distributor.appearance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WJF
 * @create 2023-06-26 15:06
 * @description TODO
 */
@Data
public class AppDistributorDetailBaseVO {
    @Schema(description = "id")
    private String id;

    @Schema(description = "经销商id")
    private String distributorId;

    @Schema(description = "经销商详细信息")
    private String distributorDetail;
}
