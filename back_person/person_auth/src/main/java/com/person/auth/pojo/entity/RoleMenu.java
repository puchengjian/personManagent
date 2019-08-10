package com.person.auth.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: pzy
 * @create: 2019/8/5 15:30
 */
@Data
@ApiModel("角色菜单关联数据")
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = -9023097887121266967L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("角色Id")
    private String roleId;

    @ApiModelProperty("菜单Id")
    private String menuId;

}
