package com.rc.cloud.app.operate.appearance.admin.req;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.common.ProductDictKeyEnum;
import com.rc.cloud.app.operate.domain.common.ProductOriginEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "保存商品请求数据")
public class ProductSaveRequest {


    @Schema(description = "商品id")
    private String id;

    @Schema(description = "商品一级分类")
    private String firstCategory;

    @Schema(description = "商品二级分类")
    private String secondCategory;

    @Schema(description = "商品三级分类")
    private String thirdCategory;

    @Schema(description = "商品品牌")
    private String brandId;

    @Schema(description = "商品名")
    @NotBlank(message = "产品名称不能为空")
    private String name;

    @Schema(description = "商品主图")
    @NotBlank(message = "请上传的商品主图")
    private String listImage;

    @Schema(description = "商品描述，可选")
    private String remark;

    @Schema(description = "商品标签，可选")
    private String tag;

    @Schema(description = "商品自定义分类，可选")
    private String customClassificationId;

    @Valid
    @Schema(description = "商品主图列表")
    private List<ProductImageSaveRequest> masterAlbums;

    @Valid
    @Schema(description = "商品尺寸图片列表")
    private List<ProductImageSaveRequest> sizeAlbums;

    @Schema(description = "材质")
    private String caiZhi;

    @Schema(description = "尺寸")
    private String chiCun;

    @Schema(description = "承重")
    private String chengZhong;

    @Schema(description = "条码")
    private String tiaoMa;

    @Schema(description = "是否是新品")
    private Boolean  newFlag;

    @Schema(description = "是否是爆品")
    private Boolean  explosivesFlag;

    @Schema(description = "爆品图片")
    private String explosivesImage;

    @Schema(description = "是否公开")
    private Boolean  publicFlag;

    @Schema(description = "是否是首页推荐，设置为true后会展示的app首页，ffcat应用")
    private Boolean  recommendFlag;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值最小为0")
    @Max(value = 9999, message = "排序值最大为9999")
    private Integer sort;

    @Schema(description = "商品规格")
    private List<ProductAttributeSaveRequest> attributes;

    @Schema(description = "商品sku")
    @Valid
    private List<ProductSkuSaveRequest> skus;

    @Schema(description = "上线状态，可以马上上线")
    private Integer onShelfStatus;

    @Schema(description = "视频地址")
    private String videoUrl;

    @Schema(description = "视频图片")
    private String videoImg;

    @Schema(description = "安装视频地址")
    private String installVideoUrl;

    @Schema(description = "安装视频图片")
    private String installVideoImg;

    @Schema(description = "安装详情")
    private String installDetail;

    @Schema(description = "详情")
    private String detail;

    @Schema(description = "商品spucode")
    @NotBlank(message = "产品货号不能为空")
    private String spuCode;

    public static ProductSaveDTO from(ProductSaveRequest request) {
        ProductSaveDTO productSaveDTO=new ProductSaveDTO();
        BeanUtil.copyProperties(request,productSaveDTO);
        List<ProductDictSaveDTO> dicts =new ArrayList<>();
        ProductDictSaveDTO dto1=new ProductDictSaveDTO();
        dto1.setKey(ProductDictKeyEnum.CaiZhi.name);
        dto1.setValue(request.getCaiZhi());
        dto1.setSort(1);
        ProductDictSaveDTO dto2=new ProductDictSaveDTO();
        dto2.setKey(ProductDictKeyEnum.ChiCun.name);
        dto2.setValue(request.getChiCun());
        dto2.setSort(2);
        ProductDictSaveDTO dto3=new ProductDictSaveDTO();
        dto3.setKey(ProductDictKeyEnum.ChengZhong.name);
        dto3.setValue(request.getChengZhong());
        dto3.setSort(3);
        ProductDictSaveDTO dto4=new ProductDictSaveDTO();
        dto4.setKey(ProductDictKeyEnum.TiaoMa.name);
        dto4.setValue(request.getTiaoMa());
        dto4.setSort(4);
        dicts.add(dto1);
        dicts.add(dto2);
        dicts.add(dto3);
        dicts.add(dto4);
        productSaveDTO.setDicts(dicts);
        productSaveDTO.setProductType(0);
        //TODO 适应不同应用应该有不同的参数，或者通过租户重新去填充参数，目前只有ffcat
        //商品类型为0，普通商品
        productSaveDTO.setProductOrigin(ProductOriginEnum.Self.value);
        //ffcat目前只有自营
        //默认批量发货
        //当此参数设置为true后购买数量只能是装箱数的倍数
        productSaveDTO.setPackingLowestBuyFlag(true);
        return productSaveDTO;
    }
}
