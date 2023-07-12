package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 超级单品
 */
public class Explosives implements ValueObject<Explosives> {
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
            AssertUtils.assertArgumentNotNull(image,"image must not be null.");
        }
    }

    public String getImage() {
        return image;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public boolean sameValueAs(Explosives other) {
        return false;
    }
}
