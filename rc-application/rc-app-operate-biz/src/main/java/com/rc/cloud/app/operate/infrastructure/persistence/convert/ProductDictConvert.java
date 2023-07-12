package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import org.mapstruct.factory.Mappers;

import java.util.List;

//@Mapper
public interface ProductDictConvert
{

    ProductDictConvert INSTANCE = Mappers.getMapper(ProductDictConvert.class);


//    ProductDict convert(ProductDictPO productDictPO);
//
//    ProductDictPO convert(ProductDict productDict);
//
    List<ProductDict> convertList(List<ProductDictPO> list);

    //List<ProductDictPO> convertList(List<ProductDict> list);

}
