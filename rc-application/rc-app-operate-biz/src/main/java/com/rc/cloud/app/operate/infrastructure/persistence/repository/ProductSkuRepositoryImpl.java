package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.rc.cloud.app.operate.domain.productsku.ProductSkuEntity;
import com.rc.cloud.app.operate.domain.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSkuRepositoryImpl implements ProductSkuRepository{


    @Autowired
    ProductSkuMapper productSkuMapper;

    @Override
    public List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getProductId, productId.id());
        List<ProductSkuDO> productSkuDOList = this.productSkuMapper.selectList(wrapper);

        List<ProductSkuEntity> resList =new ArrayList<>();
        for (ProductSkuDO productSkuDO : productSkuDOList) {
           // ProductSkuEntity productSkuEntity=new ProductSkuEntity();


          //  resList.add(productSkuEntity);
        }
        return resList;
    }
}
