package com.rc.cloud.app.distributor.appearance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 经销商来源 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DistributorSourceBaseVO {

    @Schema(description = "获客方式", example = "赵六")
    private String name;

    @Schema(description = "说明")
    private String description;
}
