package com.k.bootweb.service;

import com.k.bootweb.mapper.PostMapper;
import com.k.bootweb.pojo.dao.Page;
import com.k.bootweb.pojo.dao.Post;
import com.k.bootweb.pojo.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> queryPostList() {
        return postMapper.queryPostList();
    }

    @Override
    public String queryPostcontentByUid(int uid){
        List<Post> postList = postMapper.queryPostByUid(uid);
        String content="";
        for(Post p:postList){
            content=content+p.getContent();
        }
        return content;
    }

    @Override
    public Post findPostById(int id) {
        return postMapper.selectPostById(id);
    }

    @Override
    public PostDto<Object> findAllUserByPage(Page page){
        page.setRows(postMapper.getPostListNum());
//        int maxpage=page.getTotal();
//        HashMap<String,Integer> map = new HashMap<String,Integer>();
//        int start,limit;
        //start为偏移量，end为查询个数
//        start=page.getOffset();
//        limit=page.getLimit();
//        map.put("start",start);
//        map.put("limit",limit);
        if(page.getCurrent()<1||page.getCurrent()>page.getTotal()){
            return PostDto.failed(null,"查询页数越界",page.getTotal());
        }
        else{
            return PostDto.ok(postMapper.queryPostByLimit(page),"查询成功", page.getTotal());
        }

    }

    @Override
    public int updateCommentCount(int id, int commentCount) {
        return postMapper.updateCommentCount(id, commentCount);
    }

    @Override
    public int updateStatus(int id, int status) {
        return postMapper.updateStatus(id, status);
    }

    @Override
    public int insertPost(Post post){
        return postMapper.insertPost(post);
    }

    @Override
    public int updateType(int id, int type){
        return postMapper.updateType(id, type);
    }

}
