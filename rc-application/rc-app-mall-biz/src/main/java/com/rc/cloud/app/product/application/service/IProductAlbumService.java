package com.rc.cloud.app.product.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxun.qlive.goods.model.ProductAlbum;

import java.util.List;

/**
*
* @Author taotianhong
* @Date 2021-03-25
* @Description:
*/
public interface IProductAlbumService extends IService<ProductAlbum> {

    List<ProductAlbum> getProductAlbumList(int productId);

    Boolean saveProductAlbums(List<ProductAlbum> productAlbums);

    Integer removeProductAlbums(int productId);


    void setProductAlbumsRedis(int productId, List<ProductAlbum> productAlbums);

    List<ProductAlbum> getProductAlbumsRedis(int productId);
    ProductAlbum selectOne(QueryWrapper<ProductAlbum> productAlbumQueryWrapper);
}
