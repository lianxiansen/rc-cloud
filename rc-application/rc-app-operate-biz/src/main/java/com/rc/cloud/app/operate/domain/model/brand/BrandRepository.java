package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.List;

/**
 * @Interface BrandRepository
 * @Author liandy
 * @Date 2023/8/4 13:14
 * @Description  品牌资源库
 * @Version 1.0
 */
public interface BrandRepository {
    /**
     * 保存品牌
     * @param brand 品牌
     * @return
     */
    boolean save(Brand brand);

    /**
     * 根据唯一标识查找品牌
     * @param brandId
     * @return
     */
    Brand findById(BrandId brandId);


    /**
     * 根据唯一标识删除品牌
     * @param brandId 品牌标识
     * @return
     */
    boolean removeById(BrandId brandId);

    /**
     * 按查询条件获取品牌分页列表
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @param name 品牌名称
     * @return
     */
    PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name);

    /**
     * 按查询条件获取品牌列表
     * @param name 品牌名称
     * @return
     */
    List<Brand> findList(String name);

    boolean existById(BrandId brandId);
}
