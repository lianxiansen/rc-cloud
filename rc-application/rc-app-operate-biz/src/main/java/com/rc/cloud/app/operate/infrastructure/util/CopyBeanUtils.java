package com.rc.cloud.app.operate.infrastructure.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/8
 * @Description: 复制属性名称不一致的对象
 */
public class CopyBeanUtils {

    /**
     * ps：Test2 test2 = CopyBeanUtils.springCopyProperties(i1, Test2.class);
     *
     * @param source      原对象
     * @param targetClass 目标对象class
     * @param <T>
     * @return copy完后的对象
     */
    public static <T> T springCopyProperties(Object source, Class<T> targetClass) {
        Assert.notNull(source, "targetClass must not be null");
        T target = BeanUtils.instantiateClass(targetClass);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * ps：Test2 test2 = CopyBeanUtils.copyProperties(i1, Test2.class);
     *
     * @param source      原对象
     * @param targetClass 目标对象class
     * @param <T>
     * @return copy完后的对象
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        Assert.notNull(source, "targetClass must not be null");

        T target = BeanUtils.instantiateClass(targetClass);
        copyProperties(source, target);
        return target;
    }

    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null,null, (String[]) null);
    }

    public static void copyProperties(Object source, Object target,CopyBeanStrategyEnum strategy) throws BeansException {
        copyProperties(source, target, strategy, null, (String[]) null);//
    }

    public static void copyProperties(Object source, Object target,@Nullable CopyBeanStrategyEnum strategy, Class<?> editable) throws BeansException {
        copyProperties(source, target,strategy, editable, (String[]) null);
    }

    /**
     * @param source           原对象
     * @param target           目标对象
     * @param ignoreProperties 排除某些不需要复制的属性
     * @throws BeansException
     */
    public static void copyProperties(Object source, Object target,  @Nullable CopyBeanStrategyEnum strategy,String... ignoreProperties) throws BeansException {
        copyProperties(source, target, strategy,null, ignoreProperties);
    }

    private static void copyProperties(Object source, Object target, @Nullable CopyBeanStrategyEnum strategy, @Nullable Class<?> editable, @Nullable String... ignoreProperties) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        HashMap<String, String> copySourceNameMap = new HashMap<>();

        for (Field field : actualEditable.getDeclaredFields()) {
            CopySourceName annotation = field.getAnnotation(CopySourceName.class);
            if (annotation != null) {
                copySourceNameMap.put(field.getName(), annotation.value());
            }else{
                if (strategy != null) {
                    if (strategy.value == CopyBeanStrategyEnum.fromModel.value) {
                        copySourceNameMap.put(field.getName(), convertToDBModelName(field.getName()));

                    } else if (strategy.value == CopyBeanStrategyEnum.formVo.value) {
                        copySourceNameMap.put(field.getName(), convertToVoName(field.getName()));
                    }
                }
            }
        }

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            String name = targetPd.getName();
            String sourceName = copySourceNameMap.get(name);
            if (sourceName != null) {
                name = sourceName;
            }
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(name))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), name);
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
//                            if(value instanceof Boolean){
//                                if(value==null){
//                                    value=false;
//                                }
//                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            //数据库为null的时候会执行这里
                            //Boolean为null赋值到boolean
                            //Integer为nul赋值到integer
                            //throw new FatalBeanException(
                            //        "Could not copy property '" + name + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }


    private static String convertToDBModelName(String s){
        for (char i = 'a'; i <= 'z' ; i++) {
            Character c=new Character(i);
            s=s.replace("_"+i, c.toString().toUpperCase());
        }
        return s;
    }

    /**
     * 转化为vo
     * @param s
     * @return
     */
    private static String convertToVoName(String s){
        if("sortID".equals(s)){
            return "sort_id";
        }
        if("linkID".equals(s)){
            return "link_id";
        }
        for (char i = 'A'; i <= 'Z' ; i++) {
            Character c=new Character(i);
            s=s.replace(c.toString(),"_"+c.toString().toLowerCase());
        }
        return s;
    }
}
