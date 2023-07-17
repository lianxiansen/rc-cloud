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
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssConfigDO;
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
public class OssConfigRepositoryImpl extends ServiceImpl<SysOssConfigMapper, SysOssConfigDO> implements OssConfigRepository, IService<SysOssConfigDO> {

    @Override
    public OssConfig find(String id) {
        SysOssConfigDO sysOssConfigDO = this.getById(id);
        return OssConfigConverter.toOssConfig(sysOssConfigDO);
    }

    @Override
    public Boolean store(OssConfig ossConfig) {
        return this.saveOrUpdate(OssConfigConverter.fromOssConfig(ossConfig));
    }

    @Override
    public int store(OssConfig ossConfig, LambdaUpdateWrapper<SysOssConfigDO> luw) {
        return this.baseMapper.update(OssConfigConverter.fromOssConfig(ossConfig), luw);
    }

    @Override
    public Page<OssConfigDTO> store(PageQuery pageQuery, LambdaQueryWrapper<SysOssConfigDO> luw) {
        return baseMapper.selectVoPage(pageQuery.build(), luw);
    }

    @Override
    public void remove(String id) {
        this.removeById(id);
    }

    @Override
    public List<OssConfig> selectList() {
        List<SysOssConfigDO> sysOssConfigDOS = baseMapper.selectList();
        List<OssConfig> ossConfigs = new ArrayList<>();
        for (SysOssConfigDO item :
                sysOssConfigDOS) {
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
