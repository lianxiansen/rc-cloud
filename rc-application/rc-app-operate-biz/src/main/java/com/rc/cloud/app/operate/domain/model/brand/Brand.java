package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;


public class Brand extends Entity {

    private BrandId id;
    /**
     * 品牌名
     */
    private String name;

    private String type;

    private int sort;
    private boolean enable;

    public Brand(BrandId id, String name) {
        setId(id);
        setName(name);
    }

    private void setId(BrandId id) {
        this.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public BrandId getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.assertArgumentNotNull(name, "name must not be null");
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isEnable() {
        return enable;
    }


    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }
}
