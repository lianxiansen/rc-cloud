package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.productgroup.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupItemPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupPO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ProductGroupConvert
 * @Author: liandy
 * @Date: 2023/7/14 15:01
 * @Description: TODO
 */
public class ProductGroupConvert {
    public static ProductGroupPO convert2ProductGroupPO(ProductGroup productGroup) {
        ProductGroupPO productGroupPO = new ProductGroupPO();
        productGroupPO.setId(productGroup.getId().id());
        productGroupPO.setProductId(productGroup.getProductId().id());
        productGroupPO.setName(productGroup.getName());
        productGroupPO.setTenantId(productGroup.getTenantId().id());
        return productGroupPO;
    }


    public static List<ProductGroupItemPO> convert2ProductGroupItemPOBatch(List<ProductGroupItem> items) {
        List<ProductGroupItemPO> pos = new ArrayList<>();
        items.forEach(item -> {
            ProductGroupItemPO po = convert2ProductGroupItemPO(item);
            pos.add(po);
        });
        return pos;
    }

    public static ProductGroupItemPO convert2ProductGroupItemPO(ProductGroupItem item) {
        ProductGroupItemPO po = new ProductGroupItemPO()
                .setId(item.getId().id())
                .setProductGroupId(item.getProductGroupId().id())
                .setProductId(item.getProductId().id());
        return po;
    }
    public static ProductGroup convert2ProductGroup(ProductGroupPO productGroupPO) {
        ProductGroupId id = new ProductGroupId(productGroupPO.getId());
        String name = productGroupPO.getName();
        TenantId tenantId = new TenantId(productGroupPO.getTenantId());
        ProductId productId = new ProductId(productGroupPO.getProductId());
        ProductGroup productGroup = new ProductGroup(id, name, tenantId, productId);
        productGroup.setCreateTime(new CreateTime(productGroupPO.getCreateTime()));
        return productGroup;
    }
    public static List<ProductGroupItem> convert2ProductGroupItemBatch(List<ProductGroupItemPO> pos) {
        List<ProductGroupItem> items = new ArrayList<>();
        pos.forEach(po -> {
            items.add(convert2ProductGroupItem(po));
        });
        return items;
    }


    public static ProductGroupItem convert2ProductGroupItem(ProductGroupItemPO po) {
        ProductGroupItemId productGroupItemId = new ProductGroupItemId(po.getId());
        ProductGroupId productGroupId= new ProductGroupId(po.getProductGroupId());
        ProductGroupItem item = new ProductGroupItem(productGroupItemId,productGroupId, new ProductId(po.getProductId()));
        return item;
    }


    public static List<ProductGroup> convert2ProductGroupWithItemsBatch(List<ProductGroupPO> pos, List<ProductGroupItemPO> itemPOs){
        List<ProductGroup> productGroups=new ArrayList<>();
        pos.forEach(po->{
            ProductGroup productGroup=ProductGroupConvert.convert2ProductGroup(po);
            List<ProductGroupItemPO> productGroupItemPOs =itemPOs.stream().filter(s->s.getProductGroupId().equals(productGroup.getId().id())).collect(Collectors.toList());
            ProductGroupConvert.convert2ProductGroupItemBatch(productGroupItemPOs).forEach(item->{
                productGroup.appendItem(item);
            });
            productGroups.add(productGroup);
        });
        return productGroups;
    }
}
