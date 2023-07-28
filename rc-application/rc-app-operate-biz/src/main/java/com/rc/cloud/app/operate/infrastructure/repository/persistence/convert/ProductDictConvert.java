package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDictPO;

import java.util.ArrayList;
import java.util.List;

public class ProductDictConvert
{

    public static ProductDict convert(ProductDictPO po){
       if(po!=null){
           ProductDict productDict=new ProductDict(po.getId());
           productDict.setKey(po.getKey());
           productDict.setValue(po.getValue());
           productDict.setSort(po.getSort());
           productDict.setProductId(new ProductId(po.getProductId()));
           productDict.setTenantId(new TenantId(po.getTenantId()));
           return productDict;
       }
       return null;
    }

    public static ProductDictPO convert(ProductDict productDict){
        ProductDictPO productDictPO=new ProductDictPO();
        productDictPO.setId(productDict.getId());
        productDictPO.setKey(productDict.getKey());
        productDictPO.setValue(productDict.getValue());
        productDictPO.setProductId(productDict.getProductId().id());
        productDictPO.setTenantId(productDict.getTenantId().id());
        return productDictPO;
    }

    public static List<ProductDict> convertList(List<ProductDictPO> list){
        List<ProductDict> resList =new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                            resList.add(convert(x))
                    );
        }
        return resList;
    }


    public static List<ProductDictPO> convertPOList(List<ProductDict> list){
        List<ProductDictPO> resList =new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                    resList.add(convert(x))
            );
        }
        return resList;
    }
}
