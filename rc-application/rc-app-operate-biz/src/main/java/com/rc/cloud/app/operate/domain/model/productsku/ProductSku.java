package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Enabled;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProductSku extends Entity {


    private ProductSkuId id;
    private TenantId tenantId;
    private ProductId productId;
    private Price price;

    public ProductSku(ProductSkuId id, ProductId productId,
                      TenantId tenantId, Price price) {
        init();
        setId(id);
        setTenantId(tenantId);
        setPrice(price);
    }


    /**
     * sku货号，可以为空，为空商品的货号做为它的货号
     */
    private String skuCode;


    public ProductSku skuCode(String skuCode){
        this.assertArgumentNotNull(skuCode, "skuCode must not be null");
        this.skuCode =skuCode;
        return this;
    }


    public String getSkuCode(){
        return this.skuCode;
    }

    /**
     * 供货价
     */
    private SupplyPrice supplyPrice;

    public ProductSku supplyPrice(SupplyPrice supplyPrice){
        this.assertArgumentNotNull(supplyPrice, "supplyPrice must not be null");
        this.supplyPrice =supplyPrice;
        return this;
    }


    /**
     * 重量
     */
    private Weight weight;

    public ProductSku weight(Weight weight){
        this.assertArgumentNotNull(weight, "weight must not be null");
        this.weight =weight;
        return this;
    }

    /**
     * 外部id
     */
    private OutId outId;

    public ProductSku outId(OutId outId){
        this.assertArgumentNotNull(outId, "outId must not be null");
        this.outId =outId;
        return this;
    }

    /**
     * 图片
     */
    private boolean hasImageFlag;
    private List<ProductSkuImageEntity> skuImageList;

    public ProductSku skuImageList(List<ProductSkuImageEntity> skuImageList){
        this.assertArgumentNotNull(hasImageFlag, "hasImageFlag must not be null");
        if(skuImageList!=null && skuImageList.size()>0){
            this.hasImageFlag =true;
            skuImageList =skuImageList;
        }
        return this;
    }

    /**
     * 限购
     */
    private LimitBuy limitBuy;

    /**
     * 库存
     */
    private Inventory inventory;


    /**
     * 暂时不考虑
     */
    private SeckillSku seckillSku;


    /**
     * 排序
     */
    private Sort sort;


    private boolean enabledFlag;

    public void setId(ProductSkuId id) {
        this.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    public ProductSkuId getId(){
        return this.id;
    }

    public void setTenantId(TenantId tenantId) {
        this.assertArgumentNotNull(tenantId, "tenantId must not be null");
        this.tenantId = tenantId;
    }

    public TenantId getTenantId(){
        return this.tenantId;
    }


    public void setPrice(Price price){
        this.assertArgumentNotNull(tenantId, "price must not be null");
        this.price =price;
    }

    private void init(){
        sort=new Sort(99);
        setSort(sort);
        inventory =new Inventory(0);
        hasImageFlag=false;
        skuAttributes=new TreeSet<>();
    }

    public void setSort(Sort sort){
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
    }

    public Sort getSort() {
        return this.sort;
    }


    public SupplyPrice getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(SupplyPrice supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public OutId getOutId() {
        return outId;
    }

    public void setOutId(OutId outId) {
        this.outId = outId;
    }

    public boolean isHasImageFlag() {
        return hasImageFlag;
    }

    public void setHasImageFlag(boolean hasImageFlag) {
        this.hasImageFlag = hasImageFlag;
    }

    public LimitBuy getLimitBuy() {
        return limitBuy;
    }

    public void setLimitBuy(LimitBuy limitBuy) {
        this.limitBuy = limitBuy;
    }

    public Price getPrice() {
        return price;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public SeckillSku getSeckillSku() {
        return seckillSku;
    }

    public void setSeckillSku(SeckillSku seckillSku) {
        this.seckillSku = seckillSku;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    private SortedSet<ProductSkuAttributeEntity> skuAttributes;

    public void addSkuAttribute(String name, String value, int sort){
        ProductSkuAttributeEntity productSkuAttributeEntity=new ProductSkuAttributeEntity();
        productSkuAttributeEntity.setAttribute(name);
        productSkuAttributeEntity.setAttributeValue(value);
        productSkuAttributeEntity.setSort(sort);
        skuAttributes.add(productSkuAttributeEntity);
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
