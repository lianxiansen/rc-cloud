package com.rc.cloud.resource.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.assembler.OssConfigDTOAssembler;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.application.service.OssConfigQueryService;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfigRepository;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssConfigBO;
import com.rc.cloud.resource.infrastructure.persistence.mapper.SysOssConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 对象存储配置Service业务层处理
 * @author hqf@rc
 * @date 2022-04-21
 */
@Slf4j
@Service
@Primary
public class OssConfigQueryServiceImpl implements OssConfigQueryService {

    @Autowired
    private SysOssConfigMapper baseMapper;

    @Autowired
    private OssConfigRepository ossConfigRepository;

    @Override
    // 2022-04-25 改成ddd模式
    public OssConfigDTO queryById(String id) {
        OssConfig ossConfig = ossConfigRepository.find(id);
        OssConfigDTO ossConfigDTO = OssConfigDTOAssembler.fromOssConfig(ossConfig);
        return ossConfigDTO;
    }

    @Override
    public TableDataInfo<OssConfigDTO> queryPageList(OssConfigCommand ossConfigCommand, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOssConfigBO> lqw = buildQueryWrapper(ossConfigCommand);
        Page<OssConfigDTO> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<SysOssConfigBO> buildQueryWrapper(OssConfigCommand ossConfigCommand) {
        LambdaQueryWrapper<SysOssConfigBO> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(ossConfigCommand.getConfigKey()), SysOssConfigBO::getConfigKey, ossConfigCommand.getConfigKey());
        lqw.like(StringUtils.isNotBlank(ossConfigCommand.getBucketName()), SysOssConfigBO::getBucketName, ossConfigCommand.getBucketName());
        lqw.eq(StringUtils.isNotBlank(ossConfigCommand.getStatus()), SysOssConfigBO::getStatus, ossConfigCommand.getStatus());
        return lqw;
    }
}
