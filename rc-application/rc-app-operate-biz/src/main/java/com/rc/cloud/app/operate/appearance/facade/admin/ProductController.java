package com.rc.cloud.app.operate.appearance.facade.admin;

import com.rc.cloud.app.operate.appearance.request.ProductSaveVO;
import com.rc.cloud.app.operate.application.data.ProductSaveDTO;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.common.core.enums.CommonStatusEnum;
import com.rc.cloud.common.core.util.tree.TreeUtil;
import com.rc.cloud.common.core.web.CodeResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;


@Tag(name = "产品")
@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

    @Autowired
    private ProductApplicationService productApplicationService;
    @PostMapping("create")
    @Operation(summary = "创建产品")
    public CodeResult<Long> createProduct(@Valid @RequestBody ProductSaveVO reqVO) {
        ProductSaveDTO productSaveDTO=reqVO.toProductSaveDTO();
        productApplicationService.saveOrUpdateProduct(null);
        return CodeResult.ok();
    }

}
