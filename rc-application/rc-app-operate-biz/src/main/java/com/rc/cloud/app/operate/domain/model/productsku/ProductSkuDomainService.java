package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSkuDomainService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    public int batchSaveProductSku(List<ProductSku> productSkuList){
        return productSkuRepository.batchSaveProductSku(productSkuList);
    }

    public int updateProductSku(ProductSku productSku){
        return productSkuRepository.updateProductSku(productSku);
    }

    public void findProductSkuById(ProductSkuId productSkuId) {
        productSkuRepository.findById(productSkuId);
    }

    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        return productSkuRepository.getProductSkuListByProductId(productId);
    }
}
