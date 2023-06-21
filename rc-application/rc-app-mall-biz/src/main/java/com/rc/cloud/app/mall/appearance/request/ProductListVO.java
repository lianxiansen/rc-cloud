package com.rc.cloud.app.mall.appearance.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class ProductListVO {

    @Max(value = 5, message = "product_type不支持(范围：0-5)")
    @Min(value = 0, message = "product_type不支持(范围：0-5)")
    private int product_type;

    @Pattern(regexp = "^\\s*$|^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s{1}(\\d{1,2}:){2}\\d{1,2}$", message = "begin_time格式不正确(格式：yyyy/MM/dd HH:mm:ss)")
    private String begin_time;

    @Pattern(regexp = "^\\s*$|^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s{1}(\\d{1,2}:){2}\\d{1,2}$", message = "end_time格式不正确(格式：yyyy/MM/dd HH:mm:ss)")
    private String end_time;

    private String keywords;

    private String creator;

    @Max(value = 2, message = "check_status不支持(范围：0-2)")
    @Min(value = 0, message = "check_status不支持(范围：0-2)")
    private Integer check_status;

    @Min(value = 1, message = "page_index不支持(范围：不小于1)")
    private int page_index;

    @Max(value = 100, message = "page_size不支持(范围：10-100)")
    @Min(value = 10, message = "page_size不支持(范围：10-100)")
    private int page_size;

    private int pid;

    @Max(value = 1, message = "order_by不支持(范围：0-1)")
    @Min(value = 0, message = "order_by不支持(范围：0-1)")
    private int order_by;

    @Max(value = 1, message = "asc_type不支持(范围：0-1)")
    @Min(value = 0, message = "asc_type不支持(范围：0-1)")
    private int asc_type;

    private String product_category_id;
}
