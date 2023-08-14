package com.rc.cloud.app.marketing.appearance.api.vo;

import com.rc.cloud.app.marketing.application.bo.CartBO;
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
public class CartProductVO {
    private String productId;

    private String productName;

    private String productImage;

    private String outid;

    private String spuCode;

    private List<CartBO> detailList;

    public CartProductVO() {
        detailList = new ArrayList<>();
    }

    public CartProductVO(CartProductVO bo) {
        this.productId = bo.getProductId();
        this.productName = bo.getProductName();
        this.productImage = bo.getProductImage();
        this.spuCode = bo.getSpuCode();
        this.setOutid(bo.getOutid());
        this.detailList = bo.getDetailList();
    }
}
