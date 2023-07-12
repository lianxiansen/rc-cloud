package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.dto.ProductSkuAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuGetDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;
import com.rc.cloud.app.operate.domain.model.productsku.*;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SupplyPrice;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSkuApplicationService
{

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private TenantService tenantService;

    @Resource
    private IdRepository idRepository;
    /**
     * 修改sku
     * @param productSkuSaveDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateProductSku(ProductSkuSaveDTO productSkuSaveDTO){
        TenantId tenantId = new TenantId("");
        boolean exist = productSkuRepository.exist(new ProductSkuId(productSkuSaveDTO.getId()));
        ProductSku productSku = null;
        if (!exist) {
            throw new IllegalArgumentException("skuid有误");
        } else {
            productSku = productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
        }
        if (productSkuSaveDTO.getPrice() != null) {
            productSku.setPrice(new com.rc.cloud.app.operate.domain.model.productsku.valobj.Price(productSkuSaveDTO.getPrice()));
        }
        if (productSkuSaveDTO.getEnabledFlag() != null) {
            productSku.setEnabledFlag(productSkuSaveDTO.getEnabledFlag());
        }
        if (productSkuSaveDTO.getInventory() != null) {
            productSku.setInventory(new com.rc.cloud.app.operate.domain.model.productsku.valobj.Inventory(productSkuSaveDTO.getInventory()));
        }
        if (productSkuSaveDTO.getSort() != null) {
            productSku.setSort(new com.rc.cloud.app.operate.domain.model.productsku.valobj.Sort(productSkuSaveDTO.getSort()));
        }
        if (productSkuSaveDTO.getWeight() != null) {
            productSku.setWeight(new com.rc.cloud.app.operate.domain.model.productsku.valobj.Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))));
        }
        if (productSkuSaveDTO.getSupplyPrice() != null) {
            productSku.setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice()))));
        }
        //sku图片
        if (productSkuSaveDTO.getAlbums() != null) {
            List<ProductSkuImage> productSkuImageList = new ArrayList<>();
            int pos = 1;
            for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(idRepository.nextId()));
                productSkuImage.setSort(album.getSort());
                productSkuImage.setUrl(album.getUrl());
                pos++;
                productSkuImageList.add(productSkuImage);
            }
            productSku.skuImageList(productSkuImageList);
        }
        //sku属性
        if (productSkuSaveDTO.getAttributes() != null) {
            ProductSkuAttribute productSkuAttribute = new ProductSkuAttribute(
                    new ProductSkuAttributeId(idRepository.nextId()),
                    productSku.getId(), tenantId
            );
            for (ProductSkuAttributeSaveDTO attribute : productSkuSaveDTO.getAttributes()) {
                productSkuAttribute.addSkuAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
            }
            productSku.setProductSkuAttributeEntity(productSkuAttribute);
            productSkuRepository.updateProductSku(productSku);
        }

        return productSku.getId().id();

    }


    /**
     * 返回sku
     * @param productSkuGetDTO
     * @return
     */
    public ProductSkuBO getProductSku(ProductSkuGetDTO productSkuGetDTO){

        return null;
    }

    /**
     * 返回sku列表
     * @param productSkuGetDTO
     * @return
     */
    public List<ProductSkuBO> getProductSkuList(ProductSkuGetDTO productSkuGetDTO){

        return null;
    }


}
