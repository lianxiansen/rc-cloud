package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryFactory;
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
    @Autowired
    private ProductCategoryFactory productCategoryBuilderFactory;

    public void saveList(List<ProductCategoryAggregation> all){
        List<ProductCategoryDO> list=new ArrayList<>();
        all.forEach(item->{
            ProductCategoryDO productCategoryDO = ProductCategoryConvert.convert2ProductCategoryDO(item);
            list.add(productCategoryDO);
        });
        productCategoryMapper.updateBatch(list,list.size());
    }

    /**
     * 津贴用的
     *
     * @return
     */
    @Override
    public List<ProductCategoryAggregation> getFirstList(Locked locked, Layer layer, Parent parent) {
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("IsLock", locked.getFlag());
        wrapper.eq("Layer", layer.getValue());
        wrapper.eq("ParentID", parent.getId());
        wrapper.eq("deleted", '0');
        wrapper.orderByAsc("SortID");
        List<ProductCategoryAggregation> list = new ArrayList<>();
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
    public ProductCategoryAggregation findById(ProductCategoryId productCategoryId) {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductCategoryDO::getId, productCategoryId.id());
        ProductCategoryDO productCategoryDO = this.productCategoryMapper.selectOne(wrapper);
        return convert2ProductCategoryAggregation(productCategoryDO);
    }

    @Override
    public List<ProductCategoryAggregation> findAll() {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        List<ProductCategoryDO> list = this.productCategoryMapper.selectList(wrapper);
        List<ProductCategoryAggregation> resultList=new ArrayList<>();
        list.forEach(productCategoryDO->{
            resultList.add(convert2ProductCategoryAggregation(productCategoryDO));
        });
        return resultList;
    }

    @Override
    public void save(ProductCategoryAggregation productCategoryAggregation) {
        ProductCategoryDO productCategoryDO = ProductCategoryConvert.convert2ProductCategoryDO(productCategoryAggregation);
        this.productCategoryMapper.insert(productCategoryDO);
    }

    /**
     * 持久化对象转领域对象
     * @param productCategoryDO
     * @return
     */
    private ProductCategoryAggregation convert2ProductCategoryAggregation(ProductCategoryDO productCategoryDO ){
        ProductCategoryId id = new ProductCategoryId(productCategoryDO.getId());
        TenantId tenantId = new TenantId(productCategoryDO.getTenantId());
        ChName name = new ChName(productCategoryDO.getName());
        ProductCategoryFactory.ProductCategoryReBuilder rebuilder = productCategoryBuilderFactory.reBuilder(id, tenantId, name);
        rebuilder.enName(new EnName(productCategoryDO.getEnglishName()));
        rebuilder.icon(new Icon(productCategoryDO.getIcon()));
        rebuilder.enabled(new Enabled(productCategoryDO.getEnabledFlag()));
        rebuilder.page(new Page(productCategoryDO.getProductCategoryPageImage(), productCategoryDO.getProductListPageImage()));
        rebuilder.sort(new Sort(productCategoryDO.getSortId()));
        if(null!=productCategoryDO.getParentId()){
            rebuilder.parentId(new ProductCategoryId(productCategoryDO.getParentId()));
        }
        return rebuilder.rebuild();
    }

}
