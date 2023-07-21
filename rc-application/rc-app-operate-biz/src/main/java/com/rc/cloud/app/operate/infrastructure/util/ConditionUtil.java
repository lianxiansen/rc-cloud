package com.rc.cloud.app.operate.infrastructure.util;

/**
 * @ClassName: ConditionUtil
 * @Author: liandy
 * @Date: 2023/7/21 08:44
 * @Description: TODO
 */
public class ConditionUtil {
    public static boolean booleanValue(boolean ...condition){
        boolean booleanValue =true;
        for (int i = 0; i < condition.length; i++) {
            booleanValue = booleanValue && condition[i];
        }
        return booleanValue;
    }
}
