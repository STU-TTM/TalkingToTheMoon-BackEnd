package com.k.bootweb.mapper;

import com.k.bootweb.pojo.dao.Page;
import com.k.bootweb.pojo.dao.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PostMapper {

    List<Post> queryPostList();

    List<Post> queryPostByLimit(Page page);

    List<Post> queryPostByUid(int uid);

    Post selectPostById(int id);

    int getPostListNum();

    int updateCommentCount(@Param("id")int id, @Param("commentCount")int commentCount);

    int deletePost(int id);

    int insertPost(Post post);

    int updateStatus(@Param("id")int id, @Param("status")int status);

    int updateType(@Param("id")int id, @Param("type")int type);

    int updatePost(Post user);

}
