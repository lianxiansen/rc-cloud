package com.rc.cloud.app.operate.domain.model.productcategory.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName: ProductCategoryUpdateEvent
 * @Author: liandy
 * @Date: 2023/6/24 15:54
 * @Description: TODO
 */
public class ProductCategoryRefreshEvent extends ApplicationEvent {


    public ProductCategoryRefreshEvent(Object source) {
        super(source);
    }
}
