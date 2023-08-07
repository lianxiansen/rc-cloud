package com.rc.cloud.app.operate.domain.model.customclassification;

import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;

public interface CustomClassificationRepository {

    void insertCustomClassification(CustomClassification customClassification);

    void updateCustomClassification(CustomClassification customClassification);

    CustomClassification findById(CustomClassificationId customClassificationId);

    boolean exist(CustomClassificationId customClassificationId);

    void removeCustomClassification(CustomClassificationId customClassificationId);
}
