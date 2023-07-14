package com.rc.cloud.app.system.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:50
 * @description 客户端 Response VO
 */
@Schema(description = "管理后台 - 客户端 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OauthClientDetailsRespVO extends OauthClientDetailsBaseVO {

    /**
     * ID
     */
    @Schema(description = "id")
    private String id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
