package com.rc.cloud.app.operate.domain.product.service;

import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductFactory;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProductSaveService
 * @Author: liandy
 * @Date: 2023/6/24 09:18
 * @Description: TODO
 */
@Service
public class SaveProductDomainService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private ProductFactory productFactory;

    public void execute(TenantId tenantId, Name name, Remark remark, Tag tag, BrandId brandId, ProductCategoryId productCategoryId,
                        CustomClassification customClassification, Newest newest, Explosives explosives, Recommend recommend, Open open,
                        OnshelfStatus onshelfStatus, Enable enable, Video video, MasterImage masterImage, Type type, List<Image> productImages) {
        ProductAggregation productEntry= productFactory.createProduct(tenantId,name,remark,tag,brandId,productCategoryId,customClassification,newest,
                explosives,recommend,open,onshelfStatus,enable,video,masterImage,type,productImages);
        productRepository.saveProductEntry(productEntry);
    }
}
