package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorChannelRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelPO;
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

    DistributorChannelPO convert(AppDistributorChannelCreateReqVO bean);

    DistributorChannelPO convert(AppDistributorChannelUpdateReqVO bean);

    AppDistributorChannelRespVO convert(DistributorChannelPO bean);

    List<AppDistributorChannelRespVO> convertList(List<DistributorChannelPO> list);

    PageResult<AppDistributorChannelRespVO> convertPage(PageResult<DistributorChannelPO> page);

}
