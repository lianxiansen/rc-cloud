package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

@Data
public class ProductInfoVO {

    private int pid;

    private int sk_pid;

    private Boolean is_auto_act;

    private int is_sk_deta_page;

    private int uid;

    public Boolean getIs_auto_act() {
        if(is_auto_act==null)
        {
            return  false;
        }
        return is_auto_act;
    }
}
