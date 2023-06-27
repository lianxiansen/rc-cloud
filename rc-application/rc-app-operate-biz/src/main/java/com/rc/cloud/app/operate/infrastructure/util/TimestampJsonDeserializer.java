package com.rc.cloud.app.operate.infrastructure.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * @Author:chenjianxiang
 * @Date 2021/7/27
 * @Description:
 */
public class TimestampJsonDeserializer implements ObjectDeserializer {
    @Override
    public  Timestamp deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        Object parse = defaultJSONParser.parse();
        if(parse==null){
            return null;
        }
        if(parse instanceof String){
            final String input = (String)parse;
            //\/Date(-6847833900000+0805)\/
            try {
                if(StrUtil.isNotEmpty(input) && input.contains("-")){
                    return null;
                }
                int start=input.indexOf("(");
                int end= input.indexOf("+");
                String result=input.substring(start+1,end);
                return new Timestamp(Math.abs(Convert.toLong(result)));
            } catch (Exception e) {
            } finally {
            }
        }
        return null;
    }



    @Override
    public int getFastMatchToken() {
        return 2;
    }
}
