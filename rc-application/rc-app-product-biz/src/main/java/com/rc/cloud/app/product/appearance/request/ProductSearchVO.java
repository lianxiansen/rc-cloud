package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ProductSearchVO  {
    /// <summary>
    /// 商品分类ID
    /// 多个请以英文半角字符 , 隔开
    /// </summary>
    public String cids ;
    /// <summary>
    /// 商品品牌ID
    /// 多个请以英文半角字符 , 隔开
    /// </summary>
    public String bids;
    /// <summary>
    /// 商户ID
    /// </summary>
    public int mid;
    /// <summary>
    /// 关键词
    /// </summary>
    public String keywords;
    /// <summary>
    /// 排序类型
    /// 0：推荐 1：综合 2：销量降 3：销量升 4：新品降 5：新品升 6：价格降 7：价格升
    /// </summary>
    @PositiveOrZero(message = "sort应大于等于0")
    public int sort;
    /// <summary>
    /// 单页深度
    /// </summary>
    @Min(value = 1, message = "size应大于0")
    public int size;
    /// <summary>
    /// 上次筛选结果最后一条信息
    /// </summary>
    public int[] last;
}
