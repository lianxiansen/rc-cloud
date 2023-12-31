package com.rc.cloud.app.system.vo.dept.post;

import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rc@hqf
 * @date 2023-07-11 16:49
 * @description 岗位分页 Request VO
 */
@Schema(description = "管理后台 - 岗位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostPageReqVO extends PageParam {

    @Schema(description = "岗位编码,模糊匹配", example = "RC")
    private String code;

    @Schema(description = "岗位名称,模糊匹配", example = "芋道")
    private String name;

    @Schema(description = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

}
