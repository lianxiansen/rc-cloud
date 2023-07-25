package com.rc.cloud.common.core.domain;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName IdUtil
 * @Author liandy
 * @Date 2023/7/25 16:52
 * @Description id唯一标识工具类
 * @Version 1.0
 */
public class IdUtil{
    /**
     *
     * @param idList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends AbstractId> List<T> toList(List<String> idList, Class<T > clazz) {
        List<T> tList = new ArrayList<>();
        Constructor<T> constructor = ReflectUtil.getConstructor(clazz, String.class);
        idList.forEach(item->{
            try {
               T t= constructor.newInstance(item);
               tList.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return tList;
    }

    public static <T extends AbstractId> List<String> toList(List<T> idList) {
        return idList.stream().map(item->item.getId()).collect(Collectors.toList());
    }
}
