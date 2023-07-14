package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDetailPO;

public class ProductDetailConvert {


    public static ProductDetail convert(ProductDetailPO po){
        ProductDetail productDetail=new ProductDetail(new ProductDetailId(po.getId())
                , new TenantId(po.getTenantId()),
                new ProductId(po.getProductId()),po.getDetail());
        return productDetail;
    }


    public static ProductDetailPO convert(ProductDetail productDetail){
        ProductDetailPO po=new ProductDetailPO();
        po.setProductId(productDetail.getProductId().id());
        po.setTenantId(productDetail.getTenantId().id());
        po.setDetail(productDetail.getDetail());
        po.setId(productDetail.getId().id());
        return po;
    }
    
}
