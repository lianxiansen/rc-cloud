package com.rc.cloud.app.mall.application.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rc.cloud.app.mall.appearance.request.ProductCategorySaveVO;
import com.rc.cloud.app.mall.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.mall.application.data.ProductShelfEnum;
import com.rc.cloud.app.mall.application.service.IProductCategoryService;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ProductCategoryMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ProductCategoryRatationMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.po.CategoryFindGoods;
import com.rc.cloud.app.mall.infrastructure.persistence.po.Product;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategory;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategoryRatation;
import com.rc.cloud.app.mall.infrastructure.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.rc.cloud.app.mall.infrastructure.util.MapUtil.distinctByKey;


/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryRatationMapper productCategoryRatationMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 津贴用的
     *
     * @return
     */
    @Override
    public List<ProductCategory> getFirstList() {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("IsLock", false);
            wrapper.eq("Layer", 1);
            wrapper.eq("ParentID", 0);
            wrapper.orderByAsc("SortID");
            List<ProductCategory> list = productCategoryMapper.selectList(wrapper);

            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 津贴用的。
     *
     * @param vo
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryOtherList(ProductCategoryVO vo) {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("ParentID", vo.getParentid());

            if (vo.getId() > 0) {
                wrapper.ne("ID", vo.getId());
            }
            if (vo.getIslock() != null) {
                wrapper.eq("IsLock", vo.getIslock());
            }
            wrapper.orderBy(true, true, "SortID");
            List<ProductCategory> list = productCategoryMapper.selectList(wrapper);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    /**
     * @param list
     * @param id
     * @return 获取到的个数
     */
    @Override
    public long getFindCount(List<CategoryFindGoods> list, int id) {
        try {
            return list.stream().filter(u -> u.getCategoryId() == id).count();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * @param id
     * @return 是否有下级
     */
    @Override
    public long getBannerCount(int id) {
        try {
            QueryWrapper<ProductCategoryRatation> wrapper = new QueryWrapper<>();
            wrapper.eq("CategoryID", id);
            long count = productCategoryRatationMapper.selectCount(wrapper);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * @param id
     * @return 是否有下级
     */
    @Override
    public long getLowerCount(int id) {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("ParentID", id);
            wrapper.eq("IsLock", false);
            long count = productCategoryMapper.selectCount(wrapper);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 通用获取规格列表
     *
     * @param vo
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(ProductCategoryVO vo) {
        try {
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
           wrapper.eq("ParentID", vo.getParentid());
            if (!StrUtil.isEmptyOrUndefined(vo.getKeyword())) {
                wrapper.like("Title", vo.getKeyword());
            }
            if (vo.getIslock() != null) {
                wrapper.eq("IsLock", vo.getIslock());
            }
//            if (vo.getParentid() > 0) {
//                wrapper.eq("ParentID", vo.getParentid());
//            }
            if (vo.getLayer() > 0) {
                wrapper.eq("Layer", vo.getLayer());
            }
            if (vo.getId() > 0) {
                wrapper.ne("ID", vo.getId());
            }
            wrapper.eq("IsDeleted", false);
            wrapper.orderBy(true, true, "SortID");
            List<ProductCategory> list = productCategoryMapper.selectList(wrapper);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * @param category_id
     */
    @Override
    public List<List<Integer>> getProductCategoryLevel(int category_id) {
        try {
            List<Integer> levelList = new ArrayList<>();
            this.getProductCategoryParentLayer(category_id, levelList);
            //Collections.sort(levelList, Collections.reverseOrder());
            Collections.reverse(levelList);
            for (int i = 0; i < 3; i++) {
                if (levelList.size() == i) {
                    levelList.add(0);
                }
            }
            List<List<Integer>> result = new ArrayList<>();
            result.add(levelList);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * @param id
     * @param levelList
     */
    protected void getProductCategoryParentLayer(int id, List<Integer> levelList) {
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", id);
        wrapper.eq("IsDeleted", false);
        wrapper.select("top 1 *");
        ProductCategory model = productCategoryMapper.selectOne(wrapper);
        if (model == null) {
            return;
        }
        levelList.add(model.getId());
        if (model.getLayer() == 1 || model.getParentId() == 0) {
            return;
        }
        this.getProductCategoryParentLayer(model.getParentId(), levelList);//递归进入当前项的子级
    }

    @Override
    public List<List<Integer>> getProductCategoryLevel(String keyword) {
        try {
            List<List<Integer>> pc_dto = new ArrayList<>();
            //需要排除低层级的父级
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.like("Title", keyword);
            wrapper.eq("IsDeleted", false);
            List<ProductCategory> categoryList = productCategoryMapper.selectList(wrapper);
            List<ProductCategory> thirdCategories = categoryList.stream().filter(u -> u.getLayer() == 3).filter(distinctByKey(b -> b.getParentId())).collect(Collectors.toList());//ParentId去重
            List<Integer> thirdParentCategoryIDs = thirdCategories.stream().map(ProductCategory::getParentId).distinct().collect(Collectors.toList());
            List<ProductCategory> secondCategories = categoryList.stream().filter(u -> u.getLayer() == 2 && !thirdParentCategoryIDs.contains(u.getId())).filter(distinctByKey(b -> b.getParentId())).collect(Collectors.toList());
            List<Integer> secondParentCategory = categoryList.stream().map(ProductCategory::getParentId).distinct().collect(Collectors.toList());
            List<ProductCategory> firstCategories = categoryList.stream().filter(u -> u.getLayer() == 1 && !secondParentCategory.contains(u.getId())).collect(Collectors.toList());
            firstCategories.forEach(v -> {
                List<Integer> temp = new ArrayList<>();
                temp.add(v.getId());
                temp.add(0);
                temp.add(0);
                pc_dto.add(temp);
            });
            secondCategories.forEach(v -> {
                List<Integer> temp = new ArrayList<>();
                temp.add(v.getParentId());
                temp.add(v.getId());
                temp.add(0);
                pc_dto.add(temp);
            });
            thirdCategories.forEach(v -> {
                List<Integer> temp = new ArrayList<>();
                ProductCategory parent = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().eq("ID", v.getParentId()));
                if (parent != null) {
                    temp.add(parent.getParentId());
                    temp.add(v.getParentId());
                    temp.add(v.getId());
                    pc_dto.add(temp);
                }
            });
            return pc_dto;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 获取一条
     *
     * @param id
     * @return 更新数
     */
    @Override
    public ProductCategory getProductCategory(int id) {
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", id);
        ProductCategory model = productCategoryMapper.selectOne(wrapper);
        if (model != null && model.getLayer() > 1 && model.getParentId() > 0) {
            ProductCategory modelII = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().eq("ID", model.getParentId()).eq("IsDeleted", false).select("ParentID"));
            if (modelII != null) {
                model.setGrandParentId(modelII.getParentId());
            }
        }
        return model;
    }

    /**
     * 保存和更新
     *
     * @param model
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int saveProductCategory(ProductCategory model) {
        try {
            int status = 0;
            ProductCategory s = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().ne("ID", model.getId()).eq("Title", model.getTitle()).select("top 1 *"));
            if (s != null) {
                if (s.getIsDeleted().equals(0)) {
                    throw new RuntimeException("当前类别名已经使用，请更换名称");
                } else {
                    s.setIsDeleted(0);
                    s.setParentId(model.getParentId());
                    s.setLayer(model.getLayer());
                    s.setLinkUrl(model.getLinkUrl());
                    s.setMasterImage(model.getMasterImage());
                    s.setSortId(model.getSortId());
                    s.setIsLock(model.getIsLock());
                    model = s;
                }
            }
            ProductCategory parentCategory = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().eq("ID", model.getParentId()).eq("IsDeleted", 0));
            boolean parentIsLock = false;
            if (parentCategory != null) {
                parentIsLock = parentCategory.getIsLock();
                model.setLayer(parentCategory.getLayer() + 1);
            } else {
                model.setLayer(1);
            }
            if (model.getLayer().equals(3)) {
                if (model.getId() > 0 && productCategoryMapper.selectCount(new QueryWrapper<ProductCategory>().eq("ParentID", model.getId())) > 0) {
                    throw new RuntimeException("当前类别已有子级类别，不允许设置为三级类别");
                }
            }
            if (model.getLayer() > 3) {
                throw new RuntimeException("类别最多设置三级");
            }
            if (parentIsLock) {
                model.setIsLock(true);
            }
            if (model.getId() > 0) {
                ProductCategory oriCategory = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().eq("ID", model.getId()));
                if (oriCategory == null) {
                    throw new RuntimeException("类别对象为空");
                }
                // 拷贝，且更新使用当前类别名称的产品
                String idStr = "," + model.getId().toString() + ",";
                //旗下有产品的时候，层级不允许修改，那么子级也就没有更新必要了
                if (productMapper.selectCount(new QueryWrapper<Product>().eq("IsDeleted", false).like("ProductCategoryIDs", idStr)) > 0) {
                    if (!model.getLayer().equals(oriCategory.getLayer())) {
                        model.setLayer(oriCategory.getLayer());//层级不变
                    }
                    //如果旗下有产品，但是当前需要修改为隐藏状态，则进行错误返回。
                    if (parentIsLock) {
                        throw new RuntimeException("当前类别下已有产品，不允许选择父级为隐藏状态的类别");
                    }
                    model.setIsLock(false);
                } else {
                    if (model.getLayer().equals(1) || model.getLayer().equals(2)) {
                        List<ProductCategory> models = productCategoryMapper.selectList(new QueryWrapper<ProductCategory>().eq("ParentID", model.getId()).eq("IsDeleted", 0).eq("IsLock", !model.getIsLock()));
                        //取下级与传入的lock相反的列表要更新这些
                        models.forEach(v -> {
                            ProductCategory cloneP = ObjectUtil.clone(v);
                            cloneP.setIsLock(!cloneP.getIsLock());
                            productCategoryMapper.update(cloneP, new QueryWrapper<ProductCategory>().eq("ID", cloneP.getId()));
                            if (cloneP.getLayer().equals(1)) {
                                List<ProductCategory> modelsII = productCategoryMapper.selectList(new QueryWrapper<ProductCategory>().eq("ParentID", cloneP.getId()).eq("IsDeleted", 0).eq("IsLock", !cloneP.getIsLock()));
                                modelsII.forEach(y -> {
                                    ProductCategory clonePII = ObjectUtil.clone(y);
                                    clonePII.setIsLock(!clonePII.getIsLock());
                                    productCategoryMapper.update(clonePII, new QueryWrapper<ProductCategory>().eq("ID", clonePII.getId()));
                                });
                            }
                        });
                    }
                }
                oriCategory.setIsLock(model.getIsLock());
                oriCategory.setLayer(model.getLayer());
                oriCategory.setMasterImage(model.getMasterImage());
                oriCategory.setParentId(model.getParentId());
                oriCategory.setSortId(model.getSortId());
                //当原来类别的名称和现在的不同的时候，需要去更新所有产品的类别name值
                if (!oriCategory.getTitle().equals(model.getTitle())) {
                    //针对店铺的
                    String oriTitle = String.format(",%s,", oriCategory.getTitle());
                    String nowTitle = String.format(",%s,", model.getTitle());

                    List<Product> productList = productMapper.selectList(new QueryWrapper<Product>().like("ProductCategoryNames", oriTitle));
                    productList.forEach(v -> {
                        v.setProductCategoryNames(v.getProductCategoryNames().replace(oriTitle, nowTitle));
                        productMapper.update(v, new QueryWrapper<Product>().eq("ID", v.getId()));
                    });
                    oriCategory.setTitle(model.getTitle());
                }
                status = productCategoryMapper.update(oriCategory, new QueryWrapper<ProductCategory>().eq("ID", oriCategory.getId()));
            } else {
                status = productCategoryMapper.insert(model);
            }
            return status;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    /**
     * 修改排序
     *
     * @param ids
     * @param sorts
     * @return 更新数
     */
    @Override
    public int updateSortId(String ids, String sorts) {
        List<Integer> idList = ListUtil.ToIntList(StringUtil.trim(ids, Convert.toChar(",")));
        List<Integer> sortIdList =ListUtil.ToIntList(StringUtil.trim(sorts, Convert.toChar(",")));
        int updatedCount = 0;//已更新的记录数
        int status =0;
        for (int i = 0; i < idList.size(); i++) {
            ProductCategory productCategory = getProductCategory(idList.get(i));
            if (productCategory != null) {
                productCategory.setSortId(sortIdList.get(i));
                QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", idList.get(i));
                //productCategory.setUpdateTime(DateUtil.date().toTimestamp());
                 status += productCategoryMapper.update(productCategory, wrapper);

            }
        }
        if (status > 0) {
            refreshRedis(null);
            //redisUtil.putHash(RedisKey.getProductCategoryHashKey(), productCategory.getId(), productCategory);
            //redisUtil.putHash(RedisKey.getProductCategoryHashKey(), productCategory.getId(), productCategory);
            updatedCount++;
        }
        return updatedCount;
    }


    /**
     * 修改审核状态
     *
     * @param ids
     * @param checkStatus
     * @return 更新数
     */
    @Override
    public int updateCheckStatus(String ids, boolean checkStatus) {
        List<Integer> idList =ListUtil.ToIntList(StringUtil.trim(ids, Convert.toChar(",")));
        int updatedCount = 0;//已更新的记录数
        int status =0;
        for (Integer id : idList) {
            ProductCategory productCategory = getProductCategory(id);
            if (productCategory != null) {
                productCategory.setIsLock(checkStatus);
                QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", id);
                status += productCategoryMapper.update(productCategory, wrapper);


            }
        }
        if (status > 0) {
            refreshRedis(null);
            //redisUtil.putHash(RedisKey.getProductCategoryHashKey(), productCategory.getId(), productCategory);
            //redisUtil.putHash(RedisKey.getProductCategoryHashKey(), productCategory.getId(), productCategory);
            updatedCount++;
        }
        return updatedCount;
    }

    /**
     * 移除
     *
     * @param ids
     * @return 更新数
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int removeProductCategory(String ids) {
        try {
            List<Integer> idList =ListUtil.ToIntList(StringUtil.trim(ids, Convert.toChar(",")));
            int updatedCount = 0;//已更新的记录数
            for (Integer id : idList) {
                QueryWrapper<ProductCategory> productCategoryQueryWrapper = new QueryWrapper<ProductCategory>().eq("ID", id);
                ProductCategory model = productCategoryMapper.selectOne(productCategoryQueryWrapper);
                if (model == null) {
                    throw new RuntimeException(String.format("类别ID[%s]未找到产品类别", id));
                }
                if (productCategoryMapper.selectCount(new QueryWrapper<ProductCategory>().eq("ParentID", id).eq("IsDeleted", 0)) > 0) {
                    throw new RuntimeException(String.format("类别ID[%s]当前类别下有相应的子类别,不允许删除，需要删除子类别后才能删除", id));
                }
                if (model.getIsLock().equals(false)) {
                    throw new RuntimeException(String.format("类别ID[%s]当前类别不是隐藏状态，不允许删除", id));
                }
                String idStr = "," + model.getId().toString() + ",";

                if (productMapper.selectCount(new QueryWrapper<Product>().eq("IsDeleted", false).eq("IsOnShelf", ProductShelfEnum.OnShelf.value).like("ProductCategoryIDs", idStr)) > 0) {//如果存在产品，则不允许隐藏，直接设置为false
                    throw new RuntimeException(String.format("类别ID[%s]前类别下有使用中的产品，不允许删除", id));
                }
                model.setIsDeleted(1);
                model.setLayer(1);
                model.setParentId(0);
                int status = productCategoryMapper.update(model, productCategoryQueryWrapper);
                if (status > 0) {
                    updatedCount++;
                }
            }
            if(updatedCount>0){
                refreshRedis(null);
            }
            return updatedCount;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }


    /**
     * @param vo
     * @param model
     * @return
     */
    @Override
    public int copyAndSaveProductCategory(ProductCategorySaveVO vo, ProductCategory model) {
        CopyBeanUtils.copyProperties(vo, model, CopyBeanStrategyEnum.formVo);
        model.setMasterImage(vo.getMasterimage());
        model.setTitle(vo.getTitle());
        model.setParentId(vo.getParentid());
        model.setSortId(vo.getSortid());
        model.setLayer(vo.getLayer());
        model.setIsLock(vo.getIslock());
        model.setId(vo.getId());
        return this.saveProductCategory(model);
    }
    /**
     * 刷新缓存
     */
    @Override
    public void refreshRedis(Object ids) {
        try {
            if (ids == null) {//更新全部缓存
                redisUtil.deleteHash(RedisKey.getProductCategoryHashKey());
                List<ProductCategory> productCategories = productCategoryMapper.selectList(new QueryWrapper<>());
                for (ProductCategory model : productCategories) {
                    {
                        redisUtil.putHash(RedisKey.getProductCategoryHashKey(), model.getId(), model);
                    }
                }
            } else {//更新部分缓存
                List<Integer> idList = ListUtil.ToIntList(ids.toString());
                for (Integer id : idList) {
                    ProductCategory productCategory = productCategoryMapper.selectOne(new QueryWrapper<ProductCategory>().eq("ID", id));
                    if (productCategory != null) {
                        redisUtil.putHash(RedisKey.getProductCategoryHashKey(), id, productCategory);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public List<ProductCategory> getRedisList(){
       return redisUtil.getListFromHashList(RedisKey.getProductCategoryHashKey(),ProductCategory.class);
    }
}
