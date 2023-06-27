package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 超级单品
 */
public class Explosives extends ValueObject {
    /**
     * 是否是超级单品 0否，1是
     */
    private boolean flag;
    /**
     * 超级单品海报URL，分类海报显示在产品分类页，尺寸500*280
     */
    private String image;
    public Explosives(){
        this.flag = false;
    }

    public Explosives(boolean flag,String image){
        this.flag =flag;
        if(flag){
            this.assertArgumentNotNull(image,"image must not be null.");
        }
    }
}
