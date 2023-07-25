package com.rc.cloud.app.operate.domain.model.cart;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.cart.identifier.*;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.common.core.exception.ServiceException2;

/**
 * @author WJF
 * @create 2023-07-22 9:31
 * @description TODO
 */

public class Cart {

    private CartId id;

    /**
     * 用户ID
     */
    private UserId userId;

    /**
     * 类型 1.产品 2.拼单 3.秒杀 4.砍价
     */
    private Integer type;


    /**
     * 产品属性唯一Id
     */
    private ProductUniqueId productUniqueId;

    public CartId getId() {
        return id;
    }

    public void setId(CartId id) {
        this.id = id;
    }

    /**
     * 数量
     */
    private Integer num;

    /**
     * 创建时间
     */
    private CreateTime createTime;

    /**
     * 是否购买 0：未购买，1：已购买
     */
    private Integer payed;

    /**
     * 是否为立即购买
     */
    private Integer newState;

    /**
     * 拼团id
     */
    private CombinationId combinationId;

    /**
     * 秒杀产品id
     */
    private SeckillId seckillId;

    /**
     * 砍价id
     */
    private BargainId bargainId;

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public ProductUniqueId getProductUniqueId() {
        return productUniqueId;
    }

    public void setProductUniqueId(ProductUniqueId productUniqueId) {
        this.productUniqueId = productUniqueId;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        if (num <= 0) {
            throw new ServiceException2("购物车数量必须大于0");
        }
        this.num = num;
    }

    public CreateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(CreateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getNewState() {
        return newState;
    }

    public void setNewState(Integer newState) {
        this.newState = newState;
    }

    public CombinationId getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(CombinationId combinationId) {
        this.combinationId = combinationId;
    }

    public SeckillId getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(SeckillId seckillId) {
        this.seckillId = seckillId;
    }

    public BargainId getBargainId() {
        return bargainId;
    }

    public void setBargainId(BargainId bargainId) {
        this.bargainId = bargainId;
    }
}
