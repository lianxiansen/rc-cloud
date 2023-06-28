package com.rc.cloud.app.operate.domain.category.service;

import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.category.valobj.Layer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SaveProductCategoryDomainService
 * @Author: liandy
 * @Date: 2023/6/27 13:32
 * @Description: TODO
 */
@Service
public class SaveProductCategoryDomainService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public void execute(ProductCategoryAggregation productCategoryAggregation) {
        Layer layer = new Layer();
        if (null != productCategoryAggregation.getParentId()) {
            ProductCategoryAggregation parentProductCategoryAggregation= productCategoryRepository.findById(productCategoryAggregation.getParentId());
            if (null==parentProductCategoryAggregation) {
                throw new IllegalArgumentException("父分类无效");
            }
            layer.setValue(parentProductCategoryAggregation.getLayer().getValue()+1);
        }
        productCategoryAggregation.setLayer(layer);
        productCategoryRepository.save(productCategoryAggregation);
    }

}
