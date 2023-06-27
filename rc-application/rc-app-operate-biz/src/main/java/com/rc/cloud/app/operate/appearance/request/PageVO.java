package com.rc.cloud.app.operate.appearance.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/8
 * @Description:
 */

public class PageVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 9008896600900756372L;

    /**
     * 当前页码
     */
    private Integer page_index;

    /**
     * 每页条数
     */

    @Max(value = 100, message = "最大长度为100")
    @Min(value = 1, message = "最小长度为1")
    private Integer page_size;

    /**
     * 设置默认值
     * @return
     */
    public String paramValidate() {
        page_index = page_index == null ? 1 : page_index;
        page_size = page_size == null ? 10 : page_size;

        if (page_index < 1 || page_size < 0) {
            return "page_index不能小于1，page_size不能小于0";
        }
        return null;
    }

    /**
     * 设置默认值
     *
     * @return
     */
    public Integer getStart() {
        return (this.getPage_index() - 1) * this.getPage_size();
    }

    public Integer getPage_index() {
        return page_index == null ? 1 : page_index;
    }

    public void setPage_index(Integer page_index) {
        this.page_index = page_index;
    }

    public Integer getPage_size() {
        return page_size == null ? 10 : page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
    public PageVO(){
        page_index=1;
        page_size=10;
    }
}
