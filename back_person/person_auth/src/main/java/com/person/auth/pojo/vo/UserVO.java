package com.person.auth.pojo.vo;

import com.person.auth.pojo.entity.User;
import com.person.utils.Excel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: pzy
 * @create: 2019/8/21 10:39
 */
public class UserVO extends User {

    @ApiModelProperty("角色名称")
    @Excel(name = "角色", orderNum = 3)
    private String roleName;

    @ApiModelProperty("角色Id")
    private String roleId;

    @ApiModelProperty("绑定角色是否是超级管理员")
    private boolean admin;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
