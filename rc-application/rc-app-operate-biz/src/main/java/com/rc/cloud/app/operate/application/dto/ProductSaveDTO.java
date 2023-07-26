package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;


/**
     {
     “id”:"111111",
     "productType":0,
     "firstCategory":"家居用品",
     "secondCategory":"日用百货",
     "thirdCategory":"清洁洗剂",
     "brandId":"222",
     "name":"优生活香水洗衣液持久留香柔顺护色机洗手洗天然家庭装",
     "remark":"优生活香水洗衣液持久留香柔顺护色机洗手洗天然家庭装",
     "tag":"洗衣液",
     "customClassificationId":"1",
     "albums":[{"url":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg","sort":1}
     ,{"url":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg","sort":2],

     "dicts":[{"key":"材质","value":"塑料","sort":99}],
     "price":"9.9",
     "newFlag":true,
     "explosivesFlag":true,
     "publicFlag":true,
     "recommendFlag":true,
     "attributes":[
     {"name":"颜色","value":"红","sort":9},
     {"name":"颜色","value":"黄","sort":9},
     {"name":"颜色","value":"蓝","sort":9},
     {"name":"尺寸","value":"X","sort":9},
     {"name":"尺寸","value":"XL","sort":9}
     ],
     "onShelfStatus":1,
     "enabledFlag":true,
     "videoUrl":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg",
     "videoImg":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg",
     "installVideoUrl":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg",
     "installVideoImg":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg",
     "skus":[
     {
     "id":"",
     "skuCode":"",
     "supplyPrice":"",
     "weight":"",
     "hasImageFlag":"",
     "enabledFlag":"",
     "albums":[{"url":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg","sort":1}
     ,{"url":"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg","sort":2]
     "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}],
     "inventory":99,
     "sort":99,

     }

     ]
     }

 */
@Data
public class ProductSaveDTO {


    private String id;

    private String outId;

    //默认普通商品
    private Integer productType;

    private Integer productOrigin;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String brandId;

    private String name;

    private String listImage;

    private String remark;

    private String tag;

    //外部系统关联使用
    private String customClassificationId;


    @Valid
    private List<ProductImageSaveDTO> masterAlbums;

    @Valid
    private List<ProductImageSaveDTO> sizeAlbums;

    private List<ProductDictSaveDTO> dicts;

    private Boolean packingLowestBuyFlag;


    private Boolean  newFlag;

    private Boolean  explosivesFlag;

    private String explosivesImage;

    private Boolean  publicFlag;

    private Boolean  recommendFlag;

    private String tenantId;

    private Integer sort;

    private String attributeId;

    private List<ProductAttributeSaveDTO> attributes;

    private List<ProductSkuSaveDTO> skus;

    private Integer onShelfStatus;


    private String videoUrl;
    private String videoImg;
    private String installVideoUrl;
    private String installVideoImg;
    private String installDetail;

    private String detailId;
    private String detail;

    private String spuCode;

}
