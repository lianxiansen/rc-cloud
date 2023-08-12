package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class ProductImageSaveDTO {

    private String id;

    private String url;

    private Integer sort;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImageSaveDTO that = (ProductImageSaveDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getSort(), that.getSort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getSort());
    }
}
