package com.rc.cloud.app.operate.domain.model.productsku;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.util.AssertUtils;

import java.util.List;

public class ProductSku extends AggregateRoot {


    private ProductSkuId id;
    private ProductId productId;
    private Price price;

    public ProductSku(ProductSkuId id, ProductId productId) {
        init();
        setId(id);
        setProductId(productId);

    }

    private void init(){
        setSort(new Sort(99));
        inventory =new Inventory(0);
        enabledFlag =true;


    }
    /**
     * sku货号，可以为空，为空商品的货号做为它的货号
     */
    private String skuCode;


    public String getSkuCode(){
        return this.skuCode;
    }

    /**
     * 供货价
     */
    private SupplyPrice supplyPrice;

    public SupplyPrice getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(SupplyPrice supplyPrice) {
        AssertUtils.assertArgumentNotNull(supplyPrice, "supplyPrice must not be null");
        this.supplyPrice = supplyPrice;
    }

    /**
     * 重量
     */
    private Weight weight;

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        AssertUtils.assertArgumentNotNull(weight, "weight must not be null");
        this.weight = weight;
    }
    /**
     * 装箱数
     */
    private PackingNumber packingNumber;

    public PackingNumber getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(PackingNumber packingNumber) {
        AssertUtils.assertArgumentNotNull(packingNumber, "packingNumber must not be null");
        this.packingNumber = packingNumber;
    }

    /**
     * 箱规
     */
    private CartonSize cartonSize;

    public CartonSize getCartonSize() {
        return cartonSize;
    }

    public void setCartonSize(CartonSize cartonSize) {
        AssertUtils.assertArgumentNotNull(cartonSize, "cartonSize must not be null");
        this.cartonSize = cartonSize;
    }

    /**
     * 外部id
     */
    private OutId outId;

    private List<ProductSkuImage> skuImageList;

    /**
     * 限购
     */
    private LimitBuy limitBuy;
    public LimitBuy getLimitBuy() {
        return limitBuy;
    }

    public void setLimitBuy(LimitBuy limitBuy) {
        AssertUtils.assertArgumentNotNull(limitBuy, "limitBuy must not be null");
        this.limitBuy = limitBuy;
    }
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

    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public void setId(ProductSkuId id) {
        AssertUtils.assertArgumentNotNull(id, "id must not be null");
        this.id = id;
    }

    @Override
    public ProductSkuId getId(){
        return this.id;
    }


    public void setPrice(Price price){
        AssertUtils.assertArgumentNotNull(price, "price must not be null");
        this.price =price;
    }



    public void setSort(Sort sort){
        AssertUtils.assertArgumentNotNull(sort, "sort must not be null");
        this.sort = sort;
    }

    public Sort getSort() {
        return this.sort;
    }


    public OutId getOutId() {
        return outId;
    }

    public void setOutId(OutId outId) {
        AssertUtils.assertArgumentNotNull(outId, "outId must not be null");
        this.outId = outId;
    }


    public Price getPrice() {
        return price;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        AssertUtils.assertArgumentNotNull(inventory, "inventory must not be null");
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
        AssertUtils.assertArgumentNotNull(productId, "productId must not be null");
        this.productId = productId;
    }


    public void setSkuCode(String skuCode) {
        AssertUtils.assertArgumentNotNull(skuCode, "skuCode must not be null");
        this.skuCode = skuCode;
    }



    private ProductSkuAttribute productSkuAttribute;


    public void setProductSkuAttributeEntity(ProductSkuAttribute productSkuAttribute) {
        this.productSkuAttribute = productSkuAttribute;
    }

    public List<ProductSkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<ProductSkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }



    public void setProductSkuAttribute(ProductSkuAttribute productSkuAttribute) {
        this.productSkuAttribute = productSkuAttribute;
    }

    public ProductSkuAttribute getProductSkuAttribute() {
        return productSkuAttribute;
    }
}
