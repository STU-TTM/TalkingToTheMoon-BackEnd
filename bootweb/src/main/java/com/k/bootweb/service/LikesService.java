package com.k.bootweb.service;

import org.springframework.stereotype.Service;

@Service
public interface LikesService {

    /**
     * @Description: 对实体点赞,对相应的用户加赞
     * @param userId :点赞的人
     * @param entityType
     * @param entityId
     * @param entityUserId :被赞的人，即作者
     * @return: void
     **/
    void like(int userId,int entityType,int entityId,int entityUserId);

    /**
     * @Description: 查询某实体（帖子，评论等）点赞数量
     * @param entityType
     * @param entityId
     * @return: long
     **/
    long findEntityLikeCount(int entityType,int entityId);

    /**
     * @Description:查询某人对某实体的点赞状态
     * @param entityType
     * @param entityId
     * @param userId
     * @return: int
     **/
    int findEntityLikeStatus(int entityType,int entityId,int userId);

//    /**
//     * @Description: 查询某个用户获得赞，用于在个人主页查看收获了多少赞
//     * @param userId
//     * @return: int
//     * @Date 2020/5/12
//     **/
//    int findUserLikeCount(int userId);

}
