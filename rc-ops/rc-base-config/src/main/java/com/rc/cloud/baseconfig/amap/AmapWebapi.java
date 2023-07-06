package com.rc.cloud.baseconfig.amap;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-06-26 13:30
 * @description 高德数据开放接口
 */

public class AmapWebapi {

    //获取高德地图开放平台接口，获取中国以下省市区3级地区
    public static synchronized DistrictDTO getDistrict() {
        //获取高德地图开放平台接口，获取中国以下省市区3级地区
        final String provinceCityUrl = "https://restapi.amap.com/v3/config/district?keywords=中华人民共和国&subdistrict=3&key=f7f8152446d8c9846614f37206427290";
        String provinceCityStr = HttpUtil.get(provinceCityUrl);
        DistrictDTO provinceCity = JSONObject.parseObject(provinceCityStr,DistrictDTO.class);

        return provinceCity;
    }

    public static List<District> getAllProvinceCity(){
        //所有行政区列表
        List<District> districtList = new ArrayList<>();

        DistrictDTO districtDTO = getDistrict();
        //国家
        DistrictEntity conuntryEntity = districtDTO.getDistricts().get(0);
        District country = DistrictConvert.INSTANCE.convert(conuntryEntity);
        districtList.add(country);

        conuntryEntity.getDistricts().forEach(provinceEntity -> {
            //省
            District province = DistrictConvert.INSTANCE.convert(provinceEntity);
            province.setParentadcode(country.getAdcode());
            province.setParentcitycode(country.getCitycode());
            province.setLevel(District.PROVINCE_LEVEL);
            districtList.add(province);

            provinceEntity.getDistricts().forEach(cityEntity -> {
                //市
                District city = DistrictConvert.INSTANCE.convert(cityEntity);
                city.setParentadcode(provinceEntity.getAdcode());
                city.setParentcitycode(provinceEntity.getCitycode());
                city.setLevel(District.CITY_LEVEL);
                districtList.add(city);

                cityEntity.getDistricts().forEach(countyEntity -> {
                    //区
                    District county = DistrictConvert.INSTANCE.convert(countyEntity);
                    county.setParentcitycode(cityEntity.getCitycode());
                    county.setParentadcode(cityEntity.getAdcode());
                    county.setLevel(District.COUNTY_LEVEL);
                    districtList.add(county);
                });
            });
        });

        return districtList;
    }


}
