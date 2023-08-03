package com.rc.cloud.app.marketing.domain.entity.cart;

import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-28 9:17
 * @description TODO
 */
@Data
public class CartProductDetail {

    private ProductId id;

    private String name;

    private String masterImage;

    private String brandId;

    private String brandName;

    private String remark;

    private String tag;

    private int productType;

    private String spuCode;

    private int productOrigin;

    private String outId;

    private int onshelfStatus;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String videoUrl;

    private String videoImg;

    private boolean packingLowestBuyFlag;

    private boolean newFlag;

    private boolean explosivesFlag;

    private String explosivesImage;

    private boolean publicFlag;

    private boolean recommendFlag;

    private int sort;

    private String customClassificationId;

    private String productListImage;

    private LocalDateTime createTime;
}
