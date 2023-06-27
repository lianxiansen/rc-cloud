package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.category.ProductCategoryEntity;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.valobj.*;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategory;
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
public class ProductCategoryRepositoryImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryRepository {
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
    public List<ProductCategoryEntity> getFirstList(ProductCategoryLocked locked, ProductCategoryLayer layer, ProductCategoryParent parent) {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("IsLock", locked.getFlag());
            wrapper.eq("Layer", layer.getLevel());
            wrapper.eq("ParentID", parent.getId());
            wrapper.orderByAsc("SortID");
            List<ProductCategoryEntity> list=new ArrayList<>();
            productCategoryMapper.selectList(wrapper).forEach(item->{
                list.add(new ProductCategoryConvert().convertToProductCategoryEntry(item));
            });
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ProductCategoryId nextId(){
        return new ProductCategoryId(remoteIdGeneratorService.uidGenerator());
    }


}
