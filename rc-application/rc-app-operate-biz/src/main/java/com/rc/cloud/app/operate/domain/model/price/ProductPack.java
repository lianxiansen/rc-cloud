package com.rc.cloud.app.operate.domain.model.price;

import com.rc.cloud.app.operate.domain.model.price.enums.CategoryEnum;
import com.rc.cloud.app.operate.domain.model.price.enums.SkuSourceEnum;

import java.math.BigDecimal;
import java.util.List;

public class ProductPack {

    /**
     * 这里注意下，product和sku的关系，一个商品可能有很多规格，比如Product是"NIKE运动鞋"，SKU就是"NIKE运动鞋黑色40码"
     */

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品CODE
     */
    private String productCode;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKU CODE
     */
    private String skuCode;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 商品来源
     */
    private SkuSourceEnum skuSource;

    /**
     * 类目
     */
    private CategoryEnum category;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 优惠信息，一个商品可能有多个优惠信息
     */
    private List<PromotionInfo> promotionList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public SkuSourceEnum getSkuSource() {
        return skuSource;
    }

    public void setSkuSource(SkuSourceEnum skuSource) {
        this.skuSource = skuSource;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public List<PromotionInfo> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<PromotionInfo> promotionList) {
        this.promotionList = promotionList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (getClass() != obj.getClass()) {
                return false;
            } else {
                if (((ProductPack) obj).getSkuId().equals(this.getSkuId())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


}
