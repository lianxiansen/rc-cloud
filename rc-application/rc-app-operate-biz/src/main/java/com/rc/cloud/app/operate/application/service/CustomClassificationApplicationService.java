package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;

import java.util.List;

public interface CustomClassificationApplicationService {

    CustomClassificationBO create(CustomClassificationCreateDTO productCreateCategoryDTO);

    CustomClassificationBO update(CustomClassificationUpdateDTO CustomClassificationUpdateDTO);

    boolean remove(String id);

    List<CustomClassificationBO> findAll();

    CustomClassificationBO findById(String id);
}
