package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.appearance.admin.resp.convert.CustomClassificationConvert;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.common.core.util.date.LocalDateTimeUtils;
import com.rc.cloud.common.core.util.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CustomClassificationResponse extends TreeNode {


    @Schema(description = "产品自定义分类名称")
    private String name;

    @Schema(description = "自定义分类图片")
    private String customClassificationImage;

    @Schema(description = "自定义商品海报")
    private String productPoster;

    @Schema(description = "自定义分类海报")
    private String customClassificationPoster;

    @Schema(description = "父级产品分类唯一标识")
    private String parentId;
    @Schema(description = "状态，是否启用")
    private boolean enabled;
    @Schema(description = "排序")
    private int sort;
    @Schema(description = "创建时间")
    private String createTime;

    public static CustomClassificationResponse from(CustomClassificationBO customClassificationBO){
        CustomClassificationResponse response= CustomClassificationConvert.convertToCustomClassificationResponse(customClassificationBO);
        return response;
    }


    public static List<CustomClassificationResponse> from(List<CustomClassificationBO> classificationBOList){
        List<CustomClassificationResponse> list=new ArrayList<CustomClassificationResponse>();
        classificationBOList.forEach(item->{
            list.add(from(item));
        });
        return list;
    }
}
