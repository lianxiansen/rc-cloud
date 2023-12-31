package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDetailPO;

public class ProductDetailConvert {


    public static ProductDetail convertDomain(ProductDetailPO po){
       if(po!=null){
           ProductDetail productDetail=new ProductDetail(new ProductDetailId(new ProductId(po.getProductId()))
                    );
           productDetail.setDetail(new Detail(po.getDetail()));
           productDetail.setInstallVideoUrl(new Url(po.getInstallVideoUrl()));
           productDetail.setInstallVideoImg(new Url(po.getInstallVideoImg()));
           productDetail.setInstallDetail(new Detail(po.getInstallDetail()));

           return productDetail;
       }
       return null;
    }


    public static ProductDetailPO convertProductDetailPO(ProductDetail productDetail){
        ProductDetailPO po=new ProductDetailPO();
        po.setProductId(productDetail.getId().getProductId().id());
        po.setDetail(productDetail.getDetail().getValue());
        po.setInstallDetail(productDetail.getInstallDetail().getValue());
        po.setInstallVideoImg(productDetail.getInstallVideoImg().getValue());
        po.setInstallVideoUrl(productDetail.getInstallVideoUrl().getValue());
        return po;
    }

}
