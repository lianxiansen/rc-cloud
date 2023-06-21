package com.rc.cloud.app.mall.application.data;

import com.rc.cloud.app.mall.appearance.request.PageVO;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/8
 * @Description:
 */
public class BaseQuery extends PageVO {

    public BaseQuery(){
        this.keyword="";
        this.type=-1;
        this.order_by=0;
        this.desc=-1;
        this.sort=0;
    }

    private int id;

    private  String keyword;

    private int type;

    private int order_by;

    private int desc;

    private int sort;

    private String token;

    private String userToken;

    private String liveToken;
    private String name;
    private String visit_ip;
    private String code;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder_by() {
        return order_by;
    }

    public void setOrder_by(int order_by) {
        this.order_by = order_by;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getLiveToken() {
        return liveToken;
    }

    public void setLiveToken(String liveToken) {
        this.liveToken = liveToken;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getVisit_ip() { return visit_ip; }
    public void setVisit_ip(String visit_ip) {
        this.visit_ip = visit_ip;
    }

    public String getCode() { return code; }
    public void setCode(String code) {
        this.code = code;
    }
}
