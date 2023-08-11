package com.rc.cloud.app.operate.domain.model.customclassification;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Layer;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.constants.CustomClassificationErrorCodeConstants;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;

public class CustomClassification extends AggregateRoot {

    CustomClassificationId id;

    private String name;

    private Url customClassificationImage;

    private Url productPoster;

    private Url customClassificationPoster;

    /**
     * 层级
     */
    private Layer layer;

    private Sort sort;
    /**
     * 父级产品分类唯一标识
     */
    private CustomClassificationId parentId;

    private Boolean enabledFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Url getCustomClassificationImage() {
        return customClassificationImage;
    }

    public void setCustomClassificationImage(Url customClassificationImage) {
        this.customClassificationImage = customClassificationImage;
    }

    public Url getProductPoster() {
        return productPoster;
    }

    public void setProductPoster(Url productPoster) {
        this.productPoster = productPoster;
    }

    public Url getCustomClassificationPoster() {
        return customClassificationPoster;
    }

    public void setCustomClassificationPoster(Url customClassificationPoster) {
        this.customClassificationPoster = customClassificationPoster;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public CustomClassificationId getParentId() {
        return parentId;
    }

    public void setParentId(CustomClassificationId parentId) {
        this.parentId = parentId;
    }

    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    CustomClassification enable() {
        this.enabledFlag = true;
        return this;
    }

    CustomClassification disable() {
        AssertUtils.assertArgumentNotNull(enabledFlag, "enabledFlag must not be null");
        this.enabledFlag = false;
        return this;
    }



    public CustomClassification(CustomClassificationId id, String name) {
        this.id = id;
        this.name = name;
        customClassificationImage =new Url("");
        productPoster =new Url("");
        customClassificationPoster =new Url("");
        enabledFlag=true;
        root();
    }


    void setParent(CustomClassification parent) {
        AssertUtils.assertArgumentNotNull(parent, "parent must not be null");
        if(parent.getId().equals(this.getId())){
            throw new ServiceException(CustomClassificationErrorCodeConstants.RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF);
        }
        Layer layer = parent.getLayer().increment();
        setLayer(layer);
        this.parentId = parent.getId();
    }

    void root() {
        this.parentId = null;
        this.setLayer(new Layer(Layer.ROOT));
    }

    @Override
    public CustomClassificationId getId() {
        return this.id;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    private CreateTime createTime;

    public CreateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(CreateTime createTime) {
        this.createTime = createTime;
    }
}
