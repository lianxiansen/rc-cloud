package com.rc.cloud.app.operate.application.bo;


import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.common.core.util.AssertUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductGroupBO {

    private String Id;

    private String name;

    private LocalDateTime createTime;

    private Collection<ProductGroupItemBO> itemList;



    public static List<ProductGroupBO> convertBatch(List<ProductGroup> productGroupList){
        List<ProductGroupBO> boList=new ArrayList<>();
        productGroupList.forEach(item->{
            boList.add(convert(item));
        });
        return boList;
    }

    public static ProductGroupBO convert(ProductGroup productGroup){
        AssertUtils.notNull(productGroup,"productGroup must be not null");
        ProductGroupBO productGroupBO= new ProductGroupBO();
        productGroupBO.setId(productGroup.getId().id())
                .setName(productGroup.getName())
                .setCreateTime(productGroup.getCreateTime().getTime())
                .setItemList(new ArrayList<>());
        Collection<ProductGroupItemBO> itemBOList=new ArrayList<>();
        if(ObjectUtils.isNotEmpty(productGroup.getProductGroupItemList())){
            productGroup.getProductGroupItemList().forEach(item->{
                ProductGroupItemBO itemBO=new ProductGroupItemBO()
                        .setId(item.getId().id())
                        .setProductId(item.getProductId().id())
                        .setCreateTime(item.getCreateTime().getTime())
                        .setProductName(item.getProductName());
                itemBOList.add(itemBO);
            });
        }
        return productGroupBO;
    }
}
