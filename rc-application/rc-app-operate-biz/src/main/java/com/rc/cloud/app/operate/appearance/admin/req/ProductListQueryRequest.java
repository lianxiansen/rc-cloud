package com.rc.cloud.app.operate.appearance.admin.req;


import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "产品列表请求")
public class ProductListQueryRequest extends PageParam {

    @Schema(description = "搜索关键字：商品名称")
    private String name;

    @Schema(description = "搜索关键字：商品货号")
    private String spuCode;

    @Schema(description = "搜索关键字：商品分类")
    private String category;



}
