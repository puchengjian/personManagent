package com.person.json;

import com.person.constant.HttpConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/5 15:46
 */
@Data
@ApiModel("请求返回json数据")
public class SuccessOrFailure {

    @ApiModelProperty("状态码")
    private Integer status;

    @ApiModelProperty("数据")
    private Object data;

    @ApiModelProperty("身份认证")
    private Object token;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("总数量")
    private Integer total;

    private SuccessOrFailure() {
    }

    private SuccessOrFailure(Object data, Integer status, Integer total) {
        this.data = data;
        this.status = status;
        this.total = total;
    }

    private SuccessOrFailure(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static SuccessOrFailure SUCCESS = new SuccessOrFailure(null, HttpConst.SUCCESS, 0);
    public static SuccessOrFailure FAILURE = new SuccessOrFailure(HttpConst.INTERNAL_SERVER_ERROR, HttpConst.ERROR_500);

    public static SuccessOrFailure SUCCESS(Object data) {
        return new SuccessOrFailure(data, HttpConst.SUCCESS, 0);
    }

    public static SuccessOrFailure SUCCESS(Object data, Object token) {
        SuccessOrFailure successOrFailure = new SuccessOrFailure(data, HttpConst.SUCCESS, 0);
        successOrFailure.setToken(token);
        return successOrFailure;
    }

    public static SuccessOrFailure SUCCESS(Object data, Integer total) {
        return new SuccessOrFailure(data, HttpConst.SUCCESS, total);
    }

    public static SuccessOrFailure SUCCESS(String msg) {
        return new SuccessOrFailure(HttpConst.SUCCESS, msg);
    }

    public static SuccessOrFailure FAILURE(Integer status, String msg) {
        return new SuccessOrFailure(status, msg);
    }

}
