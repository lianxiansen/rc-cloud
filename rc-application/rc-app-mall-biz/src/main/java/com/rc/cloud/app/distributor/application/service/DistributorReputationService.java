package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationDO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * 经销商客户信誉 Service 接口
 *
 * @author wjf
 */
public interface DistributorReputationService {

    /**
     * 创建经销商客户信誉
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReputation(@Valid AppDistributorReputationCreateReqVO createReqVO);

    /**
     * 更新经销商客户信誉
     *
     * @param updateReqVO 更新信息
     */
    void updateReputation(@Valid AppDistributorReputationUpdateReqVO updateReqVO);

    /**
     * 删除经销商客户信誉
     *
     * @param id 编号
     */
    void deleteReputation(Long id);

    /**
     * 获得经销商客户信誉
     *
     * @param id 编号
     * @return 经销商客户信誉
     */
    DistributorReputationDO getReputation(Long id);

    /**
     * 获得经销商客户信誉列表
     *
     * @param ids 编号
     * @return 经销商客户信誉列表
     */
    List<DistributorReputationDO> getReputationList(Collection<Long> ids);

    /**
     * 获得经销商客户信誉分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商客户信誉分页
     */
    PageResult<DistributorReputationDO> getReputationPage(AppDistributorReputationPageReqVO pageReqVO);

}
