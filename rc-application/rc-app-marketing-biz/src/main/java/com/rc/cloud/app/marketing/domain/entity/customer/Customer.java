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
    private String name;
    private String mobile;


    public String getId() {
        return id;
    }

    public Customer(String id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public static Customer mock(){
        return new Customer("5b6b70eafeaa9938cff8e430245090c7","李丽丽","13857685225");
    }
}
