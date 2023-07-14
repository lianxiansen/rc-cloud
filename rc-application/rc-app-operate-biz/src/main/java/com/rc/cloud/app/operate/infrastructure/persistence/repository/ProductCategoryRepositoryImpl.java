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
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    private ProductMapper productMapper;



    /**
     * 津贴用的
     *
     * @return
     */
    @Override
    public List<ProductCategory> getFirstList(Locked locked, Layer layer, Parent parent) {
        QueryWrapper<ProductCategoryPO> wrapper = new QueryWrapper<>();
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
    public ProductCategory findById(ProductCategoryId productCategoryId) {
        LambdaQueryWrapperX<ProductCategoryPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductCategoryPO::getId, productCategoryId.id());
        ProductCategoryPO productCategoryPO = this.productCategoryMapper.selectOne(wrapper);
        return convert2ProductCategoryAggregation(productCategoryPO);
    }

    @Override
    public List<ProductCategory> findAll() {
        LambdaQueryWrapperX<ProductCategoryPO> wrapper = new LambdaQueryWrapperX<>();
        List<ProductCategoryPO> list = this.productCategoryMapper.selectList(wrapper);
        List<ProductCategory> resultList=new ArrayList<>();
        list.forEach(productCategoryPO ->{
            resultList.add(convert2ProductCategoryAggregation(productCategoryPO));
        });
        return resultList;
    }

    @Override
    public boolean save(ProductCategory productCategory) {
        ProductCategoryPO productCategoryPO = ProductCategoryConvert.convert2ProductCategoryDO(productCategory);
        if(this.productCategoryMapper.insert(productCategoryPO)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(ProductCategory productCategory) {
        return removeById(productCategory.getId());
    }

    @Override
    public boolean removeById(ProductCategoryId productCategoryId) {
        if(this.productCategoryMapper.deleteById(productCategoryId.id())>0){
            return true;
        }
        return false;
    }


    @Override
    public boolean existsByParentId(ProductCategoryId productCategoryId) {
        LambdaQueryWrapperX<ProductCategoryPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductCategoryPO::getParentId, productCategoryId.id());
        return this.productCategoryMapper.exists(wrapper);
    }
    /**
     * 持久化对象转领域对象
     * @param productCategoryPO
     * @return
     */
    private ProductCategory convert2ProductCategoryAggregation(ProductCategoryPO productCategoryPO){
        ProductCategoryId id = new ProductCategoryId(productCategoryPO.getId());
        TenantId tenantId = new TenantId(productCategoryPO.getTenantId());
        ChName name = new ChName(productCategoryPO.getName());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(productCategoryPO.getEnglishName()));
        productCategory.setIcon(new Icon(productCategoryPO.getIcon()));
        productCategory.setEnabled(new Enabled(productCategoryPO.getEnabledFlag()));
        productCategory.setPage(new Page(productCategoryPO.getProductCategoryPageImage(), productCategoryPO.getProductListPageImage()));
        productCategory.setSort(new Sort(productCategoryPO.getSortId()));
        if(null!= productCategoryPO.getParentId()){
            productCategory.setParentId(new ProductCategoryId(productCategoryPO.getParentId()));
        }
        return productCategory;
    }

}
