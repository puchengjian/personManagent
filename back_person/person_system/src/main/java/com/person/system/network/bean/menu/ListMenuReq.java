package com.person.system.network.bean.menu;

import com.person.network.bean.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/21 16:04
 */
@Data
@ApiModel("查询全部菜单参数")
public class ListMenuReq {

    @ApiModelProperty("搜索字段")
    private String searchKey;
    @ApiModelProperty("搜索值")
    private String searchValue;
    @ApiModelProperty(hidden = true)
    private String userId;

}
