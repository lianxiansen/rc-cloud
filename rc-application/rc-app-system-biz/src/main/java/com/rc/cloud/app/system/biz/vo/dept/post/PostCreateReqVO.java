package com.rc.cloud.app.system.biz.vo.dept.post;

import com.rc.cloud.app.system.biz.vo.dept.post.PostBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 岗位创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostCreateReqVO extends PostBaseVO {
}
