//CHECKSTYLE:OFF
package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.DistributorChannelCreateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorChannelRespVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorChannelUpdateReqVO;
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

    DistributorChannelPO convert(DistributorChannelCreateReqVO bean);

    DistributorChannelPO convert(DistributorChannelUpdateReqVO bean);

    DistributorChannelRespVO convert(DistributorChannelPO bean);

    List<DistributorChannelRespVO> convertList(List<DistributorChannelPO> list);

    PageResult<DistributorChannelRespVO> convertPage(PageResult<DistributorChannelPO> page);

}
