/**
 * @author hqf@rc
 * date 2022-04-22 16:44
 */
package com.rc.cloud.resource.domain.model.oss;

import com.rc.cloud.common.core.domain.AbstractId;
import org.apache.commons.lang3.StringUtils;

public class OssId extends AbstractId {

    private String id;

    public OssId(final String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("文件id不能为空");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }
//
//    @Override
//    public boolean sameValueAs(OssId other) {
//        return other != null && this.id.equals(other.id);
//    }

    @Override
    public String toString() {
        return id;
    }
}
