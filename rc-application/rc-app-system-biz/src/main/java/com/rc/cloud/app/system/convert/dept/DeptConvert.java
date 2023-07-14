package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptRespVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptSimpleRespVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 部门管理
 */
@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    /**
     * 将PO转换为VO
     *
     * @param list List<SysDeptPO>
     * @return List<DeptRespVO>
     */
    List<DeptRespVO> convertList(List<SysDeptPO> list);

    /**
     * 将PO列表转换为精简VO列表
     *
     * @param list List<SysDeptPO>
     * @return List<DeptSimpleRespVO>
     */
    List<DeptSimpleRespVO> convertList02(List<SysDeptPO> list);

    /**
     * 将PO转换为VO
     *
     * @param bean SysDeptPO
     * @return DeptRespVO
     */
    DeptRespVO convert(SysDeptPO bean);

    /**
     * 创建请求参数转换PO
     *
     * @param bean DeptCreateReqVO
     * @return SysDeptPO
     */
    SysDeptPO convert(DeptCreateReqVO bean);

    /**
     * 更新请求参数转换PO
     *
     * @param bean DeptUpdateReqVO
     * @return SysDeptPO
     */
    SysDeptPO convert(DeptUpdateReqVO bean);

}
