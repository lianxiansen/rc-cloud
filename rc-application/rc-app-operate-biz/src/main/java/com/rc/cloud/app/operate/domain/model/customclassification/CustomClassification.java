package com.rc.cloud.app.operate.domain.model.customclassification;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;

public class CustomClassification extends Entity {

    CustomClassificationId id;

    private String name;

    private Url customClassificationImage;

    private Url productPoster;

    private Url customClassificationPoster;

    private Boolean enabledFlag;

    public CustomClassification(CustomClassificationId id, String name) {
        this.id = id;
        this.name = name;
        customClassificationImage =new Url("");
        productPoster =new Url("");
        customClassificationPoster =new Url("");
        enabledFlag=true;
    }

    @Override
    public AbstractId getId() {
        return null;
    }
}
