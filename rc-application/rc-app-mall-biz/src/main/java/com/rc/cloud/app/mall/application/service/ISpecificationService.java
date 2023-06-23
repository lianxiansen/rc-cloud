package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
public interface ISpecificationService extends IService<Specification> {

    Specification getSpecificationBySpecId(int specId);

    Specification getSpecificationBySpecNameFromAlibaba(String specName);

    Integer saveSpecification(Specification specification);
}
