package com.rc.cloud.app.operate.mapstruct.mapper;

import com.rc.cloud.app.operate.mapstruct.data.Source;
import com.rc.cloud.app.operate.mapstruct.data.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleMapper {
    SimpleMapper INSTANCE = Mappers.getMapper(SimpleMapper.class);

    @Mapping(source = "id",target = "id")
    @Mapping(target = "name",ignore=true)
    Target convert(Source a);
}
