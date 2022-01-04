package com.k.bootweb.pojo.dao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @ApiModelProperty(value = "帖子编号",position = 1)
    private int id;
    @ApiModelProperty(value = "用户编号",position = 2)
    private int uid;
    @ApiModelProperty(value = "匿名名称",position = 3)
    private String anonymity;
    @ApiModelProperty(value = "标题",position = 4)
    private String title;
    @ApiModelProperty(value = "内容",position = 5)
    private String content;
    @ApiModelProperty(value = "图片地址",position = 6)
    private String picture;
    @ApiModelProperty(value = "点赞数",position = 7)
    private int likes;
    @ApiModelProperty(value = "评论数",position = 8)
    private int comment;
    @ApiModelProperty(value = "创建时间",position = 9)
    private Date create_time;
    @ApiModelProperty(value = "类型",position = 10)
    //0-普通; 1-置顶;
    private int type;
    @ApiModelProperty(value = "状态",position = 11)
    //0-正常; 1-精华; 2-拉黑;
    private int status;
    @ApiModelProperty(value = "分数",position = 12)
    private int score;

}
