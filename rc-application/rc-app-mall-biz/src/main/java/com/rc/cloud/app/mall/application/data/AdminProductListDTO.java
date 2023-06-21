package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @auther Ushop
 * @date 2021/4/1 17:00
 * @Description AdminProductListDTO
 * @PROJECT_NAME qyy-live
 */
@Data
public class AdminProductListDTO {
    private int id;
    private boolean is_on_shelf;

    public boolean getIs_on_shelf() {
        return is_on_shelf;
    }

    public void setIs_on_shelf(boolean is_on_shelf) {
        this.is_on_shelf = is_on_shelf;
    }

    private boolean is_recommend;

    public boolean getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    private String master_image;
    private String name;
    private BigDecimal price;
    /// <summary>
    /// 分销佣金
    /// </summary>
    private BigDecimal distributor_amount;
    private int product_status;
    private String product_category_names;

    private String share_title;
    private String share_desc;
    private int sort_id;
    private int store_sort_id;
    private int merchant_id;
    private String merchant_name;
    private long create_time;
    //    private long long_create_time;
    private boolean is_deleted;

    public boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    private String manager_name;
    private int copartner_id;
    private long off_shelf_time;
    private int inventory;
    private int collection;
}
