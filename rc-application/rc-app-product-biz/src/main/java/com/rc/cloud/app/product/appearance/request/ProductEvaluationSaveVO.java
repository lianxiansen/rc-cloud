package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @auther Ushop
 * @date 2021/4/14 13:54
 * @Description ProductEvaluationVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductEvaluationSaveVO {
    //3好评，2中评，1差评
    @Positive(message = "请传入好评差评还是中评")
    private int reputation;
    //商品描述分数
    @Positive(message = "请传入商品描述分数")
    private int description_tally;
    //卖家服务分数
    @Positive(message = "请传入卖家服务分数")
    private int service_attitude;
    //发货速度分数
    @Positive(message = "请传入发货速度分数")
    private int delivery_speed;
    //头像
    @NotBlank(message = "请传入头像")
    private String head_image_url;
    //评论内容
    private String content;
    //商品id
    private int product_id;
    //社群商品id
    private String brand_community_product_id;
    //社群商品品牌名
    private String brand_community_brand_name;

    //昵称
    @NotBlank(message = "请传入昵称")
    private String nick_name;
    //规格
    @NotBlank(message = "请传入规格")
    private String item_specification;
    //图片
    private String albums;
    //创建时间
    @NotBlank(message = "请传入评论时间")
    private String create_time;
    //订单类型，12社群，0普通
    @PositiveOrZero(message = "请传入订单类型")
    private int order_type;
    private int merchant_id;
    private int mcn_product_id;
}
