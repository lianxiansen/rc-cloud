package com.rc.cloud.app.operate.domain.model.productdict;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductDictDomainService {

    @Autowired
    private ProductDictRepository productDictRepository;

    public Set<ProductDict> getProductDictSetByProductId(ProductId productId){
        List<ProductDict> dicts = productDictRepository.getProductDictByProductId(productId);
        return dicts.stream().collect(Collectors.toSet());
    }

    public void saveProductDict(Set<ProductDict> productDictList){
        productDictRepository.saveProductDict(productDictList.stream().collect(Collectors.toList()));
    }

    public void deleteProductDict(ProductId productId) {
        productDictRepository.removeProductDictByProductId(productId.id());
    }
}
