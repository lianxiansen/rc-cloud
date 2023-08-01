package com.rc.cloud.app.marketing.domain.cart;

/**
 * @ClassName CartItem
 * @Author liandy
 * @Date 2023/7/29 13:32
 * @Description TODO
 * @Version 1.0
 */
public class CartItem {
    private String id;

    public String getId() {
        return this.id;
    }
    public CartItem(String id){
        this.id = id;
    }
}
