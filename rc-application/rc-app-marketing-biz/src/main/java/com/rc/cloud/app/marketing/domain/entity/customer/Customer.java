package com.rc.cloud.app.marketing.domain.entity.customer;

import lombok.Data;

/**
 * @ClassName Customer
 * @Author liandy
 * @Date 2023/8/5 16:59
 * @Description 顾客
 * @Version 1.0
 */
@Data
public class Customer {
    private String id;
    private String mobile;

    public Customer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Customer mock(){
        return new Customer("5b6b70eafeaa9938cff8e430245090c7");
    }
}
