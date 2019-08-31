package com.person.system.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: pzy
 * @create: 2019/8/31 10:42
 */
@Data
@ApiModel("好友关联数据")
public class Friend implements Serializable {
    private static final long serialVersionUID = 1781896382958637299L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("登录人Id")
    private String myUserId;

    @ApiModelProperty("好友Id")
    private String friendUserId;


}
