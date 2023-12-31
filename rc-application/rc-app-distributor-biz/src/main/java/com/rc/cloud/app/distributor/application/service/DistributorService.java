package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;

import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * 经销商 Service 接口
 *
 * @author wjf
 */
public interface DistributorService {

    /**
     * 创建经销商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String create(@Valid DistributorCreateReqVO createReqVO);

    /**
     * 更新经销商
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid DistributorUpdateReqVO updateReqVO);

    /**
     * 删除经销商
     *
     * @param id 编号
     */
    void delete(String id);

    /**
     * 删除至回收站，更新回收站状态字段
     *
     * @param id 编号
     */
    void deleteToRecycle(String id);

    /**
     * 锁定经销商
     *
     * @param id 编号
     */
    void lock(String id);

    /**
     * 解除锁定
     *
     * @param id 编号
     */
    void unlock(String id);

    /**
     * 从回收站恢复，更新回收站状态字段
     *
     * @param id 编号
     */
    void recycle(String id);

    /**
     * 获得经销商
     *
     * @param id 编号
     * @return 经销商
     */
    DistributorPO get(String id);

    /**
     * 获得经销商
     *
     * @param id 编号
     * @return 经销商
     */
    DistributorDetailPO getDetail(String id);

    /**
     * 获得经销商列表
     *
     * @param ids 编号
     * @return 经销商列表
     */
    List<DistributorPO> getList(Collection<String> ids);

    /**
     * 获得经销商分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商分页
     */
    PageResult<DistributorPO> getPage(DistributorPageReqVO pageReqVO);

    /**
     * 获取经销商列表
     *
     * @param exportReqVO
     * @return DistributorPO列表
     */
    List<DistributorPO> getList(DistributorExportReqVO exportReqVO);
}
