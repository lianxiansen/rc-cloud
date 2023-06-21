package com.rc.cloud.app.mall.appearance.response;

import lombok.Data;

@Data
public class ProductListItemDTO {

    private Integer pid;

    private String title;

    private String image;

    private String price;

    private String max_price;

    private String category;

    private String merchant;

    private Integer merchant_id;

    private Boolean is_recommend;

    private Integer shelf;

    private Boolean is_del;

    private Integer check_status;

    private Integer left;

    private Integer sort_id;

    private String create_time;

    private String update_time;

    private String creator;

    private Boolean is_can_use_coupon;

    private String product_link;
}
