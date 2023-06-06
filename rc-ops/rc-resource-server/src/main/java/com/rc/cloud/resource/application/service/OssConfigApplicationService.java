package com.rc.cloud.resource.application.service;

import com.rc.cloud.common.mybatis.page.PageQuery;
import com.rc.cloud.common.mybatis.page.TableDataInfo;
import com.rc.cloud.resource.application.command.OssConfigCommand;
import com.rc.cloud.resource.application.dto.OssConfigDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 对象存储配置Service接口
 * @author hqf@rc
 * @date 2022-04-21
 */
public interface OssConfigApplicationService {

    /**
     * 初始化OSS配置
     */
    void init();

    /**
     * 查询单个
     */
    OssConfigDTO queryById(String ossConfigId);

    /**
     * 查询列表
     */
    TableDataInfo<OssConfigDTO> queryPageList(OssConfigCommand cmd, PageQuery pageQuery);


    /**
     * 根据新增业务对象插入对象存储配置
     *
     * @param cmd 对象存储配置新增业务对象
     * @return
     */
    Boolean insertByCmd(OssConfigCommand cmd);

    /**
     * 根据编辑业务对象修改对象存储配置
     *
     * @param cmd 对象存储配置编辑业务对象
     * @return
     */
    Boolean updateByCmd(OssConfigCommand cmd);

    /**
     * 校验并删除数据
     *
     * @param ids     主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    /**
     * 启用停用状态
     */
    Boolean updateOssConfigStatus(OssConfigCommand cmd);
}
