package com.rc.cloud.app.operate.domain.product.service;

import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProductUpdateService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class UpdateProductDomainService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantService tenantRepository;

    public void execute(ProductId productId,Name name, Remark remark, Tag tag, BrandId brandId, ProductCategoryId productCategoryId,
                        CustomClassification customClassification, Newest newest, Explosives explosives, Recommend recommend, Open open,
                        OnshelfStatus onshelfStatus, Enable enable, Video video, MasterImage masterImage, Type type, List<Image> productImages)  {
        ProductAggregation productEntry= productRepository.findById(productId);
        //TODO 领域层产品修改逻辑
        productEntry.setName(name);
        productRepository.saveProductEntry(productEntry);
    }
}
