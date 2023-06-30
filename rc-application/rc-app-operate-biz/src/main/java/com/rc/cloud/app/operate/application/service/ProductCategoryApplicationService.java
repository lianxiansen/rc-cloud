package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductCategoryDTO;
import com.rc.cloud.app.operate.application.data.ProductCategoryData;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.service.ContainsProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.productcategory.service.FirstProductCategoryListDomainService;
import com.rc.cloud.app.operate.domain.productcategory.service.SaveProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Icon;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Name;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Page;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Sort;
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
    private SaveProductCategoryDomainService saveProductCategoryDomainService;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategoryData> getFirstList() {
        List<ProductCategoryData> list = new ArrayList<>();
        firstProductCategoryListDomainService.execute().forEach(item -> {
            list.add(ProductCategoryData.from(item));
        });
        return list;
    }

    public void createProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(productCategoryDTO.getTenantId());
        Name name = new Name(productCategoryDTO.getName());
        ProductCategoryAggregation productCategoryAggregation = new ProductCategoryAggregation(id, tenantId, name);
        productCategoryAggregation.setIcon(new Icon(productCategoryDTO.getIcon()));
        productCategoryAggregation.setPage(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategoryDTO.getProductListPageImage()));
        productCategoryAggregation.setSort(new Sort(productCategoryDTO.getSortId()));
        saveProductCategoryDomainService.execute(productCategoryAggregation);

    }
}
