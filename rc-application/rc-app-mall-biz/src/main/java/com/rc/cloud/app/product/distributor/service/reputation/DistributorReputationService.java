package com.rc.cloud.app.product.distributor.service.reputation;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationPageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.reputation.vo.AppDistributorReputationUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.reputation.DistributorReputationDO;
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
    Integer createReputation(@Valid AppDistributorReputationCreateReqVO createReqVO);

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
    void deleteReputation(Integer id);

    /**
     * 获得经销商客户信誉
     *
     * @param id 编号
     * @return 经销商客户信誉
     */
    DistributorReputationDO getReputation(Integer id);

    /**
     * 获得经销商客户信誉列表
     *
     * @param ids 编号
     * @return 经销商客户信誉列表
     */
    List<DistributorReputationDO> getReputationList(Collection<Integer> ids);

    /**
     * 获得经销商客户信誉分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商客户信誉分页
     */
    PageResult<DistributorReputationDO> getReputationPage(AppDistributorReputationPageReqVO pageReqVO);

}
