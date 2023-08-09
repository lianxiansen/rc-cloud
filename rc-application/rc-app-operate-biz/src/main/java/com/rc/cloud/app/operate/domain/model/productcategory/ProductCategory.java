package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.ReInheritShouldNotSpecifyMyselfSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 产品分类
 */
public class ProductCategory extends AggregateRoot {
    private ProductCategoryId id;
    /**
     * 分类名
     */
    private ChName chName;
    /**
     * 分类名（英文名）
     */
    private EnName enName;
    /**
     * 图标图片
     */
    private Icon icon;
    /**
     * 页面
     */
    private Page page;
    /**
     * 层级
     */
    private Layer layer;
    /**
     * 父级产品分类唯一标识
     */
    private ProductCategoryId parentId;
    /**
     * 状态，是否启用
     */
    private Enabled enabled;
    /**
     * 排序
     */
    private Sort sort;

    private CreateTime createTime;

    ProductCategory(ProductCategoryId id, ChName name) {
        setId(id);
        setChName(name);
        init();
    }

    private void init() {
        setEnName(new EnName(""));
        setIcon(new Icon());
        setPage(new Page());
        setLayer(new Layer());
        enable();
        setSort(new Sort());
    }

    public void setCreateTime(CreateTime createTime) {
        this.createTime = createTime;
    }

    public CreateTime getCreateTime() {
        return this.createTime;
    }

    public void setId(ProductCategoryId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    @Override
    public ProductCategoryId getId() {
        return this.id;
    }

     ProductCategory setChName(ChName chName) {
        AssertUtils.assertArgumentNotNull(chName, "name must not be null");
        this.chName = chName;
        return this;
    }

    public ChName getChName() {
        return this.chName;
    }

    ProductCategory setEnName(EnName enName) {
        AssertUtils.assertArgumentNotNull(enName, "name must not be null");
        this.enName = enName;
        return this;
    }

    public EnName getEnName() {
        return this.enName;
    }

    ProductCategory setIcon(Icon icon) {
        AssertUtils.assertArgumentNotNull(icon, "icon must not be null");
        this.icon = icon;
        return this;
    }

    public Icon getIcon() {
        return this.icon;
    }

    ProductCategory setPage(Page page) {
        AssertUtils.assertArgumentNotNull(page, "page must not be null");
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

    ProductCategory setParentId(ProductCategoryId parentId) {
        this.parentId = parentId;
        return this;
    }

    public ProductCategoryId getParentId() {
        return this.parentId;
    }

    ProductCategory enable() {
        this.enabled = new Enabled(true);
        return this;
    }

    ProductCategory disable() {
        AssertUtils.assertArgumentNotNull(enabled, "enabled must not be null");
        this.enabled = new Enabled(false);
        return this;
    }

    public Enabled getEnabled() {
        return this.enabled;
    }


    ProductCategory setLayer(Layer layer) {
        AssertUtils.assertArgumentNotNull(layer, "layer must not be null");
        this.layer = layer;
        return this;
    }

    public Layer getLayer() {
        return this.layer;
    }

    ProductCategory setSort(Sort sort) {
        AssertUtils.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    public Sort getSort() {
        return this.sort;
    }

    /**
     * 继承父级商品分类
     * @param parent
     */
    void inheritFrom(ProductCategory parent) {
        AssertUtils.assertArgumentNotNull(parent, "parent must not be null");
        Layer layer = parent.getLayer().increment();
        setLayer(layer);
        this.parentId = parent.getId();
    }

    /**
     * 重新继承父级商品分类
     * @param parent
     */
    void reInheritFrom(ProductCategory parent) {
        AssertUtils.assertArgumentNotNull(parent, "parent must not be null");
        if (!new ReInheritShouldNotSpecifyMyselfSpecification(parent.getId()).isSatisfiedBy(this)) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF);
        }
        inheritFrom(parent);
    }

    /**
     * 设为根产品分类
     */
    void root() {
        this.parentId = null;
        this.setLayer(new Layer(Layer.ROOT));
    }


}
