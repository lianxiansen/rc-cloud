package com.rc.cloud.baseconfig.amap;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.time.Duration;

/**
 * @author WJF
 * @create 2023-06-26 13:30
 * @description 高德数据开放接口
 */

public class AmapWebapi {

    //获取高德地图开放平台接口，获取中国以下省市区3级地区
    public static synchronized Object getAllProvinceCity() {
        //获取高德地图开放平台接口，获取中国以下省市区3级地区
        final String provinceCityUrl = "https://restapi.amap.com/v3/config/district?keywords=中华人民共和国&subdistrict=3&key=f7f8152446d8c9846614f37206427290";
        String provinceCityStr = HttpUtil.get(provinceCityUrl);
        Object provinceCity = JSONObject.parseObject(provinceCityStr);
        return provinceCity;
    }

}
