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
        //TODO
        if(Objects.nonNull(customClassification.getParentId())){
            bo.setParentId(customClassification.getParentId().id());
        }
        return bo;
    }
    
}
