package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDictPO;

import java.util.ArrayList;
import java.util.List;

public class ProductDictConvert
{

    public static ProductDict convertDomain(ProductDictPO po){
       if(po!=null){
           ProductDict productDict=new ProductDict(new ProductId(po.getProductId()),po.getDictKey());
           productDict.setValue(po.getDictValue());
           productDict.setSort(po.getSort());
           productDict.setTenantId(new TenantId(po.getTenantId()));
           return productDict;
       }
       return null;
    }

    public static ProductDictPO convertProductDictPO(ProductDict productDict){
        ProductDictPO productDictPO=new ProductDictPO();
        productDictPO.setDictKey(productDict.getKey());
        productDictPO.setDictValue(productDict.getValue());
        productDictPO.setProductId(productDict.getProductId().id());
        productDictPO.setTenantId(productDict.getTenantId().id());
        return productDictPO;
    }

    public static List<ProductDict> convertDomainList(List<ProductDictPO> list){
        List<ProductDict> resList =new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                            resList.add(convertDomain(x))
                    );
        }
        return resList;
    }


    public static List<ProductDictPO> convertPOList(List<ProductDict> list){
        List<ProductDictPO> resList =new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(x->
                    resList.add(convertProductDictPO(x))
            );
        }
        return resList;
    }
}
