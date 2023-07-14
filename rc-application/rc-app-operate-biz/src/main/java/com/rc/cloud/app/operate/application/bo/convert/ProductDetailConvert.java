package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

public class ProductDetailConvert {


    public static ProductDetail convert(String productDetailId ,String productId, String tenantId, String detail){
        ProductDetail productDetail=new ProductDetail(new ProductDetailId(productDetailId),new TenantId(tenantId),
                new ProductId(productId),detail);
        return productDetail;
    }


//    public static ProductDetailPO convert(ProductDetail productDetail){
//        ProductDetailPO po=new ProductDetailPO();
//        po.setProductId(productDetail.getProductId().id());
//        po.setTenantId(productDetail.getTenantId().id());
//        po.setDetail(productDetail.getDetail());
//        po.setId(productDetail.getId().id());
//        return po;
//    }
    
}
