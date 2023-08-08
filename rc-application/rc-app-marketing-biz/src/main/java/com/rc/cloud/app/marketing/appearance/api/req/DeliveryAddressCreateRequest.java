package com.rc.cloud.app.marketing.appearance.api.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName DeliveryAddressDTO
 * @Author liandy
 * @Date 2023/8/3 14:17
 * @Description 收货地址创建数据请求对象
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@Schema(description = "收货地址创建request")
public class DeliveryAddressCreateRequest {

    /**
     * 姓名
     */
    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "振信")
    @NotBlank(message = "姓名不能为空")
    private String name;
    /**
     * 手机号
     */
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13857895451")
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    /**
     * 邮政编号
     */
    @Schema(description = "邮政编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "230000")
    @NotBlank(message = "邮政编号不能为空")
    private String zipcode;

    /**
     * 省编码
     */
    @Schema(description = "省编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "410000")
    @NotBlank(message = "省编码不能为空")
    private String provinceCode;
    /**
     * 省
     */
    @Schema(description = "省", requiredMode = Schema.RequiredMode.REQUIRED, example = "河南省")
    @NotBlank(message = "省不能为空")
    private String province;

    /**
     * 市编码
     */
    @Schema(description = "市编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "410300")
    @NotBlank(message = "市编码不能为空")
    private String cityCode;
    /**
     * 市
     */
    @Schema(description = "市", requiredMode = Schema.RequiredMode.REQUIRED, example = "洛阳市")
    @NotBlank(message = "市不能为空")
    private String city;

    /**
     *
     */
    @Schema(description = "县区编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "410323")
    @NotBlank(message = "县区编码不能为空")
    private String districtCode;
    /**
     * 区
     */
    @Schema(description = "县区", requiredMode = Schema.RequiredMode.REQUIRED, example = "新安县")
    @NotBlank(message = "县区不能为空")
    private String district;


    /**
     * 详细地址
     */
    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "城南镇顺安街60号楼1901")
    @NotBlank(message = "详细地址不能为空")
    private String detail;
    /**
     * 是否为默认
     */
    @Schema(description = "是否为默认", example = "false")
    private Boolean defaulted;


}
