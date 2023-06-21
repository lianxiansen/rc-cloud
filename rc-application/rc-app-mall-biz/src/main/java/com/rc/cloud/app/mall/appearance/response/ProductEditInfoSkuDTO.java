package com.rc.cloud.app.mall.appearance.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductEditInfoSkuDTO {

    private Integer id;

    private String item_names;

    private Integer wh_sku_id;

    private String wh_spec_ids;

    private List<ProductEditInfoSkuSpecKVPDTO> ali_spec_kvps;

    private String ali_sku_id;

    private String ali_spec_id;

    private String cargo_no;

    private String cargo_code;

    private BigDecimal price;

    private BigDecimal supply_price;

    private BigDecimal weight;

    private BigDecimal partner_price;

    private Integer on_sale;

    private String image;
}
