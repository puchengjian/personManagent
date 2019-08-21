package com.person.auth.network.bean.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/21 9:06
 */
@Data
@ApiModel("新增角色参数")
public class InsertRoleReq {

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("权限Id集合")
    private List<String> menuIdList = new ArrayList<>();

}
