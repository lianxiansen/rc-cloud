package com.rc.cloud.app.operate.appearance.vo;


import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductGroupVO {

    private String Id;

    private String name;

    private String createTime;

    private Collection<ProductGroupItemVO> itemList;

    public static List<ProductGroupVO> from(List<ProductGroupBO> boList){
        return null;
    }

    public static ProductGroupVO from(ProductGroupBO bo){
        return null;
    }
}
