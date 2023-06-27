package com.rc.cloud.app.operate.application.data;

import lombok.Data;

import java.util.List;

/**
 * @auther Ushop
 * @date 2021/4/9 10:32
 * @Description AdminProductEvaluationDTO
 * @PROJECT_NAME qyy-live
 */
@Data
public class AdminProductEvaluationDTO {
    /// <summary>
    /// 评论ID值
    /// </summary>
    private int id;
    private long create_time;
    /// <summary>
    /// 评价内容
    /// </summary>
    private String content;
    /// <summary>
    /// 商户回复内容
    /// </summary>
    private String reply;
    /// <summary>
    /// 用户名
    /// </summary>
    private String user_name;
    /// <summary>
    /// 用户id
    /// </summary>
    private int user_id;
    /// <summary>
    /// Json
    /// 格式：[{"ImageUrl":"","SortID":""},{"ImageUrl":"","SortID":""}]
    /// </summary>
    private String albums;
    /// <summary>
    /// 评论图片string转至列表
    /// </summary>
    private List<AdminImageSortDTO> album_list;
    /// <summary>
    /// 好评，1差评，2中评，3好评
    /// </summary>
    private int reputation;
    //    /// <summary>
//    /// 产品名称
//    /// </summary>
//    private String product_title;
//    /// <summary>
//    /// 产品图
//    /// </summary>
//    private String master_image;
    /// <summary>
    /// 产品规格
    /// </summary>
    private String item_specification;
    /// <summary>
    /// 订单号
    /// </summary>
    private String order_number;
    /// <summary>
    /// 产品ID
    /// </summary>
    private int product_id;
    /// <summary>
    /// 产品ID
    /// </summary>
    private String str_product_id;
    /// <summary>
    /// 品牌名
    /// </summary>
    private String brand_community_brand_name;
    /// <summary>
    /// 社群和自营商品ID
    /// </summary>
    private String akc_or_self_product_id;
    /// <summary>
    /// 订单ID
    /// </summary>
    private int order_id;
    /// <summary>
    /// 商品评论列表用到
    /// </summary>
    private String product_name;
    /// <summary>
    /// 商品评论列表用到
    /// </summary>
    private String product_master_image;
    private Integer order_type;
    private boolean is_from_alibaba;
    private boolean is_from_we_applet;
    private boolean is_top;
    private boolean is_system;

    public boolean getIs_from_alibaba() {
        return is_from_alibaba;
    }

    public void setIs_from_alibaba(boolean is_from_alibaba) {
        this.is_from_alibaba = is_from_alibaba;
    }

    public boolean getIs_from_we_applet() {
        return is_from_we_applet;
    }

    public void setIs_from_we_applet(boolean is_from_we_applet) {
        this.is_from_we_applet = is_from_we_applet;
    }

    public boolean getIs_top() {
        return is_top;
    }

    public void setIs_top(boolean is_top) {
        this.is_top = is_top;
    }

    public boolean getIs_system() {
        return is_system;
    }

    public void setIs_system(boolean is_system) {
        this.is_system = is_system;
    }
}
