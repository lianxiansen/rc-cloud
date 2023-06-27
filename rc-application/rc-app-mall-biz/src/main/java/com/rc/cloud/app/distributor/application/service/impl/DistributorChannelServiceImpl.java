package com.rc.cloud.app.distributor.application.service.impl;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorChannelConvert;
import com.rc.cloud.app.distributor.application.service.DistributorChannelService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorChannelMapper;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
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
    public Long createChannel(AppDistributorChannelCreateReqVO createReqVO) {
        // 插入
        DistributorChannelDO channel = DistributorChannelConvert.INSTANCE.convert(createReqVO);
        channel.setCreateTime(LocalDateTime.now());
        channelMapper.insert(channel);
        // 返回
        return channel.getId();
    }

    @Override
    public void updateChannel(AppDistributorChannelUpdateReqVO updateReqVO) {
        // 校验存在
        validateChannelExists(updateReqVO.getId());
        // 更新
        DistributorChannelDO updateObj = DistributorChannelConvert.INSTANCE.convert(updateReqVO);
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
    public DistributorChannelDO getChannel(Long id) {
        return channelMapper.selectById(id);
    }

    @Override
    public List<DistributorChannelDO> getChannelList(Collection<Long> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorChannelDO> getChannelPage(AppDistributorChannelPageReqVO pageReqVO) {
        return channelMapper.selectPage(pageReqVO);
    }


}
