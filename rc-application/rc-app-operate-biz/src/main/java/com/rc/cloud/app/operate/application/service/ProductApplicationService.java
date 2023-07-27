package com.rc.cloud.app.operate.application.service;

import cn.hutool.core.collection.CollectionUtil;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDictConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandDomainService;
import com.rc.cloud.app.operate.domain.model.product.*;
import com.rc.cloud.app.operate.domain.model.product.identifier.*;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailDomainService;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictDomainService;
import com.rc.cloud.app.operate.domain.model.productsku.*;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ProductService
 * @Author: liandy
 * @Date: 2023/6/23 15:51
 * @Description:
 */
public interface ProductApplicationService {


     void validateTenantId(TenantId tenantId);

    /**
     * 创建商品
     *
     *
     * @param productSaveDTO
     * @return
     */
     ProductBO createProduct(ProductSaveDTO productSaveDTO);
    /**
     * 修改商品
     *
     * @param productSaveDTO
     * @return
     */
    ProductBO updateProduct(ProductSaveDTO productSaveDTO);

    /**
     * 获取商品
     * TODO
     * @param productQueryDTO
     * @return
     */
    ProductBO getProduct(ProductQueryDTO productQueryDTO);

    /**
     * 获取商品列表
     *
     * @return
     */
     PageResult<ProductBO> getProductList(ProductListQueryDTO query);

     int changeNewStatus(String productId, boolean newFlag);


     int changeOnShelfStatus(String productId, int onShelfStatus);

     int changePublicStatus(String productId, boolean publicFlag);

     int changeRecommendStatus(String productId, boolean recommendFlag);


}
