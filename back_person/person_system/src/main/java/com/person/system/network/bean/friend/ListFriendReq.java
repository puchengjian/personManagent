package com.person.system.network.bean.friend;

import com.person.network.bean.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/31 13:40
 */
@Data
@ApiModel("查询全部好友参数")
public class ListFriendReq extends BaseReq {

    @ApiModelProperty("搜索字段")
    private String searchKey;
    @ApiModelProperty("搜索值")
    private String searchValue;
    @ApiModelProperty(hidden = true)
    private String userId;

}
