package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-29 13:18
 * @description TODO
 */

@Data
@Accessors(chain = true)
public class CartProductBO {
    private String productId;

    private String productName;

    private String productImage;

    private List<CartBO> detailList;

    public CartProductBO() {
        detailList = new ArrayList<>();
    }

    public CartProductBO(CartProductBO bo) {
        this.productId = bo.getProductId();
        this.productName = bo.getProductName();
        this.productImage = bo.getProductImage();
        this.detailList = bo.getDetailList();
    }
}
