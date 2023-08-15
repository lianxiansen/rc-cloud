package com.rc.cloud.app.operate.appearance.admin.v1;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.app.operate.appearance.admin.req.ProductListQueryRequest;
import com.rc.cloud.app.operate.appearance.admin.req.ProductRemoveRequest;
import com.rc.cloud.app.operate.appearance.admin.req.ProductSaveRequest;
import com.rc.cloud.app.operate.appearance.admin.req.ProductSkuRequest;
import com.rc.cloud.app.operate.appearance.admin.resp.*;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductValidateBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenjianxiang
 * @Date: 2023/7/1
 * @Description:
 */
@Tag(name = "产品")
@RestController
@RequestMapping("/admin/product")
@Validated
public class ProductController {

    @Autowired
    private ProductApplicationService productApplicationService;
    @PostMapping("create")
    @Operation(summary = "创建产品")
    public CodeResult<ProductDetailResponse> createProduct(@Valid @RequestBody ProductSaveRequest request) {
        return CodeResult.ok(ProductDetailResponse.from(
                productApplicationService
                        .createProduct(ProductSaveRequest.from(request))
                )
        );
    }
    @PostMapping("edit")
    @Operation(summary = "修改产品")
    public CodeResult<ProductDetailResponse> editProduct(@Valid @RequestBody ProductSaveRequest request) {
        return CodeResult.ok(ProductDetailResponse.from(
                productApplicationService.updateProduct(ProductSaveRequest.from(request))
                )
        );
    }



    @PostMapping("remove")
    @Operation(summary = "移除产品")
    public CodeResult<ProductRemoveResponse> removeProduct(@Valid @RequestBody ProductRemoveRequest request) {
        ProductRemoveDTO productRemoveDTO=new ProductRemoveDTO();
        productRemoveDTO.setProductIds(request.getIds());
        return CodeResult.ok(ProductRemoveResponse.from(productApplicationService.removeProductBatch(productRemoveDTO)));
    }



    @PostMapping("get")
    @Operation(summary = "获得产品")
    public CodeResult<ProductDetailResponse> getProduct(@Valid @RequestBody ProductQueryDTO query) {
        ProductBO product = productApplicationService.getProduct(query);
        if(product!=null){
            return CodeResult.ok(ProductDetailResponse.from(product));
        }else{
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }

    }

    @PostMapping("validate")
    @Operation(summary = "校验产品是否可以购买")
    public CodeResult<List<ProductValidateResponse>> validateProduct(@Valid @RequestBody List<ProductSkuRequest> query) {
        List<ProductValidateDTO> dtoList=new ArrayList<>();
        dtoList =BeanUtil.copyToList(query,ProductValidateDTO.class);
        List<ProductValidateBO> list = productApplicationService.validateProductList(dtoList);
        List<ProductValidateResponse> responseList=BeanUtil.copyToList(list,ProductValidateResponse.class);
        return CodeResult.ok(responseList);
    }


    @PostMapping("list")
    @Operation(summary = "产品列表")
    public CodeResult<PageResult<ProductListResponse>> listProduct(@Valid @RequestBody ProductListQueryRequest request) {
        ProductListQueryDTO query=new ProductListQueryDTO();
        BeanUtil.copyProperties(request,query);
        query.setNeedBrandName(true);
        PageResult<ProductBO> productList = productApplicationService.getProductList(query);
        return CodeResult.ok(ProductListResponse.from(productList));
    }

    @PostMapping("changeNewStatus")
    @Operation(summary = "修改New字段")
    public CodeResult<Long> changeNewStatus(@Valid @RequestBody ProductChangeNewStatusDTO dto){
        productApplicationService.changeNewStatus(dto.getProductId(), dto.isNewFlag());
        return CodeResult.ok();
    }


    @PostMapping("changeOnShelfStatus")
    @Operation(summary = "修改上下架字段")
    public CodeResult<Long> changeOnShelfStatus(@Valid @RequestBody ProductChangeOnshelfStatusDTO dto){
        productApplicationService.changeOnshelfStatus(dto.getProductId(), dto.getOnshelfStatus());
        return CodeResult.ok();
    }


    @PostMapping("changePublicStatus")
    @Operation(summary = "修改Public字段")
    public CodeResult<Long> changePublicStatus(@Valid @RequestBody ProductChangePublicStatusDTO dto){
        productApplicationService.changePublicStatus(dto.getProductId(), dto.isPublicFlag());
        return CodeResult.ok();
    }

    @PostMapping("changeRecommendStatus")
    @Operation(summary = "修改Recommend字段")
    public CodeResult<Long> changeRecommendStatus(@Valid @RequestBody ProductChangeRecommendStatusDTO dto){
        productApplicationService.changeRecommendStatus(dto.getProductId(), dto.isRecommendFlag());
        return CodeResult.ok();
    }


    @PostMapping("changeExplosivesStatus")
    @Operation(summary = "修改Explosives字段，以及上传爆品图片")
    public CodeResult<Long> changeExplosivesStatus(@Valid @RequestBody ProductChangeExplosivesDTO dto){
        productApplicationService.changeExplosivesStatus(dto.getProductId(),dto.isExplosivesFlag() ,dto.getExplosivesImage());
        return CodeResult.ok();
    }




    @PostMapping("uploadPromotionImage")
    @Operation(summary = "上传推广图")
    public CodeResult<Long> uploadPromotionImage(@Valid @RequestBody ProductUploadPromotionImageDTO dto){
        productApplicationService.uploadPromotionImage(dto.getProductId(), dto.getPromotionImage());
        return CodeResult.ok();
    }


    @PostMapping("changeRecycleStatus")
    @Operation(summary = "修改Recycle字段")
    public CodeResult<Long> changeRecycleStatus(@Valid @RequestBody ProductChangeRecycleStatusDTO dto){
        productApplicationService.changeRecycleStatus(dto.getProductId(), dto.isRecycleFlag());
        return CodeResult.ok();
    }


}
