package com.rc.cloud.app.operate.appearance.admin.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品移除请求")
public class ProductRemoveRequest {

    @Schema(description = "产品id集合")
    private List<String> ids;

}
