package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Layer;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Locked;
import com.rc.cloud.app.operate.domain.productcategory.valobj.Parent;
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
public class ProductCategoryRepositoryImpl implements  ProductCategoryRepository {
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
    public List<ProductCategoryAggregation> getFirstList(Locked locked, Layer layer, Parent parent) {
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("IsLock", locked.getFlag());
        wrapper.eq("Layer", layer.getValue());
        wrapper.eq("ParentID", parent.getId());
        wrapper.eq("deleted", '0');
        wrapper.orderByAsc("SortID");
        List<ProductCategoryAggregation> list = new ArrayList<>();
        productCategoryMapper.selectList(wrapper).forEach(item -> {
            list.add(ProductCategoryConvert.convert2ProductCategoryAggregation(item));
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
        return ProductCategoryConvert.convert2ProductCategoryAggregation( this.productCategoryMapper.selectOne(wrapper));
    }

    @Override
    public List<ProductCategoryAggregation> findAll() {
        LambdaQueryWrapperX<ProductCategoryDO> wrapper = new LambdaQueryWrapperX<>();
        List<ProductCategoryDO> list = this.productCategoryMapper.selectList(wrapper);;
        List<ProductCategoryAggregation> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(ProductCategoryConvert.convert2ProductCategoryAggregation(item));
        });
        return result;
    }

    @Override
    public void save(ProductCategoryAggregation productCategoryAggregation) {
        ProductCategoryDO productCategoryDO = ProductCategoryConvert.convert2ProductCategoryDO(productCategoryAggregation);
        this.productCategoryMapper.insert(productCategoryDO);
    }

}
