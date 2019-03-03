package com.pan.entity;

import java.util.Date;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import com.pan.common.annotation.CheckUsernameGroup;
import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.TelephoneBindGroup;
import com.pan.common.annotation.UserEditGroup;

/**
 * 用户信息实体类
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -2820248584713373399L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空", groups = {LoginGroup.class, RegisterGroup.class, CheckUsernameGroup.class})
    @Size(max = 15, min = 5, message = "用户名长度必须在5-15之间", groups = {RegisterGroup.class, CheckUsernameGroup.class})
    @Pattern(regexp = "^(?![0-9]+$)([a-zA-Z0-9_\\.\\-]+$)", message = "用户名只能包含字母，数字，点和下划线和减号,且不能全为数字", groups = {RegisterGroup.class, CheckUsernameGroup.class})
    private String username;
    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空", groups = {RegisterGroup.class, UserEditGroup.class})
    @Size(max = 10, min = 2, message = "昵称长度必须在2-10之间", groups = {RegisterGroup.class, UserEditGroup.class})
    @Pattern(regexp = "^[\\u4e00-\\u9fff\\w]{2,10}$", message = "昵称只能包含2-10位字母，数字，中文和下划线", groups = {RegisterGroup.class, UserEditGroup.class})
    private String nickname;
    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Size(max = 12, min = 6, message = "密码长度必须在6-12之间", groups = {RegisterGroup.class})
    @Pattern(regexp = "^[a-zA-Z0-9_\\.\\-]+$", message = "密码只能包含字母，数字，点和下划线", groups = {RegisterGroup.class})
    private String password;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;
    /**
     * 用户状态
     * 0禁用，1正常
     */
    private Integer status;
    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号不能为空", groups = {TelephoneBindGroup.class})
    @Pattern(regexp = "^((1[3|4|5|7|8][0-9]{1})+\\d{8})$", message = "请输入正确的手机号码", groups = {TelephoneBindGroup.class})
    private String telephone;
    /**
     * 用户头像
     */
    private String userPortrait;
    /**
     * 当前用户是否是管理员
     * 0-否,1-是
     */
    private Integer adminFlag;
    /**
     * 地址
     */
    @Size(message = "地址不能超过100个字", max = 100, groups = {UserEditGroup.class})
    private String address;

    public User() {
        super();
    }

    public User(String userId, Integer status) {
        super();
        this.userId = userId;
        this.status = status;
    }
}
