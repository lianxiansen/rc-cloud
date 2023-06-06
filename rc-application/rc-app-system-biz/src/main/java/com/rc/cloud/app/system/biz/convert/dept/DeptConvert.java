package com.rc.cloud.app.system.biz.convert.dept;

import com.rc.cloud.app.system.api.dept.dto.DeptRespDTO;
import com.rc.cloud.app.system.biz.model.dept.DeptDO;
import com.rc.cloud.app.system.biz.vo.dept.dept.DeptCreateReqVO;
import com.rc.cloud.app.system.biz.vo.dept.dept.DeptRespVO;
import com.rc.cloud.app.system.biz.vo.dept.dept.DeptSimpleRespVO;
import com.rc.cloud.app.system.biz.vo.dept.dept.DeptUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    List<DeptRespVO> convertList(List<DeptDO> list);

    List<DeptSimpleRespVO> convertList02(List<DeptDO> list);

    DeptRespVO convert(DeptDO bean);

    DeptDO convert(DeptCreateReqVO bean);

    DeptDO convert(DeptUpdateReqVO bean);

    List<DeptRespDTO> convertList03(List<DeptDO> list);

    DeptRespDTO convert03(DeptDO bean);

}
