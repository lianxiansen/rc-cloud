package com.rc.cloud.app.operate.domain.model.productsku;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSkuService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    public int batchSaveProductSku(List<ProductSku> productSkuList){
        if(CollectionUtil.isEmpty(productSkuList)){
            throw new ServiceException();
        }
        return productSkuRepository.batchSaveProductSku(productSkuList);
    }

    public int updateProductSku(ProductSku productSku){
        return productSkuRepository.updateProductSku(productSku);
    }

    public ProductSku findProductSkuById(ProductSkuId productSkuId) {
        return productSkuRepository.findById(productSkuId);
    }

    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        return productSkuRepository.getProductSkuListByProductId(productId);
    }

    public void deleteProductSkuByProductId(ProductId productId) {
        List<ProductSku> productSkuList = getProductSkuListByProductId(productId);
        if(CollectionUtil.isNotEmpty(productSkuList)){
            for (ProductSku productSku : productSkuList) {
                productSkuRepository.removeProductSku(productSku.getId());
            }
        }
    }

    public void deleteProductSku(ProductSkuId productSkuId) {
        productSkuRepository.removeProductSku(productSkuId);
    }
}
