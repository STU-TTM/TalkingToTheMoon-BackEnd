package com.k.bootweb.pojo.dao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket implements Serializable {
    @ApiModelProperty(value = "编号",position = 1)
    private int id;
    @ApiModelProperty(value = "用户编号",position = 1)
    private int userId;
    @ApiModelProperty(value = "ticket",position = 1)
    private String ticket;
    @ApiModelProperty(value = "状态",position = 1)
    private int status;
    @ApiModelProperty(value = "过期时间",position = 1)
    private Date expired;

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", expired=" + expired +
                '}';
    }
}
