package com.rc.cloud.app.product.distributor.service.source;

import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourceCreateReqVO;
import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourcePageReqVO;
import com.rc.cloud.app.product.distributor.controller.app.source.vo.AppDistributorSourceUpdateReqVO;
import com.rc.cloud.app.product.distributor.convert.source.DistributorSourceConvert;
import com.rc.cloud.app.product.distributor.dal.dataobject.source.DistributorSourceDO;
import com.rc.cloud.app.product.distributor.dal.mysql.source.DistributorSourceMapper;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商来源 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorSourceServiceImpl implements DistributorSourceService {

    @Resource
    private DistributorSourceMapper sourceMapper;

    @Override
    public Integer createSource(AppDistributorSourceCreateReqVO createReqVO) {
        // 插入
        DistributorSourceDO source = DistributorSourceConvert.INSTANCE.convert(createReqVO);
        sourceMapper.insert(source);
        // 返回
        return source.getId();
    }

    @Override
    public void updateSource(AppDistributorSourceUpdateReqVO updateReqVO) {
        // 校验存在
        validateSourceExists(updateReqVO.getId());
        // 更新
        DistributorSourceDO updateObj = DistributorSourceConvert.INSTANCE.convert(updateReqVO);
        sourceMapper.updateById(updateObj);
    }

    @Override
    public void deleteSource(Integer id) {
        // 校验存在
        validateSourceExists(id);
        // 删除
        sourceMapper.deleteById(id);
    }

    private void validateSourceExists(Integer id) {
        if (sourceMapper.selectById(id) == null) {
            throw exception(new ErrorCode(5,"SOURCE_NOT_EXISTS"));
        }
    }

    @Override
    public DistributorSourceDO getSource(Integer id) {
        return sourceMapper.selectById(id);
    }

    @Override
    public List<DistributorSourceDO> getSourceList(Collection<Integer> ids) {
        return sourceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorSourceDO> getSourcePage(AppDistributorSourcePageReqVO pageReqVO) {
        return sourceMapper.selectPage(pageReqVO);
    }

}
