package com.rc.cloud.common.core.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description="分页参数")
@Data
public class PageParam implements Serializable {

    public static final Integer PAGE_NO = 1;
    public static final Integer PAGE_SIZE = 10;
    public static final Integer MAX_PAGE_SIZE = 100;
    @Schema(description = "页码，从 1 开始", requiredMode = Schema.RequiredMode.REQUIRED,example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    @Schema(description = "每页条数，最大值为 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

    /**
     * 传入的排序字段必须是数据库的字段的骆驼峰形式
     * StrUtil.toUnderlineCase
     * 方法进行转换
     */
    @Schema(description = "按哪个字段排序", requiredMode = Schema.RequiredMode.REQUIRED,example = "id")
    private String order;

    @Schema(description = "升序还是降序", requiredMode = Schema.RequiredMode.REQUIRED,example = "true")
    private Boolean asc;
}
