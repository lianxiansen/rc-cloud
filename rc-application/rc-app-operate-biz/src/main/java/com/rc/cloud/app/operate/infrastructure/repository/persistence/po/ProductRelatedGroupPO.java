package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;

@TableName("product_related_group")
public class ProductRelatedGroupPO extends BaseDO {


    private static final long serialVersionUID = 265345L;

    @TableField("id")
    private Long Id;

    @TableField("name")
    private Long name;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenantId;

    @TableField("product_id")
    private Long productId;

    /**
     * 相关商品id
     */
    @TableField("related_product_id")
    private Long relatedProductId;

    @TableField("sort_id")
    private Integer sortId;

    public ProductRelatedGroupPO() {
    }

    public Long getId() {
        return this.Id;
    }

    public Long getName() {
        return this.name;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getRelatedProductId() {
        return this.relatedProductId;
    }

    public Integer getSortId() {
        return this.sortId;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setRelatedProductId(Long relatedProductId) {
        this.relatedProductId = relatedProductId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String toString() {
        return "ProductRelatedGroup(Id=" + this.getId() + ", name=" + this.getName() + ", tenantId=" + this.getTenantId() + ", productId=" + this.getProductId() + ", relatedProductId=" + this.getRelatedProductId() + ", sortId=" + this.getSortId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductRelatedGroupPO)) return false;
        final ProductRelatedGroupPO other = (ProductRelatedGroupPO) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        final Object this$Id = this.getId();
        final Object other$Id = other.getId();
        if (this$Id == null ? other$Id != null : !this$Id.equals(other$Id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$tenantId = this.getTenantId();
        final Object other$tenantId = other.getTenantId();
        if (this$tenantId == null ? other$tenantId != null : !this$tenantId.equals(other$tenantId)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$relatedProductId = this.getRelatedProductId();
        final Object other$relatedProductId = other.getRelatedProductId();
        if (this$relatedProductId == null ? other$relatedProductId != null : !this$relatedProductId.equals(other$relatedProductId))
            return false;
        final Object this$sortId = this.getSortId();
        final Object other$sortId = other.getSortId();
        if (this$sortId == null ? other$sortId != null : !this$sortId.equals(other$sortId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductRelatedGroupPO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $Id = this.getId();
        result = result * PRIME + ($Id == null ? 43 : $Id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $tenantId = this.getTenantId();
        result = result * PRIME + ($tenantId == null ? 43 : $tenantId.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $relatedProductId = this.getRelatedProductId();
        result = result * PRIME + ($relatedProductId == null ? 43 : $relatedProductId.hashCode());
        final Object $sortId = this.getSortId();
        result = result * PRIME + ($sortId == null ? 43 : $sortId.hashCode());
        return result;
    }
}
