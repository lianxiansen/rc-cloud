package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommend;
import com.rc.cloud.app.operate.domain.model.productrecommend.ProductRecommendRepository;
import com.rc.cloud.app.operate.domain.model.productrecommend.identifier.ProductRecommendId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductRecommendConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductRecommendMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductRecommendPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Service
public class ProductRecommendRepositoryImpl implements ProductRecommendRepository {

    @Autowired
    private ProductRecommendMapper productRecommendMapper;


    @Override
    public boolean save(ProductRecommend ProductRecommend) {
        ProductRecommendPO po = ProductRecommendConvert.convert2ProductRecommendPO(ProductRecommend);
        String idVal = po.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(productRecommendMapper.selectById((Serializable) idVal))) {
            return productRecommendMapper.updateById(po) > 0;
        }
        return productRecommendMapper.insert(po) > 0;
    }


    @Override
    public ProductRecommend findById(ProductRecommendId productRecommendId) {
        ProductRecommendPO productRecommendPO = productRecommendMapper.selectById((Serializable) productRecommendId.id());
        if (Objects.isNull(productRecommendPO)) {
            return null;
        }
        return ProductRecommendConvert.convert2ProductRecommend(productRecommendPO);
    }

    @Override
    public boolean removeById(ProductRecommendId productRecommendId) {
        return productRecommendMapper.deleteById(productRecommendId.id().toString()) > 0;
    }


    @Override
    public List<ProductRecommend> findListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductRecommendPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductRecommendPO::getProductId, productId.id());
        List<ProductRecommendPO> pos = productRecommendMapper.selectList(wrapper);
        return ProductRecommendConvert.convert2ProductRecommendBatch(pos);
    }
}
