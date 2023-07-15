package com.rc.cloud.app.operate.domain.model.productgroup.specification;

import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.common.core.domain.AbstractSpecification;
public class AppendProductGroupItemLimitSpecification extends AbstractSpecification {

    public static final int MAX_ITEM_SIZE=10;
    public AppendProductGroupItemLimitSpecification(){
    }
    @Override
    public boolean isSatisfiedBy(Object o) {
        ProductGroup productGroup= (ProductGroup) o;
        return productGroup.getProductGroupItems().size()<MAX_ITEM_SIZE;
    }
}
