package com.rc.cloud.app.shop.application.service;

import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigDataRespVO;
import com.rc.cloud.app.shop.appearance.resp.ApplicationPageConfigRespVO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigCreateDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigPublishDTO;
import com.rc.cloud.app.shop.application.dto.ApplicationPageConfigSaveDTO;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-21 14:07
 * @description TODO
 */

public interface ApplicationPageService {

    /**
     * 保存页面配置数据
     *
     * @param reqVO
     */
    void savePageConfig(ApplicationPageConfigSaveDTO reqVO);

    /**
     * 创建页面配置
     *
     * @param reqVO
     */
    void createPageConfig(ApplicationPageConfigCreateDTO reqVO);

    /**
     * 删除页面配置
     *
     * @param id
     */
    void deletePageConfig(String id);

    /**
     * 获取配置数据
     *
     * @param id
     * @return ApplicationPageConfigDataRespVO
     */
    ApplicationPageConfigDataRespVO getConfig(String id);

    /**
     * 获取配置列表
     *
     * @return List<ApplicationPageConfigRespVO>
     */
    List<ApplicationPageConfigRespVO> getList();

    /**
     * 发布页面
     * @param reqVO
     * @return
     */
    void publishPageConfig(ApplicationPageConfigPublishDTO reqVO);
}
