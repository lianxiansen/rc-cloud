package com.rc.cloud.app.operate.appearance.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * @auther Ushop
 * @date 2021/3/28 10:38
 * @Description ProductCategoryDTO
 * @PROJECT_NAME qyy-live
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)//漂准化
public class AdminProductCategoryDTO {
    private int id;
    private String masterImage;
    private String title;
    private int parentID;
    private int layer;
    private boolean isLock;
    private boolean hasChildren;

    private long findCount;
    private long bannerCount;
private List<Object> childrenList;

    private String linkUrl;
    private int sortID;
    private int isDeleted;
    private int linkType;
    private int seckillProductID;
    private int productID;
    private int groupBookingProductID;
    private long createTime;
    private int grandParentID;

    public boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
