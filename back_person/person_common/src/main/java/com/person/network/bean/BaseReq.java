package com.person.network.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/5 16:22
 */
@ApiModel("顶级参数")
public class BaseReq {

    private Integer page = 1;

    private Integer size = 10;

    public Integer getPage() {
        if (page == 0) {
            page = 1;
        }
        return (page - 1) * size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
