package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.brand.valobj.Type;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.brand.valobj.Name;
import com.rc.cloud.app.operate.domain.model.brand.valobj.Enable;


public class BrandEntity extends Entity {

    private BrandId id;
    /**
     * 品牌名
     */
    private Name name;

    private Type type;

    private Sort sort;
    private Enable enable;

    public void disable(){
        this.enable =new Enable(false);
    }
    public void enable(){
        this.enable =new Enable(true);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public BrandId getId() {
        return id;
    }

    public void setId(BrandId id) {
        this.id = id;
    }

    public Enable getEnable() {
        return enable;
    }

    public void setEnable(Enable enable) {
        this.enable = enable;
    }
}
