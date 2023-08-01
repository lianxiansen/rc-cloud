package com.rc.cloud.app.operate.domain.model.productdict;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductDictService {

    @Autowired
    private ProductDictRepository productDictRepository;

    public List<ProductDict> getProductDictSetByProductId(ProductId productId){
        List<ProductDict> dicts = productDictRepository.getProductDictByProductId(productId);
        return dicts;
    }



    public void updateProductDict(ProductId productId,Set<ProductDict> productDictList){
        List<ProductDict> oriDicts = getProductDictSetByProductId(productId);
        List<ProductDict> currentDicts = productDictList.stream().collect(Collectors.toList());

        List<ProductDict> addList = CollectionUtil.subtractToList(currentDicts, oriDicts);
        List<ProductDict> removeList = CollectionUtil.subtractToList(oriDicts, currentDicts);
        List<ProductDict> intersectionList =
                CollectionUtil.intersection(oriDicts,currentDicts).stream().collect(Collectors.toList());


        if(addList!=null && addList.size()>0){
            for (ProductDict productDict : addList) {
                productDictRepository.insertProductDict(productDict);
            }
        }
        if(removeList!=null && removeList.size()>0){
            for (ProductDict productDict : removeList) {
                productDictRepository.removeProductDictByProductIdAndKey(productId,productDict.getKey());
            }
        }
        if(intersectionList!=null && intersectionList.size()>0){
            for (ProductDict productDict : intersectionList) {
                productDictRepository.updateProductDict(productDict);
            }
        }
    }

    public void insertProductDict(Set<ProductDict> productDictList){
        if(productDictList!=null && productDictList.size()>0){
            for (ProductDict productDict : productDictList) {
                productDictRepository.insertProductDict(productDict);
            }
        }
    }

    public void deleteProductDictByProductId(ProductId productId) {
        productDictRepository.removeProductDictByProductId(productId);
    }


}
