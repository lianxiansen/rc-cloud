package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;

import java.time.LocalDateTime;


public class Brand extends Entity {

    private BrandId id;
    /**
     * 品牌名
     */
    private String name;



    private String logo;

    private String type;

    private int sort;
    private boolean enabled;



    private CreateTime createTime;

    public Brand(BrandId id, String name) {
        setId(id);
        setName(name);
        setSort(0);
        disable();
        setCreateTime(new CreateTime(LocalDateTime.now()));
    }

    private void setId(BrandId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    @Override
    public BrandId getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        AssertUtils.assertArgumentNotEmpty(name, "name must not be empty");
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public void setCreateTime(CreateTime createTime){
        this.createTime = createTime;
    }
    public CreateTime getCreateTime() {
        return createTime;
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

    public boolean isEnabled() {
        return enabled;
    }


    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }


}
