package com.rc.cloud.app.operate.domain.model.product.identifier;

import com.rc.cloud.common.core.domain.AbstractId;

/**
 * @ClassName: ProductDictId
 * @Author: liandy
 * @Date: 2023/7/12 13:14
 * @Description: TODO
 */
public class ProductImageId extends AbstractId {

    public ProductImageId(String id) {
        super(id);
    }

    protected ProductImageId() {
        super();
    }
    @Override
    public boolean equals(Object anObject) {
        if (anObject != null && this.getClass() == anObject.getClass()) {
            ProductImageId typedObject = (ProductImageId) anObject;
            return this.sameValueAs((ProductImageId)anObject);
        }
        return false;
    }
}
