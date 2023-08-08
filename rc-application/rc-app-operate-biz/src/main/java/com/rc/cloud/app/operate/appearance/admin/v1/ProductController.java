package com.rc.cloud.app.operate.appearance.admin.v1;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.app.operate.appearance.admin.req.ProductRemoveRequest;
import com.rc.cloud.app.operate.appearance.admin.req.ProductSaveRequest;
import com.rc.cloud.app.operate.appearance.admin.req.ProductSkuRequest;
import com.rc.cloud.app.operate.appearance.admin.resp.*;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductValidateBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.common.ProductRemoveTypeEnum;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PutMapping("edit")                                                                                          @PostMapping("edit")
    @Operation(summary = "修改产品")
    public CodeResult<ProductDetailResponse> editProduct(@Valid @RequestBody ProductSaveRequest request) {
        return CodeResult.ok(ProductDetailResponse.from(
                productApplicationService.updateProduct(ProductSaveRequest.from(request))
                )
        );
    }



    @DeleteMapping("remove")
    @Operation(summary = "移除产品")
    public CodeResult<ProductRemoveResponse> removeProduct(@Valid @RequestBody ProductRemoveRequest request) {
        ProductRemoveDTO productRemoveDTO=new ProductRemoveDTO();
        productRemoveDTO.setProductIds(request.getProductIds());
        return CodeResult.ok(ProductRemoveResponse.from(productApplicationService.removeProductBatch(productRemoveDTO)));
    }



    @GetMapping("get")
    @Operation(summary = "获得产品")
    public CodeResult<ProductDetailResponse> getProduct(@Valid ProductQueryDTO query) {
        ProductBO product = productApplicationService.getProduct(query);
        return CodeResult.ok(ProductDetailResponse.from(product));
    }

    @GetMapping("validate")
    @Operation(summary = "校验产品是否可以购买")
    public CodeResult<ProductValidateResponse> validateProduct(@Valid @RequestBody ProductSkuRequest query) {
        ProductValidateDTO validateDTO=new ProductValidateDTO();
        BeanUtil.copyProperties(query,validateDTO);
        ProductValidateBO productValidateBO = productApplicationService.validateProduct(validateDTO);
        ProductValidateResponse response=new ProductValidateResponse();
        response.setCanBuy(productValidateBO.isEnabled());
        ProductValidateSkuDetailResponse skuDetailResponse=new ProductValidateSkuDetailResponse();
        BeanUtil.copyProperties(productValidateBO.getProductSkuBO(),skuDetailResponse);
        response.setSkuDetail(skuDetailResponse);
        return CodeResult.ok(response);
    }


    @GetMapping("list")
    @Operation(summary = "产品列表")
    public CodeResult<PageResult<ProductListResponse>> listProduct(@Valid ProductListQueryDTO query) {
        query.setNeedBrandName(true);
        PageResult<ProductBO> productList = productApplicationService.getProductList(query);
        return CodeResult.ok(ProductListResponse.from(productList));
    }

    @PutMapping("changeNewStatus")
    @Operation(summary = "修改New字段")
    public CodeResult<Long> changeNewStatus(@Valid @RequestBody ProductChangeNewStatusDTO dto){
        productApplicationService.changeNewStatus(dto.getProductId(), dto.isNewFlag());
        return CodeResult.ok();
    }


    @PutMapping("changeOnShelfStatus")
    @Operation(summary = "修改产品字段")
    public CodeResult<Long> changeOnShelfStatus(@Valid @RequestBody ProductChangeOnShelfStatusDTO dto){
        productApplicationService.changeOnShelfStatus(dto.getProductId(), dto.getOnShelfStatus());
        return CodeResult.ok();
    }


    @PutMapping("changePublicStatus")
    @Operation(summary = "修改Public字段")
    public CodeResult<Long> changePublicStatus(@Valid @RequestBody ProductChangePublicStatusDTO dto){
        productApplicationService.changePublicStatus(dto.getProductId(), dto.isPublicFlag());
        return CodeResult.ok();
    }

    @PutMapping("changeRecommendStatus")
    @Operation(summary = "修改Recommend字段")
    public CodeResult<Long> changeRecommendStatus(@Valid @RequestBody ProductChangeRecommendStatusDTO dto){
        productApplicationService.changeRecommendStatus(dto.getProductId(), dto.isRecommendFlag());
        return CodeResult.ok();
    }



}
