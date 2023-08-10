package com.rc.cloud.app.operate.infrastructure.repository.persistence;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationRepository;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.CustomClassificationConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.CustomClassificationMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CustomClassificationPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductCategoryPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomClassificationRepositoryImpl implements CustomClassificationRepository
{
    @Resource
    private CustomClassificationMapper customClassificationMapper;

    @Autowired
    private CustomClassificationConvert customClassificationConvert;

    @Override
    public List<CustomClassification> findAll() {
        LambdaQueryWrapperX<CustomClassificationPO> wrapper = new LambdaQueryWrapperX<>();
        List<CustomClassificationPO> list = this.customClassificationMapper.selectList(wrapper);
        List<CustomClassification> resultList=new ArrayList<>();
        list.forEach(po ->{
            resultList.add(customClassificationConvert.convertDomain(po));
        });
        return resultList;
    }

    @Override
    public CustomClassification findById(CustomClassificationId customClassificationId) {
        CustomClassificationPO po = this.customClassificationMapper.selectById(customClassificationId.id());
        return customClassificationConvert.convertDomain(po);
    }

    @Override
    public boolean save(CustomClassification customClassification) {
        CustomClassificationPO po = customClassificationConvert.convertPO(customClassification);
        String idVal = customClassification.getId().id();
        return !StringUtils.checkValNull(idVal)
                && !Objects.isNull(customClassificationMapper.selectById(idVal))
                ? customClassificationMapper.updateById(po) > 0
                : customClassificationMapper.insert(po) > 0;
    }

    @Override
    public boolean remove(CustomClassification customClassification) {
        return removeById(customClassification.getId());
    }

    @Override
    public boolean removeById(CustomClassificationId customClassificationId) {
        if(this.customClassificationMapper.deleteById(customClassificationId.id())>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByParentId(CustomClassificationId customClassificationId) {
        LambdaQueryWrapperX<CustomClassificationPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(CustomClassificationPO::getParentId, customClassificationId.id());
        return this.customClassificationMapper.exists(wrapper);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapperX<CustomClassificationPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(CustomClassificationPO::getName, name);
        return this.customClassificationMapper.exists(wrapper);
    }
}
