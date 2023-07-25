package com.rc.cloud.app.operate.domain.model.product.valobj;


import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: Popularization
 * @Author: liandy
 * @Date: 2023/6/26 13:43
 * @Description: 超级单品
 */
public class Explosives extends Entity {
    /**
     * 是否是超级单品 0否，1是
     */
    private boolean flag;
    /**
     * 超级单品海报URL，分类海报显示在产品分类页，尺寸500*280
     */
    private Url image;
    public Explosives(){
        this.flag = false;
        this.image=new Url("");
    }

    @Override
    public AbstractId getId() {
        return null;
    }

    public Explosives(boolean flag,Url image){
        this.flag =flag;
        if(flag){
            AssertUtils.assertArgumentNotNull(image,"image must not be null.");
        }
        this.image=image;
    }

    public Url getImage() {
        return image;
    }

    public void setImage(Url image) {
        this.image = image;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
