package com.rc.cloud.app.product.infrastructure.persistence.repository;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.product.appearance.request.ProductCategorySaveVO;
import com.rc.cloud.app.product.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.product.application.data.ProductShelfEnum;
import com.rc.cloud.app.product.domain.category.ProductCategoryEntry;
import com.rc.cloud.app.product.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.product.domain.category.valobj.*;
import com.rc.cloud.app.product.infrastructure.config.RedisKey;
import com.rc.cloud.app.product.infrastructure.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.product.infrastructure.persistence.po.Product;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductCategory;
import com.rc.cloud.app.product.infrastructure.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.rc.cloud.app.product.infrastructure.util.MapUtil.distinctByKey;


/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Service
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
    public List<ProductCategoryEntry> getFirstList(ProductCategoryLocked locked, ProductCategoryLayer layer,ProductCategoryParent parent) {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("IsLock", locked.getFlag());
            wrapper.eq("Layer", layer.getLevel());
            wrapper.eq("ParentID", parent.getId());
            wrapper.orderByAsc("SortID");
            List<ProductCategoryEntry> list=new ArrayList<>();
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
