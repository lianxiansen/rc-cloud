package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/10
 * @Description:
 */
@Data
public class UpdateVO {

    @NotBlank(message = "ids不能为空")
    private  String ids;
    @NotBlank(message = "sorts不能为空")
    private  String sorts;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }
}
