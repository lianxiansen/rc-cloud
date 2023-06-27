package com.rc.cloud.app.operate.application.data;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class MerchantQuery extends BaseQuery {
    private String begin_time;
    private String end_time;
    private int check_status;
    private int copartner_id;
    private int from_origin;
    public MerchantQuery(){
        check_status=-1;
        end_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateTime.now());
        begin_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.offsetMonth(DateTime.now(),-36));
    }
}
