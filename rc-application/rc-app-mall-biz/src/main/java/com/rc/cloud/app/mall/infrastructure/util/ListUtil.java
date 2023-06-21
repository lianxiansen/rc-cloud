package com.rc.cloud.app.mall.infrastructure.util;

import cn.hutool.core.convert.Convert;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/10
 * @Description:
 */
public class ListUtil {

    public static List<Integer> ToIntList(String value)
    {
        List<Integer> result =new ArrayList<>();
        String []ids= value.split(",");
        try {
            if(ids!=null && ids.length>0){
                Integer[]intArray = Convert.toIntArray(ids);

                for (Integer integer : intArray) {
                    result.add(integer);
                }
            }
        }catch (Exception e){
            throw e;
        }
        return result;
    }

    public static List<String> ToStringList(String value)
    {
        List<String> result =new ArrayList<>();
        String []ids= value.split(",");
        try {
            if(ids!=null && ids.length>0){
                String[]strArray = Convert.toStrArray(ids);
                for (String str : strArray) {
                    result.add(str);
                }
            }
        }catch (Exception e){
            throw e;
        }
        return result;
    }
}
