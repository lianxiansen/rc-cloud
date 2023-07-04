package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
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
    private TenantService tenantService;

    private void validateTenantId(TenantId tenantId){
        if(!tenantService.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateProduct(ProductSaveDTO productSaveDTO) {

        TenantId tenantId = new TenantId(productSaveDTO.getTenantId() + "");
        Name name = new Name(productSaveDTO.getName());
        Remark remark = new Remark(productSaveDTO.getRemark());
        Tag tag = new Tag(productSaveDTO.getTag());
        BrandId brandId = new BrandId(productSaveDTO.getBrandId());
        CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
        CustomClassification customClassification = new CustomClassification(productSaveDTO.getCustomClassificationId());
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


        boolean exist = productRepository.exist(new ProductId(productSaveDTO.getId()));
        Product product= null;
        if (!exist) {

            ProductId productId = productRepository.nextProductId();
            validateTenantId(tenantId);
           // product=new Product(productId,tenantId,name);
//            return productEntry;


         //   productRepository.saveProductEntry(productEntry);

        } else {

            product = productRepository.findById(new ProductId(productSaveDTO.getId()));

        }


    }


}
