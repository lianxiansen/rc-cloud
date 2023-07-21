package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import lombok.Data;

/**
 * @ClassName: ReInheritShouldNotSpecifyMyself
 * @Author: liandy
 * @Date: 2023/7/14 13:44
 * @Description: 重新指定上级分类不能是自己
 */
@Data
public class ReInheritShouldNotSpecifyMyselfSpecification extends AbstractSpecification <ProductCategory>{
    private ProductCategoryId productCategoryId;
    public ReInheritShouldNotSpecifyMyselfSpecification(ProductCategoryId productCategoryId){
        this.productCategoryId=productCategoryId;
    }
    @Override
    public boolean isSatisfiedBy(ProductCategory productCategory) {
        return !productCategoryId.equals(productCategory.getId());
    }
}
