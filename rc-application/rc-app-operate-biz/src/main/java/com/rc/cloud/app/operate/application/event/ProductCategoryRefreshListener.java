package com.rc.cloud.app.operate.application.event;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.event.ProductCategorySaveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;



@Component
public class ProductCategoryRefreshListener implements ApplicationListener<ProductCategorySaveEvent> {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public void onApplicationEvent(ProductCategorySaveEvent event) {
        Object source = event.getSource();
        if (source != null && ProductCategory.class.getClass() == source.getClass()) {
            productCategoryRepository.save((ProductCategory) source);
        }
    }
}
