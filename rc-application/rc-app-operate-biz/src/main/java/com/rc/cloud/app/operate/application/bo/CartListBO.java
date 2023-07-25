package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.cart.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-24 13:33
 * @description TODO
 */

public class CartListBO {

    private List<CartBO> validList;

    private List<CartBO> invalidList;

    public CartListBO() {
        validList = new ArrayList<>();
        invalidList = new ArrayList<>();
    }

    public List<CartBO> getValidList() {
        return validList;
    }

    public void setValidList(List<CartBO> validList) {
        this.validList = validList;
    }

    public List<CartBO> getInvalidList() {
        return invalidList;
    }

    public void setInvalidList(List<CartBO> invalidList) {
        this.invalidList = invalidList;
    }
}
