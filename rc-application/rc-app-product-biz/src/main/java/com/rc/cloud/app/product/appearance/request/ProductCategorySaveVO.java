package com.rc.cloud.app.product.appearance.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @auther Ushop
 * @date 2021/4/6 14:22
 * @Description ProductCategorySaveVO
 * @PROJECT_NAME qyy-live
 */
@Data
public class ProductCategorySaveVO {
    /// <summary>
    /// 类别设定的图片
    /// </summary>
    @NotBlank(message = "类别对应的图片为null")
    private String masterimage;
    /// <summary>
    ///标题
    /// </summary>
    @NotBlank(message = "请输入类别名称")
    private String title;
    /// <summary>
    ///父级ID
    /// </summary>
    private int parentid;
    private int sortid;
    /// <summary>
    ///层级
    /// </summary>
    private int layer;
    /// <summary>
    ///是否关闭，默认false（启用）
    /// </summary>
    private boolean islock;

    private int id;
//    public ProductCategorySaveVO(){
//        id=0;
//    }
    public boolean getIslock() {
        return islock;
    }
    public void setIslock(boolean islock) {
        this.islock = islock;
    }
}

