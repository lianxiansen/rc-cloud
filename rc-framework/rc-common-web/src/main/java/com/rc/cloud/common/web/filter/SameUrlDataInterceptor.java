package com.rc.cloud.common.web.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.cloud.common.redis.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author WJF
 * @create 2023-08-05 14:20
 * @description TODO
 */

public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";
    public final static String REPEAT_SUBMIT_KEY = "REPEAT_SUBMIT_KEY";

    private String header = "Authorization";

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String nowParams = "";

//        if (request instanceof RepeatedlyRequestWrapper) {
//            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
//            try {
//                nowParams = repeatedlyRequest.getReader().readLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(nowParams)) {
            try {
                nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> nowDataMap = new HashMap<String, Object>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String submitKey = request.getHeader(header);

        // 唯一标识（指定key + url + 消息头）
        String cacheRepeatKey = REPEAT_SUBMIT_KEY + url + submitKey;

        Object sessionObj = RedisUtils.getCacheObject(cacheRepeatKey);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (compareParams(nowDataMap, sessionMap) && compareTime(nowDataMap, sessionMap, annotation.interval())) {
                return true;
            }
        }

        RedisUtils.setCacheObject(cacheRepeatKey, nowDataMap, annotation.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        if ((time1 - time2) < interval) {
            return true;
        }
        return false;
    }
}
