package com.rc.cloud.app.operate.application.bo;


import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductGroupBO {

    private String Id;

    private String name;

    private LocalDateTime createTime;

    private Collection<ProductGroupItemBO> itemList;



    public static List<ProductGroupBO> from(List<ProductGroup> productGroupList){
        List<ProductGroupBO> boList=new ArrayList<>();
        productGroupList.forEach(item->{
            boList.add(from(item));
        });
        return boList;
    }

    public static ProductGroupBO from(ProductGroup productGroup){
        return new ProductGroupBO();
    }
}
