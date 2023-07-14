package com.rc.cloud.app.system.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:51
 * @description 客户端更新 Request VO
 */
@Schema(description = "管理后台 - 客户端更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OauthClientDetailsUpdateReqVO extends OauthClientDetailsBaseVO {
    /**
     * ID
     */
    @NotBlank(message = "id 不能为空")
    @Schema(description = "id")
    private String id;
}
