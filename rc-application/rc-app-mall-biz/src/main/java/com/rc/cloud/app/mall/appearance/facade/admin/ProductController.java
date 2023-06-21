package com.rc.cloud.app.mall.appearance.facade.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.rc.cloud.app.mall.appearance.request.*;
import com.rc.cloud.app.mall.appearance.response.*;
import com.rc.cloud.app.mall.application.data.*;
import com.rc.cloud.app.mall.application.service.*;
import com.rc.cloud.app.mall.infrastructure.config.QyyLiveProp;
import com.rc.cloud.app.mall.infrastructure.persistence.po.*;
import com.rc.cloud.app.mall.infrastructure.util.IpUtil;
import com.rc.cloud.app.mall.infrastructure.util.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController("ProductController-admin-v1")
//@AdminAuthentication
@RequestMapping("/api/admin/v1/product")
public class ProductController {

    private static QyyLiveProp qyyLiveProp = new QyyLiveProp();

//    @Autowired
//    private TokenService tokenService;

    @Autowired
    private IProductService iProductService;

//    @Autowired
//    private ISecKillProductService iSecKillProductService;

//    @Autowired
//    private IMerchantService iMerchantService;
//
//    @Autowired
//    private IWareHouseService iWareHouseService;

//    @Autowired
//    private IWareHouseProductService iWareHouseProductService;

    @Autowired
    private IProductFreightCouponService iProductFreightCouponService;

    @Autowired
    private IProductAlbumService iProductAlbumService;

    @Autowired
    private IProductFullCouponService iProductFullCouponService;

    @Autowired
    private IProductCreateMaterialService iProductCreateMaterialService;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private IItemSpecificationService iItemSpecificationService;

//    @Autowired
//    private IStoreyCategoryProductService iStoreyCategoryProductService;
//
//    @Autowired
//    private IFullCouponProductService iFullCouponProductService;
//
//    @Autowired
//    private IOperateRecordService iOperateRecordService;
//
//    @Autowired
//    private ICacherefreshRecordService iCacherefreshRecordService;

    /**
     * 保存商品
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/save")
    public ModelMap save(@Validated @RequestBody ProductSaveVO vo) {
//        Merchant merchant = null;
//        WareHouse wareHouse = null;
//        WareHouseProduct wareHouseProduct = null;
        ProductFreightCoupon productFreightCoupon = null;
        ProductFullCoupon productFullCoupon = null;

        // 1.业务判断
        if (vo.getIs_choose_freight().equals(true)) {
            if (vo.getFreight_amount().compareTo(BigDecimal.valueOf(0)) < 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请输入正确的统一运费");
        } else {
            if (vo.getFreight_tid() <= 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请选择物流模板");
        }
        if (vo.getProduct_type() == 0 && vo.getWeight().compareTo(BigDecimal.valueOf(0)) < 1)
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "普通商品的重量需大于0");
        if (CollUtil.isEmpty(vo.getSpecs()) || vo.getSpecs().size() < 1)
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品不能没有规格项");
        if (vo.getSpecs().stream().anyMatch(v -> CollUtil.isEmpty(v.getSpec_values()) || v.getSpec_values().size() < 1))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品规格项内需设置规格值");
        if (CollUtil.isEmpty(vo.getSkus()) || vo.getSkus().size() < 1)
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品不能没有sku");
        if (vo.getSkus().stream().anyMatch(v -> v.getPrice().compareTo(BigDecimal.valueOf(0)) < 1))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku零售价必须大于零");
        if (vo.getProduct_type() == 0 && vo.getSkus().stream().anyMatch(v -> v.getWeight().compareTo(BigDecimal.valueOf(0)) < 1))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku重量必须大于零");
        if (vo.getSkus().stream().anyMatch(v -> v.getOn_sale() < 0))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku库存不能小于零");
        if (vo.getIs_post_coupon().equals(true)) {
            if (vo.getPc_full_piece() <= 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置满包邮件数");
            if (StrUtil.isEmptyOrUndefined(vo.getPc_area()))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置满包邮区域");
        }
        if (vo.getIs_full_coupon().equals(true)) {
            if (Optional.ofNullable(vo.getFc_full_amount()).orElse(BigDecimal.valueOf(0)).compareTo(BigDecimal.valueOf(0)) <= 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置满减条件金额");
            if (Optional.ofNullable(vo.getFc_reduce_amount()).orElse(BigDecimal.valueOf(0)).compareTo(BigDecimal.valueOf(0)) <= 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置满减优惠金额");
            if (vo.getIs_fc_time_limit() == null)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置is_fc_time_limit");
            if (vo.getIs_fc_doubling() == null)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置is_fc_doubling");
            if (vo.getIs_fc_time_limit().equals(true))
                if (StrUtil.isEmptyOrUndefined(vo.getFc_start()) || StrUtil.isEmptyOrUndefined(vo.getFc_end()))
                    return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请完善定时满减的起止时间");
        }
        if (vo.getIs_limit_buy().equals(true))
            if (vo.getLimit_buy_num() <= 0)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置限购数量");
        if (vo.getIs_ali().equals(true)) {
            if (StrUtil.isEmptyOrUndefined(vo.getAli_product_id()))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置淘宝商品ID");
            if (StrUtil.isEmptyOrUndefined(vo.getAli_shop_id()))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置淘宝商品供应商loginId");
            if (StrUtil.isEmptyOrUndefined(vo.getAli_shop_address()))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置淘宝商品供应商所在地区");
            //1688商品唯一性检测
            Product sameProduct = iProductService.getProductByAlibabaProductId(vo.getAli_product_id(), vo.getId());
            if (ObjectUtil.isNotEmpty(sameProduct))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "已创建同一个1688商品：" + sameProduct.getName());
            if (vo.getSkus().stream().filter(v -> StrUtil.isEmptyOrUndefined(v.getAli_sku_id())).count() > 1)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku的淘宝商品skuid有遗漏");
            if (vo.getSkus().stream().filter(v -> StrUtil.isEmptyOrUndefined(v.getAli_spec_id())).count() > 1)
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku的淘宝商品specid有遗漏");
            if (vo.getSkus().stream().anyMatch(v -> CollUtil.isEmpty(v.getAli_spec_kvps()) || v.getAli_spec_kvps().size() != vo.getSpecs().size()))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku的规格属性与整个商品的规格属性不匹配");
        } else {
            if (vo.getMerchant_id() > 0) {
//                merchant = iMerchantService.getMerchant(vo.getMerchant_id());
            } else {
//                if (vo.getWh_product_id() < 1)
//                    return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "请设置库存商品ID");
//                wareHouseProduct = iWareHouseProductService.getWareHouseProduct(vo.getWh_product_id());
//                if (ObjectUtil.isEmpty(wareHouseProduct))
//                    return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "未找到库存商品");
//                merchant = iMerchantService.getMerchant(wareHouseProduct.getMerchantID());
            }
//            if (ObjectUtil.isEmpty(merchant))
//                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "未找到出售该库存商品的商家");
            if (vo.getCopartner_id() > 0 && vo.getSkus().stream().anyMatch(v -> v.getPartner_price().equals(BigDecimal.valueOf(0))))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "该商品有合伙人，请填写合伙人价");
            if (vo.getSkus().stream().anyMatch(v -> StrUtil.isEmptyOrUndefined(v.getWh_spec_ids())))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku的规格值id拼接字符串有遗漏");
            if (vo.getSkus().stream().anyMatch(v -> StrUtil.isEmptyOrUndefined(v.getWh_spec_names())))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "商品sku的名称有遗漏");
        }

        // 2.初始化或更新商品
        Product product = new Product();
        if (vo.getId() > 0) {
            product = iProductService.getProduct(vo.getId());
            if (ObjectUtil.isEmpty(product) || product.getIsDeleted().equals(true))
                return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "未找到商品");
        }
        product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (vo.getId() < 1) {
            product.setCheckStatus(0);
            product.setBaseSales(RandomUtil.randomInt(5, 11));
        }
        product.setProductType(vo.getProduct_type());
        product.setProductCategoryIDs(vo.getProduct_category_ids());
        product.setProductCategoryNames(vo.getProduct_category_names());
        if (StrUtil.isEmptyOrUndefined(product.getTopCategory())) {
            List<String> productCategoryNames = StrUtil.split(product.getProductCategoryNames(), ',', true, true);
            if (CollUtil.isNotEmpty(productCategoryNames) && productCategoryNames.size() > 0)
                product.setTopCategory(productCategoryNames.get(0));
        }
        product.setBrandID(vo.getBrand_id());
        product.setName(vo.getName());
        product.setKeywords(vo.getKeywords());
        product.setMasterImage(vo.getMaster_image());
        product.setPrice(vo.getPrice());
        product.setPopularizationAmountRate(vo.getPop_amount_rate());
        product.setBranchPerformanceRate(vo.getBran_perfor_rate());
        product.setLowestBuy(vo.getLowest_buy());
        product.setWeight(vo.getWeight());
        product.setMarkingPrice(vo.getMarking_price());
        product.setDetail(vo.getDeta());
        if (vo.getIs_shelf().equals(true)) {
            product.setIsOnShelf(ProductShelfEnum.OnShelf.value);
        } else {
            product.setIsOnShelf(ProductShelfEnum.InitShelf.value);
        }
        product.setIsRecommend(vo.getIs_recommend());
        product.setMultipleOrderCount(vo.getMulti_buy());
        product.setProductSKU(vo.getProduct_sku());
        product.setTicketType(vo.getTicket_type());
        product.setTicketRemark(vo.getTicket_remark());
        //物流信息
        product.setChooseUniformFreight(vo.getIs_choose_freight());
        product.setFreightTemplateID(vo.getFreight_tid());
        product.setUniformFreight(vo.getFreight_amount());
        Integer oriNoDeliveryTemplateID = product.getNoDeliveryTemplateID();
        product.setNoDeliveryTemplateID(vo.getNosend_area_tid());
        //商品相册
        List<ProductAlbum> albums = new ArrayList<>();
        if (CollUtil.isNotEmpty(vo.getAlbums()) && vo.getAlbums().size() > 0) {
            albums = vo.getAlbums().stream().map(a -> {
                ProductAlbum album = new ProductAlbum();
                album.setImage(a.getImage());
                album.setIsDefault(false);
                album.setSortID(a.getSort_id());
                return album;
            }).collect(Collectors.toList());
        }
        //满包邮
        product.setIsOpenFreightCoupon(vo.getIs_post_coupon());
        if (product.getIsOpenFreightCoupon().equals(true)) {
            productFreightCoupon = new ProductFreightCoupon();
            productFreightCoupon.setFullAmount(vo.getPc_full_piece());
            productFreightCoupon.setAreaNameList(vo.getPc_area());
        }
        //满减
        product.setIsOpenFullCoupon(vo.getIs_full_coupon());
        if (product.getIsOpenFullCoupon().equals(true)) {
            productFullCoupon = new ProductFullCoupon();
            productFullCoupon.setFullAmount(vo.getFc_full_amount());
            productFullCoupon.setReduceAmount(vo.getFc_reduce_amount());
            productFullCoupon.setIsDoubling(vo.getIs_fc_doubling());
            productFullCoupon.setIsTimeLimit(vo.getIs_fc_time_limit());
            if (productFullCoupon.getIsTimeLimit().equals(true)) {
                productFullCoupon.setBeginTime(new Timestamp(DateUtil.parse(vo.getFc_start()).getTime()));
                productFullCoupon.setEndTime(new Timestamp(DateUtil.parse(vo.getFc_end()).getTime()));
            }
        }
        //限购
        product.setIsOpenLimitCount(vo.getIs_limit_buy());
        product.setLimitCount(vo.getLimit_buy_num());
        //限制退款
        product.setIsRefund(vo.getIs_forbid_refund());
        product.setIsDeliveryRefund(vo.getIs_delivery_refund());
        if (product.getIsDeliveryRefund() == null) {
            product.setIsDeliveryRefund(false);
        }
        //素材
        product.setMaterialText(vo.getMaterial_txt());
        List<ProductCreateMaterial> materials = new ArrayList<>();
        if (CollUtil.isNotEmpty(vo.getMaterials()) && vo.getMaterials().size() > 0) {
            materials = vo.getMaterials().stream().map(m -> {
                ProductCreateMaterial material = new ProductCreateMaterial();
                material.setImage(m.getImage());
                material.setIsDefault(false);
                return material;
            }).collect(Collectors.toList());
        }
        //品质馆
        product.setIsQualityRecommend(vo.getIs_q_recommend());
        product.setQualityRecommendReason(vo.getQ_rcm_reason());
        //视频
        product.setVideoUrl(vo.getVideo_url());
        product.setVideoImg(vo.getVideo_image());
        //1688
        product.setIsAlibabaSelected(vo.getIs_ali());
        if (product.getIsAlibabaSelected().equals(true)) {
            product.setFromAlibabaProductID(vo.getAli_product_id());
            product.setFromAlibabaUpplierLoginId(vo.getAli_shop_id());
            //1688自建商户
//            merchant = iMerchantService.get1688Merchant(product.getFromAlibabaUpplierLoginId());
//            if (ObjectUtil.isEmpty(merchant)) {
//                merchant = new Merchant();
//                merchant.setCopartnerID(0);
//                merchant.setMerchantEnlistID(0);
//                merchant.setUserName("1688_" + product.getFromAlibabaUpplierLoginId());
//                merchant.setRealName(StrUtil.replace(product.getFromAlibabaUpplierLoginId(), "1688", ""));
//                merchant.setCompanyName(merchant.getRealName());
////                merchant.setTraceStatus(MerchantTraceStatusEnum.UnTrace.value);
////                merchant.setCheckStatus(CheckStatusEnum.NOCHECK.value);
//                merchant.setStoreAddress(StrUtil.replace(vo.getAli_shop_address(), " ", "|"));
//                merchant.setStoreName(merchant.getRealName());
//
//                SaltUtil.SaltDTO salt = SaltUtil.SaltHashEncryption("q123456");
//                merchant.setSalt(salt.getSalt());
//                merchant.setPassword(salt.getHashString());
////            }
//            product.setMerchantName(merchant.getUserName());
            product.setSupplyPrice(BigDecimal.valueOf(0));
            //1688自建仓库
//            wareHouse = iWareHouseService.getWareHouseByMerchantId(merchant.getId());
//            if (ObjectUtil.isEmpty(wareHouse)) {
//                wareHouse = new WareHouse();
//                wareHouse.setCheckStatus(CheckStatusEnum.NOCHECK.value);
//                wareHouse.setCopartnerId(0);
//                wareHouse.setMerchantId(merchant.getId());
//                wareHouse.setName("1688专用仓库");
//                wareHouse.setAddress(vo.getAli_shop_address());
//                wareHouse.setIsFreeDeliveryOfFull(false);
//            }
            product.setWareHouseProductID(0);
        } else {
            product.setWareHouseID(vo.getWh_id());
//            product.setMerchantID(merchant.getId());
//            product.setMerchantName(merchant.getUserName());
//            product.setCopartnerID(merchant.getCopartnerID());
//            product.setSupplyPrice(BigDecimal.valueOf(0));
//            product.setWareHouseProductID(vo.getWh_product_id());
        }
        //规格
        List<GoodsItemSpecificationSaveDTO> goodsSpecs = vo.getSpecs().stream().map(s -> {
            GoodsItemSpecificationSaveDTO spec = new GoodsItemSpecificationSaveDTO();
            spec.setSpecId(s.getSpec_id());
            spec.setSpecName(s.getSpec_name());
            spec.setSpecSortId(s.getSpec_sort());
            spec.setSpecValues(s.getSpec_values().stream().map(v -> {
                GoodsItemSpecificationValueSaveDTO specValue = new GoodsItemSpecificationValueSaveDTO();
                specValue.setSpecValueId(v.getSpec_vid());
                specValue.setSpecValueName(v.getSpec_vname());
                specValue.setSpecValueSortId(v.getSpec_vsort());
                return specValue;
            }).collect(Collectors.toList()));
            return spec;
        }).collect(Collectors.toList());
        //Skus
        List<GoodsSkuSaveDTO> goodsSkus = vo.getSkus().stream().map(s -> {
            GoodsSkuSaveDTO sku = new GoodsSkuSaveDTO();
            sku.setId(s.getId());
            sku.setSpecificationCombinationID(s.getWh_spec_ids());
            sku.setSpecificationCombinationName(s.getWh_spec_names());
            sku.setSupplyPrice(s.getSupply_price());
            sku.setFromAlibabaSkuId(Optional.ofNullable(s.getAli_sku_id()).orElse(""));
            sku.setFromAlibabaSpecId(Optional.ofNullable(s.getAli_spec_id()).orElse(""));
            sku.setFromAlibabaSpecKeyValuePairs(s.getAli_spec_kvps().stream().map(k -> {
                GoodsSkuSpecKVPSaveDTO kvp = new GoodsSkuSpecKVPSaveDTO();
                kvp.setSpecName(k.getSpec_name());
                kvp.setSpecValueName(k.getSpec_value());
                return kvp;
            }).collect(Collectors.toList()));
            sku.setCargoCode(s.getCargo_code());
            sku.setCargoNumber(s.getCargo_no());
            sku.setImageUrl(s.getImage());
            sku.setInventory(s.getOn_sale());
            sku.setPartnerPrice(s.getPartner_price());
            sku.setPrice(s.getPrice());
            sku.setWareHouseItemID(s.getWh_sku_id());
            sku.setWeight(s.getWeight());
            return sku;
        }).collect(Collectors.toList());
        //相似商品
        if (CollUtil.isNotEmpty(vo.getSim_goods()) && vo.getSim_goods().size() > 0) {
            List<Integer[]> similiarGoods = vo.getSim_goods().stream().map(g -> {
                List<Integer> simGoods = new ArrayList<>();
                simGoods.add(g.getPid());
                simGoods.add(g.getSort_id());
                return simGoods.toArray(new Integer[0]);
            }).collect(Collectors.toList());
            product.setSettedSimiliarProducts(JSONUtil.toJsonStr(similiarGoods));
        }
//        Integer result = iProductService.saveProduct(product, merchant, wareHouse, albums, materials, goodsSpecs, goodsSkus, productFreightCoupon, productFullCoupon, oriNoDeliveryTemplateID);
//        if (result < 1)
//            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "商品存入数据库失败");

        // 3.Redis缓存
        iProductService.setProductRedis(product, true);
//        iSecKillProductService.setSecKillProductRedis(iSecKillProductService.getSecKillProductByProductId(product.getId()), true);
//        if (product.getIsAlibabaSelected()) {
//            iMerchantService.setMerchantRedis(merchant);
//            iWareHouseService.setWareHouseRedis(wareHouse);
//        }
//        if (oriNoDeliveryTemplateID > 0)
//            iProductService.setNoDeliveryProductListRedis(oriNoDeliveryTemplateID);
//        if (product.getNoDeliveryTemplateID() > 0)
//            iProductService.setNoDeliveryProductListRedis(product.getNoDeliveryTemplateID());
//
//        // 4.1688商品上架操作
//        if (product.getIsAlibabaSelected() && product.getIsOnShelf().equals(ProductShelfEnum.OffShelf.value))
//            AliOpenGoods.Follow(product.getFromAlibabaProductID());

        return DataPack.packOk();
    }

    /**
     * 编辑商品详情
     * @param vo 接口入参
     * @return 商品详情
     */
    @RequestMapping("/editInfo")
    public ModelMap editInfo(@Validated @RequestBody ProductEditInfoVO vo) {
        Integer productId = vo.getId();
        ProductEditInfoDTO res = new ProductEditInfoDTO();

        Product product = iProductService.getProduct(productId);
        if (ObjectUtil.isEmpty(product)) {
            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR, "商品不存在");
        }
        //商户
        res.setMerchant_id(product.getMerchantID());
//        Merchant merchant = iMerchantService.getMerchant(res.getMerchant_id());
//        if (ObjectUtil.isNotEmpty(merchant)) {
//            if (product.getIsAlibabaSelected().equals(true))
//                res.setMerchant_name(merchant.getUserName());
//            else
//                res.setMerchant_name(StrUtil.format("{}({}/{}})", merchant.getUserName(), merchant.getRealName(), (StrUtil.isEmptyOrUndefined(merchant.getCompanyName()) ? "无" : merchant.getCompanyName())));
//        }
        //仓库
        res.setWh_id(product.getWareHouseID());
//        if (product.getIsAlibabaSelected().equals(true)) {
//            WareHouse wareHouse = iWareHouseService.getWareHouseByMerchantId(product.getMerchantID());
//            if (ObjectUtil.isNotEmpty(wareHouse))
//                res.setWh_name(StrUtil.format("{}({})", wareHouse.getName(), wareHouse.getAddress()));
//            if (StrUtil.isEmptyOrUndefined(res.getWh_name()))
//                res.setWh_name("1688专用仓库");
//        } else {
//            if (product.getWareHouseID().equals(0)) {
//                WareHouseProduct wareHouseProduct = iWareHouseProductService.getWareHouseProduct(product.getWareHouseProductID());
//                if (ObjectUtil.isNotEmpty(wareHouseProduct))
//                    product.setWareHouseID(wareHouseProduct.getWareHouseID());
//            }
//            WareHouse wareHouse = iWareHouseService.getWareHouse(product.getWareHouseID());
//            if (ObjectUtil.isNotEmpty(wareHouse))
//                res.setWh_name(StrUtil.format("{}({})", wareHouse.getName(), wareHouse.getAddress()));
//        }
        res.setProduct_type(product.getProductType());
        res.setProduct_category_ids(product.getProductCategoryIDs());
        res.setProduct_category_names(product.getProductCategoryNames());
        res.setBrand_id(product.getBrandID());
        res.setName(product.getName());
        res.setKeywords(product.getKeywords());
        res.setMaster_image(product.getMasterImage());
        res.setPrice(product.getPrice());
        res.setPop_amount_rate(product.getPopularizationAmountRate());
        res.setBran_perfor_rate(product.getBranchPerformanceRate());
        res.setLowest_buy(product.getLowestBuy());
        res.setWeight(product.getWeight());
        res.setMarking_price(product.getMarkingPrice());
        res.setTicket_type(0);
        //1688
        res.setIs_ali(product.getIsAlibabaSelected());
        if (res.getIs_ali().equals(true)) {
            res.setAli_product_id(product.getFromAlibabaProductID());
            res.setAli_shop_id(product.getFromAlibabaUpplierLoginId());
//            res.setAli_shop_address(Optional.ofNullable(merchant).orElse(new Merchant()).getStoreAddress());
            if (!StrUtil.isEmptyOrUndefined(res.getAli_shop_address()))
                res.setAli_shop_address(res.getAli_shop_address().replace("|", " "));
        } else {
            res.setTicket_remark(product.getTicketRemark());
            res.setProduct_sku(product.getProductSKU());
            res.setTicket_type(Optional.ofNullable(product.getTicketType()).orElse(0));
            res.setWh_product_id(product.getWareHouseProductID());
            res.setCopartner_id(product.getCopartnerID());
        }
        //运费
        res.setIs_choose_freight(product.getChooseUniformFreight());
        if (res.getIs_choose_freight().equals(true)) {
            res.setFreight_amount(product.getUniformFreight());
        } else {
            res.setFreight_tid(product.getFreightTemplateID());
        }
        res.setNosend_area_tid(product.getNoDeliveryTemplateID());
        //满包邮
        res.setIs_post_coupon(product.getIsOpenFreightCoupon());
        if (res.getIs_post_coupon().equals(true)) {
            ProductFreightCoupon productFreightCoupon = iProductFreightCouponService.getProductFreightCouponByProductId(product.getId());
            if (ObjectUtil.isNotEmpty(productFreightCoupon)) {
                res.setPc_full_piece(productFreightCoupon.getFullAmount());
                res.setPc_area(productFreightCoupon.getAreaNameList());
            }
        }
        //满减
        res.setIs_full_coupon(product.getIsOpenFullCoupon());
        if (res.getIs_full_coupon().equals(true)) {
            ProductFullCoupon productFullCoupon = iProductFullCouponService.getProductFullCouponByProductId(product.getId());
            if (ObjectUtil.isNotEmpty(productFullCoupon)) {
                res.setFc_full_amount(productFullCoupon.getFullAmount());
                res.setFc_reduce_amount(productFullCoupon.getReduceAmount());
                res.setIs_fc_time_limit(productFullCoupon.getIsTimeLimit());
                res.setIs_fc_doubling(Optional.ofNullable(productFullCoupon.getIsDoubling()).orElse(false));
                if (res.getIs_fc_time_limit().equals(true)) {
                    res.setFc_start(ObjectUtil.isNotEmpty(productFullCoupon.getBeginTime()) ? DateUtil.format(DateUtil.date(productFullCoupon.getBeginTime().getTime()), "yyyy/MM/dd HH:mm:ss") : "");
                    res.setFc_end(ObjectUtil.isNotEmpty(productFullCoupon.getEndTime()) ? DateUtil.format(DateUtil.date(productFullCoupon.getEndTime().getTime()), "yyyy/MM/dd HH:mm:ss") : "");
                }
            }
        }
        //限购
        res.setIs_limit_buy(product.getIsOpenLimitCount());
        if (res.getIs_limit_buy().equals(true)) {
            res.setLimit_buy_num(product.getLimitCount());
        }
        //退款
        res.setIs_forbid_refund(product.getIsRefund());
        res.setIs_delivery_refund(Optional.ofNullable(product.getIsDeliveryRefund()).orElse(false));
        res.setMaterial_txt(product.getMaterialText());
        res.setIs_q_recommend(Optional.ofNullable(product.getIsQualityRecommend()).orElse(false));
        res.setQ_rcm_reason(product.getQualityRecommendReason());
        res.setVideo_image(product.getVideoImg());
        res.setVideo_url(product.getVideoUrl());
        res.setDeta(product.getDetail());
        res.setShelf(product.getIsOnShelf());
        res.setIs_recommend(product.getIsRecommend());
        res.setMulti_buy(Optional.ofNullable(product.getMultipleOrderCount()).orElse(0));
        //相册
        List<ProductAlbum> albums = iProductAlbumService.getProductAlbumList(product.getId());
        if (CollUtil.isNotEmpty(albums) && albums.size() > 0)
            res.setAlbums(albums.stream().map(ProductAlbum::getImage).collect(Collectors.toList()));
        //素材
        List<ProductCreateMaterial> materials = iProductCreateMaterialService.getProductCreateMaterials(product.getId());
        if (CollUtil.isNotEmpty(materials) && materials.size() > 0) {
            res.setMaterials(materials.stream().map(ProductCreateMaterial::getImage).collect(Collectors.toList()));
        }
        //规格
        List<ItemSpecification> itemSpecs = iItemSpecificationService.getItemSpecifications(product.getId());
        if (CollUtil.isNotEmpty(itemSpecs) && itemSpecs.size() > 0) {
            Map<Integer, List<ItemSpecification>> map = itemSpecs.stream().sorted(Comparator.comparing(ItemSpecification::getSortIDForTitle)).sorted(Comparator.comparing(ItemSpecification::getSortID)).collect(Collectors.groupingBy(ItemSpecification::getSpecificationID));
            res.setSpecs(map.keySet().stream().map(key -> {
                List<ItemSpecification> values = map.get(key);
                ProductEditInfoSpecDTO dto = new ProductEditInfoSpecDTO();
                dto.setSpec_id(values.get(0).getSpecificationID());
                dto.setSpec_name(values.get(0).getSpecificationTitle());

                Map<Integer, List<ItemSpecification>> valueMap = values.stream().collect(Collectors.groupingBy(ItemSpecification::getSpecificationValueID));
                List<ProductEditInfoSpecValueDTO> vdtos = valueMap.keySet().stream().map(vkey -> {
                    List<ItemSpecification> vValues = valueMap.get(vkey);
                    ProductEditInfoSpecValueDTO vdto = new ProductEditInfoSpecValueDTO();
                    vdto.setSpec_vid(vValues.get(0).getSpecificationValueID());
                    vdto.setSpec_vname(vValues.get(0).getSpecificationContent());
                    return vdto;
                }).collect(Collectors.toList());
                dto.setSpec_values(vdtos);
                return dto;
            }).collect(Collectors.toList()));
        }
        //sku
        List<Item> items = iItemService.getItemList(product.getId());
        if (CollUtil.isNotEmpty(items) && items.size() > 0) {
            res.setSkus(items.stream().map(i -> {
                ProductEditInfoSkuDTO skuDTO = new ProductEditInfoSkuDTO();
                skuDTO.setId(i.getId());
                skuDTO.setItem_names(i.getSpecificationCombinationName());
                skuDTO.setWh_sku_id(i.getWareHouseItemID());
                skuDTO.setWh_spec_ids(i.getSpecificationCombinationID());
                skuDTO.setAli_sku_id(i.getFromAlibabaSkuId());
                skuDTO.setAli_spec_id(i.getFromAlibabaSpecId());
                skuDTO.setAli_spec_kvps(Optional.of(itemSpecs.stream().filter(s -> s.getItemID().equals(i.getId())).map(s -> {
                    ProductEditInfoSkuSpecKVPDTO kvpdto = new ProductEditInfoSkuSpecKVPDTO();
                    kvpdto.setSpec_name(s.getSpecificationTitle());
                    kvpdto.setSpec_value(s.getSpecificationContent());
                    return kvpdto;
                }).collect(Collectors.toList())).orElse(null));
                skuDTO.setCargo_no(i.getNumber());
                skuDTO.setCargo_code(i.getCodeNumber());
                skuDTO.setPrice(i.getPrice());
                skuDTO.setSupply_price(i.getSupplyPrice());
                skuDTO.setWeight(i.getWeight());
                skuDTO.setPartner_price(i.getPartnerPrice());
                skuDTO.setOn_sale(i.getInventory());
                skuDTO.setImage(i.getImageUrl());
                return skuDTO;
            }).collect(Collectors.toList()));
        }
        //相似商品
        if (!StrUtil.isEmptyOrUndefined(product.getSettedSimiliarProducts())) {
            List<int[]> settedSimiliarProducts = JSONUtil.toList(product.getSettedSimiliarProducts(), int[].class);
            List<Integer> settedSimiliarProductIds = settedSimiliarProducts.stream().map(p -> p[0]).collect(Collectors.toList());
            List<Product> similiarProductList = iProductService.getSimiliarProductList(settedSimiliarProductIds);
            res.setSim_goods(settedSimiliarProducts.stream().map(p -> {
                Product simProduct = similiarProductList.stream().filter(d -> d.getId().equals(p[0])).findFirst().orElse(new Product());
                ProductEditInfoSimiliarGoodsDTO dto = new ProductEditInfoSimiliarGoodsDTO();
                dto.setPid(simProduct.getId());
                dto.setPname(simProduct.getName());
                dto.setCover(simProduct.getMasterImage());
                dto.setSort_id(p[1]);
                return dto;
            }).collect(Collectors.toList()));
        }

        return DataPack.packOk(res);
    }

    /**
     * 后台商品列表
     * @param vo 接口入参
     * @return 商品列表
     */
    @RequestMapping("/list")
    public ModelMap list(@Validated @RequestBody ProductListVO vo) {
        ProductListDTO res = new ProductListDTO();

        PageInfo<Product> productPageInfo = iProductService.getProductPageListInAdmin(vo);
        List<Product> productList = productPageInfo.getList();
        if (CollUtil.isNotEmpty(productList) && productList.size() > 0) {
            res.setPage_count(productPageInfo.getPages());
            res.setTotal_count(productPageInfo.getTotal());
            List<ProductListItemDTO> list = productList.stream().map(p -> {
                ProductListItemDTO product = new ProductListItemDTO();
                product.setIs_can_use_coupon(p.getProductStatus() == 1);
                product.setCategory(p.getProductCategoryNames());
                product.setCheck_status(Optional.ofNullable(p.getCheckStatus()).orElse(0));
                product.setCreator(p.getManagerName());
                product.setCreate_time(DateUtil.format(DateUtil.date(p.getCreateTime().getTime()), "yyyy年M月d日 HH:mm"));
                product.setImage(p.getMasterImage());
                product.setIs_del(p.getIsDeleted());
                product.setIs_recommend(p.getIsRecommend());
                product.setMerchant(p.getMerchantName());
                product.setMerchant_id(p.getMerchantID());
                product.setProduct_link(StrUtil.format("{}/Product/Detail/{}", qyyLiveProp.getGateWay(), p.getId()));
                product.setPrice(p.getPrice().setScale(2, BigDecimal.ROUND_DOWN).toString());
                product.setPid(p.getId());
                product.setShelf(p.getIsOnShelf());
                product.setSort_id(p.getSortID());
                product.setTitle(p.getName());
                product.setUpdate_time(DateUtil.format(DateUtil.date(p.getUpdateTime().getTime()) ,"yyyy年M月d日 HH:mm"));
                product.setLeft(Optional.ofNullable(p.getMultipleOrderCount()).orElse(0));
                return product;
            }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(list) && list.size() > 0) {
                list.forEach(g -> {
                    g.setMax_price(g.getPrice());
                    List<Item> items = iItemService.getItemsRedis(g.getPid());
                    if (CollUtil.isNotEmpty(items) && items.size() > 0) {
                        g.setMax_price(items.stream().max(Comparator.comparing(Item::getPrice)).map(Item::getPrice).get().setScale(2, BigDecimal.ROUND_DOWN).toString());
                    }
                });
            }
            res.setList(Optional.ofNullable(list).orElse(new ArrayList<>()));
        }
        return DataPack.packOk(res);
    }

    /**
     * 商品SKU列表
     * @param vo 接口入参
     * @return SKU列表
     */
    @RequestMapping("/items")
    public ModelMap items(@Validated @RequestBody ProductItemsVO vo) {
        ProductItemsDTO res = new ProductItemsDTO();

        List<Item> items = iItemService.getItemList(vo.getPid());
        if (CollUtil.isNotEmpty(items) && items.size() > 0) {
            res.setList(items.stream().map(i -> {
                ProductItemsItemInfoDTO dto = new ProductItemsItemInfoDTO();
                dto.setCargo_no(i.getNumber());
                dto.setName(i.getSpecificationCombinationName());
                dto.setSupply_price(i.getSupplyPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                dto.setPartner_price(i.getPartnerPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                dto.setPrice(i.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                dto.setWeight(i.getWeight().toString());
                dto.setImage(i.getImageUrl());
                dto.setLeft(i.getInventory());
                return dto;
            }).collect(Collectors.toList()));
        }

        return DataPack.packOk(res);
    }

    /**
     * 批量保存排序
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/saveSort")
    public ModelMap saveSort(@Validated @RequestBody ProductSaveSortVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        // 1.业务判断
        if (vo.getList().size() > 20)
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "单次请求保存的数量不超过20个");
        if (vo.getList().stream().collect(Collectors.groupingBy(ProductSaveSortProductSortVO::getPid, Collectors.counting())).values().stream().anyMatch(c -> c > 1))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "提交保存的商品不能重复");

        // 2.保存排序
        Boolean result = iProductService.saveProductSort(vo.getList());
        if (result.equals(false)) {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "提交数据库失败");
        }

        // 3.Redis缓存
        iProductService.setProductSortRedis(vo.getList());

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 批量设置推荐
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/setRecommend")
    public ModelMap setRecommend(@Validated @RequestBody ProductSetBooleanValueVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        // 1.业务判断
        if (vo.getPids().stream().anyMatch(p -> p < 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "pids须全都大于0");
        }
        if (vo.getPids().size() > 20) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "单次请求保存的数量不超过20个");
        }
        if (vo.getPids().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().anyMatch(c -> c > 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "提交保存的商品不能重复");
        }

        // 2.保存状态
        Boolean result = iProductService.setProductRecommend(vo.getPids(), vo.getSet_value());
        if (result.equals(false)) {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "提交数据库失败");
        }

        // 3.Redis缓存
        iProductService.setProductRecommendRedis(vo.getPids(), vo.getSet_value());

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 批量设置上下架
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/setShelfStatus")
    public ModelMap setShelfStatus(@Validated @RequestBody ProductSetBooleanValueVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        // 1.业务判断
        if (vo.getPids().stream().anyMatch(p -> p < 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "pids须全都大于0");
        }
        if (vo.getPids().size() > 20) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "单次请求保存的数量不超过20个");
        }
        if (vo.getPids().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().anyMatch(c -> c > 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "提交保存的商品不能重复");
        }

        // 2.保存状态
        Boolean result = iProductService.setProductShelfStatus(vo.getPids(), vo.getSet_value());
        if (result.equals(false)) {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "提交数据库失败");
        }

        // 3.Redis缓存
        iProductService.setProductShelfStatusRedis(vo.getPids(), vo.getSet_value());

        // 4.相关数据
//        if (vo.getSet_value().equals(false)) {
//            //删除楼层商品
//            iStoreyCategoryProductService.deleteByProductIds(vo.getPids());
//            //删除满减商品
//            iFullCouponProductService.deleteByProductIds(vo.getPids());
//            //未在抢购中的抢购商品下线
//            SecKillProductListVO unDuringSkProductListVO = new SecKillProductListVO();
//            unDuringSkProductListVO.setProductIds(vo.getPids());
//            unDuringSkProductListVO.setIsDuring(0);
//            iSecKillProductService.setSecKillProductsOffShelf(iSecKillProductService.getSecKillProductList(unDuringSkProductListVO));
//            //在抢购中的抢购商品库存清零
//            SecKillProductListVO duringSkProductListVO = new SecKillProductListVO();
//            duringSkProductListVO.setProductIds(vo.getPids());
//            duringSkProductListVO.setIsDuring(1);
//            iSecKillProductService.setSecKillProductsClearInventory(iSecKillProductService.getSecKillProductList(duringSkProductListVO));
//        }

//        // 5.1688关注或取消
//        for (Integer productId : vo.getPids()) {
//            Product productRedis = iProductService.getProductRedis(productId);
//            if (vo.getSet_value().equals(true))
//                AliOpenGoods.Follow(productRedis.getFromAlibabaProductID());
//            else
//                AliOpenGoods.Unfollow(productRedis.getFromAlibabaProductID());
//        }

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 批量设置审核状态
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/setCheckStatus")
    public ModelMap setCheckStatus(@Validated @RequestBody ProductSetBooleanValueVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        // 1.业务判断
        if (vo.getPids().stream().anyMatch(p -> p < 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "pids须全都大于0");
        }
        if (vo.getPids().size() > 20) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "单次请求保存的数量不超过20个");
        }
        if (vo.getPids().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().anyMatch(c -> c > 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "提交保存的商品不能重复");
        }

        // 2.保存状态
        Boolean result = iProductService.setProductCheckStatus(vo.getPids(), vo.getSet_value());
        if (result.equals(false)) {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "提交数据库失败");
        }

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 批量删除商品
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/delete")
    public ModelMap delete(@Validated @RequestBody ProductSetBooleanValueVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        // 1.业务判断
        if (vo.getPids().stream().anyMatch(p -> p < 1)) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "pids须全都大于0");
        }
        if (vo.getPids().size() > 20) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "单次请求保存的数量不超过20个");
        }
        if (vo.getPids().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream().anyMatch(c -> c > 1))
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS, "提交保存的商品不能重复");

        // 2.保存状态
        Boolean result = iProductService.deleteGoods(vo.getPids(), vo.getSet_value());
        if (result.equals(false))
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "提交数据库失败");

        // 3.Redis缓存
        iProductService.setProductDeleteRedis(vo.getPids(), vo.getSet_value());

        // 4.1688关注或取消
//        if (vo.getSet_value().equals(false)) {
//            List<Product> productsRedis = iProductService.getProductsRedis(vo.getPids());
//            if (CollUtil.isNotEmpty(productsRedis) && productsRedis.size() > 0) {
//                for (Product productRedis : productsRedis) {
//                    AliOpenGoods.Unfollow(productRedis.getFromAlibabaProductID());
//                }
//            }
//        }

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 操作记录
     * @param vo 接口入参
     * @return 记录列表
     */
//    @RequestMapping("/operateRecords")
//    public ModelMap operateRecords(@Validated @RequestBody ProductOperateRecordsVO vo) {
//        ProductOperateRecordsDTO res = new ProductOperateRecordsDTO();
//
//        OperateRecordListQuery query = new OperateRecordListQuery();
//        query.setEntityId(String.valueOf(vo.getPid()));
//        query.setKeyword(vo.getKeywords());
//        query.setPage_index(vo.getPage_index());
//        query.setPage_size(vo.getPage_size());
//        PageInfo<OperateRecord> pageInfo = iOperateRecordService.getOperateRecords(query);
//        if (ObjectUtil.isNotEmpty(pageInfo)) {
//            res.setPage_count(pageInfo.getPages());
//            res.setTotal_count(pageInfo.getTotal());
//            res.setList(pageInfo.getList().stream().map(p -> {
//                ProductOperateRecordDTO dto = new ProductOperateRecordDTO();
//                dto.setBusiness_name(p.getBusinessName());
//                dto.setTable_name(p.getTableName());
//                dto.setEntity_id(p.getEntityID());
//                dto.setOperate_time(DateUtil.format(p.getCreateTime() ,"yyyy/MM/dd HH:mm:ss"));
//                dto.setStatus(p.getIsUpdated().equals(true) ? "已更新" : "未更新");
//                dto.setOperator_ip(p.getOperateIP());
//                dto.setOperator_name(p.getOperateLoginName());
//                return dto;
//            }).collect(Collectors.toList()));
//        }
//        return DataPack.packOk(res);
//    }

    /**
     * 获取最后操作人姓名
     * @param vo 接口入参
     * @return 最后操作人姓名
     */
//    @RequestMapping("/lastOperatorName")
//    public ModelMap lastOperatorName(@Validated @RequestBody ProductLastOperatorNameVO vo) {
//        OperateRecord lastRecord = iOperateRecordService.getLastOperateRecord(vo.getId());
//        if (ObjectUtil.isEmpty(lastRecord))
//            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR, "未找到操作记录");
//        return DataPack.packOk(lastRecord.getOperateLoginName());
//    }

    /**
     * 获取后台商品相关统计数据
     * @return 统计数据
     */
//    @RequestMapping("/productStatistics")
//    public ModelMap productStatistics() {
//        ProductStatisticsDTO res = new ProductStatisticsDTO();
//
//        CacherefreshRecord cacherefreshRecord = iCacherefreshRecordService.getProductCacherefreshRecord();
//        if (ObjectUtil.isNotEmpty(cacherefreshRecord))
//            res.setLast_refresh_time(DateUtil.format(cacherefreshRecord.getLastTime(), "yyyy/MM/dd HH:mm:ss"));
//        res.setDb_product_count(iProductService.count());
//        res.setRedis_product_count(iProductService.getProductRedisCount());
//
//        return DataPack.packOk(res);
//    }

    /**
     * 保存商品好评率
     * @param vo 接口入参
     * @return 保存结果
     */
    @RequestMapping("/saveFavorableRate")
    public ModelMap saveFavorableRate(@Validated @RequestBody ProductSaveFavorableRateVO vo) {
        ProductSaveResultDTO res = new ProductSaveResultDTO();

        if (IpUtil.requestIpIsNotInWhitelist())
            return DataPack.packResult(ApiCodeEnum.FORBIDDEN, "没有访问权限");
        Product product = iProductService.getProduct(vo.getId());
        if (ObjectUtil.isEmpty(product))
            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR, "商品不存在");
        product.setFeedbackRate(vo.getRate());
        product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (iProductService.saveProduct(product) < 1)
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR, "商品保存失败");
        //操作记录
//        OperateRecord record = new OperateRecord();
//        record.setTableName("Product");
//        record.setIsUpdated(false);
//        record.setOperateIP(NetUtil.getLocalhostStr());
//        record.setOperateLoginName(tokenService.getUserName());
//        record.setBusinessName(OperateRecordBusinessEnum.ProductUpdate.name);
//        record.setBusinessID(OperateRecordBusinessEnum.ProductUpdate.value);
//        record.setEntityID(StrUtil.format(",{},", product.getId()));
//        record.setState(CreateOrderNoUtil.StateNumberWithThreadID());
//        iOperateRecordService.saveOrUpdate(record);

        res.setResult(true);
        res.setFinish_time(DateUtil.format(DateUtil.date(System.currentTimeMillis()), "yyyy/MM/dd HH:mm:ss"));
        return DataPack.packOk(res);
    }

    /**
     * 商品选择器
     * @param vo 接口入参
     * @return 商品列表
     */
    @RequestMapping("/selector")
    public ModelMap selector(@Validated @RequestBody ProductSelectorVO vo) {
        ProductSelectorDTO res = new ProductSelectorDTO();

        ProductListVO query = new ProductListVO();
        query.setProduct_type(1);
        query.setKeywords(vo.getKeyword());
        query.setProduct_category_id(String.valueOf(vo.getCid()));
        query.setPage_index(vo.getPage_index());
        query.setPage_size(vo.getPage_size());
        PageInfo<Product> pageInfo = iProductService.getProductPageListInAdmin(query);
        if (ObjectUtil.isNotEmpty(pageInfo)) {
            res.setPage_count(pageInfo.getPages());
            res.setTotal_count(pageInfo.getTotal());
            res.setList(pageInfo.getList().stream().map(p -> {
                ProductSelectorProductDTO dto = new ProductSelectorProductDTO();
                dto.setPid(p.getId());
                dto.setName(p.getName());
                dto.setCover(p.getMasterImage());
                return dto;
            }).collect(Collectors.toList()));
        }
        return DataPack.packOk(res);
    }
}
