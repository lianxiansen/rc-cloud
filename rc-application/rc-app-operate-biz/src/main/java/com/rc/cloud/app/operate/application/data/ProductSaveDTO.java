package com.rc.cloud.app.operate.application.data;

import com.rc.cloud.app.operate.appearance.request.*;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSaveDTO {
    @Min(value = 0, message = "id不支持(范围：不小于0)")
    private int id;

    @Max(value = 2, message = "product_type不支持(范围：0-2)")
    @Min(value = 0, message = "product_type不支持(范围：0-2)")
    private int product_type;

    @NotBlank(message = "产品类别不能为空")
    @Pattern(regexp = "^(,\\d+){2,3},$", message = "product_category_ids格式不正确(格式：如,1,2,3,)")
    private String product_category_ids;

    @NotBlank(message = "产品类别不能为空")
    @Pattern(regexp = "^(,[\\u4E00-\\u9FA50-9a-zA-Z\\/]+){2,3},$", message = "product_category_names格式不正确(格式：如,分类1,分类2,分类3,)")
    private String product_category_names;

    @Min(value = 0, message = "brand_id不支持(范围：不小于0)")
    private int brand_id;

    @NotBlank(message = "产品名称不能为空")
    private String name;

    private String keywords;

    @NotBlank(message = "请设置产品主图")
    @Pattern(regexp = "^((https://jintian-file.oss-cn-hangzhou.aliyuncs.com/)|(https://cbu01.alicdn.com/img/ibank/))[0-9a-zA-Z/!?_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "master_image错误：不支持的url")
    private String master_image;

    @Valid
    private List<ProductSaveProductAlbumsVO> albums;

    private String material_txt;

    @Valid
    private List<ProductSaveProductMaterialsVO> materials;

    @DecimalMin(value = "0.01", message = "商品零售价需大于0")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "推广金比例不小于0")
    @DecimalMax(value = "0.34", message = "推广金比例需小于0.35")
    private BigDecimal pop_amount_rate;

    @DecimalMin(value = "0.00", message = "bran_perfor_rate不小于0")
    private BigDecimal bran_perfor_rate;

    @Min(value = 0, message = "lowest_buy不支持(范围：不小于0)")
    private int lowest_buy;

    @DecimalMin(value = "0.00", message = "weight不支持(范围：不小于0)")
    private BigDecimal weight;

    @DecimalMin(value = "0.00", message = "marking_price不支持(范围：不小于0)")
    private BigDecimal marking_price;

    @Min(value = 0, message = "merchant_id不支持(范围：不小于0)")
    private int merchant_id;

    @Min(value = 0, message = "wh_id不支持(范围：不小于0)")
    private int wh_id;

    @Min(value = 0, message = "wh_product_id不支持(范围：不小于0)")
    private int wh_product_id;

    @Min(value = 0, message = "copartner_id不支持(范围：不小于0)")
    private int copartner_id;

    @NotNull(message = "请设置is_ali")
    private Boolean is_ali;

    private String ali_product_id;

    private String ali_shop_id;

    private String ali_shop_address;

    @Valid
    @NotNull(message = "请设置商品specs")
    private List<ProductSaveProductSpecsVO> specs;

    @Valid
    @NotNull(message = "请设置商品skus")
    private List<ProductSaveProductSkusVO> skus;

    @NotNull(message = "请设置is_choose_freight")
    private Boolean is_choose_freight;

    @Min(value = 0, message = "freight_tid不支持(范围：不小于0)")
    private int freight_tid;

    @DecimalMin(value = "0.00", message = "freight_amount不支持(范围：不小于0)")
    private BigDecimal freight_amount;

    @Min(value = 0, message = "nosend_area_tid不支持(范围：不小于0)")
    private int nosend_area_tid;

    @NotNull(message = "请设置is_post_coupon")
    private Boolean is_post_coupon;

    @Min(value = 0, message = "pc_full_piece不支持(范围：不小于0)")
    private int pc_full_piece;

    private String pc_area;

    @NotNull(message = "请设置is_full_coupon")
    private Boolean is_full_coupon;

    @DecimalMin(value = "0.00", message = "fc_full_amount不支持(范围：不小于0)")
    private BigDecimal fc_full_amount;

    @DecimalMin(value = "0.00", message = "fc_reduce_amount不支持(范围：不小于0)")
    private BigDecimal fc_reduce_amount;

    private Boolean is_fc_time_limit;

    private Boolean is_fc_doubling;

    @Pattern(regexp = "^\\s*$|^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s{1}(\\d{1,2}:){2}\\d{1,2}$", message = "fc_start格式不正确(格式：yyyy/MM/dd HH:mm:ss)")
    private String fc_start;

    @Pattern(regexp = "^\\s*$|^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s{1}(\\d{1,2}:){2}\\d{1,2}$", message = "fc_end格式不正确(格式：yyyy/MM/dd HH:mm:ss)")
    private String fc_end;

    @NotNull(message = "请设置is_limit_buy")
    private Boolean is_limit_buy;

    @Min(value = 0, message = "limit_buy_num不支持(范围：不小于0)")
    private int limit_buy_num;

    @NotNull(message = "请设置is_forbid_refund")
    private Boolean is_forbid_refund;

    private Boolean is_delivery_refund;

    @NotNull(message = "请设置is_q_recommend")
    private Boolean is_q_recommend;

    private String q_rcm_reason;

    @Pattern(regexp = "^((https://cloud.video.taobao.com/play/u/)|(https://douyin.titiyomi.com/))[0-9a-zA-Z/-]+((.mp4)|(.MP4)|(.Mp4))$", message = "video_url错误：不支持的url")
    private String video_url;

    @Pattern(regexp = "^((https://jintian-file.oss-cn-hangzhou.aliyuncs.com/)|(https://cbu01.alicdn.com/img/ibank/))[0-9a-zA-Z/!?_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "video_image错误：不支持的url")
    private String video_image;

    private String deta;

    @NotNull(message = "请设置is_shelf")
    private Boolean is_shelf;

    @NotNull(message = "请设置is_recommend")
    private Boolean is_recommend;

    @Min(value = 0, message = "multi_buy不支持(范围：不小于0)")
    private int multi_buy;

    private List<ProductSaveSimiliarGoodsVO> sim_goods;

    private String product_sku;

    private String ticket_remark;

    @Min(value = 0, message = "ticket_type不支持(范围：不小于0)")
    private int ticket_type;
}
