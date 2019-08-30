package com.person.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: pzy
 * @create: 2019/8/8 16:43
 */
@ApiModel("树形结构")
@Data
public class Tree<T> {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("父级Id")
    private String parentId;

    @ApiModelProperty("名称")
    private String text;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("页面")
    private String component;

    @ApiModelProperty("文件夹")
    private String folder;

    private String icon;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("权限")
    private String perms;

    @ApiModelProperty("启用状态")
    private boolean enabled;

    @ApiModelProperty("requireAuth 是否需要登录 true-登录")
    private Map<String, Object> meta = new HashMap<>();

    @ApiModelProperty("子节点")
    private List<Tree<T>> children = new ArrayList<>();


    @Override
    public String toString() {
        String builder = "树形数据[ " +
                " 主键: " + id +
                ",菜单名称: " + text + "，路径: " + path + ", 跳转页面: " + component + ", 文件夹" + folder +
                ",类型: " + type + "，权限: " + perms + ", 状态: " + enabled +
                ",图标: " + icon + "，meta: " + meta + "，父级: " + parentId +
                ", 子节点:" + children +
                " ]";

        return builder;
    }


}
