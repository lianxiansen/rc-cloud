package com.rc.cloud.app.operate.application.bo;


import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class CustomClassificationBO {

    private String id;

    private String name;

    private String customClassificationImage;

    private String productPoster;

    private String customClassificationPoster;


    private String parentId;

    private boolean enabled;

    private int sort;
    private LocalDateTime createTime;
    public static List<CustomClassificationBO> convertBatch(List<CustomClassification> customClassifications) {
        List<CustomClassificationBO> boList=new ArrayList<>();
        customClassifications.forEach(item->{
            boList.add(CustomClassificationBO.convert(item));
        });
        return boList;
    }
    public static CustomClassificationBO convert(CustomClassification customClassification) {
        CustomClassificationBO bo=new CustomClassificationBO();
        bo= new CustomClassificationBO();
        bo.setName(customClassification.getName());
        bo.setCustomClassificationImage(customClassification.getCustomClassificationImage().getValue());
        bo.setCustomClassificationPoster(customClassification.getCustomClassificationPoster().getValue());
        bo.setEnabled(customClassification.getEnabledFlag());
        bo.setProductPoster(customClassification.getProductPoster().getValue());
        bo.setSort(customClassification.getSort().getValue());
        if(customClassification.getCreateTime()!=null){
            bo.setCreateTime(customClassification.getCreateTime().getTime());
        }
        if(Objects.nonNull(customClassification.getParentId())){
            bo.setParentId(customClassification.getParentId().id());
        }
        return bo;
    }
    
}
