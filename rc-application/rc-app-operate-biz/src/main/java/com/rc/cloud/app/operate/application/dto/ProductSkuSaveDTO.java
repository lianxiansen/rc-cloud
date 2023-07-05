package com.rc.cloud.app.operate.application.dto;

import java.util.List;

public class ProductSkuSaveDTO {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String skuCode;

    private String supplyPrice;

    private String weight;

    private boolean hasImageFlag;

    private boolean enabledFlag;

    private List<ProductSkuImageSaveDTO> albums;

    /**
     * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
     */
    private List<ProductSkuAttributeSaveDTO> attributes;

    private int inventory;

    private int sort;

    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isHasImageFlag() {
        return hasImageFlag;
    }

    public void setHasImageFlag(boolean hasImageFlag) {
        this.hasImageFlag = hasImageFlag;
    }

    public List<ProductSkuImageSaveDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<ProductSkuImageSaveDTO> albums) {
        this.albums = albums;
    }

    public List<ProductSkuAttributeSaveDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductSkuAttributeSaveDTO> attributes) {
        this.attributes = attributes;
    }
}
