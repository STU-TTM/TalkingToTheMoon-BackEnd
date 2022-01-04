package com.k.bootweb.pojo.dao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    //评论编号
    @ApiModelProperty(value = "评论编号",position = 1)
    private int id;
    //用户账号
    @ApiModelProperty(value = "用户账号",position = 2)
    private int uid;
    //实体类型（1为帖子，2为评论）
    @ApiModelProperty(value = "实体类型（1为帖子，2为评论）",position = 3)
    private int entity_type;
    //实体编号
    @ApiModelProperty(value = "实体编号",position = 4)
    private int entity_id;
    //回复目标编号（如果是回复他人的评论，就为他人的uid）
    @ApiModelProperty(value = "回复目标编号（如果是回复他人的评论，就为他人的uid）",position = 5)
    private int target_id;
    //评论内容
    @ApiModelProperty(value = "评论内容",position = 6)
    private String content;
    //帖子状态（0正常/1精华/2拉黑）
    @ApiModelProperty(value = "帖子状态（0正常/1精华/2拉黑）",position = 7)
    private int status;
    //评论点赞数
    @ApiModelProperty(value = "评论点赞数",position = 8)
    private int likes;
    //评论时间
    @ApiModelProperty(value = "评论时间",position = 9)
    private Date create_time;

}