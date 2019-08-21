package com.person.auth.network.bean.user;

import com.person.network.bean.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/20 17:06
 */
@Data
@ApiModel("查询全部用户参数")
public class ListUserReq extends BaseReq {

    @ApiModelProperty("搜索字段")
    private String searchKey;
    @ApiModelProperty("搜索值")
    private String searchValue;

}
