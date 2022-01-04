package com.k.bootweb.controller;


import com.k.bootweb.pojo.dao.Comment;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.service.CommentService;
import com.k.bootweb.utils.HostHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @ApiOperation("添加评论")
    @PostMapping("/addcomment")
    public Result addComment(@RequestBody  Comment comment) {
        comment.setUid(hostHolder.getUser().getUid());
        comment.setStatus(0);
        comment.setCreate_time(new Date());
        commentService.addComment(comment);

        return Result.success("添加成功");
    }

    @ApiOperation("删除评论")
    @PostMapping("/deletecomment")
    public Result deleteComment(@RequestBody  Comment comment) {
//        comment.setUid(hostHolder.getUser().getUid());
//        comment.setStatus(0);
//        comment.setCreate_time(new Date());
//        commentService.addComment(comment);
//
//        return Result.success("添加成功");
        return null;
    }
}
