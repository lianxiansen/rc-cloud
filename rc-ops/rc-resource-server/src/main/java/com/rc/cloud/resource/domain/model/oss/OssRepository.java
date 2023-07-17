package com.rc.cloud.resource.domain.model.oss;

import java.util.Collection;
import java.util.List;

/**
 * Oss文件-Repository接口
 *
 * @author hqf@rc
 * @date 2022-04-22
 **/
public interface OssRepository {

    /**
     * 根据id查找Oss文件
     * @param id
     * @return
     */
    Oss find(String id);

    /**
     * 保存Oss文件
     * @param oss
     */
    void store(Oss oss);

    /**
     * 删除oss文件，做的是软删除
     * @param id
     */
    void remove(String id);

    List<Oss> selectBatchIds(Collection<Long> ids);

    Integer deleteBatchIds(Collection<Long> ids);
}
