package com.rc.cloud.app.operate.application.data;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EvaluationDTO {

    private String product_id;

    private String head_image_url;

    private String nick_name;

    private String content;

    private Date time;

    private Integer description_tally;

    private Integer service_attitude;

    private Integer delivery_speed;

    private Integer credit;

    private Integer reputation;

    private String albums;

    private List<String> image_list;

    private Integer order_id;

    private Integer order_item_id;

    private Integer is_system;

    private String reply;
}
