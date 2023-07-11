package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.api.dept.vo.SysPostVO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.vo.dept.post.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    List<PostSimpleRespVO> convertList02(List<SysPostPO> list);

    PageResult<PostRespVO> convertPage(PageResult<SysPostPO> page);

    PostRespVO convert(SysPostPO id);

    SysPostPO convert(PostCreateReqVO bean);

    SysPostPO convert(PostUpdateReqVO reqVO);

    List<PostExcelVO> convertList03(List<SysPostPO> list);

    List<SysPostVO> convertToVOList(List<SysPostPO> postList);
}
