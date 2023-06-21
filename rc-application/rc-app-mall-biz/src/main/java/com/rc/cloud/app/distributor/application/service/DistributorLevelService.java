package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;

import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorLevelDO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorLevelUpdateReqVO;
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
    Integer createLevel(@Valid AppDistributorLevelCreateReqVO createReqVO);

    /**
     * 更新经销商客户等级
     *
     * @param updateReqVO 更新信息
     */
    void updateLevel(@Valid AppDistributorLevelUpdateReqVO updateReqVO);

    /**
     * 删除经销商客户等级
     *
     * @param id 编号
     */
    void deleteLevel(Integer id);

    /**
     * 获得经销商客户等级
     *
     * @param id 编号
     * @return 经销商客户等级
     */
    DistributorLevelDO getLevel(Integer id);

    /**
     * 获得经销商客户等级列表
     *
     * @param ids 编号
     * @return 经销商客户等级列表
     */
    List<DistributorLevelDO> getLevelList(Collection<Integer> ids);

    /**
     * 获得经销商客户等级分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商客户等级分页
     */
    PageResult<DistributorLevelDO> getLevelPage(AppDistributorLevelPageReqVO pageReqVO);

}
