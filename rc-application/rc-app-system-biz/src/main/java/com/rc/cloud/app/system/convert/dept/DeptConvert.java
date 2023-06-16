package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.api.dept.dto.DeptRespDTO;
import com.rc.cloud.app.system.api.dept.model.SysDeptDO;
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

    List<DeptRespVO> convertList(List<SysDeptDO> list);

    List<DeptSimpleRespVO> convertList02(List<SysDeptDO> list);

    DeptRespVO convert(SysDeptDO bean);

    SysDeptDO convert(DeptCreateReqVO bean);

    SysDeptDO convert(DeptUpdateReqVO bean);

    List<DeptRespDTO> convertList03(List<SysDeptDO> list);

    DeptRespDTO convert03(SysDeptDO bean);

}
