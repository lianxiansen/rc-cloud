package com.rc.cloud.common.core.util.json;

/**
 * @ClassName JsonDiffUtil
 * @Author liandy
 * @Date 2023/8/1 08:54
 * @Description json差异工具
 * @Version 1.0
 */

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;


public class JsonDiffUtil {

    private static final JsonParser parser = new JsonParser();
    private static final Gson gson = new Gson();

    //默认是忽略值比较
    private boolean diffValue = false;

    private static final Logger logger = LoggerFactory.getLogger(JsonDiffUtil.class);
    private Set<String> ignoreFields = new HashSet();

    public JsonDiffUtil() {
    }

    ;

    public JsonDiffUtil(boolean diffValue) {
        this.diffValue = diffValue;
    }


    /*
     * 验证两个json字符串结构是否一致，正反两次比较
     */
    public void diff(String exceptJson, String actualJson) {
        Assert.isTrue(equals(exceptJson, actualJson));
        Assert.isTrue(equals(actualJson, exceptJson));
    }

    /**
     * 比较两个json字符串是否
     */
    public boolean equals(String exceptJson, String actualJson) {
        if (exceptJson.equals(actualJson)) {
            return true;
        }
        //json String 转 JsonElement
        JsonElement exceptJsonElement = parser.parse(exceptJson);
        JsonElement actualJsonElement = parser.parse(actualJson);

        //按key排序
        sort(exceptJsonElement);
        sort(actualJsonElement);

        return equals(exceptJsonElement, actualJsonElement);
    }

    private boolean equals(JsonElement except, JsonElement actual) {
        if (except.isJsonObject()) {
            return equals(except.getAsJsonObject(), actual.getAsJsonObject());
        } else if (except.isJsonArray()) {
            return equals(except.getAsJsonArray(), actual.getAsJsonArray());
        } else if (except.isJsonPrimitive()) {
            return equals(except.getAsJsonPrimitive(), actual.getAsJsonPrimitive());
        } else {
            if (except.isJsonNull() && actual.isJsonNull()) {
                return true;
            }
            logger.info("except= {},actual= {}", except, actual);
            return false;
        }
    }


    public JsonDiffUtil addIngoreKey(String ingoreKey) {
        this.ignoreFields.add(ingoreKey);
        return this;
    }

    public JsonDiffUtil setIgnoreFields(Set ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }

    private boolean equals(JsonObject except, JsonObject actual) {
        Set<String> exceptSet = except.keySet();
        for (String key : exceptSet) {
            if (ignoreFields.contains(key)) {
                continue;
            }
            JsonElement exceptValue = except.get(key);
            JsonElement actualValue = actual.get(key);
            if (actualValue == null) {
                logger.error("not found key：" + key);
                return false;
            }
            if (exceptValue.isJsonArray() && exceptValue.getAsJsonArray().size() != actualValue.getAsJsonArray().size()) {
                logger.error("JSON Array size not equal,key is {}", key);
                return false;
            }
            boolean flag = equals(exceptValue, actualValue);
            if (!flag) {
                if (exceptValue.isJsonPrimitive()) {
                    if (diffValue) {
                        logger.error("value not equal,key is {}, except value is {}, actual value is {}", key, exceptValue, actualValue);
                    } else {
                        logger.error("not found key：" + key);
                    }

                }
                return false;
            }


        }
        return true;
    }

    private boolean equals(JsonArray exceptArray, JsonArray actualArray) {
        List<JsonElement> exceptList = toSortedList(exceptArray);
        List<JsonElement> actaulList = toSortedList(actualArray);
        for (int i = 0; i < exceptList.size(); i++) {
            if (!equals(exceptList.get(i), actaulList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean equals(JsonPrimitive except, JsonPrimitive actual) {

        if (!diffValue) {
            return actual != null;
        }
        return except.equals(actual);
    }

    private List<JsonElement> toSortedList(JsonArray array) {
        List<JsonElement> list = new ArrayList<>();
        array.forEach(list::add);
        list.sort(Comparator.comparing(gson::toJson));
        return list;
    }

    /*
     * 根据json key排序
     */
    public static void sort(JsonElement je) {
        if (je.isJsonNull()) {
            return;
        }

        if (je.isJsonPrimitive()) {
            return;
        }

        if (je.isJsonArray()) {
            JsonArray array = je.getAsJsonArray();
            Iterator<JsonElement> it = array.iterator();
            while (it.hasNext()) {
                sort(it.next());
            }
            return;
        }

        if (je.isJsonObject()) {
            Map<String, JsonElement> tm = new TreeMap(getComparator());
            for (Map.Entry<String, JsonElement> en : je.getAsJsonObject().entrySet()) {
                tm.put(en.getKey(), en.getValue());
            }

            for (Map.Entry<String, JsonElement> en : tm.entrySet()) {
                je.getAsJsonObject().remove(en.getKey());
                je.getAsJsonObject().add(en.getKey(), en.getValue());
                sort(en.getValue());
            }
            return;
        }
    }

    private static Comparator<String> getComparator() {
        return String::compareTo;
    }


}
