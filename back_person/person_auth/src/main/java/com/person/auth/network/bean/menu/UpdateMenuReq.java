package com.person.auth.network.bean.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/21 15:39
 */
@Data
@ApiModel("修改用户参数")
public class UpdateMenuReq {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("是否需要登录")
    private boolean requireAuth;


}
