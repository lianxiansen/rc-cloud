package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelPO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorLevelUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * 经销商客户等级 Service 接口
 *
 * @author wjf
 */
public interface DistributorLevelService {

    /**
     * 创建经销商客户等级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLevel(@Valid DistributorLevelCreateReqVO createReqVO);

    /**
     * 更新经销商客户等级
     *
     * @param updateReqVO 更新信息
     */
    void updateLevel(@Valid DistributorLevelUpdateReqVO updateReqVO);

    /**
     * 删除经销商客户等级
     *
     * @param id 编号
     */
    void deleteLevel(Long id);

    /**
     * 获得经销商客户等级
     *
     * @param id 编号
     * @return 经销商客户等级
     */
    DistributorLevelPO getLevel(Long id);

    /**
     * 获得所有经销商客户等级
     *
     * @param
     * @return 经销商客户等级
     */
    List<DistributorLevelPO> getAll();
    /**
     * 获得经销商客户等级列表
     *
     * @param ids 编号
     * @return 经销商客户等级列表
     */
    List<DistributorLevelPO> getLevelList(Collection<Long> ids);

    /**
     * 获得经销商客户等级分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商客户等级分页
     */
    PageResult<DistributorLevelPO> getLevelPage(DistributorLevelPageReqVO pageReqVO);

}
