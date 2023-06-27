package com.rc.cloud.app.distributor.application.service;

import java.util.*;
import javax.validation.*;

import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailDO;
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
    Long create(@Valid AppDistributorCreateReqVO createReqVO);

    /**
     * 更新经销商
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid AppDistributorUpdateReqVO updateReqVO);

    /**
     * 删除经销商
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得经销商
     *
     * @param id 编号
     * @return 经销商
     */
    DistributorDO get(Long id);

    /**
     * 获得经销商
     *
     * @param id 编号
     * @return 经销商
     */
    DistributorDetailDO getDetail(Long id);

    /**
     * 获得经销商列表
     *
     * @param ids 编号
     * @return 经销商列表
     */
    List<DistributorDO> getList(Collection<Long> ids);

    /**
     * 获得经销商分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商分页
     */
    PageResult<DistributorDO> getPage(AppDistributorPageReqVO pageReqVO);

    List<DistributorDO> getList(AppDistributorExportReqVO exportReqVO) ;
}
