package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;
import com.rc.cloud.app.operate.application.service.CustomClassificationApplicationService;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassification;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationBuildFactory;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationRebuildFactory;
import com.rc.cloud.app.operate.domain.model.customclassification.CustomClassificationService;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.customclassification.valobj.Url;
import com.rc.cloud.app.operate.infrastructure.constants.CustomClassificationErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomClassificationApplicationServiceImpl implements CustomClassificationApplicationService {
    @Resource
    private CustomClassificationService customClassificationService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private CustomClassificationBuildFactory customClassificationBuildFactory;
    @Autowired
    private CustomClassificationRebuildFactory customClassificationRebuildFactory;
    @Override
    public CustomClassificationBO createCustomClassification(CustomClassificationCreateDTO customClassificationCreateDTO) {
        if (StringUtils.isEmpty(customClassificationCreateDTO.getName())) {
            throw new ServiceException(CustomClassificationErrorCodeConstants.NAME_NOT_EMPTY);
        }
        CustomClassification customClassification = buildCustomClassification(customClassificationCreateDTO);
        customClassificationService.create(customClassification);
        return CustomClassificationBO.convert(customClassification);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomClassificationBO updateCustomClassification(CustomClassificationUpdateDTO updateDTO) {
        if (StringUtils.isEmpty(updateDTO.getId())) {
            throw new ServiceException(CustomClassificationErrorCodeConstants.ID_NOT_EMPTY);
        }
        CustomClassification customClassification = customClassificationService.findById(new CustomClassificationId(updateDTO.getId()));
        if (Objects.isNull(customClassification)) {
            throw new ServiceException(CustomClassificationErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        CustomClassification updatedModel = rebulidCustomClassification(updateDTO, customClassification);
        customClassificationService.update(updatedModel);
        return CustomClassificationBO.convert(updatedModel);
    }




    @Override
    public boolean removeCustomClassification(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(CustomClassificationErrorCodeConstants.ID_NOT_EMPTY);
        }
        CustomClassificationId customClassificationId = new CustomClassificationId(id);
        CustomClassification customClassification = customClassificationService.findById(customClassificationId);
        return customClassificationService.remove(customClassification);
    }

    @Override
    public List<CustomClassificationBO> findCustomClassifications() {
        List<CustomClassificationBO> boList = new ArrayList<>();
        List<CustomClassification> list = customClassificationService.findAll();
        return CustomClassificationBO.convertBatch(list);
    }

    @Override
    public CustomClassificationBO findCustomClassificationById(String id) {
        return CustomClassificationBO.convert(customClassificationService.findById(new CustomClassificationId(id)));
    }


    private CustomClassification buildCustomClassification(final CustomClassificationCreateDTO dto) {
       CustomClassificationBuildFactory.CustomClassificationBuilder builder
               = customClassificationBuildFactory.create(new CustomClassificationId(idRepository.nextId()), dto.getName());

        builder.customClassificationPoster(new Url(dto.getCustomClassificationPoster()));
        builder.customClassificationImage(new Url(dto.getCustomClassificationImage()));
        builder.productPoster(new Url(dto.getProductPoster()));

        if (Objects.nonNull(dto.getEnabled())) {
            builder.setEnabled(new Enabled(dto.getEnabled()));
        }
        if (Objects.nonNull(dto.getSort())) {
            builder.sort(new Sort(dto.getSort()));
        }
        if (!StringUtils.isEmpty(dto.getParentId())) {
            CustomClassificationId parentId = new CustomClassificationId(dto.getParentId());
            builder.parentId(parentId);
        }
        CustomClassification customClassification = builder.build();
        return customClassification;
    }

    private CustomClassification rebulidCustomClassification(final CustomClassificationUpdateDTO updateDTO
            , CustomClassification domainModel) {
        CustomClassificationRebuildFactory.CustomClassificationRebuilder rebuilder =
                customClassificationRebuildFactory.create(domainModel);
        if (Objects.nonNull(updateDTO.getParentId())) {
            if (StringUtils.isEmpty(updateDTO.getParentId())) {
                rebuilder.parentId(null);
            } else {
                rebuilder.parentId(new CustomClassificationId(updateDTO.getParentId()));
            }
        }
        if (StringUtils.isNotEmpty(updateDTO.getName())) {
            rebuilder.name(updateDTO.getName());
        }

        if (StringUtils.isNotEmpty(updateDTO.getCustomClassificationImage())) {
            rebuilder.customClassificationImage(new Url(updateDTO.getCustomClassificationImage()));
        }
        if (StringUtils.isNotEmpty(updateDTO.getCustomClassificationPoster())) {
            rebuilder.customClassificationPoster(new Url(updateDTO.getCustomClassificationPoster()));
        }
        if (StringUtils.isNotEmpty(updateDTO.getProductPoster())) {
            rebuilder.productPoster(new Url(updateDTO.getProductPoster()));
        }
        if (Objects.nonNull(updateDTO.getSort())) {
            rebuilder.sort(new Sort(updateDTO.getSort()));
        }
        if (Objects.nonNull(updateDTO.getEnabled())) {
            rebuilder.setEnabled(new Enabled(updateDTO.getEnabled().booleanValue()));
        }
        domainModel = rebuilder.rebuild();
        return domainModel;
    }
}
