package com.rc.cloud.app.operate.infrastructure.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author:taotianhong
 * @Date 2021/3/16
 * @Description:
 */
public class MapUtil {

    /**
     * 元素列表转换类型
     * @param obj 要转换的元素
     * @param elementType T元素类型
     * @param <T>
     * @return
     */
    public static <T> List<T> ChangeList(Object obj, Class<T> elementType) {
        if (obj == null) return null;
        return JSONUtil.toList(JSONUtil.parseArray(JSONUtil.toJsonStr(obj)), elementType);
    }

    /**
     * 元素单例转换类型
     * @param obj 要转换的元素
     * @param elementType T元素类型
     * @param <T>
     * @return
     */
    public static <T> T ChangeObject(Object obj, Class<T> elementType) {
        if (obj == null) return null;
        return JSONUtil.toBean(JSONUtil.toJsonStr(obj), elementType);
    }

    /**
     * Long[] -> long[]
     * @param array Long数组
     * @return long数组
     */
    public static long[] LongArray2longArray(Long[] array) {
        if (array == null) return null;
        return Arrays.stream(array).mapToLong(m -> m.longValue()).toArray();
    }

    /**
     * List<long> -> long[]
     *
     * @param list Long集合
     * @return long[]
     */
    public static long[] LongList2longArray(List<Long> list) {
        if (list == null) return null;
        return LongArray2longArray(list.stream().toArray(Long[]::new));
    }

    /**
     * distinct方法
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
       /* 1.使用put方法添加键值对，如果map集合中没有该key对应的值，则直接添加，并返回null，如果已经存在对应的值，则会覆盖旧值，value为新的值。
        2.使用putIfAbsent方法添加键值对，如果map集合中没有该key对应的值，则直接添加，并返回null，如果已经存在对应的值，则依旧为原来的值。*/
    }


    // 将map key首字母转换为小写
    public static Map<String, Object> transformfirstZLowerCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.substring(0,1).toLowerCase()+key.substring(1);
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        try{
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
        }
        catch (Exception e){
return null;
        }
    }
    // 将map key值全部转换为大写
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toUpperCase();
//			newKey = newKey.replace("_", "");
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }
    // 将map key全部转换为小写
    public static Map<String, Object> transformLowerCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
//			newKey = newKey.replace("_", "");
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }
    public static Map<String, Object> queryToMap(String url) {
        Map<String, Object>  map = new HashMap<>();
            if (url != null  && url.indexOf("=") > -1) {
                if(url.indexOf("&") > -1){
                    String[] arrTemp = url.split("&");
                    for (String str : arrTemp) {
                        String[] qs = str.split("=");
                        map.put(qs[0], qs[1]);
                    }
                }else{
                    String[] qs = url.split("=");
                    map.put(qs[0], qs[1]);
                }
            }
        return map;
    }

    /**
     * key大小写通配拿value值
     * @param query
     * @param key
     * @return
     */
    public static String getCaseMatching(String query,String key) {
        try{
            Map queryMap= queryToMap(query);
            Map mapLowerCase= transformLowerCase(queryMap);
            return Convert.toStr(mapLowerCase.get(key.toLowerCase(Locale.ROOT)),null);
        }
        catch (Exception e){
            return null;
        }
    }
}
