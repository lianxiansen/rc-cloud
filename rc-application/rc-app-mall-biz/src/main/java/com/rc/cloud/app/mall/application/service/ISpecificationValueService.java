package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.mall.infrastructure.persistence.po.SpecificationValue;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
public interface ISpecificationValueService extends IService<SpecificationValue> {

    SpecificationValue getSpecificationValueByKeyIdAndValueId(int specId, int specValueId);

    Integer saveSpecificationValue(SpecificationValue specificationValue);
}
