package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDetailPO;

public class ProductDetailConvert {


    public static ProductDetail convertDomain(ProductDetailPO po){
       if(po!=null){
           ProductDetail productDetail=new ProductDetail(
                   new TenantId(po.getTenantId())
                   ,new ProductId(po.getProductId())
                   ,new Detail(po.getDetail())
                   ,new Url(po.getInstallVideoUrl())
                   ,new Url(po.getInstallVideoImg())
                   ,new Detail(po.getInstallDetail())
                    );
           return productDetail;
       }
       return null;
    }


    public static ProductDetailPO convertProductDetailPO(ProductDetail productDetail){
        ProductDetailPO po=new ProductDetailPO();
        po.setProductId(productDetail.getProductId().id());
        po.setTenantId(productDetail.getTenantId().id());
        po.setDetail(productDetail.getDetail().getValue());
        po.setInstallDetail(productDetail.getInstallDetail().getValue());
        po.setInstallVideoImg(productDetail.getInstallVideoImg().getValue());
        po.setInstallVideoUrl(productDetail.getInstallVideoUrl().getValue());
        return po;
    }
    
}
