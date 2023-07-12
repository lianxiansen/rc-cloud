package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.ValueObject;

/**
 * @ClassName: Detail
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Detail implements ValueObject<Detail> {
    private String id;
    private String value;

    public Detail(String id,String value){
      this.id=id;
      setValue(value);
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("商品详情不为空");
        }

    }

    public String getId() {
        return id;
    }

    @Override
    public boolean sameValueAs(Detail other) {
        return false;
    }
}
