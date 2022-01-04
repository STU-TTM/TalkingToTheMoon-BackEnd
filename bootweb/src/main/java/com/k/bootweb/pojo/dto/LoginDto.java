package com.k.bootweb.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @ApiModelProperty(value = "邮箱",position = 1)
    private String email;
    @ApiModelProperty(value = "用户密码",position = 2)
    private String password;
    @ApiModelProperty(value = "记住我",position = 3)
    private boolean rememberme;
}
