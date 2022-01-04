package com.k.bootweb.service;

import com.k.bootweb.pojo.dao.Page;
import com.k.bootweb.pojo.dao.Post;
import com.k.bootweb.pojo.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<Post> queryPostList();

    String queryPostcontentByUid(int uid);

    public Post findPostById(int id);

    int insertPost(Post post);

    PostDto<Object> findAllUserByPage(Page page);

    int updateCommentCount(int id, int commentCount);

    int updateStatus(int id, int status);

    int updateType(int id, int type);
}
