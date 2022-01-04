package com.k.bootweb.pojo.dao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @ApiModelProperty(value = "用户号",position = 1)
    private Integer uid;
    @ApiModelProperty(value = "用户名",position = 2)
    private String username;
    @ApiModelProperty(value = "用户密码",position = 3)
    private String password;
    @ApiModelProperty(value = "邮箱",position = 4)
    private String email;
    @ApiModelProperty(value = "盐值",position = 5)
    private String salt;
    @ApiModelProperty(value = "类型",position = 6)
    private int type;
    @ApiModelProperty(value = "状态",position = 7)
    private int status;
    @ApiModelProperty(value = "头像",position = 8)
    private String head_portrait;
    @ApiModelProperty(value = "健康状态",position = 9)
    private double psy_status;
    @ApiModelProperty(value = "创建时间",position = 10)
    private Date create_time;


    public User(String username,String password,String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }

}
