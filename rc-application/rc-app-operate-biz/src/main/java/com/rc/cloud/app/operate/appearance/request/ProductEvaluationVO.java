package com.rc.cloud.app.operate.appearance.request;

import cn.hutool.core.date.DateUtil;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther Ushop
 * @date 2021/4/9 11:21
 * @Description ProductEvaluationVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductEvaluationVO extends PageVO {

    private String eurl;
    private String eimg;
    private String etit;
    @PositiveOrZero(message = "商品id应大于0")
    private int product_id;
    @PositiveOrZero(message = "订单id应大于0")
    private int order_id;
    @PositiveOrZero(message = "用户id应大于0")
    private int user_id;
    @PositiveOrZero(message = "评论类型筛选应大于0或等于0")
    private int reputation;//评论类型筛选

    private String begin_time;
    private String end_time;
    private String brand_community_brand_name;

    private String keyword;

    private int search_order_group_type;   //订单类型1：普通订单（除去社群和1688）。2品牌社群自营。3品牌社群爱库存。4.1688订单
    private Integer search_is_system;     //评论来源 0用户评论，1非用户评论
    private Integer search_is_album;        //有图/无图 0无图，1有图
    private int evaluation_type;    //评论类型，1社群，2mcn，其余的是普通产品
    private String str_product_id;//根据evaluation_type来传的id

    public ProductEvaluationVO() {
        begin_time = "2019-8-1";
        end_time = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.offsetDay(new Date(System.currentTimeMillis()), 1));
    }
}
