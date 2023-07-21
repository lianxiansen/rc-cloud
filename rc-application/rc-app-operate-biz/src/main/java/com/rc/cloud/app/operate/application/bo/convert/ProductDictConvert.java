package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductDictBO;
import com.rc.cloud.app.operate.application.dto.ProductDictSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public  class ProductDictConvert {

    public static ProductDict convert(String productId, String tenantId,ProductDictSaveDTO dto){
        ProductDict productDict=new ProductDict(dto.getId());
        productDict.setKey(dto.getKey());
        productDict.setValue(dto.getValue());
        productDict.setSort(dto.getSort());
        productDict.setProductId(new ProductId(productId));
        productDict.setTenantId(new TenantId(tenantId));
        return productDict;
    }


    public static List<ProductDict> convertList(String productId, String tenantId,List<ProductDictSaveDTO> list){
        List<ProductDict> resList =new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                    resList.add(convert(productId,tenantId,x))
            );
        }
        return resList;
    }

    public static ProductDictBO convert(ProductDict productDict){
        ProductDictBO bo =new ProductDictBO();
        bo.setSort(productDict.getSort());
        bo.setKey(productDict.getKey());
        bo.setValue(productDict.getValue());
        return bo;
    }

    public static Map<String, String> convertProductDictMap(List<ProductDict> list){
        Map<String, String> dicts = list.stream().collect(Collectors.toMap(ProductDict::getKey, ProductDict::getValue));
        return dicts;
    }

}
