package com.rc.cloud.app.operate.domain.model.product.identifier;

import com.rc.cloud.common.core.domain.AbstractId;

/**
 * @ClassName: ProductDictId
 * @Author: liandy
 * @Date: 2023/7/12 13:14
 * @Description: TODO
 */
public class ProductImageId extends AbstractId {
    private String id;
    public ProductImageId(String id) {
        super(id);
    }
    @Override
    public String id() {
        return this.id;
    }
    protected ProductImageId() {
        super();
    }
    public String getId(){
        return id;
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
