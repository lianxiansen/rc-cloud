package com.rc.cloud.app.operate.domain.model.productsku;


import com.rc.cloud.app.operate.domain.model.productsku.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.domain.ValueObject;

import java.util.Objects;

/**
 * @Author: chenjianxiang
 * @Date: 2023/6/23 17:02
 * @Description: 换成值对象，只关心图片地址和排序
 */
public class ProductSkuImage extends ValueObject {


    private ProductSkuId productSkuId;
    private Url url;
    private Sort sort;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }


    public ProductSkuImage(ProductSkuId productSkuId, Url url, Sort sort) {
        this.productSkuId = productSkuId;
        this.url = url;
        this.sort = sort;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSkuImage that = (ProductSkuImage) o;
        boolean f1= Objects.equals(productSkuId.id(), that.productSkuId.id());
        boolean f2= Objects.equals(url.getValue(), that.getUrl().getValue());
        boolean f3= Objects.equals(sort.getValue(), that.getSort().getValue());
        return f1 && f2 && f3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productSkuId.id(), getUrl().getValue(), getSort().getValue());
    }

    public ProductSkuId getProductSkuId() {
        return productSkuId;
    }
}
