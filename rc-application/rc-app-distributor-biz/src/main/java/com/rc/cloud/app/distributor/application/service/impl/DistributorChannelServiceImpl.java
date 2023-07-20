package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorChannelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorChannelMapper;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商渠道 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorChannelServiceImpl implements DistributorChannelService {

    @Resource
    private DistributorChannelMapper channelMapper;

    @Override
    public String createChannel(DistributorChannelCreateReqVO createReqVO) {
        // 插入
        DistributorChannelPO channel = DistributorChannelConvert.INSTANCE.convert(createReqVO);
        channelMapper.insert(channel);
        // 返回
        return channel.getId();
    }

    @Override
    public void updateChannel(DistributorChannelUpdateReqVO updateReqVO) {
        // 校验存在
        validateChannelExists(updateReqVO.getId());
        // 更新
        DistributorChannelPO updateObj = DistributorChannelConvert.INSTANCE.convert(updateReqVO);
        channelMapper.updateById(updateObj);
    }

    @Override
    public void deleteChannel(String id) {
        // 校验存在
        validateChannelExists(id);
        // 删除
        channelMapper.deleteById(id);
    }

    private void validateChannelExists(String id) {
        if (channelMapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public DistributorChannelPO getChannel(String id) {
        return channelMapper.selectById(id);
    }

    @Override
    public List<DistributorChannelPO> getAll() {
        return channelMapper.selectList();
    }

    @Override
    public List<DistributorChannelPO> getChannelList(Collection<String> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorChannelPO> getChannelPage(DistributorChannelPageReqVO pageReqVO) {
        return channelMapper.selectPage(pageReqVO);
    }


}
