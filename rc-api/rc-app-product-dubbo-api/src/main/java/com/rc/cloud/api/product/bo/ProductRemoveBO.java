package com.rc.cloud.api.product.bo;

import cn.hutool.core.lang.Pair;
import lombok.Data;

import java.util.List;

@Data
public class ProductRemoveBO {


    private  List<String> needRemoveList;

    private List<String> successList;

    private List<Pair<String,String>> failList;

}
