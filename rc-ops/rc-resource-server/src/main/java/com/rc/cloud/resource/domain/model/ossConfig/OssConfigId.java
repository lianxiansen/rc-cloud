/**
 * @author hqf@rc
 * date 2022-04-23 09:06
 */
package com.rc.cloud.resource.domain.model.ossConfig;

import com.rc.cloud.common.core.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class OssConfigId implements ValueObject<OssConfigId> {

    private String id;

    public OssConfigId(final String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("文件id不能为空");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean sameValueAs(OssConfigId other) {
        return other != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }
}