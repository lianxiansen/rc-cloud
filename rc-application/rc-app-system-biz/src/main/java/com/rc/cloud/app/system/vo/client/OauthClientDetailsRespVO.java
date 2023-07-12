/**
 * @author rc@hqf
 * date 2023-07-11 16:50
 */
package com.rc.cloud.app.system.vo.client;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
