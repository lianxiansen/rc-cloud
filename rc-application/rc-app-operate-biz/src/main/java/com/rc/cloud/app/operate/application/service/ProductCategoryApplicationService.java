package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductCategoryDTO;
import com.rc.cloud.app.operate.application.data.ProductCategoryData;
import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.category.service.ContainsProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.category.service.FirstProductCategoryListDomainService;
import com.rc.cloud.app.operate.domain.category.service.SaveProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.category.valobj.Icon;
import com.rc.cloud.app.operate.domain.category.valobj.Layer;
import com.rc.cloud.app.operate.domain.category.valobj.Name;
import com.rc.cloud.app.operate.domain.category.valobj.Page;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
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
    private FirstProductCategoryListDomainService firstProductCategoryListDomainService;

    @Autowired
    private ContainsProductCategoryDomainService containsProductCategoryListDomainService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    SaveProductCategoryDomainService saveProductCategoryDomainService;

    public List<ProductCategoryData> getFirstList() {
        List<ProductCategoryData> list = new ArrayList<>();
        firstProductCategoryListDomainService.execute().forEach(item -> {
            list.add(ProductCategoryData.from(item));
        });
        return list;
    }

    public void saveProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryId productCategoryId = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(productCategoryDTO.getTenantId());
        Name name = new Name(productCategoryDTO.getName(), productCategoryDTO.getEnglishName());
        Icon icon = new Icon(productCategoryDTO.getIcon());
        Page page = new Page(productCategoryDTO.getProductCategoryPageImage(), productCategoryDTO.getProductListPageImage());
        ProductCategoryAggregation productCategoryAggregation = new ProductCategoryAggregation(productCategoryId, tenantId, name);
        productCategoryAggregation.setIcon(icon);
        productCategoryAggregation.setPage(page);
        saveProductCategoryDomainService.execute(productCategoryAggregation);

    }
}
