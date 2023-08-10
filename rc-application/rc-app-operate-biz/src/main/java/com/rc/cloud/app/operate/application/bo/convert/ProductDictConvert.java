package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductDictBO;
import com.rc.cloud.app.operate.application.dto.ProductDictSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;

import java.util.*;
import java.util.stream.Collectors;

public  class ProductDictConvert {

    public static ProductDict convertDomain(String productId,ProductDictSaveDTO dto){
        ProductDict productDict=new ProductDict(new ProductId(productId),dto.getKey());
        productDict.setValue(dto.getValue());
        productDict.setSort(dto.getSort());
        return productDict;
    }


    public static Set<ProductDict> convertProductDictSet(String productId, List<ProductDictSaveDTO> list){
        Set<ProductDict> resList =new HashSet<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                    resList.add(convertDomain(productId,x))
            );
        }
        return resList;
    }

    public static ProductDictBO convertProductDictBO(ProductDict productDict){
        ProductDictBO bo =new ProductDictBO();
        bo.setSort(productDict.getSort());
        bo.setKey(productDict.getKey());
        bo.setValue(productDict.getValue());
        return bo;
    }

    public static Map<String, String> convertProductDictMap(Set<ProductDict> list){
        Map<String, String> dicts = list.stream().collect(Collectors.toMap(ProductDict::getKey, ProductDict::getValue));
        return dicts;
    }

}
