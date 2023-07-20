package com.rc.cloud.app.distributor.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorConvert;
import com.rc.cloud.app.distributor.appearance.facade.admin.convert.DistributorDetailConvert;
import com.rc.cloud.app.distributor.application.event.DistributorDeleteEvent;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorConstants;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorDetailMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.app.distributor.infrastructure.util.CommonUtil;
import com.rc.cloud.app.distributor.infrastructure.util.SpringContextHolder;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private DistributorContactService contactService;

    @Resource
    private PasswordEncoder webPasswordEncoder;

    @Override
    @Transactional
    public String create(DistributorCreateReqVO createReqVO) {
        // 插入
        DistributorPO distributorPO = DistributorConvert.INSTANCE.convert(createReqVO);

        DistributorDetailPO distributorDetailPO = DistributorDetailConvert.INSTANCE.convert(createReqVO);
        List<DistributorContactPO> contactDOList = DistributorContactConvert.INSTANCE.convertList(createReqVO.getContacts());

        if (contactDOList.size() > 0) {
            List<String> names = contactDOList.stream().map(DistributorContactPO::getName).distinct().collect(Collectors.toList());
            List<String> mobiles = contactDOList.stream().map(DistributorContactPO::getMobile).distinct().collect(Collectors.toList());
            distributorPO.setContact(StringUtils.join(names, ","));
            distributorPO.setMobile(StringUtils.join(mobiles, ","));
        }
        mapper.insert(distributorPO);
        distributorDetailPO.setDistributorId(distributorPO.getId());
        //插入明细表
        detailMapper.insert(distributorDetailPO);
        contactDOList.forEach(x -> {
            x.setDistributorId(distributorPO.getId());
            x.setPassword(webPasswordEncoder.encode(CommonUtil.getFinalMobile(x.getMobile())));
        });
        contactService.updateContacts(distributorPO.getId(), contactDOList);
        // 返回
        return distributorPO.getId();
    }

    @Override
    @Transactional
    public void update(DistributorUpdateReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());

        DistributorPO updateObj = DistributorConvert.INSTANCE.convert(updateReqVO);
        DistributorDetailPO detailObj = DistributorDetailConvert.INSTANCE.convert(updateReqVO);
        List<DistributorContactPO> contacts = DistributorContactConvert.INSTANCE.convertList2(updateReqVO.getContacts());

        if (contacts.size() > 0) {
            List<String> names = contacts.stream().map(DistributorContactPO::getName).distinct().collect(Collectors.toList());
            List<String> mobiles = contacts.stream().map(DistributorContactPO::getMobile).distinct().collect(Collectors.toList());
            updateObj.setContact(StringUtils.join(names, ","));
            updateObj.setMobile(StringUtils.join(mobiles, ","));
        }
        // 更新主表
        mapper.updateById(updateObj);

        Wrapper<DistributorDetailPO> detailDOQueryWrapper = new QueryWrapper<DistributorDetailPO>().lambda()
                .eq(DistributorDetailPO::getDistributorId, updateReqVO.getId());
        DistributorDetailPO detailDO = detailMapper.selectOne(detailDOQueryWrapper);
        if (detailDO == null) { //如果存在原有记录
            //插入明细表
            detailObj = new DistributorDetailPO();
            detailObj.setDistributorId(updateReqVO.getId());
            detailObj.setDistributorDetail(updateReqVO.getDistributorDetail());
            detailMapper.insert(detailObj);
        } else {
            detailObj.setId(detailDO.getId());
            //更新明细表
            detailMapper.updateById(detailObj);
        }
        //更新联系人表
        contactService.updateContacts(updateReqVO.getId(), contacts);
    }

    @Override
    @Transactional
    public void delete(String id) {
        // 校验存在
        validateExists(id);
        // 删除
        mapper.deleteById(id);
        DistributorDeleteEvent deleteEvent = new DistributorDeleteEvent(id);
        SpringContextHolder.publishEvent(deleteEvent);
    }

    @Override
    @Transactional
    public void deleteToRecycle(String id) {
        // 校验存在
        validateExists(id);
        // 删除
        DistributorPO distributorPO = mapper.selectById(id);
        distributorPO.setRecycleFlag(DistributorConstants.IS_RECYCLE_FLAG);
        mapper.updateById(distributorPO);
        DistributorDeleteEvent deleteEvent = new DistributorDeleteEvent(id);
        SpringContextHolder.publishEvent(deleteEvent);
    }

    @Override
    public void lock(String id) {
        // 校验存在
        validateExists(id);
        // 锁定
        DistributorPO distributorPO = mapper.selectById(id);
        distributorPO.setLocking(DistributorConstants.IS_LOCK_FLAG);
        mapper.updateById(distributorPO);
    }

    @Override
    public void unlock(String id) {
        // 校验存在
        validateExists(id);
        // 解锁
        DistributorPO distributorPO = mapper.selectById(id);
        distributorPO.setLocking(DistributorConstants.IS_NOT_LOCK_FLAG);
        mapper.updateById(distributorPO);
    }

    @Override
    public void recycle(String id) {
        // 校验存在
        validateExists(id);
        // 恢复
        DistributorPO distributorPO = mapper.selectById(id);
        distributorPO.setRecycleFlag(DistributorConstants.IS_NOT_RECYCLE_FLAG);
        mapper.updateById(distributorPO);
    }

    private void validateExists(String id) {
        if (mapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_NOT_EXISTS);
        }
    }

    @Override
    public DistributorPO get(String id) {
        return mapper.selectById(id);
    }


    @Override
    public DistributorDetailPO getDetail(String id) {
        // 校验存在
        validateExists(id);
        return detailMapper.selectByDistributorId(id);
    }

    @Override
    public List<DistributorPO> getList(Collection<String> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorPO> getPage(DistributorPageReqVO pageReqVO) {
        return mapper.selectPage(pageReqVO);
    }

    @Override
    public List<DistributorPO> getList(DistributorExportReqVO exportReqVO) {
        return mapper.selectList(exportReqVO);
    }

}
