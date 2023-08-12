package com.rc.cloud.resource.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.domain.model.oss.OssRepository;
import com.rc.cloud.resource.infrastructure.persistence.converter.OssConverter;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssBO;
import com.rc.cloud.resource.infrastructure.persistence.mapper.SysOssMapper;
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
public class OssRepositoryImpl extends ServiceImpl<SysOssMapper, SysOssBO> implements OssRepository, IService<SysOssBO> {

    @Override
    public Oss find(String id) {
        SysOssBO sysOssBO = this.getById(id);
        return OssConverter.toOss(sysOssBO);
    }

    @Override
    public void store(Oss oss) {
        this.saveOrUpdate(OssConverter.fromOss(oss));
    }

    @Override
    public void remove(String id) {
        this.removeById(id);
    }

    @Override
    public List<Oss> selectBatchIds(Collection<Long> ids) {
        List<SysOssBO> sysOssBOList = this.baseMapper.selectBatchIds(ids);
        List<Oss> ossList = new ArrayList<>();
        for (SysOssBO item :
                sysOssBOList) {
            Oss oss = OssConverter.toOss(item);
            ossList.add(oss);
        }

        return ossList;
    }

    @Override
    public Integer deleteBatchIds(Collection<Long> ids) {
        return this.baseMapper.deleteBatchIds(ids);
    }
}
