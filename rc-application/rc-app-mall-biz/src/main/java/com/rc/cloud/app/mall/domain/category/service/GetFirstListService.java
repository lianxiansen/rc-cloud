package com.rc.cloud.app.mall.domain.category.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.mall.application.data.ProductCategoryData;
import com.rc.cloud.app.mall.domain.category.entity.ProductCategoryEntry;
import com.rc.cloud.app.mall.domain.category.repository.ProductCategoryRepository;
import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryLayer;
import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryLocked;
import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryParent;
import com.rc.cloud.app.mall.infrastructure.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GetFirstListService
 * @Author: liandy
 * @Date: 2023/6/23 14:55
 * @Description: TODO
 */
@Service
public class GetFirstListService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public List<ProductCategoryEntry> getFirstList() {
        return productCategoryRepository.getFirstList(new ProductCategoryLocked(false),new ProductCategoryLayer(1),
               new ProductCategoryParent("0"));
    }


}
