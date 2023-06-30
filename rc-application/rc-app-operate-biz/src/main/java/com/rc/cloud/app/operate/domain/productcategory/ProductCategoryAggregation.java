package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;

/**
 * @ClassName: ProductCategoryEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品分类
 */
public class ProductCategoryAggregation extends Entity {
    private ProductCategoryId id;
    private TenantId tenantId;
    private Name name;
    private Icon icon;
    private Page page;
    private Layer layer;
    private ProductCategoryId parentId;
    private Enabled enabled;
    private Sort sort;

    ProductCategoryAggregation(ProductCategoryId id, TenantId tenantId, Name name) {
        setId(id);
        setTenantId(tenantId);
        setName(name);
        init();
    }

    private void init(){
        icon=new Icon();
        page = new Page();
        layer = new Layer();
        enabled=new Enabled();
        sort=new Sort();
        setIcon(icon);
        setPage(page);
        setLayer(layer);
        setEnabled(enabled);
        setSort(sort);
    }

    public void setId(ProductCategoryId id) {
        this.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public ProductCategoryId getId(){
        return this.id;
    }

    public void setTenantId(TenantId tenantId) {
        this.assertArgumentNotNull(tenantId, "tenantId must not be null");
        this.tenantId = tenantId;
    }

    public TenantId getTenantId(){
        return this.tenantId;
    }

    public void setName(Name name) {
        this.assertArgumentNotNull(name, "name must not be null");
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    public void setIcon(Icon icon) {
        this.assertArgumentNotNull(icon, "icon must not be null");
        this.icon = icon;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public void setPage(Page page) {
        this.assertArgumentNotNull(page, "page must not be null");
        this.page = page;
    }
    public Page getPage() {
        return this.page;
    }

    public void setParentId(ProductCategoryId parentId) {
        this.parentId = parentId;
    }

    public ProductCategoryId getParentId() {
        return this.parentId;
    }

    public void setEnabled(Enabled enabled){
        this.assertArgumentNotNull(enabled, "enabled must not be null");
        this.enabled =enabled;
    }

    public Enabled getEnabled(){
        return this.enabled;
    }


    public void setLayer(Layer layer) {
        this.assertArgumentNotNull(layer, "layer must not be null");
        this.layer = layer;
    }
    public Layer getLayer(){
        return this.layer;
    }
    public void setSort(Sort sort){
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
    }

    public Sort getSort() {
        return this.sort;
    }
    public void extendsFromParent(ProductCategoryAggregation parent){
        if(null!=parent){
            Layer layer= parent.getLayer().addLayer(new Layer(1));
            setLayer(layer);
            this.parentId=parent.getId();
        }else{
            setLayer(new Layer(1));
            this.parentId=null;
        }

    }

}
