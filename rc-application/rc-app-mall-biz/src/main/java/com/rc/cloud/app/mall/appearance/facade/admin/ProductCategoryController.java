package com.rc.cloud.app.mall.appearance.facade.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

import com.rc.cloud.app.mall.appearance.request.UpdateVO;
import com.rc.cloud.app.mall.appearance.response.AdminProductCategoryDTO;
import com.rc.cloud.app.mall.appearance.response.ApiCodeEnum;
import com.rc.cloud.app.mall.appearance.response.DataPack;
import com.rc.cloud.app.mall.appearance.request.ProductCategorySaveVO;
import com.rc.cloud.app.mall.appearance.request.ProductCategoryVO;
import com.rc.cloud.app.mall.application.service.IProductCategoryService;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategory;
import com.rc.cloud.app.mall.infrastructure.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther Ushop
 * @date 2021/3/28 10:50
 * @Description ProductCategoryController
 * @PROJECT_NAME qyy-live
 */
@Slf4j
@RestController("ProductCategoryController-admin")
@RequestMapping("/api/admin/v1/productCategory")
//@AdminAuthentication
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService iProductCategoryService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 津贴产品初始类别
     * 接口没有做：http://xxx/api/v1/ShoppingAllowance/ChooseCategory
     * 后台没走接口：http://test.admin.live.qyouyuan.com/admin/ShoppingAllowance/ChooseCategory/?idlist=,0,0,0,&
     *
     * @return
     */
    @RequestMapping("/firstList")
    public ModelMap getfirstList(@RequestBody HashMap param) {
        List<Integer> idList = new ArrayList<>();
        if (param.get("idlist") != null) {
            idList = ListUtil.ToIntList(StringUtil.trim(param.get("idlist").toString(), Convert.toChar(",")));
        }
        List<ProductCategory> arr = iProductCategoryService.getFirstList();
        if (arr == null) {
            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR);
        }
        List<AdminProductCategoryDTO> list = new ArrayList<>();
        arr.forEach(v -> {
            AdminProductCategoryDTO t = new AdminProductCategoryDTO();
            CopyBeanUtils.copyProperties(v, t, CopyBeanStrategyEnum.fromModel);
            t.setCreateTime(v.getCreateTime().getTime());
            list.add(t);
        });
        Map<String, Object> result = new HashMap<>();
        result.put("third_category_id", idList.size() >= 3 ? idList.get(2) : 0);
        result.put("second_category_id", idList.size() >= 2 ? idList.get(1) : 0);
        result.put("first_category_id", idList.size() >= 1 ? idList.get(0) : 0);
        result.put("first_product_category_list", list);
        return DataPack.packOk(result);
    }

    /**
     * 可获取N级，除去1级的
     * 接口没有做：http://xxx/api/v1/ShoppingAllowance/GetCategory
     * 后台没走接口：http://test.admin.live.qyouyuan.com/admin/ShoppingAllowance/GetCategory
     *
     * @return
     */
    @RequestMapping("/getCategory")
    public ModelMap getCategory(@Validated @RequestBody ProductCategoryVO vo) {
        List<ProductCategory> arr = iProductCategoryService.getProductCategoryOtherList(vo);
        if (arr == null) {
            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR);
        }
        List<AdminProductCategoryDTO> list = new ArrayList<>();
        arr.forEach(v -> {
            AdminProductCategoryDTO t = new AdminProductCategoryDTO();
            CopyBeanUtils.copyProperties(v, t, CopyBeanStrategyEnum.fromModel);
            t.setCreateTime(v.getCreateTime().getTime());
            t.setHasChildren(iProductCategoryService.getLowerCount(v.getId()) > 0 ? true : false);
            list.add(t);
        });
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        return DataPack.packOk(result);
    }

    /**
     * 检索类别分支，检索类别分支 (通过类别ID或关键字)
     * 传入的参数其中一个必然为空，否则优先检索关键字
     * 1.keyword不为空categoryid为空时检索返回含关键字相关的所有商品类的分支
     * 2.keyword为空categoryid不为空时检索返回categoryid这条商品类的分支
     *
     * @return
     */
    @RequestMapping("/getLevel")
    public ModelMap getLevel(@RequestBody HashMap param) {
        List<List<Integer>> nowCategoryIDList;
        String keyword = FetchUtil.queryString(param, "keyword");
        String categoryId = FetchUtil.queryString(param, "categoryid");

        if (keyword == null && categoryId == null) {
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS);
        }
        if (StrUtil.isEmptyOrUndefined(keyword)) {
            nowCategoryIDList = iProductCategoryService.getProductCategoryLevel(Convert.toInt(categoryId,0));
        } else {
            nowCategoryIDList = iProductCategoryService.getProductCategoryLevel(keyword.trim());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("levellist", nowCategoryIDList);
        return DataPack.packOk(result);
    }

    /**
     * 通用类别列表
     * http://xxx/api/v1/Category/List
     *
     * @return
     */
    @RequestMapping("/list")
    public ModelMap getList(@Validated @RequestBody ProductCategoryVO vo) {
        if (vo.getParentid() == 0) {
            vo.setLayer(1);
        }
        List<ProductCategory> arr = iProductCategoryService.getProductCategoryList(vo);
        List<AdminProductCategoryDTO> list = new ArrayList<>();
        List<CategoryFindGoods> categoryFindGoodsList = redisUtil.getListFromHashList(RedisKey.getCategoryFindGoodsHashKey(), CategoryFindGoods.class);
        arr.forEach(v -> {
            AdminProductCategoryDTO t = new AdminProductCategoryDTO();
            CopyBeanUtils.copyProperties(v, t, CopyBeanStrategyEnum.fromModel);
            t.setCreateTime(v.getCreateTime().getTime());
            t.setFindCount(iProductCategoryService.getFindCount(categoryFindGoodsList, v.getId()));
            t.setBannerCount(iProductCategoryService.getBannerCount(v.getId()));
            t.setHasChildren(iProductCategoryService.getLowerCount(v.getId()) > 0 ? true : false);
            t.setProductID(v.getProductId());
            t.setIsDeleted(v.getIsDeleted());
            t.setParentID(v.getParentId());
            t.setSortID(v.getSortId());
            t.setIsLock(v.getIsLock());
            t.setGroupBookingProductID(v.getGroupBookingProductId());
            t.setSeckillProductID(v.getSeckillProductId());
            list.add(t);
        });
        return DataPack.packOk(list);
    }

    /**
     * 一条
     * http://xxx/api/v1/Category/Detail
     *
     * @return
     */
    @RequestMapping("/detail")
    public ModelMap getDetail(@RequestBody HashMap param) {
        int id = FetchUtil.queryInt(param, "id");
        ProductCategory model = iProductCategoryService.getProductCategory(id);
        if (model == null) {
            return DataPack.packResult(ApiCodeEnum.CURD_NULL_ERROR);
        }
        AdminProductCategoryDTO result = new AdminProductCategoryDTO();
        CopyBeanUtils.copyProperties(model, result, CopyBeanStrategyEnum.fromModel);
        result.setCreateTime(model.getCreateTime().getTime());
        result.setParentID(model.getParentId());
        result.setProductID(model.getProductId());
        result.setSeckillProductID(model.getSeckillProductId());
        result.setSortID(model.getSortId());
        result.setGroupBookingProductID(model.getGroupBookingProductId());
        return DataPack.packOk(result);
    }

    /**
     * 商品分类新增
     * http://xxx/api/v1/Category/Save
     *
     * @param vo
     * @return
     */
    @RequestMapping("/save")
    public ModelMap save(@Validated @RequestBody ProductCategorySaveVO vo) {
        vo.setTitle(vo.getTitle().trim());
        int status = iProductCategoryService.copyAndSaveProductCategory(vo, new ProductCategory());
        if (status > 0) {
            return DataPack.packOk();
        } else {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR);
        }
    }

    /**
     * 商品分类新增
     * http://xxx/api/v1/Category/Update
     *
     * @param vo
     * @return
     */
    @RequestMapping("/update")
    public ModelMap update(@Validated @RequestBody ProductCategorySaveVO vo) {
        vo.setTitle(vo.getTitle().trim());
        int status = iProductCategoryService.copyAndSaveProductCategory(vo, new ProductCategory());
        if (status > 0) {
            return DataPack.packOk();
        } else {
            return DataPack.packResult(ApiCodeEnum.CURD_CREATE_ERROR);
        }
    }

    /**
     * 商品分类隐藏和显示
     * http://xxx/Api/v1/Category/Hide
     *
     * @param param
     * @return
     */
    @RequestMapping("/hide")
    public ModelMap setCheckStatus(@RequestBody HashMap param) {//不能放两个@RequestBody,能用@RequestBody HashMap param;HashMap ids=(HashMap) param.get("ids");

        String ids = FetchUtil.queryString(param, "ids");
        boolean checkStatus = FetchUtil.queryBoolean(param, "islock");
        int status = iProductCategoryService.updateCheckStatus(ids,checkStatus);
        if (status > 0) {
            return DataPack.packOk();
        } else {
            return DataPack.packResult(ApiCodeEnum.CURD_UPDATE_ERROR);
        }
    }

    /**
     * 保存排序
     *
     * @param updateVO
     * @return
     */
    @RequestMapping("/saveSort")
    public ModelMap saveSort(@Validated @RequestBody UpdateVO updateVO) {
        String ids = updateVO.getIds();
        String sorts = updateVO.getSorts();
        int status = iProductCategoryService.updateSortId(ids, sorts);
        if (status > 0) {
            return DataPack.packOk();
        } else {
            return DataPack.packResult(ApiCodeEnum.CURD_UPDATE_ERROR);
        }
    }

    /**
     * 商品分类删除
     * http://xxx/Api/v1/Category/Delete
     *
     * @param param
     * @return
     */
    @RequestMapping("/delete")
    public ModelMap delete(@RequestBody HashMap param) {
        String ids = FetchUtil.queryString(param, "ids");
        if(StrUtil.isEmptyOrUndefined(ids)){
            return DataPack.packResult(ApiCodeEnum.INVALID_PARAMETERS,"ids不能为空");
        }
        int status = iProductCategoryService.removeProductCategory(ids);
        if (status > 0) {
            return DataPack.packOk();
        } else {
            return DataPack.packResult(ApiCodeEnum.CURD_DELETE_ERROR);
        }
    }

    /**
     * 更新缓存
     * http://xxx/Api/v1/Category/Refresh
     *
     * @return
     */
    @RequestMapping("/refresh")
    public ModelMap refreshRedis(@RequestBody HashMap param) {
        String ids = FetchUtil.queryString(param, "ids");
        iProductCategoryService.refreshRedis(ids);
        return DataPack.packOk();
    }
}


