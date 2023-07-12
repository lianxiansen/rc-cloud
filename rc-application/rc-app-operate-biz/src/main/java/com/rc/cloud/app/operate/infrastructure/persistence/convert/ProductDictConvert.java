package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductDictConvert
{

    ProductDictConvert INSTANCE = Mappers.getMapper(ProductDictConvert.class);


    @Mapping(source = "id",target = "id")
   // @Mapping(source = "key",target = "key")
    @Mapping(source = "value",target = "value")
    @Mapping(source = "sortId",target = "sort")
    ProductDict convert(ProductDictPO productDictPO);

    @Mapping(source = "id",target = "id")
    //Mapping(source = "key",target = "key")
    @Mapping(source = "value",target = "value")
    @Mapping(source = "sort",target = "sortId")
    ProductDictPO convert(ProductDict productDict);

    List<ProductDict> convertList(List<ProductDictPO> list);


}
