package com.rc.cloud.app.operate.application.bo;

import cn.hutool.core.lang.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRemoveBO {


    private  List<String> needRemoveList;

    private List<String> successList;

    private List<Pair<String,String>> failList;

}
