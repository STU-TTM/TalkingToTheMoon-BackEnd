package com.k.bootweb.controller;

import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.pojo.dto.LikeDto;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.service.LikesService;
import com.k.bootweb.utils.HostHolder;
import com.k.bootweb.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(path = "/like")
    @ResponseBody
    public Result like(@RequestBody LikeDto likeDto) {
        User user = hostHolder.getUser();
        //点赞
        likesService.like(user.getUid(), likeDto.getEntityType(), likeDto.getEntityId(), likeDto.getEntityUserId());
        //点赞数量
        long likeCount = likesService.findEntityLikeCount(likeDto.getEntityType(), likeDto.getEntityId());
        //点赞状态
        int likeStatus = likesService.findEntityLikeStatus(likeDto.getEntityType(), likeDto.getEntityId(), user.getUid());
        //返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return Result.success("点赞成功",map);
    }
}
