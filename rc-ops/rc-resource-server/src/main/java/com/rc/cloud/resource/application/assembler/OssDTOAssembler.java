package com.rc.cloud.resource.application.assembler;

import cn.hutool.core.bean.BeanUtil;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.domain.model.oss.Oss;

/**
 * Assembler class for the AuthenticationDTOAssembler.
 *
 * @author hqf@rc
 * @date 2022-04-25 09:36
 * 可以写的方法：from，to，getList等
 **/
public class OssDTOAssembler {

    public static OssDTO fromOss(final Oss oss) {
        OssDTO ossDTO = new OssDTO();
        BeanUtil.copyProperties(oss, ossDTO);
        ossDTO.setOssId(oss.getOssId().getId());
        return ossDTO;
    }
}
