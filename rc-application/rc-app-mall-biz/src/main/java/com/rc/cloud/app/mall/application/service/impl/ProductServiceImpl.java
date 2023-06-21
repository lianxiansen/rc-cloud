package com.rc.cloud.app.mall.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rc.cloud.app.mall.appearance.request.*;
import com.rc.cloud.app.mall.application.data.*;
import com.rc.cloud.app.mall.application.service.*;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.po.*;
import com.rc.cloud.app.mall.infrastructure.util.MapUtil;
import com.rc.cloud.app.mall.infrastructure.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private IItemSpecificationService iItemSpecificationService;

    @Autowired
    private IProductAlbumService iProductAlbumService;

//    @Autowired
//    private IMyPictureService iMyPictureService;

    @Autowired
    private IProductFreightCouponService iProductFreightCouponService;

    @Autowired
    private IProductFullCouponService iProductFullCouponService;

//    @Autowired
//    private IMerchantService iMerchantService;
//
//    @Autowired
//    private IWareHouseService iWareHouseService;

    @Autowired
    private IProductCreateMaterialService iProductCreateMaterialService;

//    @Autowired
//    private IStoreyCategoryProductService iStoreyCategoryProductService;

    @Autowired
    private ISpecificationService iSpecificationService;

    @Autowired
    private ISpecificationValueService iSpecificationValueService;

//    @Autowired
//    private ISecKillProductService iSecKillProductService;
//
//    @Autowired
//    private ISecKillItemService iSecKillItemService;
//
//    @Autowired
//    private ISecKillItemSpecificationService iSecKillItemSpecificationService;

//    @Autowired
//    private IOperateRecordService iOperateRecordService;

    /**
     * 获取商品
     *
     * @param productId 商品ID
     * @return 商品信息
     */
    @Override
    public Product getProduct(int productId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", productId);
        return productMapper.selectOne(wrapper);
    }

    /**
     * 获取商品缓存或数据库
     *
     * @param productId 商品ID
     * @return 商品信息
     */
    public Product getProductRedisOrBase(int productId) {
        Product result;
        result = redisUtil.getHash(RedisKey.getProductHashKey(), Convert.toLong(productId), Product.class);
        if (result == null) {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", productId);
            return productMapper.selectOne(wrapper);
        }
        return result;
    }

    /**
     * 根据淘宝商品ID获取商品
     *
     * @param fromAlibabaProductId 淘宝商品ID
     * @return 商品实例
     */
    @Override
    public Product getProductByAlibabaProductId(String fromAlibabaProductId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("IsAlibabaSelected", 1);
        wrapper.eq("FromAlibabaProductID", fromAlibabaProductId);
        return productMapper.selectOne(wrapper);
    }

    /**
     * 根据淘宝商品ID获取商品
     * @param fromAlibabaProductId 淘宝商品ID
     * @param id 商品ID
     * @return 商品实例
     */
    @Override
    public Product getProductByAlibabaProductId(String fromAlibabaProductId, Integer id) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("IsAlibabaSelected", 1);
        wrapper.eq("FromAlibabaProductID", fromAlibabaProductId);
        wrapper.ne("ID", id);
        return productMapper.selectOne(wrapper);
    }

    /**
     * 获取不发货地区模板商品列表
     * @param noDeliveryTemplateId 不发货地区模板ID
     * @return 商品列表
     */
    @Override
    public List<Product> getProductListByNoDeliveryTemplateId(int noDeliveryTemplateId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("NoDeliveryTemplateID", noDeliveryTemplateId);
        return productMapper.selectList(wrapper);
    }

    /**
     * 后台商品列表专用筛选
     * @param query 筛选参数
     * @return 商品列表
     */
    @Override
    public PageInfo<Product> getProductPageListInAdmin(ProductListVO query) {
        if (ObjectUtil.isEmpty(query)) {
            query = new ProductListVO();
        }
        PageHelper.startPage(query.getPage_index(), query.getPage_size());
        List<Product> list = productMapper.getProductPageListInAdmin(query);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<AdminProductListDTO> getAdminProductList(ProductRequestVO query) {
        try {
            if (query == null) {
                query = new ProductRequestVO();
            }
            int orderby = 1;
            boolean isStore = false;
            if (query.getIs_merchant_login() != null && Convert.toBool(query.getIs_merchant_login())) {
                isStore = true;
                orderby = 2;
            }
            if (query.getShelf() != null && query.getShelf().equals(ProductShelfEnum.OffShelf.value)) {
                orderby = 3;

            } else {
                if (query.getByinventory() != null) {
                    if (query.getByinventory().equals(1)) {
                        if (!isStore) {
                            orderby = 4;
                        } else {
                            orderby = 5;
                        }
                    } else if (query.getByinventory().equals(2)) {
                        if (!isStore) {
                            orderby = 6;
                        } else {
                            orderby = 7;
                        }
                    }
                } else {
                    if (!isStore) {
                        orderby = 8;
                    } else {
                        orderby = 9;
                    }
                }
            }
            query.setOrderby(orderby);
            PageHelper.startPage(query.getPage_index(), query.getPage_size());
            List<AdminProductListDTO> list = productMapper.getAdminProductList(query);
            PageInfo<AdminProductListDTO> pageInfo = new PageInfo<>(list);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 获取相似商品列表
     * @param productIds 商品ID
     * @return 商品列表
     */
    @Override
    public List<Product> getSimiliarProductList(List<Integer> productIds) {
        return productMapper.getSimiliarProductList(new SimiliarProductQuery(productIds));
    }

    /**
     * 获取商品列表
     * @param productIds 商品ID
     * @return 商品列表
     */
    public List<Product> getProductList(List<Integer> productIds) {
        return productMapper.getProductList(new SimiliarProductQuery(productIds));
    }

    /**
     * 新增或修改
     * @param product 商品实例
     * @return
     */
    @Override
    public boolean saveOrUpdate(Product product) {
        if (product.getId() > 0) {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", product.getId());
            return (productMapper.update(product, wrapper) > 0);
        } else {
            return (productMapper.insert(product) > 0);
        }
    }

    /**
     * 保存商品
     * @param product                 商品信息
     * @param merchant                商户信息
     * @param wareHouse               仓库信息
     * @param productAlbums           商品相册列表
     * @param productCreateMaterials  商品素材列表
     * @param goodsSpecs              商品规格列表
     * @param goodsSkus               商品SKU列表
     * @param oriNoDeliveryTemplateID 旧的不发货模板
     * @return 商品ID
     */
//    @Override
//    @Transactional(rollbackFor = {Exception.class} )
//    public Integer saveProduct(Product product, Merchant merchant, WareHouse wareHouse, List<ProductAlbum> productAlbums, List<ProductCreateMaterial> productCreateMaterials, List<GoodsItemSpecificationSaveDTO> goodsSpecs, List<GoodsSkuSaveDTO> goodsSkus, ProductFreightCoupon productFreightCoupon, ProductFullCoupon productFullCoupon, int oriNoDeliveryTemplateID) {
//        Integer result = 0;
//        try {
//            Boolean isUpdate = (product.getId() > 0);
//            // 1688自建商户和仓库
//            if (product.getIsAlibabaSelected()) {
//                if (iMerchantService.saveMerchant(merchant) < 1) return result;
//                wareHouse.setMerchantId(merchant.getId());
//                if (iWareHouseService.saveWareHouse(wareHouse) < 1) return result;
//                product.setMerchantID(merchant.getId());
//                product.setWareHouseID(wareHouse.getId());
//            }
//            // 商品
//            BigDecimal minPrice = BigDecimal.valueOf(goodsSkus.stream().mapToDouble(s -> s.getPrice().doubleValue()).min().getAsDouble());
//            product.setPrice(minPrice);
//            if (!saveOrUpdate(product)) {
//                log.debug(StrUtil.format("数据库保存商品失败：{}；方法：{}", product.getName(), "com.qxun.qlive.goods.service.impl.ProductServiceImpl.saveProduct"));
//                return result;
//            }
//            result = product.getId();
//            // 商品相册
//            iProductAlbumService.removeProductAlbums(product.getId());
//            List<MyPicture> myPictures = new ArrayList<>();
//            if (ObjectUtil.isNotNull(productAlbums) && ObjectUtil.isNotEmpty(productAlbums) && productAlbums.size() > 0) {
//                for (int i = 0; i < productAlbums.size(); i++) {
//                    productAlbums.get(i).setProductID(product.getId());
//                    MyPicture myPicture = iMyPictureService.getMyPictureNeedSave(product.getId(), productAlbums.get(i).getImage());
//                    if (ObjectUtil.isNotNull(myPicture)) {
//                        myPictures.add(myPicture);
//                    }
//                }
//                iProductAlbumService.saveProductAlbums(productAlbums);
//            }
//            // 商品素材
//            iProductCreateMaterialService.removeProductCreateMaterials(product.getId());
//            if (ObjectUtil.isNotNull(productCreateMaterials) && ObjectUtil.isNotEmpty(productCreateMaterials) && productCreateMaterials.size() > 0) {
//                for (int i = 0; i < productCreateMaterials.size(); i++) {
//                    productCreateMaterials.get(i).setProductID(product.getId());
//                    MyPicture myPicture = iMyPictureService.getMyPictureNeedSave(product.getId(), productCreateMaterials.get(i).getImage());
//                    if (ObjectUtil.isNotNull(myPicture)) {
//                        myPictures.add(myPicture);
//                    }
//                }
//                iProductCreateMaterialService.saveProductCreateMaterials(productCreateMaterials);
//            }
//            // 我的图片
//            if (ObjectUtil.isNotNull(myPictures) && ObjectUtil.isNotEmpty(myPictures) && myPictures.size() > 0) {
//                iMyPictureService.saveMyPictures(myPictures);
//            }
//            // 规格项
//            if (ObjectUtil.isNotNull(goodsSpecs) && ObjectUtil.isNotEmpty(goodsSpecs) && goodsSpecs.size() > 0) {
//                for (int i = 0; i < goodsSpecs.size(); i++) {
//                    GoodsItemSpecificationSaveDTO goodsSpec = goodsSpecs.get(i);
//                    if (product.getIsAlibabaSelected()) {
//                        if (goodsSpec.getSpecId() < 1) {
//                            Specification spec = iSpecificationService.getSpecificationBySpecNameFromAlibaba(goodsSpec.getSpecName());
//                            if (ObjectUtil.isNull(spec)) {
//                                spec = new Specification(goodsSpec.getSpecName(), 99, -1688);
//                                iSpecificationService.saveSpecification(spec);
//                                goodsSpec.setSpecId(spec.getId());
//                            }
//                        }
//                        for (GoodsItemSpecificationValueSaveDTO goodsSpecValue : goodsSpec.getSpecValues()) {
//                            SpecificationValue specValue = iSpecificationValueService.getSpecificationValueByKeyIdAndValueId(goodsSpec.getSpecId(), goodsSpecValue.getSpecValueId());
//                            if (ObjectUtil.isNull(specValue)) {
//                                specValue = new SpecificationValue(goodsSpecValue.getSpecValueName(), goodsSpec.getSpecId(), 99);
//                                iSpecificationValueService.saveSpecificationValue(specValue);
//                                goodsSpecValue.setSpecValueId(specValue.getId());
//                            }
//                        }
//                    } else {
//                        Specification spec = iSpecificationService.getSpecificationBySpecId(goodsSpec.getSpecId());
//                        if (ObjectUtil.isNull(spec)) {
//                            log.debug(StrUtil.format("商品(ID：{})保存成功，规格项保存失败：不存在的规格项：{}；方法：{}", product.getId(), goodsSpec.getSpecId(), "com.qxun.qlive.goods.service.impl.ProductServiceImpl.saveProduct"));
//                            return 0;
//                        }
//                        for (GoodsItemSpecificationValueSaveDTO goodsSpecValue : goodsSpec.getSpecValues()) {
//                            SpecificationValue specValue = iSpecificationValueService.getSpecificationValueByKeyIdAndValueId(goodsSpec.getSpecId(), goodsSpecValue.getSpecValueId());
//                            if (ObjectUtil.isNull(specValue)) {
//                                log.debug(StrUtil.format("商品(ID：{})保存成功，规格项保存失败：不存在的规格值：{}；方法：{}", product.getId(), goodsSpecValue.getSpecValueId(), "com.qxun.qlive.goods.service.impl.ProductServiceImpl.saveProduct"));
//                                return 0;
//                            }
//                        }
//                    }
//                }
//            }
//            // SKU
//            List<Item> oriItems = new ArrayList<>();
//            if (isUpdate) {
//                oriItems = iItemService.getItemList(product.getId());
//            }
//            List<Integer> newItemIds = new ArrayList<>();
//            List<Item> updateSkItems = new ArrayList<>();
//            List<ItemSpecification> itemSpecs = new ArrayList<>();
//            for (GoodsSkuSaveDTO sku : goodsSkus) {
//                Item item = null;
//                Boolean isItemUpdate = false;
//                if (sku.getId() > 0) {
//                    item = oriItems.stream().filter(i -> i.getId().equals(sku.getId())).findFirst().orElse(null);
//                    isItemUpdate = true;
//                }
//                if (ObjectUtil.isNull(item)) {
//                    item = new Item();
//                    item.setProductID(product.getId());
//                    item.setIsLock(false);
//                }
//                item.setNumber(sku.getCargoNumber());
//                item.setCodeNumber(sku.getCargoCode());
//                item.setPrice(sku.getPrice());
//                item.setSupplyPrice(sku.getSupplyPrice());
//                item.setWeight(sku.getWeight());
//                item.setPartnerPrice(sku.getPartnerPrice());
//                item.setInventory(sku.getInventory());
//                item.setImageUrl(sku.getImageUrl());
//
//                List<ItemSpecification> _itemSpecs = new ArrayList<>();
//                if (product.getIsAlibabaSelected()) {
//                    item.setFromAlibabaSkuId(sku.getFromAlibabaSkuId());
//                    item.setFromAlibabaSpecId(sku.getFromAlibabaSpecId());
//
//                    StringBuilder specCombIdStr = new StringBuilder(""), specCombNames = new StringBuilder("");
//                    for (GoodsSkuSpecKVPSaveDTO kvp : sku.getFromAlibabaSpecKeyValuePairs()) {
//                        GoodsItemSpecificationSaveDTO itemSpec = goodsSpecs.stream().filter(g -> g.getSpecName().equals(kvp.getSpecName())).findFirst().get();
//                        GoodsItemSpecificationValueSaveDTO itemSpecValue = itemSpec.getSpecValues().stream().filter(g -> g.getSpecValueName().equals(kvp.getSpecValueName())).findFirst().get();
//                        specCombIdStr.append(itemSpecValue.getSpecValueId() + ",");
//                        specCombNames.append(itemSpecValue.getSpecValueName() + "*");
//
//                        ItemSpecification itemSpecification = new ItemSpecification();
//                        itemSpecification.setItemID(item.getId());
//                        itemSpecification.setProductID(product.getId());
//                        itemSpecification.setSortID(itemSpecValue.getSpecValueSortId());
//                        itemSpecification.setSortIDForTitle(itemSpec.getSpecSortId());
//                        itemSpecification.setSpecificationID(itemSpec.getSpecId());
//                        itemSpecification.setSpecificationValueID(itemSpecValue.getSpecValueId());
//                        itemSpecification.setSpecificationTitle(itemSpec.getSpecName());
//                        itemSpecification.setSpecificationContent(itemSpecValue.getSpecValueName());
//                        itemSpecification.setIsAlibabaSelected(true);
//                        _itemSpecs.add(itemSpecification);
//                    }
//                    item.setSpecificationCombinationID(specCombIdStr.toString());
//                    item.setSpecificationCombinationName(specCombNames.toString());
//                } else {
//                    item.setWareHouseItemID(sku.getWareHouseItemID());
//                    item.setSpecificationCombinationID(sku.getSpecificationCombinationID());
//                    item.setSpecificationCombinationName(sku.getSpecificationCombinationName());
//
//                    List<String> specIds = StrUtil.split(item.getSpecificationCombinationID(), ',', true, true);
//                    List<String> specNames = StrUtil.split(item.getSpecificationCombinationName(), '*', true, true);
//                    for (int i = 0; i < specIds.size(); i++) {
//                        String _specId = specIds.get(i);
//                        GoodsItemSpecificationSaveDTO itemSpec = goodsSpecs.stream().filter(g -> g.getSpecValues().stream().filter(v -> v.getSpecValueId().equals(_specId)).count() > 0).findFirst().get();
//                        GoodsItemSpecificationValueSaveDTO itemSpecValue = itemSpec.getSpecValues().stream().filter(v -> v.getSpecValueId().equals(_specId)).findFirst().get();
//
//                        ItemSpecification itemSpecification = new ItemSpecification();
//                        itemSpecification.setItemID(item.getId());
//                        itemSpecification.setProductID(product.getId());
//                        itemSpecification.setSortID(itemSpecValue.getSpecValueSortId());
//                        itemSpecification.setSortIDForTitle(itemSpec.getSpecSortId());
//                        itemSpecification.setSpecificationID(itemSpec.getSpecId());
//                        itemSpecification.setSpecificationValueID(itemSpecValue.getSpecValueId());
//                        itemSpecification.setSpecificationTitle(itemSpec.getSpecName());
//                        itemSpecification.setSpecificationContent(itemSpecValue.getSpecValueName());
//                        itemSpecification.setIsAlibabaSelected(false);
//                        _itemSpecs.add(itemSpecification);
//                    }
//                }
//                iItemService.saveItem(item);
//                if (isItemUpdate)
//                    updateSkItems.add(item);
//                else {
//                    Integer itemId = item.getId();
//                    _itemSpecs.forEach(s -> s.setItemID(itemId));
//                }
//
//                newItemIds.add(item.getId());
//                itemSpecs.addAll(_itemSpecs);
//            }
//            List<Integer> delItemIds = oriItems.stream().filter(i -> newItemIds.stream().filter(n -> n.equals(i.getId())).count() < 1).map(n -> n.getId()).collect(Collectors.toList());
//            iItemService.removeByIds(delItemIds);
//            // SKU规格
//            iItemSpecificationService.removeItemSpecifications(product.getId());
//            iItemSpecificationService.saveItemSpecifications(itemSpecs);
//            // 商品满包邮
//            iProductFreightCouponService.removeProductFreightCoupons(product.getId());
//            if (product.getIsOpenFreightCoupon() && ObjectUtil.isNotNull(productFreightCoupon) && ObjectUtil.isNotEmpty(productFreightCoupon)) {
//                productFreightCoupon.setProductID(product.getId());
//                iProductFreightCouponService.saveProductFreightCoupon(productFreightCoupon);
//            }
//            // 商品满减
//            iProductFullCouponService.removeProductFullCoupons(product.getId());
//            if (product.getIsOpenFullCoupon() && ObjectUtil.isNotNull(productFullCoupon) && ObjectUtil.isNotEmpty(productFullCoupon)) {
//                productFullCoupon.setProductID(product.getId());
//                iProductFullCouponService.saveProductFullCoupon(productFullCoupon);
//            }
//            // 抢购
////            List<SecKillProduct> needUpdateSkProducts = iSecKillProductService.getSecKillProductListByProductId(product.getId());
////            if (ObjectUtil.isNotNull(needUpdateSkProducts) && ObjectUtil.isNotEmpty(needUpdateSkProducts) && needUpdateSkProducts.size() > 0) {
////                for (int i = 0; i < needUpdateSkProducts.size(); i++) {
////                    SecKillProduct skProduct = needUpdateSkProducts.get(i);
////                    skProduct.setProductPrice(product.getPrice());
////                    skProduct.setMasterImage(product.getMasterImage());
////                    skProduct.setProductName(product.getName());
////                }
////                iSecKillProductService.saveSecKillProducts(needUpdateSkProducts);
////
////                if (ObjectUtil.isNotNull(updateSkItems) && ObjectUtil.isNotEmpty(updateSkItems) && updateSkItems.size() > 0) {
////                    List<SecKillItemSpecification> skItemSpecs = iSecKillItemSpecificationService.getSecKillItemSpecificationListByProductId(product.getId());
////                    List<SecKillItem> newSkItems = new ArrayList<>();
////                    List<Integer> newSkItemSpecIds = new ArrayList<>();
////                    for (Item item : updateSkItems) {
////                        SecKillItem skItem = iSecKillItemService.getSecKillItemByItemId(item.getId());
////                        if (ObjectUtil.isNotNull(skItem) && ObjectUtil.isNotEmpty(skItem)) {
////                            skItem.setImageUrl(item.getImageUrl());
////                            skItem.setSupplyPrice(item.getSupplyPrice());
////                            skItem.setNumber(item.getNumber());
////                            skItem.setCodeNumber(item.getCodeNumber());
////                            skItem.setPartnerPrice(item.getPartnerPrice());
////                            skItem.setItemPrice(item.getPrice());
////                            iSecKillItemService.saveSecKillItem(skItem);
////
////                            List<ItemSpecification> thisItemSpecs = itemSpecs.stream().filter(i -> i.getItemID().equals(skItem.getItemID())).collect(Collectors.toList());
////                            for (ItemSpecification itemSpec : thisItemSpecs) {
////                                SecKillItemSpecification secKillItemSpec = skItemSpecs.stream().filter(s -> s.getItemID().equals(itemSpec.getItemID()) && s.getSpecificationID().equals(itemSpec.getSpecificationID())).findFirst().orElse(null);
////                                if (ObjectUtil.isNull(secKillItemSpec)) {
////                                    secKillItemSpec = new SecKillItemSpecification();
////                                    secKillItemSpec.setItemID(skItem.getItemID());
////                                    secKillItemSpec.setSecKillItemID(skItem.getId());
////                                    secKillItemSpec.setProductID(product.getId());
////                                    secKillItemSpec.setSecKillProductID(skItem.getSecKillProductID());
////                                    secKillItemSpec.setSpecificationID(itemSpec.getSpecificationID());
////                                }
////                                secKillItemSpec.setSpecificationTitle(itemSpec.getSpecificationTitle());
////                                secKillItemSpec.setSpecificationValueID(itemSpec.getSpecificationValueID());
////                                secKillItemSpec.setSpecificationContent(itemSpec.getSpecificationContent());
////                                secKillItemSpec.setSortID(itemSpec.getSortID());
////                                secKillItemSpec.setSortIDForTitle(itemSpec.getSortIDForTitle());
////                                secKillItemSpec.setIsAlibabaSelected(itemSpec.getIsAlibabaSelected());
////                                iSecKillItemSpecificationService.saveSecKillItemSpecification(secKillItemSpec);
////
////                                newSkItemSpecIds.add(secKillItemSpec.getId());
////                            }
////                        }
////                    }
////                    if (newSkItemSpecIds.size() > 0) {
////                        List<Integer> oriSkItemSpecIds = skItemSpecs.stream().map(s -> s.getId()).collect(Collectors.toList());
////                        List<Integer> delSkItemSpecIds = oriSkItemSpecIds.stream().filter(i -> newSkItemSpecIds.stream().filter(n -> n.equals(i)).count() < 1).collect(Collectors.toList());
////                        iSecKillItemSpecificationService.removeByIds(delSkItemSpecIds);
////                    }
////                }
////            }
//            // 操作记录
////            OperateRecord operateRecord = new OperateRecord();
////            operateRecord.setTableName("Product");
////            operateRecord.setIsUpdated(false);
////            operateRecord.setOperateIP(NetUtil.getLocalhostStr());
////            operateRecord.setOperateLoginName(tokenService.getUserName());
////            if (isUpdate.equals(true)) {
////                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductUpdate.name);
////                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductUpdate.value);
////            } else {
////                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductAdd.name);
////                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductAdd.value);
////            }
////            operateRecord.setEntityID(StrUtil.format(",{},", product.getId()));
////            operateRecord.setState(CreateOrderNoUtil.StateNumberWithThreadID());
////            iOperateRecordService.saveOrUpdate(operateRecord);
//        } catch (Exception ex) {
//            log.debug(ex.toString());
//        } finally {
//            return result;
//        }
//    }

    /**
     * 仅保存商品
     * @param product 商品信息
     * @return 商品ID
     */
    @Override
    public Integer saveProduct(Product product) {
        if (saveOrUpdate(product)) {
            setProductRedis(product, false);
        }
        return product.getId();
    }

    /**
     * 批量保存商品排序
     * @param saveSortProductSortVOS 提交商品排序信息
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean saveProductSort(List<ProductSaveSortProductSortVO> saveSortProductSortVOS) {
        Boolean result = Boolean.FALSE;
        try {
            for (ProductSaveSortProductSortVO vo : saveSortProductSortVOS) {
                QueryWrapper<Product> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", vo.getPid());
                Product product = productMapper.selectOne(wrapper);
                if (ObjectUtil.isNotEmpty(product)) {
                    product.setSortID(vo.getSort());
                    product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    if (productMapper.update(product, wrapper) < 1) {
                        throw new Exception(StrUtil.format("商品(ID：{})排序保存失败", vo.getPid()));
                    }
                }
            }
            // 操作记录
//            OperateRecord operateRecord = new OperateRecord();
//            operateRecord.setTableName("Product");
//            operateRecord.setIsUpdated(false);
//            operateRecord.setOperateIP(NetUtil.getLocalhostStr());
//            operateRecord.setOperateLoginName(tokenService.getUserName());
//            operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductSort.name);
//            operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductSort.value);
//            operateRecord.setEntityID("," + saveSortProductSortVOS.stream().map(s -> String.valueOf(s.getPid())).collect(Collectors.joining(",")) + ",");
//            operateRecord.setState(CreateOrderNoUtil.StateNumberWithThreadID());
//            iOperateRecordService.saveOrUpdate(operateRecord);
            result = Boolean.TRUE;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return result;
    }

    /**
     * 批量设置商品推荐
     * @param productIds 商品ID们
     * @param isRecommend 是否推荐
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean setProductRecommend(List<Integer> productIds, Boolean isRecommend) {
        Boolean result = Boolean.FALSE;
        try {
            for (Integer productId : productIds) {
                QueryWrapper<Product> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", productId);
                Product product = productMapper.selectOne(wrapper);
                if (ObjectUtil.isNotEmpty(product)) {
                    product.setIsRecommend(isRecommend);
                    product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    if (productMapper.update(product, wrapper) < 1) {
                        throw new Exception(StrUtil.format("商品(ID：{})排序保存失败", productId));
                    }
                }
            }
            // 操作记录
//            OperateRecord operateRecord = new OperateRecord();
//            operateRecord.setTableName("Product");
//            operateRecord.setIsUpdated(false);
//            operateRecord.setOperateIP(NetUtil.getLocalhostStr());
//            operateRecord.setOperateLoginName(tokenService.getUserName());
//            if (isRecommend) {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductRecommend.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductRecommend.value);
//            } else {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductRecommendOff.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductRecommendOff.value);
//            }
//            operateRecord.setEntityID("," + productIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ",");
//            operateRecord.setState(CreateOrderNoUtil.StateNumberWithThreadID());
//            iOperateRecordService.saveOrUpdate(operateRecord);
            result = Boolean.TRUE;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return result;
    }

    /**
     * 批量设置商品上下架状态
     * @param productIds 商品ID们
     * @param isOnShelf 是否上架
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Boolean setProductShelfStatus(List<Integer> productIds, Boolean isOnShelf) {
        Boolean result = Boolean.FALSE;
        try {
            for (Integer productId : productIds) {
                QueryWrapper<Product> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", productId);
                Product product = productMapper.selectOne(wrapper);
                if (ObjectUtil.isNotEmpty(product)) {
                    product.setIsOnShelf(isOnShelf.equals(true) ? ProductShelfEnum.OnShelf.value : ProductShelfEnum.OffShelf.value);
                    product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    product.setOffShelfTime(new Timestamp(System.currentTimeMillis()));
                    if (isOnShelf.equals(true)) {
                        product.setIsRecommend(true);
                    }
                    if (1 > productMapper.update(product, wrapper)) {
                        throw new Exception(StrUtil.format("商品(ID：{})上下架保存失败", productId));
                    }
                }
            }
            // 操作记录
//            OperateRecord operateRecord = new OperateRecord();
//            operateRecord.setTableName("Product");
//            operateRecord.setIsUpdated(false);
//            operateRecord.setOperateIP(NetUtil.getLocalhostStr());
//            operateRecord.setOperateLoginName(tokenService.getUserName());
//            if (isOnShelf) {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductShelfOn.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductShelfOn.value);
//            } else {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductShelfOff.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductShelfOff.value);
//            }
//            operateRecord.setEntityID("," + productIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ",");
//            operateRecord.setState(CreateOrderNoUtil.StateNumberWithThreadID());
//            iOperateRecordService.saveOrUpdate(operateRecord);
            result = Boolean.TRUE;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return result;
    }

    /**
     * 批量设置商品审核状态
     * @param productIds 商品ID们
     * @param isCheck 是否审核通过
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean setProductCheckStatus(List<Integer> productIds, Boolean isCheck) {
        Boolean result = Boolean.FALSE;
        try {
            List<Product> products = getSimiliarProductList(productIds);
            for (Product product : products) {
                product.setCheckStatus(isCheck.equals(true) ? 1 : 2);
                product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                if (productMapper.updateById(product) < 1) {
                    throw new Exception(StrUtil.format("商品(ID：{})审核保存失败", product.getId()));
                }
            }
            result = Boolean.TRUE;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return result;
    }

    /**
     * 批量删除商品或还原
     * @param productIds 商品ID们
     * @param isDelete 是否删除
     * @return 删除结果
     */
    @Override
    public Boolean deleteGoods(List<Integer> productIds, Boolean isDelete) {
        Boolean result = Boolean.FALSE;
        try {
            List<Product> products = getSimiliarProductList(productIds);
            for (Product product : products) {
                if (isDelete.equals(true)) {
                    SecKillProductListVO vo = new SecKillProductListVO();
                    vo.setProductIds(productIds);
//                    List<SecKillProduct> skProducts = iSecKillProductService.getSecKillProductList(vo);
//                    if (CollUtil.isNotEmpty(skProducts) && skProducts.size() > 0)
//                        throw new Exception(StrUtil.format("{}个商品由于被活动选择，无法删除", skProducts.size()));

                    product.setIsDeleted(true);
                } else {
                    product.setIsDeleted(false);
                    product.setIsOnShelf(0);
                }
                product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                if (productMapper.updateById(product) < 1) {
                    throw new Exception(StrUtil.format("商品(ID：{})删除保存失败", product.getId()));
                }
            }
            // 操作记录
//            OperateRecord operateRecord = new OperateRecord();
//            operateRecord.setTableName("Product");
//            operateRecord.setIsUpdated(false);
//            operateRecord.setOperateIP(NetUtil.getLocalhostStr());
//            operateRecord.setOperateLoginName(tokenService.getUserName());
//            if (isDelete) {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductDelete.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductDelete.value);
//            } else {
//                operateRecord.setBusinessName(OperateRecordBusinessEnum.ProductRecovery.name);
//                operateRecord.setBusinessID(OperateRecordBusinessEnum.ProductRecovery.value);
//            }
//            operateRecord.setEntityID("," + productIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ",");
//            operateRecord.setState(CreateOrderNoUtil.StateNumberWithThreadID());
//            iOperateRecordService.saveOrUpdate(operateRecord);
            result = Boolean.TRUE;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return result;
    }

    /**
     * 下单后是否限制退款
     * @param productId 商品ID
     * @return
     */
    @Override
    public Boolean checkProductIsRefund(int productId) {
        if (productId < 1) {
            return false;
        }
        return productMapper.checkProductIsRefund(productId);
    }

    /**
     * 发货后是否限制退款
     * @param productId
     * @return
     */
    @Override
    public Boolean checkProductIsDeliveryRefund(int productId) {
        if (productId < 1) return false;
        return productMapper.checkProductIsDeliveryRefund(productId);
    }

    /**
     * 设置商品缓存
     * @param product      商品信息
     * @param isFreshOther 是否同时刷新其他缓存
     */
    @Override
    public void setProductRedis(Product product, Boolean isFreshOther) {
        if (product == null) {
            return;
        }
        //商品
        redisUtil.putHash(RedisKey.getProductHashKey(), product.getId(), product);
        redisUtil.putHash(RedisKey.getSimpleProductHashKey(), product.getId(), MapUtil.ChangeObject(product, SimpleProduct.class));
        //商品详情
        redisUtil.putHash(RedisKey.getProductDetailHashKey(), product.getId(), MapUtil.ChangeObject(product, ProductDetail.class));
        if (!isFreshOther) {
            return;
        }
        //规格
        iItemService.setItemsRedis(product.getId(), iItemService.getItemList(product.getId()), true);
        //相册
        iProductAlbumService.setProductAlbumsRedis(product.getId(), iProductAlbumService.getProductAlbumList(product.getId()));
        //满包邮
        if (product.getIsOpenFreightCoupon()) {
            iProductFreightCouponService.setProductFreightCouponRedis(product.getId(), iProductFreightCouponService.getProductFreightCouponByProductId(product.getId()));
        } else {
            iProductFreightCouponService.removeProductFreightCouponRedis(product.getId());
        }
        //满减
        if (product.getIsOpenFullCoupon()) {
            iProductFullCouponService.setProductFullCouponRedis(product.getId(), iProductFullCouponService.getProductFullCouponByProductId(product.getId()));
        } else {
            iProductFullCouponService.removeProductFullCouponRedis(product.getId());
        }
    }

    /**
     * 设置不发货模板的商品列表缓存
     * @param noDeliveryTemplateId 不发货模板ID
     */
    @Override
    public void setNoDeliveryProductListRedis(int noDeliveryTemplateId) {
        String redisKey = RedisKey.getNoDeliveryProductListHashKey();
        redisUtil.deleteFromHash(redisKey, noDeliveryTemplateId);
        List<Product> products = getProductListByNoDeliveryTemplateId(noDeliveryTemplateId);
        List<ProductNoDeliveryTemplateDTO> list = products.stream().map(p -> {
            ProductNoDeliveryTemplateDTO productNoDeliveryTemplateDTO = new ProductNoDeliveryTemplateDTO();
            productNoDeliveryTemplateDTO.setId(p.getId());
            productNoDeliveryTemplateDTO.setMasterImage(p.getMasterImage());
            productNoDeliveryTemplateDTO.setName(p.getName());
            productNoDeliveryTemplateDTO.setNoDeliveryTemplateId(p.getNoDeliveryTemplateID());
            productNoDeliveryTemplateDTO.setPrice(p.getPrice());
            productNoDeliveryTemplateDTO.setProductCategoryIds(p.getProductCategoryIDs());
            productNoDeliveryTemplateDTO.setProductCategoryNames(p.getProductCategoryNames());
            return productNoDeliveryTemplateDTO;
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(list) && list.size() > 0) {
            redisUtil.putHash(redisKey, noDeliveryTemplateId, JSONUtil.toJsonStr(list));
        }
    }

    /**
     * 批量设置商品排序缓存
     * @param saveSortProductSortVOS 提交商品排序信息
     */
    @Override
    public void setProductSortRedis(List<ProductSaveSortProductSortVO> saveSortProductSortVOS) {
        String redisKey = RedisKey.getProductHashKey();
        for (ProductSaveSortProductSortVO vo : saveSortProductSortVOS) {
            Product product = getProductRedis(vo.getPid(), true);
            if (ObjectUtil.isNotEmpty(product)) {
                product.setSortID(vo.getSort());
                redisUtil.putHash(redisKey, product.getId(), product);
            }
        }
    }

    /**
     * 批量设置商品推荐属性缓存
     * @param productIds 商品ID们
     * @param isRecommend 是否推荐
     */
    @Override
    public void setProductRecommendRedis(List<Integer> productIds, Boolean isRecommend) {
        String redisKey = RedisKey.getProductHashKey();
        for (Integer productId : productIds) {
            Product product = getProductRedis(productId, true);
            if (ObjectUtil.isNotEmpty(product)) {
                product.setIsRecommend(isRecommend);
                redisUtil.putHash(redisKey, product.getId(), product);
            }
        }
    }

    /**
     * 批量设置商品上下架属性缓存
     * @param productIds 商品ID们
     * @param isOnShelf 是否上架
     */
    @Override
    public void setProductShelfStatusRedis(List<Integer> productIds, Boolean isOnShelf) {
        String productRedisKey = RedisKey.getProductHashKey();
        String simProductRedisKey = RedisKey.getSimpleProductHashKey();
        for (Integer productId : productIds) {
            Product product = getProductRedis(productId, true);
            if (ObjectUtil.isNotEmpty(product)) {
                product.setIsOnShelf(isOnShelf.equals(true) ? ProductShelfEnum.OnShelf.value : ProductShelfEnum.OffShelf.value);
                redisUtil.putHash(productRedisKey, product.getId(), product);

                SimpleProduct simpleProduct = redisUtil.getObjectFromHash(simProductRedisKey, product.getId(), SimpleProduct.class);
                if (ObjectUtil.isEmpty(simpleProduct)) {
                    simpleProduct = MapUtil.ChangeObject(product, SimpleProduct.class);
                } else {
                    simpleProduct.setIsOnShelf(product.getIsOnShelf());
                }
                redisUtil.putHash(simProductRedisKey, product.getId(), simpleProduct);
            }
        }
    }

    /**
     * 批量设置商品删除或还原缓存
     * @param productIds 商品ID们
     * @param isDelete 是否删除
     */
    @Override
    public void setProductDeleteRedis(List<Integer> productIds, Boolean isDelete) {
        if (isDelete.equals(true)) {
            redisUtil.multiDeleteFromHash(RedisKey.getProductHashKey(), productIds);
            redisUtil.multiDeleteFromHash(RedisKey.getSimpleProductHashKey(), productIds);
            redisUtil.multiDeleteFromHash(RedisKey.getProductCreateMaterialsHashKey(), productIds);
            for (Integer productId : productIds) {
                List<Item> items = redisUtil.getListFromHashList(RedisKey.getItemsProductIdHashKey(productId.toString()), Item.class);
                if (CollUtil.isNotEmpty(items) && items.size() > 0) {
                    items.forEach(i -> redisUtil.deleteFromHash(RedisKey.getItemHashKey(), i.getId()));
                }
                redisUtil.deleteHash(RedisKey.getItemsProductIdHashKey(productId.toString()));
            }
            redisUtil.multiDeleteFromHash(RedisKey.getItemSpecificationKeyValusGroupsHashKey(), productIds);
//            iStoreyCategoryProductService.deleteByProductIds(productIds);
        } else {
            List<Product> products = getSimiliarProductList(productIds);
            if (CollUtil.isNotEmpty(products) && products.size() > 0) {
                products.forEach(p -> {
                    setProductRedis(p, false);
                    iProductCreateMaterialService.setProductCreateMaterialsRedis(p.getId(), iProductCreateMaterialService.getProductCreateMaterials(p.getId()));
                    iItemService.setItemsRedis(p.getId(), iItemService.getItemList(p.getId()), true);
                });
            }
        }
    }

    /**
     * 获取商品缓存
     * @param productId 商品ID
     * @return
     */
    @Override
    public Product getProductRedis(int productId) {
        return getProductRedis(productId, false);
    }

    /**
     * 获取商品缓存
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return
     */
    @Override
    public Product getProductRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductHashKey();
        Product product = redisUtil.getObjectFromHash(redisKey, productId, Product.class);
        if (ObjectUtil.isEmpty(product) && isSyncFromDbWhenNull.equals(true)) {
            product = getProduct(productId);
            if (ObjectUtil.isNotEmpty(product))
                redisUtil.putHash(redisKey, productId, product);
        }
        return product;
    }

    /**
     * 获取商品列表缓存
     * @param productIds 商品ID们
     * @return 商品列表
     */
    @Override
    public List<Product> getProductsRedis(List<Integer> productIds) {
        return getProductsRedis(productIds, false);
    }

    /**
     * 获取商品列表缓存
     * @param productIds 商品ID们
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品列表
     */
    @Override
    public List<Product> getProductsRedis(List<Integer> productIds, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductHashKey();
        List<Product> products = redisUtil.multiGetListFromHash(redisKey, productIds, Product.class);
        if ((CollUtil.isEmpty(products) || products.size() < 1) && isSyncFromDbWhenNull.equals(true)) {
            //products = getSimiliarProductList(productIds);
            products = getProductList(productIds);
            if (CollUtil.isNotEmpty(products) && products.size() > 0)
                redisUtil.multiPutHash(redisKey, products);
        }
        return products;
    }

    /**
     * 获取商品缓存数量
     * @return 缓存数量
     */
    @Override
    public Long getProductRedisCount() {
        return redisUtil.getHashCount(RedisKey.getProductHashKey());
    }

    /**
     * 补货提醒redis
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull
     * @return
     */
    @Override
    public ProductRedisCounter getProductRedisCounterRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getProductRedisCounterHashKey();
        ProductRedisCounter model = redisUtil.getObjectFromHash(redisKey, productId, ProductRedisCounter.class);

        if (model != null) {
            if (model.getAskForReplenish() == 0) {
                model.setAskForReplenish(model.getAskForReplenish() + 1);//++
            } else if (model.getAskTime().after(DateTime.now().offset(DateField.DAY_OF_MONTH, -2)) || model.getAskTime().equals(DateTime.now().offset(DateField.DAY_OF_MONTH, -2))) {
                model.setAskForReplenish(model.getAskForReplenish() + 1);//counter.AskForReplenish++;
            } else {
                model.setAskForReplenish(1);
            }
        }


        if (ObjectUtil.isEmpty(model) && isSyncFromDbWhenNull.equals(true)) {
            model=new ProductRedisCounter();
            model.setLastTime(new Timestamp(System.currentTimeMillis()));
            model.setProductId(productId);
            model.setAskForReplenish(1);
            model.setAskTime(new Timestamp(System.currentTimeMillis()));
            if (ObjectUtil.isNotEmpty(model)) {
                redisUtil.putHash(redisKey, productId, model);
            }
        }
        return model;
    }

    @Override
    public void saveProductRedisCounterRedis(ProductRedisCounter model)
    {
        String redisKey = RedisKey.getProductRedisCounterHashKey();
        redisUtil.putHash(redisKey, model.getProductId(), model);
    }
}
