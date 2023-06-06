package com.rc.cloud.common.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.rc.cloud.common.enums.LogLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Objects;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
@Slf4j
public class FileHandler {
    @Value("${logging.file.path:}")
    private String logDir;
    //@Value("${spring.application.name}")
    private static String FILENAME_PROFIX="log";

    public String getFilePath(Object level) {
        logDir = StringUtils.removeEnd(logDir, "/");
        return new File(logDir,StrUtil.format("{}_{}.log",FILENAME_PROFIX ,LogLevelEnum.get(String.valueOf(level)).toString().toLowerCase())).getAbsolutePath();
    }

    public File getFileZip(Object level) {
        String path;
        if (Objects.nonNull(level)) {
            path = getFilePath(level);
        } else {
            path = StringUtils.removeEnd(logDir, "/");
        }
        // 日志包压缩
        File file = ZipUtil.zip(FileUtil.file(path));
        String filesize = FileUtil.readableFileSize(file);
        log.info("正在进行日志下载，路径:{},文件大小:{}", file.getPath(), filesize);
        return file;
    }
}
