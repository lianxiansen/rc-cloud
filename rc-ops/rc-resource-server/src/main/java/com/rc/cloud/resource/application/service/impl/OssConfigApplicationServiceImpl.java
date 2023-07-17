package com.rc.cloud.resource.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rc.cloud.common.core.constant.UserConstants;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.json.JsonUtils;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.common.oss.constant.OssConstant;
import com.rc.cloud.common.oss.factory.OssFactory;
import com.rc.cloud.common.redis.util.RedisUtils;
import com.rc.cloud.resource.application.assembler.OssConfigDTOAssembler;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.application.service.OssConfigApplicationService;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfig;
import com.rc.cloud.resource.domain.model.ossConfig.OssConfigRepository;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssConfigDO;
import com.rc.cloud.resource.infrastructure.persistence.mapper.SysOssConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 对象存储配置Service业务层处理
 * @author hqf@rc
 * @date 2022-04-21
 */
@Slf4j
@Service
@Primary
public class OssConfigApplicationServiceImpl implements OssConfigApplicationService {

    @Autowired
    private SysOssConfigMapper baseMapper;

    @Autowired
    private OssConfigRepository ossConfigRepository;

    /**
     * 项目启动时，初始化参数到缓存，加载配置类
     */
    @Override
    public void init() {
        List<OssConfig> list = ossConfigRepository.selectList();

        // 加载OSS初始化配置
        for (OssConfig config : list) {
            String configKey = config.getConfigKey();
            if ("0".equals(config.getStatus())) {
                RedisUtils.setCacheObject(OssConstant.CACHE_CONFIG_KEY, configKey);
            }
            setConfigCache(true, config);
        }
        // 初始化OSS工厂
        OssFactory.init();
    }

    @Override
    public OssConfigDTO queryById(String ossConfigId) {
        OssConfig ossConfig = ossConfigRepository.find(ossConfigId);
        OssConfigDTO ossConfigDTO = OssConfigDTOAssembler.fromOssConfig(ossConfig);
        return ossConfigDTO;
    }

    @Override
    public TableDataInfo<OssConfigDTO> queryPageList(OssConfigCommand cmd, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOssConfigDO> lqw = buildQueryWrapper(cmd);
        Page<OssConfigDTO> result = ossConfigRepository.store(pageQuery, lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<SysOssConfigDO> buildQueryWrapper(OssConfigCommand cmd) {
        LambdaQueryWrapper<SysOssConfigDO> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(cmd.getConfigKey()), SysOssConfigDO::getConfigKey, cmd.getConfigKey());
        lqw.like(StringUtils.isNotBlank(cmd.getBucketName()), SysOssConfigDO::getBucketName, cmd.getBucketName());
        lqw.eq(StringUtils.isNotBlank(cmd.getStatus()), SysOssConfigDO::getStatus, cmd.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByCmd(OssConfigCommand cmd) {
        OssConfig ossConfig = OssConfigDTOAssembler.toOssConfig(cmd);
        validEntityBeforeSave(ossConfig);
        Boolean storeRes = ossConfigRepository.store(ossConfig);
        return setConfigCache(storeRes, ossConfig);
    }

    @Override
    public Boolean updateByCmd(OssConfigCommand cmd) {
        OssConfig ossConfig = OssConfigDTOAssembler.toOssConfig(cmd);
        validEntityBeforeSave(ossConfig);
        LambdaUpdateWrapper<SysOssConfigDO> luw = new LambdaUpdateWrapper<>();
        luw.set(StringUtils.isBlank(ossConfig.getPrefix()), SysOssConfigDO::getPrefix, "");
        luw.set(StringUtils.isBlank(ossConfig.getRegion()), SysOssConfigDO::getRegion, "");
        luw.set(StringUtils.isBlank(ossConfig.getExt1()), SysOssConfigDO::getExt1, "");
        luw.eq(SysOssConfigDO::getId, ossConfig.getId());
        int storeRes = ossConfigRepository.store(ossConfig, luw);
        return setConfigCache(storeRes > 0, ossConfig);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(OssConfig entity) {
        if (StringUtils.isNotEmpty(entity.getConfigKey())
            && UserConstants.NOT_UNIQUE.equals(checkConfigKeyUnique(entity))) {
            throw new ServiceException2("操作配置'" + entity.getConfigKey() + "'失败, 配置key已存在!");
        }
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (isValid) {
            if (CollUtil.containsAny(ids, OssConstant.SYSTEM_DATA_IDS)) {
                throw new ServiceException2("系统内置, 不可删除!");
            }
        }
        List<OssConfig> list = Lists.newArrayList();
        for (String id : ids) {
            OssConfig ossConfig = ossConfigRepository.find(id);
            list.add(ossConfig);
        }
        Integer delRes = ossConfigRepository.deleteBatchIds(ids);
        boolean flag = delRes > 0;
        if (flag) {
            list.stream().forEach(item -> {
                RedisUtils.deleteObject(getCacheKey(item.getConfigKey()));
            });
        }
        return flag;
    }

    /**
     * 判断configKey是否唯一
     */
    private String checkConfigKeyUnique(OssConfig ossConfig) {
        String ossConfigId = ObjectUtil.isNull(ossConfig.getId()) ? "-1" : ossConfig.getId();
        // TODO:: 这段代码应该放仓库中处理
        SysOssConfigDO info = baseMapper.selectOne(new LambdaQueryWrapper<SysOssConfigDO>()
            .select(SysOssConfigDO::getId, SysOssConfigDO::getConfigKey)
            .eq(SysOssConfigDO::getConfigKey, ossConfig.getConfigKey()));
        if (ObjectUtil.isNotNull(info) && info.getId() != ossConfigId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 启用禁用状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOssConfigStatus(OssConfigCommand cmd) {
        OssConfig ossConfig = ossConfigRepository.find(cmd.getOssConfigId());
        ossConfig.disable();
        Boolean storeRes = ossConfigRepository.store(ossConfig);
        if (storeRes) {
            RedisUtils.setCacheObject(OssConstant.CACHE_CONFIG_KEY, ossConfig.getConfigKey());
        }
        return storeRes;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return OssConstant.SYS_OSS_KEY + configKey;
    }

    /**
     * 如果操作成功 则更新缓存
     *
     * @param flag   操作状态
     * @param config 配置
     * @return 返回操作状态
     */
    private boolean setConfigCache(boolean flag, OssConfig config) {
        if (flag) {
            RedisUtils.setCacheObject(
                getCacheKey(config.getConfigKey()),
                JsonUtils.toJsonString(config));
            RedisUtils.publish(OssConstant.CACHE_CONFIG_KEY, config.getConfigKey(), msg -> {
                log.info("发布刷新OSS配置 => " + msg);
            });
        }
        return flag;
    }
}
