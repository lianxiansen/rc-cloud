package com.rc.cloud.app.operate.mapstruct.mapper;

import com.rc.cloud.app.operate.mapstruct.data.Source;
import com.rc.cloud.app.operate.mapstruct.data.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeepMapper {
    DeepMapper INSTANCE = Mappers.getMapper(DeepMapper.class);
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")
    Target convert(Source a);
}
