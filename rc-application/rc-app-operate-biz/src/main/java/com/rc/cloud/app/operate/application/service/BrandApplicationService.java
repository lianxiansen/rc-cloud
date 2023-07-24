package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.common.core.pojo.PageResult;
/**
 * @Author liandy
 * @Date 2023/7/24 9:19
 * @Description  品牌应用服务
 * @Version 1.0
 */
public interface BrandApplicationService {
    /**
     * 创建品牌
     * @param createBrandDTO
     * @return
     */
    BrandBO create(BrandCreateDTO createBrandDTO);

    /**
     * 更新品牌
     * @param updateBrandDTO
     * @return
     */
    BrandBO update(BrandUpdateDTO updateBrandDTO);

    /**
     * 删除品牌
     * @param id
     * @return
     */
    boolean remove(String id);

    /**
     * 分页查找品牌列表
     * @param queryBrandDTO
     * @return
     */
    PageResult<BrandBO> selectPageResult(BrandQueryPageDTO queryBrandDTO);

    /**
     * 根据唯一标识获取品牌
     * @param id
     * @return
     */
    BrandBO findById(String id);
}
