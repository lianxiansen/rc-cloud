package com.rc.cloud.app.mall.application.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rc.cloud.app.mall.application.service.IProductAlbumService;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ProductAlbumMapper;
import com.rc.cloud.app.mall.infrastructure.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
@Service
public class ProductAlbumServiceImpl extends ServiceImpl<ProductAlbumMapper, ProductAlbum> implements IProductAlbumService {

    @Autowired
    private ProductAlbumMapper productAlbumMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取商品相册列表
     * @param productId 商品ID
     * @return 商品相册列表
     */
    @Override
    public List<ProductAlbum> getProductAlbumList(int productId) {
        QueryWrapper<ProductAlbum> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productAlbumMapper.selectList(wrapper);
    }

    /**
     * 保存商品相册列表
     * @param productAlbums 商品相册列表
     * @return 保存数量
     */
    @Override
    public Boolean saveProductAlbums(List<ProductAlbum> productAlbums) {
        return saveBatch(productAlbums);
    }

    /**
     * 删除商品的所有相册
     * @param productId 商品ID
     * @return 删除数量
     */
    @Override
    public Integer removeProductAlbums(int productId) {
        QueryWrapper<ProductAlbum> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return productAlbumMapper.delete(wrapper);
    }

    /**
     * 设置商品相册缓存
     * @param productId 商品ID
     * @param productAlbums 商品相册列表
     */
    @Override
    public void setProductAlbumsRedis(int productId, List<ProductAlbum> productAlbums) {
        redisUtil.putHash(RedisKey.getProductAlbumHashKey(), productId, JSONUtil.toJsonStr(productAlbums));
    }

    /**
     * 获取商品相册缓存
     * @param productId 商品ID
     * @return
     */
    @Override
    public List<ProductAlbum> getProductAlbumsRedis(int productId) {
        //return redisUtil.getListFromHashList(RedisKey.getProductAlbumHashKey(), ProductAlbum.class);
        return redisUtil.getListFromHash(RedisKey.getProductAlbumHashKey(),productId, ProductAlbum.class);
    }
    @Override
    public  ProductAlbum selectOne(QueryWrapper<ProductAlbum> productAlbumQueryWrapper){
        return productAlbumMapper.selectOne(productAlbumQueryWrapper);
    }
}
