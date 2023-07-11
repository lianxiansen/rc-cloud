package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorReputationUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorReputationPO;
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
    String createReputation(@Valid DistributorReputationCreateReqVO createReqVO);

    /**
     * 更新经销商客户信誉
     *
     * @param updateReqVO 更新信息
     */
    void updateReputation(@Valid DistributorReputationUpdateReqVO updateReqVO);

    /**
     * 删除经销商客户信誉
     *
     * @param id 编号
     */
    void deleteReputation(String id);

    /**
     * 获得经销商客户信誉
     *
     * @param id 编号
     * @return 经销商客户信誉
     */
    DistributorReputationPO getReputation(String id);

    /**
     * 获得所有经销商信誉
     *
     * @param
     * @return 经销商信誉
     */
    List<DistributorReputationPO> getAll();

    /**
     * 获得经销商客户信誉列表
     *
     * @param ids 编号
     * @return 经销商客户信誉列表
     */
    List<DistributorReputationPO> getReputationList(Collection<String> ids);

    /**
     * 获得经销商客户信誉分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商客户信誉分页
     */
    PageResult<DistributorReputationPO> getReputationPage(DistributorReputationPageReqVO pageReqVO);

}
