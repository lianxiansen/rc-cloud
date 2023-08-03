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

    public void batchSaveProductSku(ProductId productId, List<ProductSku> productSkuList){
        if(CollectionUtil.isEmpty(productSkuList)){
            throw new ServiceException();
        }
        //校验领域规格是否重复
        //校验skucode是否重复

        productSkuRepository.batchSaveProductSku(productId,productSkuList);
    }

    public void updateProductSku(ProductSku productSku){
        //保证对象存在
        //保证存在对象规格未被修改
        //保证skucode不重复

        productSkuRepository.updateProductSku(productSku);
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
