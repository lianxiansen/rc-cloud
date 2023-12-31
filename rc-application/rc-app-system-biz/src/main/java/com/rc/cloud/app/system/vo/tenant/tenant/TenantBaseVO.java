package com.rc.cloud.app.system.vo.tenant.tenant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 租户 Base VO，提供给添加、修改、详细的子 VO 使用
 */
@Data
public class TenantBaseVO {

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotEmpty(message = "租户名不能为空")
    private String name;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "联系人不能为空")
    private String contactName;

    @Schema(description = "联系手机", example = "15601691300")
    private String contactMobile;

    @Schema(description = "租户状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "租户状态")
    private Integer status;

    @Schema(description = "绑定域名", example = "https://www.iocoder.cn")
    private String domain;

    @Schema(description = "租户套餐编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "租户套餐编号不能为空")
    private String packageId;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "过期时间不能为空")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireTime;

    @Schema(description = "账号数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "账号数量不能为空")
    private Integer accountCount;

}
