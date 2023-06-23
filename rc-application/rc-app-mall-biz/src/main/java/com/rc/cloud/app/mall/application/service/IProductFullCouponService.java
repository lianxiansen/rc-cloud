package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
public interface IProductFullCouponService extends IService<ProductFullCoupon> {

    ProductFullCoupon getProductFullCouponByProductId(int productId);

    Integer saveProductFullCoupon(ProductFullCoupon productFullCoupon);

    void saveSecKillProductFullCoupon(ProductFullCoupon productFullCoupon, Boolean isOpenFullCoupon);

    Integer delete(int id);

    Integer removeProductFullCoupons(int productId);


    void setProductFullCouponRedis(int productId, ProductFullCoupon productFullCoupon);

    ProductFullCoupon getProductFullCouponRedis(int productId);

    ProductFullCoupon getProductFullCouponRedis(int productId, Boolean isSyncFromDbWhenNull);

    void removeProductFullCouponRedis(int productId);

    void removeProductFullCouponListRedis(int productId);
}
