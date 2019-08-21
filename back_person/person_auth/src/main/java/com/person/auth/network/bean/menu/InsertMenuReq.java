package com.person.auth.network.bean.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/21 15:36
 */
@Data
@ApiModel("新增菜单参数")
public class InsertMenuReq {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("父级Id")
    private String parentId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("是否需要登录")
        private boolean requireAuth;

    @ApiModelProperty("类型 1-菜单 2-按钮")
    private Integer type;

    @ApiModelProperty("权限")
    private String perms;

}

