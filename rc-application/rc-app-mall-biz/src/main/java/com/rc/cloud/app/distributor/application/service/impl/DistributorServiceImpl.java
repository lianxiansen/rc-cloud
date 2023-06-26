package com.rc.cloud.app.distributor.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorConvert;
import com.rc.cloud.app.distributor.application.convert.DistributorDetailConvert;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorDetailMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailDO;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
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

    @Resource
    private DistributorDetailMapper detailMapper;

    @Override
    @Transactional
    public Integer create(AppDistributorCreateReqVO createReqVO) {
        // 插入
        DistributorDO distributorDO= DistributorConvert.INSTANCE.convert(createReqVO);
        DistributorDetailDO distributorDetailDO= DistributorDetailConvert.INSTANCE.convert(createReqVO);
        int c =mapper.insert(distributorDO);
        distributorDetailDO.setDistributorId(distributorDO.getId());
        int t= detailMapper.insert(distributorDetailDO);
        // 返回
        return distributorDO.getId();
    }

    @Override
    public void update(AppDistributorUpdateReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());

        DistributorDO updateObj = DistributorConvert.INSTANCE.convert(updateReqVO);
        DistributorDetailDO detailObj= DistributorDetailConvert.INSTANCE.convert(updateReqVO);
        // 更新主表
        mapper.updateById(updateObj);

        Wrapper<DistributorDetailDO> detailDOQueryWrapper=new QueryWrapper<DistributorDetailDO>().lambda()
                .eq(DistributorDetailDO::getDistributorId,updateReqVO.getId());
        DistributorDetailDO detailDO = detailMapper.selectOne(detailDOQueryWrapper);
        detailObj.setId(detailDO.getId());
        //更新明细表
        detailMapper.updateById(detailObj);
    }

    @Override
    public void delete(Integer id) {
        // 校验存在
        validateExists(id);
        // 删除
        mapper.deleteById(id);
        //删除明细表
        detailMapper.delete(new UpdateWrapper<DistributorDetailDO>().lambda().eq(DistributorDetailDO::getDistributorId,id));
    }

    private void validateExists(Integer id) {
        if (mapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_NOT_EXISTS);
        }
    }

    @Override
    public DistributorDO get(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public DistributorDetailDO getDetail(Integer id) {
        return detailMapper.selectByDistributorId(id);
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
