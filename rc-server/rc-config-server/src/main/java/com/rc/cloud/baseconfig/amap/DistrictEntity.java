package com.rc.cloud.baseconfig.amap;

import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-06 10:00
 * @description TODO
 */
@Data
public class DistrictEntity {
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

    /*
     *行政区边界坐标点
     * 当一个行政区范围，由完全分隔两块或者多块的地块组成，每块地的 polyline 坐标串以 | 分隔 。
     * 如北京的朝阳区
     */
    private String polyline;

    //下级行政区列表，包含district元素
    private List<DistrictEntity> districts;
}
