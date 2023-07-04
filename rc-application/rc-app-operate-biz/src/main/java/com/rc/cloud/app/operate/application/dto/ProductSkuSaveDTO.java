package com.rc.cloud.app.operate.application.dto;

import java.util.List;

public class ProductSkuSaveDTO {



    private int id;

    private String skuCode;

    private String supplyPrice;

    private String weight;

    private boolean hasImageFlag;

    private List<ProductSkuImageSaveDTO> albums;

    private List<ProductSkuAttributeSaveDTO> attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ProductSkuAttributeSaveDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductSkuAttributeSaveDTO> attributes) {
        this.attributes = attributes;
    }

    public List<ProductSkuImageSaveDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<ProductSkuImageSaveDTO> albums) {
        this.albums = albums;
    }
}
