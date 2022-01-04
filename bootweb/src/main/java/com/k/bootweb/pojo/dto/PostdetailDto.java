package com.k.bootweb.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostdetailDto {
    @ApiModelProperty(value = "帖子编号",position = 1)
    private int postid;
    @ApiModelProperty(value = "当前页码",position = 2)
    private int current;
    @ApiModelProperty(value = "显示上限",position = 3)
    private int limit;
}
