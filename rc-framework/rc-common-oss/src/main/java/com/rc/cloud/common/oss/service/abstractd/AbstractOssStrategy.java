package com.rc.cloud.common.oss.service.abstractd;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.rc.cloud.common.core.util.DateUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.oss.entity.UploadResult;
import com.rc.cloud.common.oss.enumd.OssEnumd;
import com.rc.cloud.common.oss.properties.OssProperties;
import com.rc.cloud.common.oss.service.IOssStrategy;

import java.io.InputStream;

/**
 * 对象存储策略(支持七牛、阿里云、腾讯云、minio)
 * @author hqf@rc
 */
public abstract class AbstractOssStrategy implements IOssStrategy {

    protected OssProperties properties;
    public boolean isInit = false;

    public void init(OssProperties properties) {
        this.properties = properties;
    }

    @Override
    public abstract void createBucket();

    @Override
    public abstract OssEnumd getServiceType();

    public String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 文件路径
        String path = DateUtils.datePath() + "/" + uuid;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }

    @Override
    public abstract UploadResult upload(byte[] data, String path, String contentType);

    @Override
    public abstract void delete(String path);

    @Override
    public UploadResult upload(InputStream inputStream, String path, String contentType) {
        byte[] data = IoUtil.readBytes(inputStream);
        return this.upload(data, path, contentType);
    }

    @Override
    public abstract UploadResult uploadSuffix(byte[] data, String suffix, String contentType);

    @Override
    public abstract UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType);

    public abstract String getEndpointLink();
}
