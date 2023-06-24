package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorChannelRespVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorChannelUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorChannelDO;
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
