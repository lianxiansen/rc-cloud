package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.ProductSkuAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuGetDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.*;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SupplyPrice;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.pojo.PageResult;
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
    public ProductSkuBO updateProductSku(ProductSkuSaveDTO productSkuSaveDTO){

        ProductSkuId productSkuId = new ProductSkuId(productSkuSaveDTO.getId());
        //修改
        ProductSku productSku = productSkuRepository.findById(productSkuId);
        if (null == productSku) {
            throw new IllegalArgumentException("未找到当前商品SKU");
        }
        productSku= ProductSkuConvert.convert(
                new ProductSkuId(productSkuSaveDTO.getId())
                ,new ProductId(productSkuSaveDTO.getProductId())
                ,new TenantId(productSkuSaveDTO.getTenantId())
                ,productSkuSaveDTO,false,productSku);

        productSkuRepository.updateProductSku(productSku);

        return ProductSkuConvert.convert(productSku);

    }


    /**
     * 返回sku
     * @param productSkuGetDTO
     * @return
     */
    public ProductSkuBO getProductSku(ProductSkuGetDTO productSkuGetDTO){
        ProductSku productSku= productSkuRepository.findById(new ProductSkuId(productSkuGetDTO.getProductSkuId()));
        return ProductSkuConvert.convert(productSku);
    }

    /**
     * 返回sku列表
     * @param productSkuGetDTO
     * @return
     */
    public List<ProductSkuBO> getProductSkuList(ProductSkuGetDTO productSkuGetDTO){
        List<ProductSku> productSkuList = productSkuRepository.getProductSkuListByProductId(new ProductId(productSkuGetDTO.getProductId()));
        return ProductSkuConvert.convertList(productSkuList);
    }


}
