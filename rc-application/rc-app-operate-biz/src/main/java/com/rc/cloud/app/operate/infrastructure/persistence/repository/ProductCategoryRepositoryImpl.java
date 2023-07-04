package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;



    /**
     * 津贴用的
     *
     * @return
     */
    @Override
    public List<ProductCategory> getFirstList(Locked locked, Layer layer, Parent parent) {
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("IsLock", locked.getFlag());
        wrapper.eq("Layer", layer.getValue());
        wrapper.eq("ParentID", parent.getId());
        wrapper.eq("deleted", '0');
        wrapper.orderByAsc("SortID");
        List<ProductCategory> list = new ArrayList<>();
        productCategoryMapper.selectList(wrapper).forEach(item -> {
            list.add(convert2ProductCategoryAggregation(item));
        });
        return list;
    }

    @Override
    public ProductCategoryId nextId() {
        return new ProductCategoryId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ProductCategory findById(ProductCategoryId productCategoryId) {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductCategoryDO::getId, productCategoryId.id());
        ProductCategoryDO productCategoryDO = this.productCategoryMapper.selectOne(wrapper);
        return convert2ProductCategoryAggregation(productCategoryDO);
    }

    @Override
    public List<ProductCategory> findAll() {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        List<ProductCategoryDO> list = this.productCategoryMapper.selectList(wrapper);
        List<ProductCategory> resultList=new ArrayList<>();
        list.forEach(productCategoryDO->{
            resultList.add(convert2ProductCategoryAggregation(productCategoryDO));
        });
        return resultList;
    }

    @Override
    public void save(ProductCategory productCategory) {
        ProductCategoryDO productCategoryDO = ProductCategoryConvert.convert2ProductCategoryDO(productCategory);
        this.productCategoryMapper.insert(productCategoryDO);
    }

    @Override
    public void remove(ProductCategory productCategory) {
        removeById(productCategory.getId());
    }

    @Override
    public void removeById(ProductCategoryId productCategoryId) {
        this.productCategoryMapper.deleteById(productCategoryId.id());
    }

    @Override
    public boolean existsChild(ProductCategoryId productCategoryId) {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductCategoryDO::getParentId, productCategoryId.id());
        return this.productCategoryMapper.exists(wrapper);
    }

    /**
     * 持久化对象转领域对象
     * @param productCategoryDO
     * @return
     */
    private ProductCategory convert2ProductCategoryAggregation(ProductCategoryDO productCategoryDO ){
        ProductCategoryId id = new ProductCategoryId(productCategoryDO.getId());
        TenantId tenantId = new TenantId(productCategoryDO.getTenantId());
        ChName name = new ChName(productCategoryDO.getName());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(productCategoryDO.getEnglishName()));
        productCategory.setIcon(new Icon(productCategoryDO.getIcon()));
        productCategory.setEnabled(new Enabled(productCategoryDO.getEnabledFlag()));
        productCategory.setPage(new Page(productCategoryDO.getProductCategoryPageImage(), productCategoryDO.getProductListPageImage()));
        productCategory.setSort(new Sort(productCategoryDO.getSortId()));
        if(null!=productCategoryDO.getParentId()){
            productCategory.setParentId(new ProductCategoryId(productCategoryDO.getParentId()));
        }
        return productCategory;
    }

}
