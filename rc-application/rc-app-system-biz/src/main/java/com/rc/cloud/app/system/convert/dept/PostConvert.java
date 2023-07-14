package com.rc.cloud.app.system.convert.dept;

import com.rc.cloud.app.system.api.dept.vo.SysPostVO;
import com.rc.cloud.app.system.model.dept.SysPostPO;
import com.rc.cloud.app.system.vo.dept.post.PostCreateReqVO;
import com.rc.cloud.app.system.vo.dept.post.PostRespVO;
import com.rc.cloud.app.system.vo.dept.post.PostSimpleRespVO;
import com.rc.cloud.app.system.vo.dept.post.PostUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 岗位信息转换类
 */
@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    /**
     * PO列表查询结果转换为简单的VO列表
     *
     * @param list List<SysPostPO>
     * @return List<PostSimpleRespVO>
     */
    List<PostSimpleRespVO> convertList02(List<SysPostPO> list);

    /**
     * PO列表查询结果转换为VO列表
     *
     * @param page List<SysPostPO>
     * @return List<PostRespVO>
     */
    PageResult<PostRespVO> convertPage(PageResult<SysPostPO> page);

    /**
     * PO查询结果转换为VO
     *
     * @param bean SysPostPO
     * @return PostRespVO
     */
    PostRespVO convert(SysPostPO bean);

    /**
     * 创建请求参数转换
     *
     * @param bean PostCreateReqVO
     * @return SysPostPO
     */
    SysPostPO convert(PostCreateReqVO bean);

    /**
     * 更新请求参数转换
     *
     * @param reqVO PostUpdateReqVO
     * @return SysPostPO
     */
    SysPostPO convert(PostUpdateReqVO reqVO);

    /**
     * PO列表查询结果转换为VO列表
     *
     * @param postList List<SysPostPO>
     * @return List<PostExcelVO>
     */
    List<SysPostVO> convertToVOList(List<SysPostPO> postList);
}
