package com.k.bootweb.service;

import com.k.bootweb.pojo.dao.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    /**
     * 根据实体找到实体下所有评论
     */
    List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 计算实体的评论数
     */
    int findCommentCount(int entityType, int entityId);

    /**
     * 添加评论
     */
    int addComment(Comment comment);

    /**
     * 根据评论id查找评论
     */
    Comment findCommentById(int id);
}
