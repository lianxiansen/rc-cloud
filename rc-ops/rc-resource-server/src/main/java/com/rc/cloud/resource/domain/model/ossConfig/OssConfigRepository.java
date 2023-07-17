package com.rc.cloud.resource.domain.model.ossConfig;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import com.rc.cloud.resource.infrastructure.persistence.entity.SysOssConfigDO;

import java.util.Collection;
import java.util.List;

/**
 * Oss文件-Repository接口
 *
 * @author hqf@rc
 * @date 2022-04-22
 **/
public interface OssConfigRepository {


    /**
     * 根据id查找文件存储配置
     *
     * @param id
     * @return
     */
    OssConfig find(String id);

    /**
     * 保存文件存储配置
     *
     * @param ossConfig
     */
    Boolean store(OssConfig ossConfig);

    int store(OssConfig ossConfig, LambdaUpdateWrapper<SysOssConfigDO> luw);

    Page<OssConfigDTO> store(PageQuery pageQuery, LambdaQueryWrapper<SysOssConfigDO> luw);

    /**
     * 删除文件存储配置
     *
     * @param id
     */
    void remove(String id);

    List<OssConfig> selectList();

    Integer deleteBatchIds(Collection<String> ids);

}
