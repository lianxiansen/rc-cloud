package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@Schema(description = "产品详情")
public class ProductDetailResponse {


    @Schema(description = "产品id")
    private String id;

    @Schema(description = "产品名称")
    private String name;

    @Schema(description = "产品列表图片")
    private String listImage;

    @Schema(description = "产品描述")
    private String remark;

    @Schema(description = "产品标签")
    private String tag;

    @Schema(description = "产品货号")
    private String spuCode;

    @Schema(description = "品牌id")
    private String brandId;

    @Schema(description = "品牌名")
    private String brandName;

    @Schema(description = "商品分类1")
    private String firstCategory;

    @Schema(description = "商品分类2")
    private String secondCategory;

    @Schema(description = "商品分类3")
    private String thirdCategory;

    @Schema(description = "自定义分类id")
    private String customClassificationId;

    @Schema(description = "是否新品")
    private boolean newFlag;

    @Schema(description = "超级单品")
    private boolean explosivesFlag;

    @Schema(description = "自定义分类id")
    private String explosivesImage;

    @Schema(description = "是否公开")
    private boolean publicFlag;

    @Schema(description = "首页推荐")
    private boolean recommendFlag;

    @Schema(description = "上架状态")
    private int onshelfStatus;

    @Schema(description = "列表图片")
    private String productListImage;

    @Schema(description = "图片列表")
    private List<ProductImageResponse> imageList;

    @Schema(description = "材质")
    private String caiZhi;

    @Schema(description = "尺寸")
    private String chiCun;

    @Schema(description = "承重")
    private String chengZhong;

    @Schema(description = "条码")
    private String tiaoMa;

    @Schema(description = "size图片列表")
    private List<ProductImageResponse> sizeImageList;

    @Schema(description = "视频")
    private String videoUrl;

    @Schema(description = "视频主图")
    private String videoImg;

    @Schema(description = "安装视频")
    private String installVideoUrl;

    @Schema(description = "安装视频主图")
    private String installVideoImg;

    @Schema(description = "安装详情")
    private String installDetail;

    @Schema(description = "详情")
    private String detail;

    @Schema(description = "属性")
    private List<ProductAttributeResponse> attributes;

    @Schema(description = "skus")
    private List<ProductSkuDetailResponse> skus;

    public static ProductDetailResponse from(ProductBO productBO){
        ProductDetailResponse response= ProductConvert.convert2ProductDetail(productBO);
        return response;
    }

}
