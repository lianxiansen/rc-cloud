package com.rc.cloud.app.distributor.application.convert;

import com.rc.cloud.app.distributor.appearance.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.resp.DistributorDetailRespVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
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

    DistributorDetailRespVO convert(DistributorDetailPO bean);

    @Mapping(source = "id",target = "distributorId")
    @Mapping(target = "id",ignore=true)
    DistributorDetailPO convert(DistributorUpdateReqVO bean);

    @Mapping(target = "id",ignore=true)
    DistributorDetailPO convert(DistributorCreateReqVO bean);
}
