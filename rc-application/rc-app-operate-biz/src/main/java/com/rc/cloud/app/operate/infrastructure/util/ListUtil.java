package com.rc.cloud.app.operate.infrastructure.util;

import cn.hutool.core.convert.Convert;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/10
 * @Description:
 */
public class ListUtil {

    public static List<Integer> ToIntList(String value) {
        List<Integer> result = new ArrayList<>();
        String[] ids = value.split(",");
        try {
            if (ids != null && ids.length > 0) {
                Integer[] intArray = Convert.toIntArray(ids);

                for (Integer integer : intArray) {
                    result.add(integer);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public static List<String> ToStringList(String value) {
        List<String> result = new ArrayList<>();
        String[] ids = value.split(",");
        try {
            if (ids != null && ids.length > 0) {
                String[] strArray = Convert.toStrArray(ids);
                for (String str : strArray) {
                    result.add(str);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public static Boolean isEqual(List list1, List list2) {
        if (list2 == null) {
            return false;
        }
        if (list1 == null) {
            return false;
        }
        return list1.size() == list2.size() && list1.containsAll(list2);
    }
}
