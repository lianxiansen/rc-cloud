package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductSetBooleanValueVO {

    @NotNull(message = "请设置set_value")
    private Boolean set_value;

    @NotNull(message = "请设置pids")
    @Size(min = 1, message = "请设置pids")
    private List<Integer> pids;
}
