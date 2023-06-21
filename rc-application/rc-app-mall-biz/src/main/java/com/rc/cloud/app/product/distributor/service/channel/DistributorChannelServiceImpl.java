package com.rc.cloud.app.product.distributor.service.channel;

import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelPageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.product.distributor.convert.channel.DistributorChannelConvert;
import com.rc.cloud.app.product.distributor.dal.dataobject.channel.DistributorChannelDO;
import com.rc.cloud.app.product.distributor.dal.mysql.channel.DistributorChannelMapper;
import com.rc.cloud.common.core.exception.ErrorCode;
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
    public Integer createChannel(AppDistributorChannelCreateReqVO createReqVO) {
        // 插入
        DistributorChannelDO channel = DistributorChannelConvert.INSTANCE.convert(createReqVO);
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
    public void deleteChannel(Integer id) {
        // 校验存在
        validateChannelExists(id);
        // 删除
        channelMapper.deleteById(id);
    }

    private void validateChannelExists(Integer id) {
        if (channelMapper.selectById(id) == null) {
            throw exception(new ErrorCode(2,"CHANNEL_NOT_EXISTS"));
        }
    }

    @Override
    public DistributorChannelDO getChannel(Integer id) {
        return channelMapper.selectById(id);
    }

    @Override
    public List<DistributorChannelDO> getChannelList(Collection<Integer> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorChannelDO> getChannelPage(AppDistributorChannelPageReqVO pageReqVO) {
        return channelMapper.selectPage(pageReqVO);
    }


}
