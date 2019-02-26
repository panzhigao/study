package com.pan.dto;

import com.pan.entity.User;
import lombok.Data;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午7:01:46
 * 类说明
 */
@Data
public class UserDTO extends User {
    /**
     *
     */
    private static final long serialVersionUID = -9038298327313388493L;
    /**
     * 用户角色名称
     */
    private String roleNames;
    /**
     * 是否在线
     */
    private Boolean isOnline;
}
