package com.bowen.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Util {

    //最大容量
    public final static long uidListMaxSize = 50000;

    //低于最大容量数
    public final static long uidLackSize = 10000;

    //本地生成位数
    public final static int localUidSize = 19;

    //redis key
    public final static String uidListKey = "uidListRedisKey";

    public static String getLocalRandom(Integer num) {
        if(num==null){
            num=localUidSize;
        }
        StringBuilder sb = new StringBuilder();
        Random r = null;
        while (sb.length() < localUidSize) {
            r = new Random();
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

}
