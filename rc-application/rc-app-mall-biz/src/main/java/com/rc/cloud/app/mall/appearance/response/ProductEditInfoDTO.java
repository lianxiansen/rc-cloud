package com.rc.cloud.app.mall.appearance.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductEditInfoDTO {

    private Integer product_type;

    private String product_category_ids;

    private String product_category_names;

    private Integer brand_id;

    private String name;

    private String keywords;

    private String master_image;

    private List<String> albums;

    private BigDecimal price;

    private BigDecimal pop_amount_rate;

    private BigDecimal bran_perfor_rate;

    private Integer lowest_buy;

    private BigDecimal weight;

    private BigDecimal marking_price;

    private Integer wh_product_id;

    private Integer wh_id;

    private String wh_name;

    private Integer merchant_id;

    private String  merchant_name;

    private Integer copartner_id;

    private Boolean is_ali;

    private String ali_product_id;

    private String ali_shop_id;

    private String ali_shop_address;

    private List<ProductEditInfoSpecDTO> specs;

    private List<ProductEditInfoSkuDTO> skus;

    private Boolean is_choose_freight;

    private Integer freight_tid;

    private BigDecimal freight_amount;

    private Integer nosend_area_tid;

    private Boolean is_post_coupon;

    private Integer pc_full_piece;

    private String pc_area;

    private Boolean is_full_coupon;

    private BigDecimal fc_full_amount;

    private BigDecimal fc_reduce_amount;

    private Boolean is_fc_time_limit;

    private Boolean is_fc_doubling;

    private String fc_start;

    private String fc_end;

    private Boolean is_limit_buy;

    private Integer limit_buy_num;

    private Boolean is_forbid_refund;

    private Boolean is_delivery_refund;

    private String material_txt;

    private List<String> materials;

    private Boolean is_q_recommend;

    private String q_rcm_reason;

    private String video_url;

    private String video_image;

    private String deta;

    private Integer shelf;

    private Boolean is_recommend;

    private Integer multi_buy;

    private List<ProductEditInfoSimiliarGoodsDTO> sim_goods;

    private String product_sku;

    private String ticket_remark;

    private Integer ticket_type;
}
