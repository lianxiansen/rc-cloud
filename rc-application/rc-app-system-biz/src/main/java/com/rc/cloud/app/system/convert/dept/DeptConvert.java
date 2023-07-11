package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.api.dept.dto.DeptRespDTO;
import com.rc.cloud.app.system.model.dept.SysDeptPO;
import com.rc.cloud.app.system.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptRespVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptSimpleRespVO;
import com.rc.cloud.app.system.vo.dept.dept.DeptUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    List<DeptRespVO> convertList(List<SysDeptPO> list);

    List<DeptSimpleRespVO> convertList02(List<SysDeptPO> list);

    DeptRespVO convert(SysDeptPO bean);

    SysDeptPO convert(DeptCreateReqVO bean);

    SysDeptPO convert(DeptUpdateReqVO bean);

    List<DeptRespDTO> convertList03(List<SysDeptPO> list);

    DeptRespDTO convert03(SysDeptPO bean);

}
