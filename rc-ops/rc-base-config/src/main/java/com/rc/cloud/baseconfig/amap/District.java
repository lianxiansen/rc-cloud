package com.rc.cloud.baseconfig.amap;

import lombok.Data;

/**
 * @author WJF
 * @create 2023-07-06 10:33
 * @description TODO
 */

@Data
public class District {
    public final static String PROVINCE_LEVEL="province";
    public final static String CITY_LEVEL="city";
    public final static String COUNTY_LEVEL="county";

    //城市编码
    private String citycode;

    //区域编码
    private String adcode;

    //行政区名称
    private String name;

    //区域中心点
    private String center;

    //level
    private String level;

    //父级城市编码
    private String parentcitycode;

    //父级区域编码
    private String parentadcode;
    /*
     *行政区边界坐标点
     * 当一个行政区范围，由完全分隔两块或者多块的地块组成，每块地的 polyline 坐标串以 | 分隔 。
     * 如北京的朝阳区
     */
    private String polyline;

}
