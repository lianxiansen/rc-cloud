package com.rc.cloud.resource.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.command.OssCommand;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.application.service.OssQueryService;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.domain.model.oss.OssRepository;
import com.rc.cloud.resource.infrastructure.persistence.bo.SysOssBO;
import com.rc.cloud.resource.infrastructure.persistence.mapper.SysOssMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 文件上传 服务层实现
 *
 * @author hqf@rc
 */
@Service
@Primary
public class OssQueryServiceImpl implements OssQueryService {

    @Autowired
    private SysOssMapper baseMapper;

    @Autowired
    private OssRepository ossRepository;

    @Override
    public TableDataInfo<OssDTO> queryPage(OssCommand ossCommand, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOssBO> lqw = buildQueryWrapper(ossCommand);
        Page<OssDTO> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<SysOssBO> buildQueryWrapper(OssCommand ossCommand) {
        Map<String, Object> params = ossCommand.getParams();
        LambdaQueryWrapper<SysOssBO> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(ossCommand.getFileName()), SysOssBO::getFileName, ossCommand.getFileName());
        lqw.like(StringUtils.isNotBlank(ossCommand.getOriginalName()), SysOssBO::getOriginalName, ossCommand.getOriginalName());
        lqw.eq(StringUtils.isNotBlank(ossCommand.getFileSuffix()), SysOssBO::getFileSuffix, ossCommand.getFileSuffix());
        lqw.eq(StringUtils.isNotBlank(ossCommand.getUrl()), SysOssBO::getUrl, ossCommand.getUrl());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
                SysOssBO::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        lqw.eq(StringUtils.isNotBlank(ossCommand.getCreator()), SysOssBO::getCreator, ossCommand.getCreator());
        lqw.eq(StringUtils.isNotBlank(ossCommand.getService()), SysOssBO::getService, ossCommand.getService());
        return lqw;
    }

    @Override
    public Oss getById(String id) {
        Oss oss = ossRepository.find(id);
        return oss;
    }
}
