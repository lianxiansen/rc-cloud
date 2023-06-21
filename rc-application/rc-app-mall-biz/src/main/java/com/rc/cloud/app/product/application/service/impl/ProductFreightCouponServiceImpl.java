package com.rc.cloud.app.product.application.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxun.qlive.common.redis.RedisKey;
import com.qxun.qlive.common.redis.RedisUtil;
import com.qxun.qlive.goods.api.IProductFreightCouponService;
import com.qxun.qlive.goods.mapper.ProductFreightCouponMapper;
import com.qxun.qlive.goods.model.ProductFreightCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Service
public class ProductFreightCouponServiceImpl extends ServiceImpl<ProductFreightCouponMapper, ProductFreightCoupon> implements IProductFreightCouponService {

    @Autowired
    private ProductFreightCouponMapper productFreightCouponMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过商品ID获取商品满包邮
     * @param productId 商品ID
     * @return 商品满包邮信息
     */
    public ProductFreightCoupon getProductFreightCouponByProductId(int productId) {
        QueryWrapper<ProductFreightCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productFreightCouponMapper.selectOne(wrapper);
    }

    /**
     * 保存商品满包邮信息
     * @param productFreightCoupon 商品满包邮信息
     * @return
     */
    public Integer saveProductFreightCoupon(ProductFreightCoupon productFreightCoupon) {
        Integer result = 0;
        if (productFreightCoupon.getId() > 0) {
            QueryWrapper<ProductFreightCoupon> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", productFreightCoupon.getId());
            result = productFreightCouponMapper.update(productFreightCoupon, wrapper);
        } else {
            result = productFreightCouponMapper.insert(productFreightCoupon);
        }
        return result;
    }

    /**
     * 删除商品满包邮
     * @param productId
     * @return 删除数量
     */
    public Integer removeProductFreightCoupons(int productId) {
        QueryWrapper<ProductFreightCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productFreightCouponMapper.delete(wrapper);
    }

    /**
     * 设置商品满包邮缓存
     * @param productId 商品ID
     * @param productFreightCoupon 商品满包邮信息
     */
    public void setProductFreightCouponRedis(int productId, ProductFreightCoupon productFreightCoupon) {
        redisUtil.putHash(RedisKey.getProductFreightCouponHashKey(), productId, productFreightCoupon);
    }

    /**
     * 移除商品满包邮缓存
     * @param productId 商品ID
     */
    public void removeProductFreightCouponRedis(int productId) {
        redisUtil.deleteFromHash(RedisKey.getProductFreightCouponHashKey(), productId);
    }

    /**
     * 获取商品满包邮缓存
     * @param productId 商品ID
     * @return 商品满包邮信息
     */
    public ProductFreightCoupon getProductFreightCouponRedis(int productId) {
        return getProductFreightCouponRedis(productId, false);
    }

    /**
     * 获取商品满包邮缓存
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品满包邮信息
     */
    public ProductFreightCoupon getProductFreightCouponRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductFreightCouponHashKey();
        ProductFreightCoupon productFreightCoupon = redisUtil.getObjectFromHash(redisKey, productId, ProductFreightCoupon.class);
        if (ObjectUtil.isEmpty(productFreightCoupon) && isSyncFromDbWhenNull.equals(true)) {
            productFreightCoupon = getProductFreightCouponByProductId(productId);
            if (ObjectUtil.isNotEmpty(productFreightCoupon))
                setProductFreightCouponRedis(productId, productFreightCoupon);
        }
        return productFreightCoupon;
    }
}
