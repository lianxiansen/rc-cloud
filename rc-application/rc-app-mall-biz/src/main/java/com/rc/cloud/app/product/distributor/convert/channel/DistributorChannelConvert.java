package com.rc.cloud.app.product.distributor.convert.channel;

import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelRespVO;
import com.rc.cloud.app.product.distributor.controller.app.channel.vo.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.channel.DistributorChannelDO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 经销商渠道 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorChannelConvert {

    DistributorChannelConvert INSTANCE = Mappers.getMapper(DistributorChannelConvert.class);

    DistributorChannelDO convert(AppDistributorChannelCreateReqVO bean);

    DistributorChannelDO convert(AppDistributorChannelUpdateReqVO bean);

    AppDistributorChannelRespVO convert(DistributorChannelDO bean);

    List<AppDistributorChannelRespVO> convertList(List<DistributorChannelDO> list);

    PageResult<AppDistributorChannelRespVO> convertPage(PageResult<DistributorChannelDO> page);

}
