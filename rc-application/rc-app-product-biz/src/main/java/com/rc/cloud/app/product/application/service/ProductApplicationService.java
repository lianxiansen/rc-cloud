package com.rc.cloud.app.product.application.service;

import com.rc.cloud.app.product.application.data.ProductSaveDTO;
import com.rc.cloud.app.product.domain.common.DomainEventPublisher;
import com.rc.cloud.app.product.domain.common.DomainEventSubscriber;
import com.rc.cloud.app.product.domain.product.event.ProductCreatedEvent;
import com.rc.cloud.app.product.domain.product.service.ProductSaveService;
import com.rc.cloud.app.product.domain.product.service.ProductUpdateService;
import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;
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
    private ProductSaveService productSaveService;
    @Autowired
    private ProductUpdateService productUpdateService;
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateProduct(ProductSaveDTO productSaveDTO){
        DomainEventPublisher.instance().reset();
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<ProductCreatedEvent>(){
            @Override
            public void handleEvent(ProductCreatedEvent aDomainEvent) {
                //TODO 领域事件处理
            }
            @Override
            public Class<ProductCreatedEvent> subscribedToEventType() {
                return ProductCreatedEvent.class;
            }
        });
        TenantId tenantId=new TenantId(productSaveDTO.getMerchant_id()+"");
        ProductName productName=new ProductName(productSaveDTO.getName());
        List<ProductImage> productImages=new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item->{
            ProductImage image=new ProductImage(item.getImage());
            productImages.add(image);
        });
        if(productSaveDTO.getId()>0){
            productSaveService.execute(tenantId,productName,null,null,productImages);
        }else{

        }


    }


}
