package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.app.operate.appearance.request.ProductSaveSpecKeyValuePairsVO;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductSkuAttributeSaveDTO {

    private String name;

    private String value;

    private int sortId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
