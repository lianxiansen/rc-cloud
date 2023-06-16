package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.api.dept.entity.SysPostDO;
import com.rc.cloud.app.system.vo.dept.post.*;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    List<PostSimpleRespVO> convertList02(List<SysPostDO> list);

    PageResult<PostRespVO> convertPage(PageResult<SysPostDO> page);

    PostRespVO convert(SysPostDO id);

    SysPostDO convert(PostCreateReqVO bean);

    SysPostDO convert(PostUpdateReqVO reqVO);

    List<PostExcelVO> convertList03(List<SysPostDO> list);

}
