package com.rc.cloud.app.operate.domain.category.service;

import com.rc.cloud.app.operate.domain.category.ProductCategoryEntity;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.valobj.Layer;
import com.rc.cloud.app.operate.domain.category.valobj.Locked;
import com.rc.cloud.app.operate.domain.category.valobj.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: GetFirstListService
 * @Author: liandy
 * @Date: 2023/6/23 14:55
 * @Description: TODO
 */
@Service
public class FirstProductCategoryListService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public List<ProductCategoryEntity> execute() {
        return productCategoryRepository.getFirstList(new Locked(false),new Layer(1),
               new Parent("0"));
    }


}
