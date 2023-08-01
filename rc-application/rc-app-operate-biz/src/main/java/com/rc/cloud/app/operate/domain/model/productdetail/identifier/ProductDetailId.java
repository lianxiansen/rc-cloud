package com.rc.cloud.app.operate.domain.model.productdetail.identifier;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AbstractId;

import java.util.UUID;

public class ProductDetailId extends AbstractId {



    public ProductDetailId(ProductId productId) {
        this.productId =productId;
    }

    protected ProductDetailId() {
        super();
    }


    private ProductId productId;

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

}
