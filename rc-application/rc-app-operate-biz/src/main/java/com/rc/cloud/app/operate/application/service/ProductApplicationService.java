package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.common.DomainEventPublisher;
import com.rc.cloud.app.operate.domain.common.DomainEventSubscriber;
import com.rc.cloud.app.operate.domain.product.ProductEntity;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.event.ProductCreatedEvent;
import com.rc.cloud.app.operate.domain.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.service.SaveProductService;
import com.rc.cloud.app.operate.domain.product.service.UpdateProductService;
import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductService
 * @Author: liandy
 * @Date: 2023/6/23 15:51
 * @Description: TODO
 */
@Service
public class ProductApplicationService {

    @Autowired
    private SaveProductService saveProductService;

    @Autowired
    private UpdateProductService updateProductService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateProduct(ProductSaveDTO productSaveDTO) {
        DomainEventPublisher.instance().reset();
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<ProductCreatedEvent>() {
            @Override
            public void handleEvent(ProductCreatedEvent aDomainEvent) {
                //TODO 领域事件处理
            }
            @Override
            public Class<ProductCreatedEvent> subscribedToEventType() {
                return ProductCreatedEvent.class;
            }
        });
        TenantId tenantId = new TenantId(productSaveDTO.getMerchant_id() + "");
        Name name = new Name(productSaveDTO.getName());
        Remark remark = null;
        Tag tag = null;
        BrandId brandId = null;
        ProductCategoryId productCategoryId = null;
        CustomClassification customClassification = null;
        Newest newest = null;
        Explosives explosives = null;
        Recommend recommend = null;
        Open open = null;
        OnshelfStatus onshelfStatus = null;
        Enable enable = null;
        Video video = null;
        MasterImage masterImage = null;
        Type type = null;
        List<Image> productImages = new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item -> {
            Image image = new Image(item.getImage());
            productImages.add(image);
        });
        if (productSaveDTO.getId() > 0) {
            saveProductService.execute(tenantId, name, remark, tag, brandId, productCategoryId, customClassification, newest, explosives, recommend, open, onshelfStatus,
                    enable, video, masterImage, type, productImages);
        } else {
            ProductId productId = new ProductId(productSaveDTO.getId() + "");
            ProductEntity productEntity = productRepository.findById(productId);
            if (null == name) {
                name=productEntity.getName();
            }
            //TODO 应用层产品修改逻辑
            updateProductService.execute(productId, name, remark, tag, brandId, productCategoryId, customClassification, newest, explosives, recommend, open, onshelfStatus,
                    enable, video, masterImage, type, productImages);
        }


    }


}
