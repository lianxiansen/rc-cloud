package com.rc.cloud.app.shop.application.service.impl;

import com.rc.cloud.app.shop.appearance.convert.ApplicationPageConfigConvert;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigDataRespVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigRespVO;
import com.rc.cloud.app.shop.application.convert.ApplicationPageConfigDTOConvert;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigCreateDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigPublishDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigSaveDTO;
import com.rc.cloud.app.shop.application.service.ApplicationPageService;
import com.rc.cloud.app.shop.infrastructure.persistence.config.ShopConstants;
import com.rc.cloud.app.shop.infrastructure.persistence.mapper.ApplicationPageConfigMapper;
import com.rc.cloud.app.shop.infrastructure.persistence.po.ApplicationPageConfigPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-07-21 14:30
 * @description TODO
 */
@Service
public class ApplicationPageServiceImpl implements ApplicationPageService {

    @Resource
    private ApplicationPageConfigMapper configMapper;

    @Override
    public void savePageConfig(ApplicationPageConfigSaveDTO saveDTO) {
        // 校验存在
        validateConfigExists(saveDTO.getId());

        ApplicationPageConfigPO reqPO = ApplicationPageConfigDTOConvert.INSTANCE.convert(saveDTO);
        ApplicationPageConfigPO applicationPageConfigPO = configMapper.selectById(reqPO.getId());
        applicationPageConfigPO.setTemplate(reqPO.getTemplate());

        configMapper.updateById(applicationPageConfigPO);
    }

    @Override
    public void createPageConfig(ApplicationPageConfigCreateDTO createDTO) {
        ApplicationPageConfigPO po = ApplicationPageConfigDTOConvert.INSTANCE.convert(createDTO);
        configMapper.insert(po);
    }

    @Override
    public void deletePageConfig(String id) {
        // 校验存在
        validateConfigExists(id);
        configMapper.deleteById(id);
    }

    @Override
    public ApplicationPageConfigDataRespVO getConfig(String id) {
        // 校验存在
        validateConfigExists(id);
        ApplicationPageConfigPO po = configMapper.selectById(id);
        ApplicationPageConfigDataRespVO convert = ApplicationPageConfigConvert.INSTANCE.convert(po);
        return convert;
    }

    @Override
    public List<ApplicationPageConfigRespVO> getList() {
        List<ApplicationPageConfigPO> applicationPageConfigPOS = configMapper.selectList();
        List<ApplicationPageConfigRespVO> applicationPageConfigRespVOS = ApplicationPageConfigConvert.INSTANCE
                .convertList(applicationPageConfigPOS);
        return applicationPageConfigRespVOS;
    }

    /**
     * 发布配置页面
     * 将原默认页面配置设置成非默认
     * 将当前配置设置成默认
     * @param reqVO
     */
    @Override
    public void publishPageConfig(ApplicationPageConfigPublishDTO reqVO) {
        ApplicationPageConfigPO convert = ApplicationPageConfigDTOConvert.INSTANCE.convert(reqVO);
        //将原默认页面配置设置成非默认
        ApplicationPageConfigPO defaultConfig = configMapper.selectOne(ApplicationPageConfigPO::getDefaulted, 1);
        defaultConfig.setDefaulted(0);

        //将当前页面设置成默认
        ApplicationPageConfigPO po = configMapper.selectById(reqVO.getId());
        po.setDefaulted(1);
        po.setTemplate(reqVO.getTemplate());
        configMapper.updateById(po);
    }

    private void validateConfigExists(String id) {
        if (configMapper.selectById(id) == null) {
            throw exception(ShopConstants.PAGECONFIG_NOT_EXISTS);
        }
    }
}
