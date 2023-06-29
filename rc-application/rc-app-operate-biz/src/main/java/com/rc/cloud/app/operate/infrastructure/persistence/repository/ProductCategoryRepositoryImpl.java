package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Repository
public class ProductCategoryRepositoryImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryDO> implements IService<ProductCategoryDO>, ProductCategoryRepository {
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
            list.add(new ProductCategoryConvert().convert2ProductCategoryDO(item));
        });
        return list;
    }

    @Override
    public ProductCategoryId nextId() {
        return new ProductCategoryId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ProductCategoryAggregation findById(ProductCategoryId productCategoryId) {
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", productCategoryId.id());
        wrapper.eq("deleted", '0');
        return ProductCategoryConvert.INSTANCE.convert2ProductCategoryDO(this.getOne(wrapper));
    }

    @Override
    public List<ProductCategoryAggregation> findAll() {
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", '0');
        List<ProductCategoryDO> list = this.list(wrapper);
        List<ProductCategoryAggregation> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(ProductCategoryConvert.INSTANCE.convert2ProductCategoryDO(item));
        });
        return result;
    }

    @Override
    public void save(ProductCategoryAggregation productCategoryAggregation) {
        Layer layer= null;
        ProductCategoryAggregation parentCategory = null;
        if (null != productCategoryAggregation.getParentId()) {
            parentCategory = this.findById(new ProductCategoryId(productCategoryAggregation.getParentId().id()));
            notNull(parentCategory, "父级商品分类id无效");
            layer=parentCategory.getLayer().addLayer(new Layer(1));
        }else{
            layer=new Layer();
        }
        productCategoryAggregation.setLayer(layer);
        ProductCategoryDO productCategoryDO = ProductCategoryConvert.INSTANCE.convert2ProductCategoryAggregation(productCategoryAggregation);
        this.save(productCategoryDO);
    }

}
