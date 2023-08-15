package com.rc.cloud.app.marketing.appearance.api.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName ComfirmOrderResp
 * @Author liandy
 * @Date 2023/7/29 15:41
 * @Description TODO
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@Schema(description = "下单response")
public class PlaceOrderResp {
    /**
     * 收货地址唯一标识
     */
    @Schema(description = "收货地址唯一标识")
    private String deliveryAddressId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String customerName;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String customerMobile;

    /**
     * 省
     */
    @Schema(description = "省编码")
    private String provinceCode;
    @Schema(description = "省")
    private String province;

    /**
     * 市
     */
    @Schema(description = "市编码")
    private String cityCode;
    @Schema(description = "市")
    private String city;

    /**
     * 区
     */
    @Schema(description = "区编码")
    private String districtCode;
    @Schema(description = "区")
    private String district;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String deliveryAddressDetail;


    /**
     * 确认订单唯一标识符
     */
    @Schema(description = "确认订单唯一标识符")
    private String comfirmOrderId;


    @Schema(description = "下单行列表")
    private List<PlaceOrderLineResp> placeOrderLines;

    /**
     * 交易方式
     */
    @Schema(description = "交易方式编码")
    private Integer tradeTypeCode;
    @Schema(description = "交易方式名称")
    private Integer tradeTypeName;
    @Schema(description = "交易方式列表")
    private List<TradeTypeResp> tradeTypes;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式编码")
    private Integer deliveryTypeCode;
    @Schema(description = "配送方式名称")
    private Integer deliveryTypeName;
    @Schema(description = "配送方式列表")
    private List<DeliveryTypeResp> deliveryTypeTypes;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String note;

    /**
     * 商品总价
     */
    @Schema(description = "商品总价")
    private String productAmount;
    /**
     * 商品种类数
     */
    @Schema(description = "商品种类数")
    private Integer productTypeQuantity;
    /**
     * 商品件数
     */
    @Schema(description = "商品件数")
    private Integer productQuantity;

    /**
     * 运费金额
     */
    @Schema(description = "运费金额")
    private String freightAmount;


    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private String payAmount;




    @Data
    @Accessors(chain = true)
    @Schema(description = "下单行response")
    public class PlaceOrderLineResp {

        /**
         * 商品唯一标识
         */
        @Schema(description = "商品唯一标识")
        private String productId;
        /**
         * 商品名称
         */
        @Schema(description = "商品名称")
        private String productName;
        /**
         * 商品图片
         */
        @Schema(description = "商品图片")
        private String productImage;
        /**
         * 商品货号
         */
        @Schema(description = "商品货号")
        private String productArticleNo;


        /**
         * 商品项属性名称
         */
        @Schema(description = "商品项属性名称")
        private String productAttribute;
        /**
         * 商品项单价
         */
        @Schema(description = "商品项单价")
        private String productPrice;
        /**
         * 商品数量
         */
        @Schema(description = "商品数量")
        protected Integer productQuality;

        /**
         * 商品金额
         */
        @Schema(description = "商品金额")
        protected String productAmount;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "交易方式response")
    public class TradeTypeResp {
        /**
         * 编码
         */
        @Schema(description = "编码")
        private  int code;
        /**
         * 名称
         */
        @Schema(description = "名称")
        private  String name;
        /**
         * 描述
         */
        @Schema(description = "描述")
        private  String description;

    }
    @Data
    @Accessors(chain = true)
    @Schema(description = "配送方式response")
    public class DeliveryTypeResp {

        /**
         * 关键字
         */
        @Schema(description = "编码")
        private final int code;
        /**
         * 名称
         */
        @Schema(description = "名称")
        private final String name;
        /**
         * 描述
         */
        @Schema(description = "描述")
        private final String description;


    }

}
