package com.rc.cloud.app.operate.infrastructure.repository.persistence;


import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationRepository;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.CustomClassificationMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.CustomClassificationPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class CustomClassificationRepositoryImpl implements CustomClassificationRepository
{
    private CustomClassificationMapper customClassificationMapper;

    /**
     * 获取自定义分类列表
     * @param tenantId
     * @return
     */
    public List<CustomClassificationPO> getCustomClassificationListByTenantId(String tenantId){

        LambdaQueryWrapperX<CustomClassificationPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(CustomClassificationPO::getTenantId, tenantId);
        List<CustomClassificationPO> customClassificationPOList
                = this.customClassificationMapper.selectList(wrapper);
        return customClassificationPOList;
    }

    @Override
    public void insertCustomClassification(CustomClassification customClassification) {

    }

    @Override
    public void updateCustomClassification(CustomClassification customClassification) {

    }

    @Override
    public CustomClassification findById(CustomClassificationId customClassificationId) {
        return null;
    }

    @Override
    public boolean exist(CustomClassificationId customClassificationId) {
        return false;
    }

    @Override
    public void removeCustomClassification(CustomClassificationId customClassificationId) {

    }
}
