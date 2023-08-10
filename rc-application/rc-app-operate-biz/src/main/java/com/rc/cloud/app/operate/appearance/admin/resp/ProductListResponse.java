package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.common.core.pojo.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品列表")
public class ProductListResponse {

    @Schema(description = "产品id")
    private String id;
    @Schema(description = "产品名称")
    private String name;

    @Schema(description = "产品图片")
    private String listImage;

    @Schema(description = "产品货号")
    private String spuCode;

    @Schema(description = "产品分类名称")
    private String categoryName;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "是否新品")
    private boolean newFlag;

    @Schema(description = "是否爆品")
    private boolean explosivesFlag;

    @Schema(description = "爆品图片")
    private String explosivesImage;

    @Schema(description = "是否推荐")
    private boolean recommendFlag;

    @Schema(description = "是否公共")
    private boolean publicFlag;

    @Schema(description = "状态")
    private int onShelfStatus;

    @Schema(description = "创建时间")
    private String createTime;


    public static ProductListResponse from(ProductBO productBO){
        return ProductConvert.convert2ProductList(productBO);
    }

    public static PageResult<ProductListResponse> from(PageResult<ProductBO> oriPage){
        List<ProductListResponse> productList=new ArrayList<>();
        oriPage.getList().forEach(item->{
            productList.add(from(item));
        });
        PageResult<ProductListResponse> result=new PageResult<>();
        result.setTotal(oriPage.getTotal());
        result.setList(productList);
        return result;
    }

}
