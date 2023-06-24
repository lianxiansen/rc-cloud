package com.rc.cloud.app.product.application.service;

import com.baomidou.mybatisplus.extension.service.IService;

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
