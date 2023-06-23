package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: taotianhong
 * @Date 2021/3/23
 * @Description:
 */
public interface IProductCreateMaterialService extends IService<ProductCreateMaterial> {

    List<ProductCreateMaterial> getProductCreateMaterials(int productId);

    Integer saveProductCreateMaterials(List<ProductCreateMaterial> productCreateMaterials);

    Integer removeProductCreateMaterials(int productId);


    void setProductCreateMaterialsRedis(int productId, List<ProductCreateMaterial> productCreateMaterials);

    List<ProductCreateMaterial> getProductCreateMaterialsRedis(int productId);

    List<ProductCreateMaterial> getProductCreateMaterialsRedis(int productId, Boolean isSyncFromDbWhenNull);
}
