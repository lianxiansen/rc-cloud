package com.rc.cloud.app.product.distributor.convert.source;

import java.util.*;
import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourceRespVO;
import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.source.DistributorSourceDO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 经销商来源 Convert
 *
 * @author wjf
 */
@Mapper
public interface DistributorSourceConvert {

    DistributorSourceConvert INSTANCE = Mappers.getMapper(DistributorSourceConvert.class);

    DistributorSourceDO convert(AppDistributorSourceCreateReqVO bean);

    DistributorSourceDO convert(AppDistributorSourceUpdateReqVO bean);

    AppDistributorSourceRespVO convert(DistributorSourceDO bean);

    List<AppDistributorSourceRespVO> convertList(List<DistributorSourceDO> list);

    PageResult<AppDistributorSourceRespVO> convertPage(PageResult<DistributorSourceDO> page);

}
