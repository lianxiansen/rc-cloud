package com.rc.cloud.app.product.infrastructure.config;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/19
 * @Description:
 */
@Component
public class RedisKey {

    private static QyyLiveProp qyyLiveProp;


    public QyyLiveProp getQyyLiveProp() {
        return qyyLiveProp;
    }

    @Resource
    public void setQyyLiveProp(QyyLiveProp qyyLiveProp) {
        this.qyyLiveProp = qyyLiveProp;
    }

    //#############################> 以下 生活馆商品缓存 <#############################
    //#############################> 跨界者 都木有小金金 <#############################
    public static String getProductHashKey() {
        return qyyLiveProp.getDomainId() + "Product";
    }
    public static String getProductRedisCounterHashKey() {
        return qyyLiveProp.getDomainId() + "ProductRedisCounter";
    }
    public static String getSimpleProductHashKey() {
        return qyyLiveProp.getDomainId() + "SimpleProduct";
    }

    public static String getProductDetailHashKey() {
        return qyyLiveProp.getDomainId() + "ProductDetail";
    }

    public static String getNoDeliveryProductListHashKey() { return qyyLiveProp.getDomainId() + "ListProductNoDeliveryTemplateDTO"; }

    public static String getItemHashKey() {
        return qyyLiveProp.getDomainId() + "Item";
    }

    public static String getItemsProductIdHashKey(String productId) {
        return qyyLiveProp.getDomainId() + "Item" + ":" + productId;
    }

    public static String getProductAlbumHashKey() {
        return qyyLiveProp.getDomainId() + "ListProductAlbum";
    }

    public static String getProductCreateMaterialsHashKey() { return qyyLiveProp.getDomainId() + "ProductCreateMaterialList"; }

    public static String getProductFreightCouponHashKey() {
        return qyyLiveProp.getDomainId() + "ProductFreightCoupon";
    }

    public static String getProductFullCouponHashKey() {
        return qyyLiveProp.getDomainId() + "ProductFullCoupon";
    }

    public static String getProductFullCouponListHashKey() {
        return qyyLiveProp.getDomainId() + "ListProductFullCoupon";
    }

    public static String getItemSpecificationKeyValusGroupsHashKey() {
        return qyyLiveProp.getDomainId() + "ItemSpecificationKeyValusGroups";
    }

    public static String getStoreyCategoryProductHashKey() { return qyyLiveProp.getDomainId() + "StoreyCategoryProduct"; }

    public static String getFullCouponProductHashKey() { return qyyLiveProp.getDomainId() + "FullCouponProduct"; }

    public static String getFullCouponProductProductIdHashKey() { return qyyLiveProp.getDomainId() + "FullCouponProduct:ProductID"; }

    public static String getStoreProductListSearchMerchantIdPageHashKey(Integer all, Integer merchantId, Integer pageIndex) {
        return qyyLiveProp.getDomainId() + StrUtil.format("StoreProductListSearch:merchantID_{}_all_{}_pageIndex_{}_pageSize_10", merchantId, all, pageIndex);
    }

    public static String getItemInventoryLockKey(Integer productId, Integer itemId) {
        return qyyLiveProp.getDomainId() + StrUtil.format("Item_{}_{}_lock", productId, itemId);
    }

    public static String getSecKillItemInventoryLockKey(Integer productId, Integer itemId) {
        return qyyLiveProp.getDomainId() + StrUtil.format("SecKillItem_{}_{}_lock", productId, itemId);
    }

    public static String getVirtualProductHashKey() {
        return qyyLiveProp.getDomainId() + "VirtualProduct";
    }

    public static String getVirtualProductInventoryLockKey(Integer productId) {
        return qyyLiveProp.getDomainId() + StrUtil.format("VirtualProduct_{}_lock", productId);
    }
    //#############################> 以上 生活馆商品缓存 <#############################

    public static String getBCHomePageRotationHashKey() {
        return qyyLiveProp.getDomainId() + "BCHomePageRotation";
    }

    public static String getHomePageRotationHashKey() {
        return qyyLiveProp.getDomainId() + "HomePageRotation";
    }

    public static String getBCHomePageAdvertHashKey() {
        return qyyLiveProp.getDomainId() + "BCHomePageAdvert";
    }

    public static String getBCHomePageCategoryHashKey() {
        return qyyLiveProp.getDomainId() + "BCHomePageCategory";
    }

    public static String getBCHomePageNavigationHashKey() {
        return qyyLiveProp.getDomainId() + "BCHomePageNavigation";
    }

    public static String getBCHotSearchHashKey() {
        return qyyLiveProp.getDomainId() + "BCHotSearch";
    }

    public static String getBCAppIndexSettingHashKey() {
        return qyyLiveProp.getDomainId() + "BCAppIndexSetting";
    }

    public static String getBCHomeSettingHashKey() {
        return qyyLiveProp.getDomainId() + "BCHomeSetting";
    }

    public static String getShoppingAllowanceSendHashKey() {
        return qyyLiveProp.getDomainId() + "ShoppingAllowanceSend";
    }

    public static String getShoppingAllowanceHashKey() {
        return qyyLiveProp.getDomainId() + "ShoppingAllowance";
    }

    public static String getStoreFullFreeShippingHashKey() {
        return qyyLiveProp.getDomainId() + "StoreFullFreeShipping";
    }

    public static String getStoreFullFreeShippingMerchantIDHashKey() { return qyyLiveProp.getDomainId() + "StoreFullFreeShipping:MerchantID"; }

    public static String getFullGiftHashKey() {
        return qyyLiveProp.getDomainId() + "FullGift";
    }
    public static String getJsonAkcActivityCategory() {
        return qyyLiveProp.getDomainId() + "JsonAkcActivityCategory";
    }
    public static String getActivitySearch() {
        return qyyLiveProp.getDomainId() + "ActivitySearch";
    }
    public static String getRedBagHashKey() {
        return qyyLiveProp.getDomainId() + "RedBag";
    }

    public static String getCouponHashKey() {
        return qyyLiveProp.getDomainId() + "Coupon";
    }

    public static String getCouponProductHashKey() {
        return qyyLiveProp.getDomainId() + "CouponProduct";
    }

    public static String getListCouponProductCouponIDHashKey() {
        return qyyLiveProp.getDomainId() + "ListCouponProduct:CouponID";
    }

    public static String getListCouponProductProductIDHashKey() {
        return qyyLiveProp.getDomainId() + "ListCouponProduct:ProductID";
    }

    public static String getCategoryFindGoodsHashKey() {
        return qyyLiveProp.getDomainId() + "CategoryFindGoods";
    }

    public static String getProductCategoryHashKey() {
        return qyyLiveProp.getDomainId() + "ProductCategory";
    }

    public static String getSecKillActivityHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillActivity";
    }

    public static String getLastUpdateSecKillActivityByActivityIdHashKey() {
        return qyyLiveProp.getDomainId() + "LastUpdateSecKillActivityByActivityIDListint";
    }

    public static String getSecKillProductHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillProduct";
    }

    public static String getPlatformSettingHashKey() {
        return qyyLiveProp.getDomainId() + "PlatformSetting";
    }

    public static String getWeiXinConfigHashKey() {
        return qyyLiveProp.getDomainId() + "WeixinConfig";
    }

    public static String getHomePageSettingHashKey() {
        return qyyLiveProp.getDomainId() + "HomePageSetting";
    }

    public static String getSecKillItemHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillItem";
    }

    public static String getSecKillItemSecKillProductIDHashKey(String secKillProductId) { return qyyLiveProp.getDomainId() + StrUtil.format("SecKillItem:{}", secKillProductId); }

    public static String getSecKillItemSpecificationKeyValusGroupsHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillItemSpecificationKeyValusGroups";
    }

    public static String getSecKillProductProductIDHashKey() { return qyyLiveProp.getDomainId() + "SecKillProduct:ProductID"; }

    public static String getSecKillProductIDsActivityIdHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillProductIDs:ActivityId";
    }

    public static String getSecKillProductIdsActivityIdHashKey() {
        return qyyLiveProp.getDomainId() + "SecKillProductIds:ActivityId";
    }

    public static String getProductEvaluationHashKey() {
        return qyyLiveProp.getDomainId() + "ProductEvaluation";
    }

    public static String getProductEvaluationListHashKey() {
        return qyyLiveProp.getDomainId() + "ListProductEvaluation:ProductID";
    }

    public static String getMCNProductEvaluationListHashKey() {
        return qyyLiveProp.getDomainId() + "ListProductEvaluation:MCNProductID";
    }

    public static String getBrandCommunityProductEvaluationListHashKey() {
        return qyyLiveProp.getDomainId() + "ListProductEvaluation:BrandCommunityProductID";
    }

    public static String getHotSearchListHashKey() {
        return qyyLiveProp.getDomainId() + "HotSearch";
    }

    /**
     * T_Product_Collection:userId
     * @param userId
     * @return
     */
    public static String getBCProductCollectionHashKey(String userId) {
        return qyyLiveProp.getDomainId() + "T_Product_Collection:" + userId;
    }

    /**
     * T_Activity_Collection:userId
     * @param userId
     * @return
     */
    public static String getBCActivityCollectionHashKey(String userId) {
        return qyyLiveProp.getDomainId() + "T_Activity_Collection:" + userId;
    }
    /**
     * T_Activity:ProductCount:activity_id
     * @param activityId
     * @return
     */
    public static String getBCActivityProductCountHashKey(String activityId) {
        return qyyLiveProp.getDomainId() + "T_Activity:ProductCount:" + activityId;
    }
    /**
     *T_Cart:userId
     * @param userId
     * @return
     */
    public static String getBCCartHashKeyByUserIDAndShareUserID(String userId,String shareUserId){
        if(StrUtil.isNotEmpty(shareUserId)){
            return qyyLiveProp.getDomainId() + "T_Cart:" + userId+":ShareUserID:"+shareUserId;
        }else{
            return qyyLiveProp.getDomainId() + "T_Cart:" + userId;
        }
    }

    public static String getTodayTopSalesSet(DateTime dateTime) {
        return qyyLiveProp.getDomainId() + "TodayTopSales" + dateTime.toString("yyyyMMdd");
    }

    public static String getMerchantHashKey() {
        return qyyLiveProp.getDomainId() + "Merchant";
    }

    public static String getWareHouseHashKey() {
        return qyyLiveProp.getDomainId() + "WareHouse";
    }

    public static String getFreightTemplateHashKey() {
        return qyyLiveProp.getDomainId() + "FreightTemplate";
    }

    public static String getHykActivityInfoHashKey(){
        return qyyLiveProp.getDomainId() + "HykActivityInfo";
    }
    public static String getNoDeliveryTemplateHashKey() {
        return qyyLiveProp.getDomainId() + "NoDeliveryTemplate";
    }

    public static String getHykExcutedActivity(String liveId) {
        return "HaoyikuExcutedActivity:" + liveId;
    }

    public static String getHykSynchronizationStatus() {
        return "HaoyikuActivitySynchronization_Status_Monitor";
    }

    public static String getStoreProductMaterialHashKey() {
        return qyyLiveProp.getDomainId() + "StoreProductMaterial";
    }

    public static String getUserHashKey() {
        return qyyLiveProp.getDomainId() + "User";
    }

    public static String getUserWithUnionIDHashKey() {
        return qyyLiveProp.getDomainId() + "User:UnionID";
    }

    public static String getUserWithQyyUserIDHashKey() {
        return qyyLiveProp.getDomainId() + "User:QyyUserID";
    }

    public static String getCopartnerHashKey() {
        return qyyLiveProp.getDomainId() + "Copartner";
    }

    public static String getCartActivityAdvertHashKey() {
        return qyyLiveProp.getDomainId() + "CartActivityAdvert";
    }

    public static String getActivitySceneHashKey() {
        return qyyLiveProp.getDomainId() + "ActivityScene";
    }

    public static String getTurnTable001HashKey() {
        return qyyLiveProp.getDomainId() + "TurnTable001";
    }

    public static String getActivitySceneEntrySettingHashKey() {
        return qyyLiveProp.getDomainId() + "ActivitySceneEntrySetting";
    }

    public static String getTurnTable001PlayerHashKey(String guid) {
        return qyyLiveProp.getDomainId() + "TurnTable001Player:Guid" + guid;
    }

    public static String getTProductBCProductIdOrderId(String brandCommunityProductId, int orderId) {
        return qyyLiveProp.getDomainId() + "T_Product:" + brandCommunityProductId + ":" + orderId;
    }

    public static String getTProductEvaluationPageReputationBrandNameHashKey(int pageIndex, int pageSize, String reputation, String brandName) {
        return qyyLiveProp.getDomainId() + StrUtil.format("T_ProductEvaluationJson:pageIndex_{}_pageSize_{}_reputation_{}_brand_name_{}", pageIndex, pageSize, reputation, brandName);
    }

    public static String getOrderConfirmAccessHashKey() {
        return "order_confirm_access";
    }

    public static String getOrderSubmitAccessHashKey() {
        return "order_submit_access";
    }

    public static String getOrderSubmitAccessLockKey(int userId) {
        return StrUtil.format("order_submit_access_lock:{}", userId);
    }
    public static String getSiteVisitedRecordUniqueIdHashKey(String uniqueId) {
        return qyyLiveProp.getDomainId() +String.format("SiteVisitedRecord:%s",uniqueId);
    }
    public static String getSiteVisitedRecordHashKey() {
        return qyyLiveProp.getDomainId() +"SiteVisitedRecord";
    }


    /**
     * 人店相关
     */
    public static String getStoreHashKey(String storeID) {
        return String.format("Store:%s", storeID);
    }
    public static String getUVStoreHashKey(String date,String storeID) {
        return String.format("UV:%s:Store%s",date,storeID);//UV:2021-06-24:Store1
    }
    public static String getPVStoreHashKey(String date,String storeID) {
        return String.format("PV:%s:Store%s",date,storeID);//UV:2021-06-24:Store1
    }

    public static String getBCStoreHashKey(String storeID) {
        return String.format("BrandCommunityStore:%s", storeID);
    }
    public static String getBCUVStoreHashKey(String date,String storeID) {
        return String.format("BrandCommunityUV:%s:Store%s",date,storeID);//UV:2021-06-24:Store1
    }
    public static String getBCPVStoreHashKey(String date,String storeID) {
        return String.format("BrandCommunityPV:%s:Store%s",date,storeID);//UV:2021-06-24:Store1
    }

}
