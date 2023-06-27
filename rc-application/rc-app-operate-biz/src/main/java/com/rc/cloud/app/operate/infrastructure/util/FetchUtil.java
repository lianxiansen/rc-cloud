package com.rc.cloud.app.operate.infrastructure.util;

import cn.hutool.core.convert.Convert;

import java.util.*;

/**
 * @Author:chenjianxiang
 * @Date 2021/7/16
 * @Description:
 */
public class FetchUtil {


    public static String queryString(Map map,String key){
        if(map==null)
            return null;
        Object o = map.get(key);
        if(o==null){
            return null;
        }else{
            return Convert.toStr(o);
        }
    }

    public static Boolean queryBoolean(Map map,String key){
        if(map==null)
            return false;
        Object o = map.get(key);
        if(o==null){
            return false;
        }else{
            return Convert.toBool(o);
        }
    }

    public static Integer queryInt(Map map,String key){
        if(map==null)
            return 0;
        Object o = map.get(key);
        if(o==null){
            return 0;
        }else{
            return Convert.toInt(o);
        }
    }

    public static Long queryLong(Map map,String key){
        if(map==null)
            return 0l;
        Object o = map.get(key);
        if(o==null){
            return 0l;
        }else{
            return Convert.toLong(o);
        }
    }

    public static Double queryDouble(Map map,String key){
        if(map==null)
            return 0d;
        Object o = map.get(key);
        if(o==null){
            return 0d;
        }else{
            return Convert.toDouble(o);
        }
    }

    public static List<LinkedHashMap> queryMap(Map map, String key){
        if(map==null)
            return new ArrayList<LinkedHashMap>();
        Object o = map.get(key);
        if(o==null){
            return new ArrayList<LinkedHashMap>();
        }else{
            return (List<LinkedHashMap>)o;
        }
    }

    public static void main(String[] args) {
        Map<String ,Object> data =new HashMap<>();
        Integer integer = queryInt(data, "user_id");
        System.out.println(integer);
        data.put("user_id",1);
        Integer integer2 = queryInt(data, "user_id");
        System.out.println(integer2);

        data.put("price",1.1);
        int price = queryInt(data, "");
        System.out.println(price);

        String shareUserId = queryString(data, "share_user_id");
        System.out.println(shareUserId);
    }
}
