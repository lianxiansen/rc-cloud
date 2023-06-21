package com.rc.cloud.app.mall.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rc.cloud.app.mall.application.service.ISpecificationService;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.SpecificationMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.po.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 通过规格项ID获取规格项
     * @param specId 规格项ID
     * @return 规格项
     */
    @Override
    public Specification getSpecificationBySpecId(int specId) {
        QueryWrapper<Specification> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", specId);
        return specificationMapper.selectOne(wrapper);
    }

    /**
     * 通过规格项名称获取1688的规格项
     * @param specName 规格项名称
     * @return 规格项
     */
    @Override
    public Specification getSpecificationBySpecNameFromAlibaba(String specName) {
        QueryWrapper<Specification> wrapper = new QueryWrapper<>();
        wrapper.eq("Title", specName);
        wrapper.eq("MerchantID", -1688);
        return specificationMapper.selectOne(wrapper);
    }

    /**
     * 保存规格
     * @return 影响的行数
     */
    @Override
    public Integer saveSpecification(Specification specification) {
        if (specification.getId() > 0) {
            QueryWrapper<Specification> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", specification.getId());
            return specificationMapper.update(specification, wrapper);
        } else {
            return specificationMapper.insert(specification);
        }
    }
}
