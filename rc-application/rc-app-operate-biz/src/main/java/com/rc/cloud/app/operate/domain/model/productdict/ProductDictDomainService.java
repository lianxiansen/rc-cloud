package com.rc.cloud.app.operate.domain.model.productdict;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDictDomainService {

    @Autowired
    private ProductDictRepository productDictRepository;

    public List<ProductDict> getProductDictByProductId(ProductId productId){
        return productDictRepository.getProductDictByProductId(productId);
    }

    public void saveProductDict(List<ProductDict> productDictList){
        productDictRepository.saveProductDict(productDictList);
    }
}
