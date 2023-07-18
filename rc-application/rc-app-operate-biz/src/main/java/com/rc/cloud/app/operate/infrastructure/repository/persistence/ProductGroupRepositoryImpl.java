package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductGroupItemMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductGroupMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupItemPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductGroupPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductGroupRepositoryImpl implements ProductGroupRepository {

    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Autowired
    private ProductGroupItemMapper productGroupItemMapper;

    @Override
    public boolean save(ProductGroup productGroup) {
        ProductGroupPO po = ProductGroupConvert.convert2ProductGroupPO(productGroup);
        saveProductGroupPO(po);
        List<ProductGroupItemPO> items = ProductGroupConvert.convert2ProductGroupItemPOBatch(productGroup.getProductGroupItems());
//        LambdaQueryWrapperX<ProductGroupItemPO> wrapper = new LambdaQueryWrapperX<>();
//        wrapper.eq(ProductGroupItemPO::getProductGroupId, productGroup.getId().id());
//        productGroupItemMapper.delete(wrapper);
//        productGroupItemMapper.insertBatch(items);
        items.forEach(item->{
            saveProductGroupItemPO(item);
        });
        return true;
    }

    @Override
    public boolean saveProductGroupPO(ProductGroupPO po) {
        String idVal = po.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(productGroupMapper.selectById((Serializable) idVal))) {
            return productGroupMapper.updateById(po) > 0;
        }
        return productGroupMapper.insert(po) > 0;
    }

    @Override
    public boolean saveProductGroupItemPO(ProductGroupItemPO po) {
        String idVal = po.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(productGroupItemMapper.selectById((Serializable) idVal))) {
            return productGroupItemMapper.updateById(po) > 0;
        }
        return productGroupItemMapper.insert(po) > 0;
    }

    @Override
    public boolean exists(ProductGroupId productGroupId) {
        LambdaQueryWrapperX<ProductGroupPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.in(ProductGroupPO::getId, productGroupId.id());
        return productGroupMapper.exists(wrapper);
    }

    @Override
    public ProductGroup findById(ProductGroupId productGroupId) {
        ProductGroupPO productGroupPO = productGroupMapper.selectById((Serializable) productGroupId.id());
        if (Objects.isNull(productGroupPO)) {
            return null;
        }
        List<ProductGroupItemPO> itemPOs = getProductGroupItemPOs(Arrays.asList(productGroupPO.getId()));
        Optional<ProductGroup> Optional = ProductGroupConvert.convert2ProductGroupWithItemsBatch(Arrays.asList(productGroupPO), itemPOs).stream().findFirst();
        if (Optional.isPresent()) {
            return Optional.get();
        }
        return null;
    }

    private List<ProductGroupItemPO> getProductGroupItemPOs(List<String> productGroupIds) {
        LambdaQueryWrapperX<ProductGroupItemPO> wrapper2 = new LambdaQueryWrapperX<>();
        wrapper2.in(ProductGroupItemPO::getProductGroupId, productGroupIds);
        List<ProductGroupItemPO> itemPOs = productGroupItemMapper.selectList(wrapper2);
        return itemPOs;
    }


    @Override
    public boolean removeById(ProductGroupId productGroupId) {
        return productGroupMapper.deleteById(productGroupId.id().toString()) > 0;
    }

    @Override
    public boolean itemExist(ProductGroupId productGroupId, ProductId productId) {
        return false;
    }

    @Override
    public List<ProductGroup> findAll(ProductId productId) {
        LambdaQueryWrapperX<ProductGroupPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductGroupPO::getProductId, productId.id());
        List<ProductGroupPO> pos = productGroupMapper.selectList(wrapper);
        List<String> productGroupIds = pos.stream().map(ProductGroupPO::getId).collect(Collectors.toList());
        List<ProductGroupItemPO> itemPOs =getProductGroupItemPOs(productGroupIds);
        return ProductGroupConvert.convert2ProductGroupWithItemsBatch(pos, itemPOs);
    }


}
