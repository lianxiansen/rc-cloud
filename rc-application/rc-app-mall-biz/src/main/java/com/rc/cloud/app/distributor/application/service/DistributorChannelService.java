package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * 经销商渠道 Service 接口
 *
 * @author wjf
 */
public interface DistributorChannelService {

    /**
     * 创建经销商渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChannel(@Valid DistributorChannelCreateReqVO createReqVO);

    /**
     * 更新经销商渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateChannel(@Valid DistributorChannelUpdateReqVO updateReqVO);

    /**
     * 删除经销商渠道
     *
     * @param id 编号
     */
    void deleteChannel(Long id);

    /**
     * 获得经销商渠道
     *
     * @param id 编号
     * @return 经销商渠道
     */
    DistributorChannelPO getChannel(Long id);

    /**
     * 获得所有经销商渠道
     *
     * @param
     * @return 经销商渠道
     */
    List<DistributorChannelPO> getAll();

    /**
     * 获得经销商渠道列表
     *
     * @param ids 编号
     * @return 经销商渠道列表
     */
    List<DistributorChannelPO> getChannelList(Collection<Long> ids);

    /**
     * 获得经销商渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商渠道分页
     */
    PageResult<DistributorChannelPO> getChannelPage(DistributorChannelPageReqVO pageReqVO);

}
