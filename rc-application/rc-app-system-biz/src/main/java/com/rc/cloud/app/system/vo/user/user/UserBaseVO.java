package com.rc.cloud.app.system.vo.user.user;

import com.rc.cloud.common.core.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023-07-11
 * @description 用户 Base VO，提供给添加、修改、详细的子 VO 使用
 */
@Data
public class UserBaseVO {

    @SuppressWarnings("checkstyle:magicnumber")
    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "RC")
    @NotBlank(message = "用户账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    private String username;

    @SuppressWarnings("checkstyle:magicnumber")
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;

    @Schema(description = "备注", example = "我是一个用户")
    private String remark;

    @Schema(description = "部门ID", example = "我是一个用户")
    private String deptId;

    @Schema(description = "岗位编号数组", example = "[1,2,3]")
    private Set<String> postIds;

    @Schema(description = "角色编号数组", example = "[1,2,3]")
    private Set<String> roleIds;

    @SuppressWarnings("checkstyle:magicnumber")
    @Schema(description = "用户邮箱", example = "HQF@RC.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @Schema(description = "手机号码", example = "15601691300")
    @Mobile
    private String mobile;

    @Schema(description = "用户性别,参见 SexEnum 枚举类", example = "1")
    private Integer sex;

    @Schema(description = "用户头像", example = "https://www.RC.cn/xxx.png")
    private String avatar;

    @Schema(description = "状态", example = "0")
    private Integer status;

}
