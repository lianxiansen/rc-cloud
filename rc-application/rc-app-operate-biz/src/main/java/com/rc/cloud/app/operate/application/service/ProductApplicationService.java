package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.ProductFactory;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.common.DomainEventPublisher;
import com.rc.cloud.app.operate.domain.common.DomainEventSubscriber;
import com.rc.cloud.app.operate.domain.model.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.event.ProductCreatedEvent;
import com.rc.cloud.app.operate.domain.model.product.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
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
            ProductAggregation productEntry= productFactory.createProduct(tenantId,name,remark,tag,brandId,productCategoryId,customClassification,newest,
                    explosives,recommend,open,onshelfStatus,enable,video,masterImage,type,productImages);
            productRepository.saveProductEntry(productEntry);

        } else {
            ProductId productId = new ProductId(productSaveDTO.getId() + "");
            ProductAggregation productEntity = productRepository.findById(productId);
            if (null == name) {
                name=productEntity.getName();
            }

            ProductAggregation productEntry= productRepository.findById(productId);
            //TODO 应用层产品修改逻辑

        }


    }


//    public List<ProductListDTO> getProductList(ProductListQueryDTO productListQueryDTO){
//
//        PageResult<ProductDO> productPageList = productRepository.getProductPageList(productListQueryDTO);
//
//
//       // ProductListDTO res = new ProductListDTO();
//        //...
//        //resList.add(res);
//        return productPageList;
//    }

}
