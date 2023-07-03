package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSaveProductSkusVO {

    @Min(value = 0, message = "skus集合的id不支持(范围：不小于0)")
    private int id;

    @Min(value = 0, message = "wh_sku_id不支持(范围：不小于0)")
    private int wh_sku_id;

    private String wh_spec_ids;

    private String wh_spec_names;

    @Valid
    @NotEmpty(message = "ali_spec_kvps集合不能为空")
    private List<ProductSaveSpecKeyValuePairsVO> ali_spec_kvps;

    private String ali_sku_id;

    private String ali_spec_id;

    private String cargo_no;

    private String cargo_code;

    @DecimalMin(value = "0.00", message = "skus集合的price不支持(范围：不小于0)")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "skus集合的supply_price不支持(范围：不小于0)")
    private BigDecimal supply_price;

    @DecimalMin(value = "0.00", message = "skus集合的weight不支持(范围：不小于0)")
    private BigDecimal weight;

    @DecimalMin(value = "0.00", message = "skus集合的partner_price不支持(范围：不小于0)")
    private BigDecimal partner_price;

    @Min(value = 0, message = "on_sale不支持(范围：不小于0)")
    private int on_sale;

    @Pattern(regexp = "^((https://jintian-file.oss-cn-hangzhou.aliyuncs.com/)|(https://cbu01.alicdn.com/img/ibank/))[0-9a-zA-Z/!?_-]+((.jpg)|(.jpeg)|(.png)|(.JPG)|(.JPEG)|(.PNG)|(.Jpg)|(.Jpeg)|(.Png)|(.gif)|(.GIF)|(.Gif))$", message = "skus集合的image错误：不支持的url")
    private String image;
}
