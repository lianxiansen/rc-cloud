package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.common.core.util.AssertUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: ProductGroupConvert
 * @Author: liandy
 * @Date: 2023/7/15 15:02
 * @Description: TODO
 */
public class ProductGroupConvert {
    public static List<ProductGroupBO> convert2ProductGroupBOBatch(List<ProductGroup> productGroupList,List<Product> list){
        List<ProductGroupBO> boList=new ArrayList<>();
        productGroupList.forEach(productGroup->{
            ProductGroupBO bo=convert2ProductGroupBO(productGroup);
            List<ProductGroupItemBO> itemBOs=new ArrayList<>();
            productGroup.getProductGroupItems().forEach(item->{
                Product product=list.stream().filter(p -> p.getId().equals(item.getProductId())).findFirst().orElse(null);
                itemBOs.add(convert2productGroupItemBO(item,product));
            });
            bo.setItemList(itemBOs);
            boList.add(bo);
        });
        return boList;
    }
    public static ProductGroupItemBO convert2productGroupItemBO(ProductGroupItem productGroupItem,Product product){
        ProductGroupItemBO itemBO=new ProductGroupItemBO()
                .setId(productGroupItem.getId().id())
                .setProductGroupId(productGroupItem.getProductGroupId().id())
                .setProductId(productGroupItem.getProductId().id())
                .setCreateTime(productGroupItem.getCreateTime().getTime());
        if(Objects.nonNull(product)){
            itemBO.setProductName(product.getName().getValue());
            itemBO.setProductImage(Objects.isNull(product.getProductListImage())?"":product.getProductListImage().getValue());
        }
        return itemBO;
    }
    public static ProductGroupBO convert2ProductGroupBO(ProductGroup productGroup){
        AssertUtils.notNull(productGroup,"productGroup must be not null");
        ProductGroupBO productGroupBO= new ProductGroupBO();
        productGroupBO.setId(productGroup.getId().id())
                .setName(productGroup.getName())
                .setProductId(productGroup.getProductId().id())
                .setCreateTime(productGroup.getCreateTime().getTime())
                .setItemList(new ArrayList<>());
        Collection<ProductGroupItemBO> itemBOList=new ArrayList<>();
        if(ObjectUtils.isNotEmpty(productGroup.getProductGroupItems())){
            productGroup.getProductGroupItems().forEach(item->{
                ProductGroupItemBO itemBO=new ProductGroupItemBO()
                        .setId(item.getId().id())
                        .setProductId(item.getProductId().id())
                        .setCreateTime(item.getCreateTime().getTime());
                itemBOList.add(itemBO);
            });
        }
        return productGroupBO;
    }
}
