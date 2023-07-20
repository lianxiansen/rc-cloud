package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.ReInheritShouldNotSpecifyMyselfSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;

import java.time.LocalDateTime;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 产品分类
 */
public class ProductCategory extends AggregateRoot {
    private ProductCategoryId id;
    private TenantId tenantId;
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

    public ProductCategory(ProductCategoryId id, TenantId tenantId, ChName name) {
        setId(id);
        setTenantId(tenantId);
        setChName(name);
        init();
    }

    private void init(){
        setEnName(new EnName(""));
        setIcon(new Icon());
        setPage(new Page());
        setLayer(new Layer());
        setEnabled(new Enabled());
        setSort(new Sort());
        setCreateTime(new CreateTime(LocalDateTime.now()));

    }
    public void setCreateTime(CreateTime createTime){
        this.createTime = createTime;
    }

    public CreateTime getCreateTime(){
        return this.createTime;
    }

    public void setId(ProductCategoryId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    @Override
    public ProductCategoryId getId(){
        return this.id;
    }

    public void setTenantId(TenantId tenantId) {
        AssertUtils.assertArgumentNotNull(tenantId, "tenantId must not be null");
        this.tenantId = tenantId;
    }

    public TenantId getTenantId(){
        return this.tenantId;
    }

    public ProductCategory setChName(ChName chName) {
        AssertUtils.assertArgumentNotNull(chName, "name must not be null");
        this.chName = chName;
        return this;
    }

    public ChName getChName() {
        return this.chName;
    }

    public ProductCategory setEnName(EnName enName) {
        AssertUtils.assertArgumentNotNull(enName, "name must not be null");
        this.enName = enName;
        return this;
    }

    public EnName getEnName() {
        return this.enName;
    }

    public ProductCategory setIcon(Icon icon) {
        AssertUtils.assertArgumentNotNull(icon, "icon must not be null");
        this.icon = icon;
        return this;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public ProductCategory setPage(Page page) {
        AssertUtils.assertArgumentNotNull(page, "page must not be null");
        this.page = page;
        return this;
    }
    public Page getPage() {
        return this.page;
    }

    public ProductCategory setParentId(ProductCategoryId parentId) {
        this.parentId = parentId;
        return this;
    }

    public ProductCategoryId getParentId() {
        return this.parentId;
    }

    public ProductCategory setEnabled(Enabled enabled){
        AssertUtils.assertArgumentNotNull(enabled, "enabled must not be null");
        this.enabled =enabled;
        return this;
    }

    public Enabled getEnabled(){
        return this.enabled;
    }


    public ProductCategory setLayer(Layer layer) {
        AssertUtils.assertArgumentNotNull(layer, "layer must not be null");
        this.layer = layer;
        return this;
    }
    public Layer getLayer(){
        return this.layer;
    }
    public ProductCategory setSort(Sort sort){
        AssertUtils.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
        return this;
    }

    public Sort getSort() {
        return this.sort;
    }

    public void inherit(ProductCategory parent){
        AssertUtils.assertArgumentNotNull(parent, "parent must not be null");
        Layer layer= parent.getLayer().increment();
        setLayer(layer);
        this.parentId=parent.getId();
    }

    public void reInherit(ProductCategory parent){
        AssertUtils.assertArgumentNotNull(parent, "parent must not be null");
        if(!new ReInheritShouldNotSpecifyMyselfSpecification(this).isSatisfiedBy(parent.getId())){
            throw new ServiceException(ProductCategoryErrorCodeConstants.RE_INHERIT_SHOULD_NOT_SPECIFY_MYSELF);
        }
        inherit(parent);
    }

    public void root(){
        this.parentId=null;
        this.setLayer(new Layer(Layer.ROOT));
    }



}
