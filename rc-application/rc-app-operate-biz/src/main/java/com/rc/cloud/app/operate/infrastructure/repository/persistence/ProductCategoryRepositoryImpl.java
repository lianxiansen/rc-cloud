package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductCategoryPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private ProductCategoryConvert productCategoryConvert;
    @Override
    public ProductCategory findById(ProductCategoryId productCategoryId) {
        ProductCategoryPO productCategoryPO = this.productCategoryMapper.selectById((Serializable) productCategoryId.id());
        return productCategoryConvert.convert2ProductCategory(productCategoryPO);
    }

    @Override
    public List<ProductCategory> findAll() {
        LambdaQueryWrapperX<ProductCategoryPO> wrapper = new LambdaQueryWrapperX<>();
        List<ProductCategoryPO> list = this.productCategoryMapper.selectList(wrapper);
        List<ProductCategory> resultList=new ArrayList<>();
        list.forEach(productCategoryPO ->{
            resultList.add(productCategoryConvert.convert2ProductCategory(productCategoryPO));
        });
        return resultList;
    }

    @Override
    public boolean save(ProductCategory productCategory) {
        ProductCategoryPO po = productCategoryConvert.convert2ProductCategoryDO(productCategory);
        String idVal = productCategory.getId().id();
        return !StringUtils.checkValNull(idVal) && !Objects.isNull(productCategoryMapper.selectById((Serializable) idVal)) ? productCategoryMapper.updateById(po) > 0 : productCategoryMapper.insert(po) > 0;
    }

    @Override
    public boolean remove(ProductCategory productCategory) {
        return removeById(productCategory.getId());
    }

    @Override
    public boolean removeById(ProductCategoryId productCategoryId) {
        if(this.productCategoryMapper.deleteById((Serializable) productCategoryId.id())>0){
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

}
