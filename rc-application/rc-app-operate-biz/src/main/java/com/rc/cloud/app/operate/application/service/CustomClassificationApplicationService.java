package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationBuildFactory;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationRebuildFactory;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.constants.CustomClassificationErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface CustomClassificationApplicationService {


    CustomClassificationBO createCustomClassification(CustomClassificationCreateDTO productCreateCategoryDTO);

    CustomClassificationBO updateCustomClassification(CustomClassificationUpdateDTO updateDTO);


    boolean removeCustomClassification(String id);

    List<CustomClassificationBO> findCustomClassifications();

    CustomClassificationBO findCustomClassificationById(String id);

}
