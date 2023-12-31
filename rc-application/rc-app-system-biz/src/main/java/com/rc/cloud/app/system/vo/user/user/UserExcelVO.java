package com.rc.cloud.app.system.vo.user.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rc.cloud.app.system.enums.DictTypeConstants;
import com.rc.cloud.common.excel.annotations.DictFormat;
import com.rc.cloud.common.excel.convert.DictConvert;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 用户 Excel 导出 VO
 */
@Data
public class UserExcelVO {

    @ExcelProperty("用户编号")
    private String id;

    @ExcelProperty("用户名称")
    private String username;

    @ExcelProperty("用户昵称")
    private String nickname;

    @ExcelProperty("用户邮箱")
    private String email;

    @ExcelProperty("手机号码")
    private String mobile;

    @ExcelProperty(value = "用户性别", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_SEX)
    private Integer sex;

    @ExcelProperty(value = "帐号状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("最后登录IP")
    private String loginIp;

    @ExcelProperty("最后登录时间")
    private LocalDateTime loginDate;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("部门负责人")
    private String deptLeaderNickname;

}
