package com.rc.cloud.app.operate.domain.model.productsku;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductSkuService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    public void batchSaveProductSku(ProductId productId, List<ProductSku> productSkuList){
        if(CollectionUtil.isEmpty(productSkuList)){
            throw new ServiceException();
        }
        //校验领域规格是否重复
        for (ProductSku productSku : productSkuList) {
            if(productSkuList.stream().anyMatch(u->u.sameProductSkuAttributeAs(productSku.getProductSkuAttribute())
                && u.getId().id()!=productSku.getId().id()
            )){
                throw new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_ATTIBUTE_REPEAT_ERROR);
            }
        }
        //校验skucode是否重复
        if(validateSkuCodeIsRepeat(productSkuList)){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_CODE_REPEAT_ERROR);
        }
        productSkuRepository.batchSaveProductSku(productId,productSkuList);
    }

    public void updateProductSku(ProductSku productSku){
        //保证对象存在
        ProductSku oriModel = findProductSkuById(productSku.getId());
        if(oriModel==null){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_NOT_EXIST_ERROR);
        }
        //保证存在对象规格未被修改
        if(!oriModel.sameProductSkuAttributeAs(productSku.getProductSkuAttribute())){
            //修改过则报错，不允许单独修改sku规格
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_UPDATE_ERROR);
        }
        //保证skucode不重复
        if(validateSkuCodeIsRepeat(productSku.getProductId(),productSku)){
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_CODE_REPEAT_ERROR);
        }
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


    /**
     * 判断集合里的skucode是否有重复
     * @param productSkuList
     * @return true表示有重复
     */
    private boolean validateSkuCodeIsRepeat(List<ProductSku> productSkuList){
        productSkuList=productSkuList.stream().filter(u-> StringUtils.isNotEmpty(u.getSkuCode())).collect(Collectors.toList());
        Set<String> skuCodeList =new HashSet<>();
        for (ProductSku productSku : productSkuList) {
            skuCodeList.add(productSku.getSkuCode());
        }
        if(productSkuList.size()!=skuCodeList.size()){
            return true;
        }else{
            return false;
        }
    }

    private boolean validateSkuCodeIsRepeat(ProductId productId,ProductSku validateSku){
        List<ProductSku> productSkuList = getProductSkuListByProductId(productId);
        if(productSkuList.stream().anyMatch(u->u.getSkuCode().equals(validateSku.getSkuCode())
                && u.getId().id()!=validateSku.getId().id()
        )
        ){
            return true;
        }else{
            return false;
        }
    }
}
