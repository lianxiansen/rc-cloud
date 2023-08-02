package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-25 13:44
 * @description TODO
 */
@Data
public class ShopBO {

    private String name;

    private String image="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcbu01.alicdn.com%2Fimg%2Fibank%2FO1CN01dYgifE1GCQnLyotHJ_%21%211010940586-0-cib.jpg&refer=http%3A%2F%2Fcbu01.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1692855910&t=5f480bc2712ca792d02923644777e0d6";

    public ShopBO(String name){
        this.name=name;
    }
}
