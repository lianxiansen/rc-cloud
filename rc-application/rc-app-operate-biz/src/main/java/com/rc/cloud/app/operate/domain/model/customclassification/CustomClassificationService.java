package com.rc.cloud.app.operate.domain.model.customclassification;

import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.infrastructure.constants.ErrorCodeConstants;
import com.rc.cloud.app.operate.infrastructure.constants.CustomClassificationErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomClassificationService {


    @Autowired
    private CustomClassificationRepository customClassificationRepository;


    public CustomClassification create(CustomClassification customClassification) {
        if (Objects.nonNull(customClassification.getParentId())) {
            CustomClassification parentCategory = customClassificationRepository.findById(customClassification.getParentId());
            if (Objects.isNull(parentCategory)) {
                throw new ServiceException(CustomClassificationErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            customClassification.setParent(parentCategory);
        }
        if (!customClassificationRepository.save(customClassification)) {
            throw new ServiceException(ErrorCodeConstants.SYSTEM_EXCEPTION);
        }
        return customClassification;
    }


    /**
     * 更新分类
     * @param customClassification
     * @return
     */
    public boolean update(CustomClassification customClassification) {
        CustomClassification oldModel = customClassificationRepository.findById(customClassification.getId());
        if(oldModel.getParentId()!=customClassification.getParentId()){
            return updateWithParent(customClassification);
        }else{
            return customClassificationRepository.save(customClassification);
        }
    }

    /**
     * 更新类目父级，同时需要更新layer
     * @param customClassification
     * @return
     */
    public boolean updateWithParent(CustomClassification customClassification){
        List<CustomClassification> allList = customClassificationRepository.findAll();
        if (Objects.nonNull(customClassification.getParentId())) {
            CustomClassification parent = customClassificationRepository.findById(customClassification.getParentId());
            if (Objects.isNull(parent)) {
                throw new ServiceException(CustomClassificationErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            customClassification.setParent(parent);

        } else {
            customClassification.root();
        }
        updateChildrenLayer(allList, customClassification);
        return customClassificationRepository.save(customClassification);
    }


    public List<CustomClassification> findAll() {
        return customClassificationRepository.findAll();
    }


    public boolean remove(CustomClassification customClassification) {
        if(Objects.isNull(customClassification)){
            throw new ServiceException(CustomClassificationErrorCodeConstants.ID_NOT_EMPTY);
        }
        if (customClassificationRepository.existsByParentId(customClassification.getId())) {
            throw new ServiceException(CustomClassificationErrorCodeConstants.REMOVE_SHOULD_NOT_HAS_CHILD);
        }
        return customClassificationRepository.removeById(customClassification.getId());
    }

    /**
     * 从产品分类列表中找出子分类并重新继承父产品分类，继承之后子分类将根据父分类产品属性更新自身属性
     * @param allList
     * @param parent
     */
    public void updateChildrenLayer(List<CustomClassification> allList, CustomClassification parent) {
        List<CustomClassification> subList = findSubList(allList, parent);
        if (CollectionUtils.isAnyEmpty(subList)) {
            return;
        }
        subList.forEach(item -> {
            item.setParent(parent);
            customClassificationRepository.save(item);
            updateChildrenLayer(allList, item);
        });

    }

    private List<CustomClassification> findSubList(List<CustomClassification> allList, CustomClassification parent) {
        AssertUtils.notNull(allList, "allList must not be null");
        AssertUtils.notNull(parent, "parent must not be null");
        List<CustomClassification> list = new ArrayList<>();
        allList.forEach(item -> {
            if (parent.getId().equals(item.getParentId())) {
                list.add(item);
            }
        });
        return list;
    }

    public void enable(CustomClassification customClassification) {
        if(customClassification==null){
            throw new ServiceException(CustomClassificationErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        customClassification.enable();
        customClassificationRepository.save(customClassification);
    }

    public void disable(CustomClassification customClassification) {
        if(customClassification==null){
            throw new ServiceException(CustomClassificationErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        customClassification.enable();
        customClassificationRepository.save(customClassification);
    }

    public CustomClassification findById(CustomClassificationId customClassificationId) {
        return customClassificationRepository.findById(customClassificationId);
    }

    public boolean existByName(String name) {
        if(StringUtils.isNotEmpty(name)){
            return customClassificationRepository.existsByName(name);
        }
        return false;
    }
}



