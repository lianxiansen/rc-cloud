package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorChannelConvert;
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
    public Long createChannel(DistributorChannelCreateReqVO createReqVO) {
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
    public void deleteChannel(Long id) {
        // 校验存在
        validateChannelExists(id);
        // 删除
        channelMapper.deleteById(id);
    }

    private void validateChannelExists(Long id) {
        if (channelMapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public DistributorChannelPO getChannel(Long id) {
        return channelMapper.selectById(id);
    }

    @Override
    public List<DistributorChannelPO> getChannelList(Collection<Long> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorChannelPO> getChannelPage(DistributorChannelPageReqVO pageReqVO) {
        return channelMapper.selectPage(pageReqVO);
    }


}
