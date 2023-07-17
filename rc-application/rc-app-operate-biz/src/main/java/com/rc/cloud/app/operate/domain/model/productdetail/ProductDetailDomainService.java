package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailDomainService {


    @Autowired
    private ProductDetailRepository productDetailRepository;

    public void saveProductDetail(ProductDetail productDetail){
        productDetailRepository.saveProductDetail(productDetail);
    }

    public ProductDetail findProductDetailById(ProductId productId){
        return productDetailRepository.findById(productId);
    }
}
