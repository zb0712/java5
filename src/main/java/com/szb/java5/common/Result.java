package com.szb.java5.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回
 * @author 石致彬
 * @create 2021-03-28 15:33
 */
@Data
@ApiModel("统一返回结果")
public class Result {
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("状态码，200表示成功，500表示失败")
    private Integer code;
    @ApiModelProperty("错误信息")
    private String msg;
    @ApiModelProperty("数据")
    private Map<String,Object> data;

    private Result(){}

    public static Result ok(Map<String,Object> map) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(map);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

}
