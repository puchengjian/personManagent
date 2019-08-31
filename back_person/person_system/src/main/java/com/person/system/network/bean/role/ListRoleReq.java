package com.person.system.network.bean.role;

import com.person.network.bean.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/21 9:31
 */
@Data
@ApiModel("查询全部角色参数")
public class ListRoleReq extends BaseReq {

    @ApiModelProperty("搜索字段")
    private String searchKey;
    @ApiModelProperty("搜索值")
    private String searchValue;

}
