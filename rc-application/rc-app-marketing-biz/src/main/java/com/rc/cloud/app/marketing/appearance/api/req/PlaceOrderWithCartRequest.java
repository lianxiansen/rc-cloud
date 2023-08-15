package com.rc.cloud.app.marketing.appearance.api.req;

import com.rc.cloud.app.marketing.application.dto.PlaceOrderWithCartDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @ClassName PlaceOrderReq
 * @Author liandy
 * @Date 2023/7/29 15:45
 * @Description 购物车下单请求
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@Schema(description = "购物车下单请求")
public class PlaceOrderWithCartRequest {
    @Schema(description = "id唯一标识列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "购车车id唯一标识列表不能为空")
    private List<String> cartIds;

    public PlaceOrderWithCartDTO toPlaceOrderDTO() {
        PlaceOrderWithCartDTO dto = new PlaceOrderWithCartDTO();
        BeanUtils.copyProperties(this, dto);
//        BeanCopyUtils.copy(this, dto);
        return dto;
    }
}
