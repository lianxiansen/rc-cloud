package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorSourceDO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * 经销商来源 Service 接口
 *
 * @author wjf
 */
public interface DistributorSourceService {

    /**
     * 创建经销商来源
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createSource(@Valid AppDistributorSourceCreateReqVO createReqVO);

    /**
     * 更新经销商来源
     *
     * @param updateReqVO 更新信息
     */
    void updateSource(@Valid AppDistributorSourceUpdateReqVO updateReqVO);

    /**
     * 删除经销商来源
     *
     * @param id 编号
     */
    void deleteSource(Integer id);

    /**
     * 获得经销商来源
     *
     * @param id 编号
     * @return 经销商来源
     */
    DistributorSourceDO getSource(Integer id);

    /**
     * 获得经销商来源列表
     *
     * @param ids 编号
     * @return 经销商来源列表
     */
    List<DistributorSourceDO> getSourceList(Collection<Integer> ids);

    /**
     * 获得经销商来源分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商来源分页
     */
    PageResult<DistributorSourceDO> getSourcePage(AppDistributorSourcePageReqVO pageReqVO);

}