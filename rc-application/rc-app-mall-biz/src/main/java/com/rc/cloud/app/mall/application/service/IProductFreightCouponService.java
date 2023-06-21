package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductFreightCoupon;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
public interface IProductFreightCouponService extends IService<ProductFreightCoupon> {

    ProductFreightCoupon getProductFreightCouponByProductId(int productId);

    Integer saveProductFreightCoupon(ProductFreightCoupon productFreightCoupon);

    Integer removeProductFreightCoupons(int productId);


    void setProductFreightCouponRedis(int productId, ProductFreightCoupon productFreightCoupon);

    void removeProductFreightCouponRedis(int productId);

    ProductFreightCoupon getProductFreightCouponRedis(int productId);

    ProductFreightCoupon getProductFreightCouponRedis(int productId, Boolean isSyncFromDbWhenNull);
}
