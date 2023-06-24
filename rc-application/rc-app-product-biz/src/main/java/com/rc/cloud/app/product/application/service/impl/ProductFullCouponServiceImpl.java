package com.rc.cloud.app.product.application.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.product.application.service.IProductFullCouponService;
import com.rc.cloud.app.product.application.service.IProductService;
import com.rc.cloud.app.product.infrastructure.config.RedisKey;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductFullCouponMapper;
import com.rc.cloud.app.product.infrastructure.persistence.po.Product;
import com.rc.cloud.app.product.infrastructure.util.MapUtil;
import com.rc.cloud.app.product.infrastructure.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Service
public class ProductFullCouponServiceImpl extends ServiceImpl<ProductFullCouponMapper, ProductFullCoupon> implements IProductFullCouponService {

    @Autowired
    private ProductFullCouponMapper productFullCouponMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IProductService iProductService;

    /**
     * 通过商品ID获取商品满减
     * @param productId 商品ID
     * @return 商品满减信息
     */
    @Override
    public ProductFullCoupon getProductFullCouponByProductId(int productId) {
        QueryWrapper<ProductFullCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productFullCouponMapper.selectOne(wrapper);
    }

    /**
     * 保存商品满减信息
     * @param productFullCoupon 商品满减信息
     * @return
     */
    @Override
    public Integer saveProductFullCoupon(ProductFullCoupon productFullCoupon) {
        Integer result = 0;
        if (productFullCoupon.getId() > 0) {
            QueryWrapper<ProductFullCoupon> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", productFullCoupon.getId());
            result = productFullCouponMapper.update(productFullCoupon, wrapper);
        } else {
            result = productFullCouponMapper.insert(productFullCoupon);
        }
        return result;
    }

    /**
     * 保存秒杀商品的满减
     * @param productFullCoupon 商品满减
     * @param isOpenFullCoupon 是否开启满减
     */
    @Override
    public void saveSecKillProductFullCoupon(ProductFullCoupon productFullCoupon, Boolean isOpenFullCoupon) {
        Product product = iProductService.getProduct(productFullCoupon.getProductID());
        if (ObjectUtil.isNotEmpty(product)) {
            product.setIsOpenFullCoupon(isOpenFullCoupon);
            ProductFullCoupon _productFullCoupon = getProductFullCouponByProductId(product.getId());
            if (product.getIsOpenFullCoupon().equals(true)) {
                if (ObjectUtil.isEmpty(_productFullCoupon)) {
                    _productFullCoupon = new ProductFullCoupon();
                    _productFullCoupon.setProductID(product.getId());
                }
                _productFullCoupon.setFullAmount(productFullCoupon.getFullAmount());
                _productFullCoupon.setReduceAmount(productFullCoupon.getReduceAmount());
                _productFullCoupon.setIsTimeLimit(productFullCoupon.getIsTimeLimit());
                _productFullCoupon.setIsDoubling(productFullCoupon.getIsDoubling());
                if (_productFullCoupon.getIsTimeLimit().equals(true)) {
                    _productFullCoupon.setBeginTime(productFullCoupon.getBeginTime());
                    _productFullCoupon.setEndTime(productFullCoupon.getEndTime());
                }
                saveProductFullCoupon(_productFullCoupon);
                setProductFullCouponRedis(product.getId(), _productFullCoupon);
            } else {
                if (ObjectUtil.isNotEmpty(_productFullCoupon)) {
                    delete(_productFullCoupon.getId());
                }
                removeProductFullCouponRedis(product.getId());
                removeProductFullCouponListRedis(product.getId());
            }
            iProductService.saveOrUpdate(product);
            redisUtil.putHash(RedisKey.getProductHashKey(), product.getId(), product);
            redisUtil.putHash(RedisKey.getSimpleProductHashKey(), product.getId(), MapUtil.ChangeObject(product, SimpleProduct.class));

            for (int i = 1; i <= 10; i++) {
                redisUtil.deleteHash(RedisKey.getStoreProductListSearchMerchantIdPageHashKey(0, product.getMerchantID(), i));
                redisUtil.deleteHash(RedisKey.getStoreProductListSearchMerchantIdPageHashKey(1, product.getMerchantID(), i));
            }
        }
    }

    /**
     * 删除商品满减
     * @param id 商品满减ID
     * @return 删除结果
     */
    @Override
    public Integer delete(int id) {
        QueryWrapper<ProductFullCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", id);
        return productFullCouponMapper.delete(wrapper);
    }

    /**
     * 删除商品满减
     * @param productId 商品ID
     * @return 删除数量
     */
    @Override
    public Integer removeProductFullCoupons(int productId) {
        QueryWrapper<ProductFullCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productFullCouponMapper.delete(wrapper);
    }

    /**
     * 设置商品满减缓存
     * @param productId 商品ID
     * @param productFullCoupon 商品满减信息
     */
    @Override
    public void setProductFullCouponRedis(int productId, ProductFullCoupon productFullCoupon) {
        redisUtil.putHash(RedisKey.getProductFullCouponHashKey(), productId, productFullCoupon);
    }

    /**
     * 获取商品满减缓存
     * @param productId 商品ID
     * @return 商品满减信息
     */
    @Override
    public ProductFullCoupon getProductFullCouponRedis(int productId) {
        return getProductFullCouponRedis(productId, false);
    }

    /**
     * 获取商品满减缓存
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品满减信息
     */
    @Override
    public ProductFullCoupon getProductFullCouponRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductFullCouponHashKey();
        ProductFullCoupon productFullCoupon = redisUtil.getObjectFromHash(redisKey, productId, ProductFullCoupon.class);
        if (ObjectUtil.isEmpty(productFullCoupon) && isSyncFromDbWhenNull.equals(true)) {
            productFullCoupon = getProductFullCouponByProductId(productId);
            if (ObjectUtil.isNotEmpty(productFullCoupon)) {
                setProductFullCouponRedis(productId, productFullCoupon);
            }
        }
        return productFullCoupon;
    }

    /**
     * 移除商品满减缓存
     * @param productId 商品ID
     */
    @Override
    public void removeProductFullCouponRedis(int productId) {
        redisUtil.deleteFromHash(RedisKey.getProductFullCouponHashKey(), productId);
    }

    /**
     * 移除商品满减列表缓存
     * @param productId 商品ID
     */
    @Override
    public void removeProductFullCouponListRedis(int productId) {
        redisUtil.deleteFromHash(RedisKey.getProductFullCouponListHashKey(), productId);
    }
}
