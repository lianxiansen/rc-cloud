package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/9
 * @Description:
 */
@Data
public class ProductVO implements Serializable {

    private  String access_token;

    private String loginToken;

    private  String begin_time;

    private  String end_time;

    private  String keyword;

    private  String is_black_list;

    private  Integer page_index;

    private  Integer page_size;

}
