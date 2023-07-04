package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.common.DomainEventPublisher;
import com.rc.cloud.app.operate.domain.common.DomainEventSubscriber;
import com.rc.cloud.app.operate.domain.model.product.ProductFactory;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.event.ProductCreatedEvent;
import com.rc.cloud.app.operate.domain.model.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
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
    private ProductRepository productRepository;

    @Autowired
    private ProductFactory productFactory;

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
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId() + "");
        Name name = new Name(productSaveDTO.getName());
        Remark remark = new Remark(productSaveDTO.getRemark());
        Tag tag = new Tag(productSaveDTO.getTag());
        BrandId brandId = new BrandId(productSaveDTO.getBrandId());
        CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
        //CustomClassification customClassification = null;
        Newest newest = new Newest(productSaveDTO.isNewFlag());
        Explosives explosives = null;
        if(productSaveDTO.isExplosivesFlag()){
            explosives= new Explosives(productSaveDTO.isExplosivesFlag(),productSaveDTO.getExplosivesImage());
        }
        Recommend recommend = new Recommend(productSaveDTO.isRecommendFlag());
        Open open = new Open(productSaveDTO.isPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(productSaveDTO.getOnShelfStatus());
        Enable enable = new Enable(productSaveDTO.isEnabledFlag());
        Video video = new Video(productSaveDTO.getVideoUrl(),productSaveDTO.getVideoImg()
        ,productSaveDTO.getInstallVideoUrl(),productSaveDTO.getInstallVideoImg());
        List<Image> productImages = new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item -> {
            Image image = new Image(item.getImage());
            productImages.add(image);
        });
//        if (productSaveDTO.getId() > 0) {
//            ProductAggregation productEntry= productFactory.createProduct(tenantId,name,remark,tag,brandId,productCategoryId,customClassification,newest,
//                    explosives,recommend,open,onshelfStatus,enable,video,masterImage,type,productImages);
//            productRepository.saveProductEntry(productEntry);
//
//        } else {
//            ProductId productId = new ProductId(productSaveDTO.getId() + "");
//            ProductAggregation productEntity = productRepository.findById(productId);
//            if (null == name) {
//                name=productEntity.getName();
//            }
//
//            ProductAggregation productEntry= productRepository.findById(productId);
//            //TODO 应用层产品修改逻辑
//
//        }


    }


}
