package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.AppDistributorDetailRespVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author WJF
 * @create 2023-06-26 15:08
 * @description TODO
 */
@Mapper
public interface DistributorDetailConvert {

    DistributorDetailConvert INSTANCE = Mappers.getMapper(DistributorDetailConvert.class);

    AppDistributorDetailRespVO convert(DistributorDetailDO bean);

    @Mapping(source = "id",target = "distributorId")
    @Mapping(target = "id",ignore=true)
    DistributorDetailDO convert(AppDistributorUpdateReqVO bean);

    @Mapping(target = "id",ignore=true)
    DistributorDetailDO convert(AppDistributorCreateReqVO bean);
}
