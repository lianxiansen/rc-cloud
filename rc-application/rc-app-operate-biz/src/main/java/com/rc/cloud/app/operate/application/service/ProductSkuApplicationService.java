package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.dto.ProductSkuGetDTO;
import com.rc.cloud.app.operate.application.dto.ProductSkuSaveDTO;

import java.util.List;

public interface ProductSkuApplicationService
{
    /**
     * 修改sku
     * @param productSkuSaveDTO
     * @return
     */

    ProductSkuBO updateProductSku(ProductSkuSaveDTO productSkuSaveDTO);

    /**
     * 返回sku
     * @param productSkuGetDTO
     * @return
     */
     ProductSkuBO getProductSku(ProductSkuGetDTO productSkuGetDTO);
    /**
     * 返回sku列表
     * @param productSkuGetDTO
     * @return
     */
     List<ProductSkuBO> getProductSkuList(ProductSkuGetDTO productSkuGetDTO);

}
