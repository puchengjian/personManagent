package com.person.system.network.bean.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/21 9:12
 */
@Data
@ApiModel("修改角色参数")
public class UpdateRoleReq {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("角色姓名")
    private String roleName;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("权限Id集合")
    private List<String> menuIdList = new ArrayList<>();


}
