package com.rc.cloud.resource.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfigRepository;
import com.rc.cloud.resource.infrastructure.persistence.converter.OssConfigConverter;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssConfigBO;
import com.rc.cloud.resource.infrastructure.persistence.mapper.SysOssConfigMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * oss-Repository实现类
 *
 * @author hqf@rc
 * @date 2022-04-25 08:38
 **/
@Repository
public class OssConfigRepositoryImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfigBO> implements OssConfigRepository, IService<SysOssConfigBO> {

    @Override
    public OssConfig find(String id) {
        SysOssConfigBO sysOssConfigBO = this.getById(id);
        return OssConfigConverter.toOssConfig(sysOssConfigBO);
    }

    @Override
    public Boolean store(OssConfig ossConfig) {
        return this.saveOrUpdate(OssConfigConverter.fromOssConfig(ossConfig));
    }

    @Override
    public int store(OssConfig ossConfig, LambdaUpdateWrapper<SysOssConfigBO> luw) {
        return this.baseMapper.update(OssConfigConverter.fromOssConfig(ossConfig), luw);
    }

    @Override
    public Page<OssConfigDTO> store(PageQuery pageQuery, LambdaQueryWrapper<SysOssConfigBO> luw) {
        return baseMapper.selectVoPage(pageQuery.build(), luw);
    }

    @Override
    public void remove(String id) {
        this.removeById(id);
    }

    @Override
    public List<OssConfig> selectList() {
        List<SysOssConfigBO> sysOssConfigBOS = baseMapper.selectList();
        List<OssConfig> ossConfigs = new ArrayList<>();
        for (SysOssConfigBO item :
                sysOssConfigBOS) {
            OssConfig ossConfig = OssConfigConverter.toOssConfig(item);
            ossConfigs.add(ossConfig);
        }
        return ossConfigs;
    }

    @Override
    public Integer deleteBatchIds(Collection<String> ids) {
        return this.baseMapper.deleteBatchIds(ids);
    }
}
