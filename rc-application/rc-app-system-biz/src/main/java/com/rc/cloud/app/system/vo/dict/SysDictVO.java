package com.rc.cloud.app.system.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 全部字典
 */
@Data
@Schema(description = "全部字典")
public class SysDictVO {

    @Schema(description = "字典类型")
    private String type;

    @Schema(description = "字典数据列表")
    private List<DictData> dataList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @Schema(description = "字典数据")
    public static class DictData {
        @Schema(description = "字典标签")
        private String label;

        @Schema(description = "字典值")
        private String value;

        @Schema(description = "标签样式css")
        private String cssClass;

        @Schema(description = "标签颜色类型")
        private String colorType;
    }
}
