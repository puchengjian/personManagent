package com.person.auth.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: pzy
 * @create: 2019/8/5 15:23
 */
@Data
@ApiModel("角色数据")
public class Role implements Serializable {
    private static final long serialVersionUID = -2070688862322749925L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("1-true 0-false 管理员")
    private boolean admin;

    @ApiModelProperty("角色描述")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @Override
    public String toString() {
        String builder = "角色信息[ " +
                " 主键: " + id +
                ",角色名称: " + roleName + ", 管理员: " + admin +
                ",创建时间: " + createTime + "，更新时间: " + updateTime +
                " ]";

        return builder;
    }

}
