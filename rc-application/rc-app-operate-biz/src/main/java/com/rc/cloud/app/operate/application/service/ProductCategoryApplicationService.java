package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductCategoryData;
import com.rc.cloud.app.operate.domain.category.service.GetFirstListService;
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
public class ProductCategoryApplicationService {
    @Autowired
    private GetFirstListService getFirstListService;
    public List<ProductCategoryData> getFirstList() {

        List<ProductCategoryData> list=new ArrayList<>();
        getFirstListService.execute().forEach(item->{
            list.add(ProductCategoryData.from(item));
        });
        return list;
    }
}
