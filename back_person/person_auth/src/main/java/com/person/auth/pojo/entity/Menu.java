package com.person.auth.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: pzy
 * @create: 2019/8/5 15:26
 */
@Data
@ApiModel("菜单数据")
public class Menu implements Serializable {
    private static final long serialVersionUID = 3421027097119584827L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("路径")
    private String path;

    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("权限")
    private String perms;

    @ApiModelProperty("1-菜单 2-按钮")
    private Integer type;

    @ApiModelProperty("1-true 0-false true需要登录")
    private boolean requireAuth;

    @ApiModelProperty("父级Id")
    private String parentId;

    @ApiModelProperty("1-true 0-false true启用")
    private boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @Override
    public String toString() {
        String builder = "菜单信息[ " +
                " 主键: " + id +
                ",菜单名称: " + menuName + "，路径: " + path + ", 跳转页面: " + component +
                ",图标: " + icon + "，需要登录: " + requireAuth + ", 类型: " + (type == 1 ? "菜单" :"按钮") +
                ",权限: " + perms + "，父级: " + parentId + ", 启用状态: " + enabled +
                ",创建时间: " + createTime + "，更新时间: " + updateTime +
                " ]";

        return builder;
    }
}
