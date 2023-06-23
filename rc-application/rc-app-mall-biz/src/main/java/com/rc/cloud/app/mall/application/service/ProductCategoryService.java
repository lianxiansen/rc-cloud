package com.rc.cloud.app.mall.application.service;

import com.rc.cloud.app.mall.application.data.ProductCategoryData;
import com.rc.cloud.app.mall.domain.category.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductCategoryService
 * @Author: liandy
 * @Date: 2023/6/23 14:43
 * @Description: TODO
 */
@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public List<ProductCategoryData> getFirstList() {
        List<ProductCategoryData> list=new ArrayList<>();
        productCategoryRepository.getFirstList().forEach(item->{
            list.add(ProductCategoryData.from(item));
        });
        return list;
    }
}
