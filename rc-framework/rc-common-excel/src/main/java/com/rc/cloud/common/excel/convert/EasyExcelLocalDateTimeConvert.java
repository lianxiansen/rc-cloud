package com.rc.cloud.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author xBaozi
 * @version 1.0
 * @classname LocalDateTimeConverter
 * @description EasyExcel LocalDateTime转换器
 * @date 2022/3/19 2:17
 */
public class EasyExcelLocalDateTimeConvert implements Converter<LocalDateTime> {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN)));
    }
}