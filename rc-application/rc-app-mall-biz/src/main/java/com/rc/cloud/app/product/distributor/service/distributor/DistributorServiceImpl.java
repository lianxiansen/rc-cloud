package com.rc.cloud.app.product.distributor.service.distributor;

import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorExportReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorPageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.distributor.vo.AppDistributorUpdateReqVO;
import com.rc.cloud.app.product.distributor.convert.distributor.DistributorConvert;
import com.rc.cloud.app.product.distributor.dal.dataobject.distributor.DistributorDO;
import com.rc.cloud.app.product.distributor.dal.mysql.distributor.DistributorMapper;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorServiceImpl implements DistributorService {

    @Resource
    private DistributorMapper mapper;

    @Override
    public Integer create(AppDistributorCreateReqVO createReqVO) {
        // 插入
        DistributorDO distributorDO= DistributorConvert.INSTANCE.convert(createReqVO);
        mapper.insert(distributorDO);
        // 返回
        return distributorDO.getId();
    }

    @Override
    public void update(AppDistributorUpdateReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        DistributorDO updateObj = DistributorConvert.INSTANCE.convert(updateReqVO);
        mapper.updateById(updateObj);
    }

    @Override
    public void delete(Integer id) {
        // 校验存在
        validateExists(id);
        // 删除
        mapper.deleteById(id);
    }

    private void validateExists(Integer id) {
        if (mapper.selectById(id) == null) {
            throw exception(new ErrorCode(1,"不存在"));
        }
    }

    @Override
    public DistributorDO get(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public List<DistributorDO> getList(Collection<Integer> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorDO> getPage(AppDistributorPageReqVO pageReqVO) {
        return mapper.selectPage(pageReqVO);
    }

    @Override
    public List<DistributorDO> getList(AppDistributorExportReqVO exportReqVO) {
        return mapper.selectList(exportReqVO);
    }

}
