package com.rc.cloud.app.operate.domain.productsku;

import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.productsku.valobj.Sort;

public class ProductSkuEntity extends Entity {


    private ProductSkuId id;

    private TenantId tenantId;

    private ProductId productId;

    /**
     * sku货号，可以为空，为空商品的货号做为它的货号
     */
    private String skuCode;

    private SupplyPrice supplyPrice;

    private Weight weight;

    private OutId outId;

    private boolean hasImageFlag;

    private LimitBuy limitBuy;

    private Price price;

    private Inventory inventory;

    private SeckillSku seckillSku;


    /**
     * 排序
     */
    private Sort sort;


    ProductSkuEntity(ProductSkuId id, ProductId productId,
                     TenantId tenantId, Price price) {
        init();
        setId(id);
        setTenantId(tenantId);
        setPrice(price);
    }


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
        sort=new Sort();
        setSort(sort);
        inventory =new Inventory(0);
    }

    public void setSort(Sort sort){
        this.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
    }

    public Sort getSort() {
        return this.sort;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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
}
