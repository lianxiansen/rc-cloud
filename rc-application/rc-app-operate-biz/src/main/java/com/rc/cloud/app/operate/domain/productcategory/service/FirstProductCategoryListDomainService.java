package com.rc.cloud.app.operate.domain.productcategory.service;

import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Locked;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rc.cloud.app.operate.domain.category.valobj.Layer;
import java.util.List;

/**
 * @ClassName: GetFirstListService
 * @Author: liandy
 * @Date: 2023/6/23 14:55
 * @Description: TODO
 */
@Service
public class FirstProductCategoryListDomainService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public List<ProductCategoryAggregation> execute() {
        return productCategoryRepository.getFirstList(new Locked(false),new Layer(1),
               new Parent("0"));
    }


}
