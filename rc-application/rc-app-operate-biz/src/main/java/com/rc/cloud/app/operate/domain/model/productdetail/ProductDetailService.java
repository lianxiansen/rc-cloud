package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailService {


    @Autowired
    private ProductDetailRepository productDetailRepository;

    /**
     * 如果ProductDetail未找到则插入
     * 否则更新
     * @param productDetail
     */
    public void createProductDetail(ProductDetail productDetail){
        if(productDetailRepository.exist(productDetail.getId())){
            throw new ServiceException();
        }
        productDetailRepository.insertProductDetail(productDetail);
    }


    public void updateProductDetail(ProductDetail productDetail){
        ProductDetail oriDomain =productDetailRepository.findById(productDetail.getId());
        if(oriDomain==null){
            throw  new ServiceException();
        }
        oriDomain.setDetail(productDetail.getDetail());
        oriDomain.setInstallDetail(productDetail.getInstallDetail());
        oriDomain.setInstallVideoImg(productDetail.getInstallVideoImg());
        oriDomain.setInstallVideoUrl(productDetail.getInstallVideoUrl());
        productDetailRepository.updateProductDetail(oriDomain);
    }


    public ProductDetail findProductDetail(ProductDetailId productDetailId){
        return productDetailRepository.findById(productDetailId);
    }

    public void deleteProductDetail(ProductDetailId productDetailId) {
        productDetailRepository.removeProductDetail(productDetailId);
    }
}
