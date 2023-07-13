package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.dto.ProductDictSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;

import java.util.ArrayList;
import java.util.List;

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
}
