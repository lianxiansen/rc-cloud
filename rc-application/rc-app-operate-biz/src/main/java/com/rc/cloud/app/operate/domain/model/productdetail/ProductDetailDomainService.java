package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailDomainService {


    @Autowired
    private ProductDetailRepository productDetailRepository;

    /**
     * 如果ProductDetail未找到则插入
     * 否则更新
     * @param productDetail
     */
    public void saveProductDetail(ProductDetail productDetail){
        ProductDetail oriDomain = findProductDetailByProductId(productDetail.getProductId());
        if(oriDomain!=null){
            if(productDetail.getDetail().getValue()!=null){
                oriDomain.setDetail(productDetail.getDetail());
            }
            if(productDetail.getInstallDetail().getValue()!=null){
                oriDomain.setInstallDetail(productDetail.getInstallDetail());
            }
            if(productDetail.getInstallVideoImg().getValue()!=null){
                oriDomain.setInstallVideoImg(productDetail.getInstallVideoImg());
            }
            if(productDetail.getInstallVideoUrl().getValue()!=null){
                oriDomain.setInstallVideoUrl(productDetail.getInstallVideoUrl());
            }
            productDetailRepository.updateProductDetail(productDetail);
        }else{
            productDetailRepository.insertProductDetail(productDetail);
        }

    }

    public ProductDetail findProductDetailByProductId(ProductId productId){
        return productDetailRepository.findByProductId(productId);
    }

    public void deleteProductDetailByProductId(ProductId productId) {
        productDetailRepository.removeProductDetailByProductId(productId);
    }
}
