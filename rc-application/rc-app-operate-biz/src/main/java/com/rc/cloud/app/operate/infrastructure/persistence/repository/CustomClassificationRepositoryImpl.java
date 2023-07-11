package com.rc.cloud.app.operate.infrastructure.persistence.repository;


import com.rc.cloud.app.operate.infrastructure.persistence.mapper.CustomClassificationMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.CustomClassificationPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomClassificationRepositoryImpl
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

}
