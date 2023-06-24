package com.rc.cloud.app.product.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.product.application.service.ISpecificationValueService;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.SpecificationValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
@Service
public class SpecificationValueServiceImpl extends ServiceImpl<SpecificationValueMapper, SpecificationValue> implements ISpecificationValueService {

    @Autowired
    private SpecificationValueMapper specificationValueMapper;

    /**
     * 获取指定规格值
     * @param specId 规格项ID
     * @param specValueId 规格值ID
     * @return 规格值实例
     */
    @Override
    public SpecificationValue getSpecificationValueByKeyIdAndValueId(int specId, int specValueId) {
        QueryWrapper<SpecificationValue> wrapper = new QueryWrapper<>();
        wrapper.eq("SpecificationID", specId);
        wrapper.eq("ID", specValueId);
        return specificationValueMapper.selectOne(wrapper);
    }

    /**
     * 保存规格值
     * @param specificationValue 规格值实例
     * @return 规格值ID
     */
    @Override
    public Integer saveSpecificationValue(SpecificationValue specificationValue) {
        if (specificationValue.getId() > 0) {
            QueryWrapper<SpecificationValue> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", specificationValue.getId());
            return specificationValueMapper.update(specificationValue, wrapper);
        } else {
            return specificationValueMapper.insert(specificationValue);
        }
    }
}
