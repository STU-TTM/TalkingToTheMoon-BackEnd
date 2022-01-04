package com.k.bootweb.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    @ApiModelProperty(value = "返回码",position = 1)
    public int code;
    @ApiModelProperty(value = "返回消息",position = 2)
    public String msg;
    @ApiModelProperty(value = "返回数据",position = 3)
    private T data; //数据

    public Result(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public static Result fail(String msg){
        return new Result(404,msg);
    }

    public static Result fail(int code,String msg){
        return new Result(code,msg);
    }

    public static Result success(String msg){
        return new Result(200,msg);
    }

    public static Result success(String msg,Object data){
        return new Result(200,msg, data);
    }

}
