package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
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
    Long createChannel(@Valid AppDistributorChannelCreateReqVO createReqVO);

    /**
     * 更新经销商渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateChannel(@Valid AppDistributorChannelUpdateReqVO updateReqVO);

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
    PageResult<DistributorChannelPO> getChannelPage(AppDistributorChannelPageReqVO pageReqVO);

}
