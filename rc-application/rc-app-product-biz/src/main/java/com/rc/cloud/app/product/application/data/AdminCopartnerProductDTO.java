package com.rc.cloud.app.product.application.data;

import lombok.Data;

@Data
public class AdminCopartnerProductDTO {

    public int id;
    /// <summary>
    ///名称
    /// </summary>
    public String name;

    /// <summary>
    /// 主图
    /// </summary>
    public String master_image;

    /// <summary>
    /// 上架状态
    /// </summary>
    public int shelf;

    /// <summary>
    /// 商户ID
    /// </summary>
    public int merchant_id;

    /// <summary>
    /// 总销量
    /// </summary>
    public int all_sales;


    /// <summary>
    /// <summary>
    /// 今日销量
    /// </summary>
    public int today_sales;

    /// <summary>
    /// 商户名称
    /// </summary>
    public String merchant_name;

    /// <summary>
    /// 发布时间
    /// </summary>
    public long create_time;
}
