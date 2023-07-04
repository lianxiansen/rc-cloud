package com.rc.cloud.app.operate.infrastructure.persistence.repository;


import com.rc.cloud.app.operate.domain.model.product.valobj.CustomClassification;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.CustomClassificationMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.CustomClassificationDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
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
    public List<CustomClassificationDO> getCustomClassificationListByTenantId(String tenantId){

        LambdaQueryWrapperX<CustomClassificationDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(CustomClassificationDO::getTenantId, tenantId);
        List<CustomClassificationDO> customClassificationDOList
                = this.customClassificationMapper.selectList(wrapper);
        return customClassificationDOList;
    }

}
