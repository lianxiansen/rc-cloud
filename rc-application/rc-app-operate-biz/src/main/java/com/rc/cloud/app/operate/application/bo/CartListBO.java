package com.rc.cloud.app.operate.application.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-24 13:33
 * @description TODO
 */
@Data
public class CartListBO {
    public CartListBO() {
        cartList = new ArrayList<>();
    }

    private List<CartBO> cartList;
}
