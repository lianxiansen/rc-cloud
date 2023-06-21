package com.rc.cloud.app.mall.infrastructure.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;


import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/22
 * @Description: 用于保存至Redis的Json数据处理
 */
public class RedisJson {
    static  SerializeConfig config = new SerializeConfig();
    static ParserConfig parserConfig=new ParserConfig();
    static {
        config.put(Timestamp.class, new TimestampJsonSerializer());
        parserConfig.putDeserializer(Timestamp.class,new TimestampJsonDeserializer());
    }

    public static String toJSONString(Object o){
       return  JSON.toJSONString(o,config);
    }

    public  static  <T> T parseObject(String text, Class<T> clazz){
        return JSON.parseObject(text, clazz,parserConfig);
    }

    public static <T>List<T> parseList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
}
