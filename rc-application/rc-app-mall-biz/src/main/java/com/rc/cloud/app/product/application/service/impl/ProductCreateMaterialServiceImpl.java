package com.rc.cloud.app.product.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxun.qlive.common.redis.RedisKey;
import com.qxun.qlive.common.redis.RedisUtil;
import com.qxun.qlive.goods.api.IProductCreateMaterialService;
import com.qxun.qlive.goods.mapper.ProductCreateMaterialMapper;
import com.qxun.qlive.goods.model.ProductCreateMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: taotianhong
 * @Date 2021/3/23
 * @Description:
 */
@Service
public class ProductCreateMaterialServiceImpl extends ServiceImpl<ProductCreateMaterialMapper, ProductCreateMaterial> implements IProductCreateMaterialService {

    @Autowired
    private ProductCreateMaterialMapper productCreateMaterialMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取商品素材列表
     * @param productId 商品ID
     * @return 商品素材列表
     */
    public List<ProductCreateMaterial> getProductCreateMaterials(int productId) {
        QueryWrapper<ProductCreateMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productCreateMaterialMapper.selectList(wrapper);
    }

    /**
     * 保存商品素材列表
     * @param productCreateMaterials 商品素材列表
     * @return 保存数量
     */
    public Integer saveProductCreateMaterials(List<ProductCreateMaterial> productCreateMaterials) {
        Integer rows = 0;
        for (ProductCreateMaterial productCreateMaterial : productCreateMaterials) {
            if (productCreateMaterial.getId() > 0) {
                QueryWrapper<ProductCreateMaterial> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", productCreateMaterial.getId());
                rows += productCreateMaterialMapper.update(productCreateMaterial, wrapper);
            } else {
                rows += productCreateMaterialMapper.insert(productCreateMaterial);
            }
        }
        return rows;
    }

    /**
     * 删除商品的所有素材
     * @param productId 商品ID
     * @return 删除数量
     */
    public Integer removeProductCreateMaterials(int productId) {
        QueryWrapper<ProductCreateMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productCreateMaterialMapper.delete(wrapper);
    }

    /**
     * 设置商品素材缓存
     * @param productId 商品ID
     * @param productCreateMaterials 商品素材列表
     */
    public void setProductCreateMaterialsRedis(int productId, List<ProductCreateMaterial> productCreateMaterials) {
        redisUtil.putHash(RedisKey.getProductCreateMaterialsHashKey(), productId, productCreateMaterials);
    }

    /**
     * 获取商品素材缓存
     * @param productId 商品ID
     * @return 商品素材缓存
     */
    public List<ProductCreateMaterial> getProductCreateMaterialsRedis(int productId) {
        return getProductCreateMaterialsRedis(productId, false);
    }

    /**
     * 获取商品素材缓存
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品素材缓存
     */
    public List<ProductCreateMaterial> getProductCreateMaterialsRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductCreateMaterialsHashKey();
        List<ProductCreateMaterial> productCreateMaterials = redisUtil.getListFromHash(redisKey, productId, ProductCreateMaterial.class);
        if ((CollUtil.isEmpty(productCreateMaterials) || productCreateMaterials.size() < 1) && isSyncFromDbWhenNull.equals(true)) {
            productCreateMaterials = getProductCreateMaterials(productId);
            if (CollUtil.isNotEmpty(productCreateMaterials) && productCreateMaterials.size() > 0)
                setProductCreateMaterialsRedis(productId, productCreateMaterials);
        }
        return productCreateMaterials;
    }
}
