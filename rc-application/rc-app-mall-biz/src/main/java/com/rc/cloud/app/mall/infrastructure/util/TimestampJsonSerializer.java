package com.rc.cloud.app.mall.infrastructure.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/22
 * @Description:
 */
@Slf4j
public class TimestampJsonSerializer implements ObjectSerializer
{

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        SerializeWriter out = jsonSerializer.getWriter();
        if (o == null) {
            jsonSerializer.getWriter().writeNull();
            return;
        }
        Timestamp timestamp=(Timestamp)o;
        if(timestamp.getTime()<=0){
            jsonSerializer.getWriter().writeNull();
            return;
        }else{
            out.write("\"/Date("+ timestamp.getTime() +"+0800)/\"");
        }
    }
}
