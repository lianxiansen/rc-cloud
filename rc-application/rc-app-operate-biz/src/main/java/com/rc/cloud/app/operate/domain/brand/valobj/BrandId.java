package com.rc.cloud.app.operate.domain.brand.valobj;

import com.rc.cloud.app.operate.domain.common.AbstractId;

/**
 * @ClassName: ProductCategoryEntryId
 * @Author: liandy
 * @Date: 2023/6/23 13:15
 * @Description: TODO
 */
public class BrandId extends AbstractId {

    private long value;
    public BrandId(String id) {
        super(id);
    }

    protected BrandId() {
        super();
    }

    public long getValue(){
        return value;
    }

}
