package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRebuildFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductCategoryPO;
import com.rc.cloud.common.core.annotation.Convert;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
@Convert
public class ProductCategoryConvert {
    @Autowired
    private ProductCategoryRebuildFactory productCategoryRebuildFactory;

    public ProductCategoryPO convert2ProductCategoryDO(ProductCategory source) {
        if (Objects.isNull(source)) {
            return null;
        }
        ProductCategoryPO target = new ProductCategoryPO();
        target.setId(source.getId().id());
        target.setProductCategoryPageImage(source.getPage().getCategoryImage());
        target.setProductListPageImage(source.getPage().getListImage());
        target.setLayer(source.getLayer().getValue());
        target.setEnabledFlag(source.getEnabled().value());
        target.setIcon(source.getIcon().getPictureUrl());
        target.setName(source.getChName().value());
        target.setEnglishName(source.getEnName().value());
        target.setParentId(source.getParentId() == null ? "" : source.getParentId().id());
        target.setSort(source.getSort().getValue());
        target.setCreateTime(source.getCreateTime().getTime());
        return target;
    }

    public ProductCategory convert2ProductCategory(ProductCategoryPO productCategoryPO) {
        if (Objects.isNull(productCategoryPO)) {
            return null;
        }
        ProductCategoryId id = new ProductCategoryId(productCategoryPO.getId());
        TenantId tenantId = new TenantId(productCategoryPO.getTenantId());
        ChName name = new ChName(productCategoryPO.getName());
        CreateTime createTime = new CreateTime(productCategoryPO.getCreateTime());
        ProductCategoryRebuildFactory.ProductCategoryRebuilder rebuilder = productCategoryRebuildFactory.create(id, tenantId, name, createTime);

        rebuilder.enName(new EnName(productCategoryPO.getEnglishName()));
        rebuilder.icon(new Icon(productCategoryPO.getIcon()));
        if (Objects.nonNull(productCategoryPO.getEnabledFlag())) {
            rebuilder.setEnabled(new Enabled(productCategoryPO.getEnabledFlag()));
        }
        rebuilder.page(new Page(productCategoryPO.getProductCategoryPageImage(), productCategoryPO.getProductListPageImage()));
        rebuilder.sort(new Sort(productCategoryPO.getSort()));
        rebuilder.layer(new Layer(productCategoryPO.getLayer()));
        if (StringUtils.isNotEmpty(productCategoryPO.getParentId())) {
            rebuilder.parentId(new ProductCategoryId(productCategoryPO.getParentId()));
        }
        return rebuilder.rebuild();
    }

}
