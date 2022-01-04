package com.k.bootweb.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    @ApiModelProperty(value = "实体类型",position = 1)
    private int entityType;
    @ApiModelProperty(value = "实体编号",position = 2)
    private int entityId;
    @ApiModelProperty(value = "实体用户编号",position = 3)
    private int entityUserId;
    @ApiModelProperty(value = "帖子编号",position = 4)
    private int postId;
}
