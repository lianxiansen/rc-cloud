package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductImageRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/29 13:25
 * @Description: TODO
 */
@Repository
class ProductImageRepositoryImpl  extends ServiceImpl<ProductImageMapper, ProductImageDO> implements IService<ProductImageDO> {

}
