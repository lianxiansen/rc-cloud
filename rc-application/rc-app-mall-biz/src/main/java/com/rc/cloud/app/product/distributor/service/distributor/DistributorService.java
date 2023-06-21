package com.rc.cloud.app.product.distributor.service.distributor;

import java.util.*;
import javax.validation.*;

import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorExportReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorPageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorUpdateReqVO;
import com.rc.cloud.app.product.distributor.dal.dataobject.distributor.DistributorDO;
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
    Integer create(@Valid AppDistributorCreateReqVO createReqVO);

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
    void delete(Integer id);

    /**
     * 获得经销商
     *
     * @param id 编号
     * @return 经销商
     */
    DistributorDO get(Integer id);

    /**
     * 获得经销商列表
     *
     * @param ids 编号
     * @return 经销商列表
     */
    List<DistributorDO> getList(Collection<Integer> ids);

    /**
     * 获得经销商分页
     *
     * @param pageReqVO 分页查询
     * @return 经销商分页
     */
    PageResult<DistributorDO> getPage(AppDistributorPageReqVO pageReqVO);

    List<DistributorDO> getList(AppDistributorExportReqVO exportReqVO) ;
}
