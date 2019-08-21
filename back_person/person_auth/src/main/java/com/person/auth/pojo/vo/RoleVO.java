package com.person.auth.pojo.vo;

import com.person.auth.pojo.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/20 18:29
 */
public class RoleVO extends Role {

    @ApiModelProperty("菜单Id集合")
    private List<String> menuIdList = new ArrayList<>();

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
