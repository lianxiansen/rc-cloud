package com.rc.cloud.baseconfig.amap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-06 10:34
 * @description TODO
 */
@Mapper
public interface DistrictConvert {

    DistrictConvert INSTANCE = Mappers.getMapper(DistrictConvert.class);

    District convert(DistrictEntity entityList);

    List<District> convertList(List<DistrictEntity> entityList);
}
