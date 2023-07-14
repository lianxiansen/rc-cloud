package com.rc.cloud.app.system.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:50
 * @description 客户端创建 Request VO
 */
@Schema(description = "管理后台 - 客户端创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OauthClientDetailsCreateReqVO extends OauthClientDetailsBaseVO {
}
