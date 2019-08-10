package com.person.auth.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: pzy
 * @create: 2019/8/5 15:32
 */
@Data
@ApiModel("用户角色关联数据")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 7274254736128476237L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty("角色Id")
    private String roleId;

}
