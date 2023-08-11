package com.rc.cloud.app.operate.domain.model.customclassification;

import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;

import java.util.List;

public interface CustomClassificationRepository {

    CustomClassification findById(CustomClassificationId customClassificationId);

    List<CustomClassification> findAll();
    void insert(CustomClassification customClassification);
    void update(CustomClassification customClassification);
    boolean remove(CustomClassification customClassification);

    boolean removeById(CustomClassificationId customClassificationId);

    boolean existsByParentId(CustomClassificationId customClassificationId);

    boolean existsByName(String name);
}
