package com.person.chat.network.bean.friend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/9/2 10:19
 */
@Data
@ApiModel("查询全部好友参数")
public class ListFriendReq {

    @ApiModelProperty("搜索字段")
    private String searchKey;
    @ApiModelProperty("搜索值")
    private String searchValue;
    @ApiModelProperty(hidden = true)
    private String userId;

}
