package com.rc.cloud.app.mall.appearance.request;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther Ushop
 * @date 2021/4/1 16:41
 * @Description ProductRequestVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductRequestVO extends PageVO {

    private String begin_time;
    private String end_time;
    private String keyword;
    private Integer type;
    private Integer merchant_id;
    private Integer copartner_id;
    private Boolean is_deleted;
    private Boolean is_recommend;
    private Integer product_status;
    private Integer manager_id;
    private Integer product_id;
    private String not_in_product_id;
    private Boolean is_coupon_product;
    private Boolean is_merchant_login;

    private Boolean not_select_all_create_time;
    //0-上架初始, 1-上架中,2下架
    private Integer shelf;
    /// <summary>
    /// 按库存排序1升序 2降序
    /// </summary>
    private Integer byinventory;
    private int orderby;

    public ProductRequestVO() {
        begin_time = "2018-11-01";
        end_time = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.offsetDay(new Date(System.currentTimeMillis()), 1));
        orderby = 0;
    }
}
