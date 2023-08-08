package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationCreateDTO;
import com.rc.cloud.app.operate.application.dto.CustomClassificationUpdateDTO;
import com.rc.cloud.app.operate.application.service.CustomClassificationApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomClassificationApplicationServiceImpl implements CustomClassificationApplicationService {
    @Override
    public CustomClassificationBO create(CustomClassificationCreateDTO customClassificationCreateDTO) {
        return null;
    }

    @Override
    public CustomClassificationBO update(CustomClassificationUpdateDTO customClassificationUpdateDTO) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public List<CustomClassificationBO> findAll() {
        return null;
    }

    @Override
    public CustomClassificationBO findById(String id) {
        return null;
    }
}
