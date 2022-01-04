package com.k.bootweb.controller;

import com.k.bootweb.pojo.dao.Comment;
import com.k.bootweb.pojo.dao.Page;
import com.k.bootweb.pojo.dao.Post;
import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.pojo.dto.PostdetailDto;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.service.*;
import com.k.bootweb.pojo.dto.PostDto;
import com.k.bootweb.utils.Assessment;
import com.k.bootweb.utils.FastDFSClientWrapper;
import com.k.bootweb.utils.HostHolder;
import com.k.bootweb.utils.constant.CommunityConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Api("贴吧相关接口")
@RestController
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private Assessment assessment;

//    @ApiOperation("测试接口（查询所有帖子信息）")
//    @GetMapping("/queryPostList")
//    public List<Post> queryPostList(){
//        List<Post> postList = postService.queryPostList();
//        return postList;
//    }



    @ApiOperation("分页查询")
    @PostMapping("/queryPostByPage")
    public PostDto<Object> queryPostByPage(@RequestBody Page page){
        System.out.println(page);
        PostDto<Object> p= postService.findAllUserByPage(page);
        return p;
    }



    @ApiOperation("添加帖子")
    @PostMapping("/insertPost")
    public Result insertPost(@RequestBody Post post) throws IOException {
        User user = hostHolder.getUser();
        if (user == null) {
            return Result.fail(403, "你还没有登录哦!");
        }
        post.setUid(user.getUid());
        post.setStatus(0);
        post.setType(0);
        post.setScore(0);
        post.setCreate_time(new Date());
        postService.insertPost(post);

        String content=postService.queryPostcontentByUid(user.getUid());
        double psystatus= assessment.getassessment(content);
        userService.updatePsystatus(user.getUid(),psystatus);
        return Result.success("发布成功");
    }

    @ApiOperation("帖子详情")
    @PostMapping("/Postdetail")
    public Result getDiscussPost(@RequestBody PostdetailDto postdetailDto){

        Page page = new Page(postdetailDto.getCurrent(),postdetailDto.getLimit());
        HashMap<String,Object> map = new HashMap<String,Object>();
        // 帖子
        Post post = postService.findPostById(postdetailDto.getPostid());
        map.put("post",post);

        // 作者
        User author = loginService.findUserById(post.getUid());
        map.put("author",author);

        // 点赞数量
        long likeCount = likesService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_POST, postdetailDto.getPostid());
        map.put("likeCount",likeCount);
        // 点赞状态
        int likeStatus = hostHolder.getUser() == null ? 0 :
                likesService.findEntityLikeStatus(CommunityConstant.ENTITY_TYPE_POST, postdetailDto.getPostid(), hostHolder.getUser().getUid());
        map.put("likeStatus",likeStatus);



        // 评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + postdetailDto.getPostid());
        page.setRows(post.getComment());

        // 评论: 给帖子的评论
        // 回复: 给评论的评论
        // 评论列表
//        System.out.println(CommunityConstant.ENTITY_TYPE_POST + " "+  post.getId()+" "+page.getOffset()+" "+page.getLimit());
        List<Comment> commentList = commentService.findCommentsByEntity(
                CommunityConstant.ENTITY_TYPE_POST, post.getId(), page.getOffset(), page.getLimit());

        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment", comment);
                // 作者
                commentVo.put("author", loginService.findUserById(comment.getUid()));

                //点赞数量
                likeCount = likesService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("likeCount", likeCount);
                //点赞状态,需要判断当前用户是否登录，没有登录无法点赞
                likeStatus = hostHolder.getUser() == null ? 0 : likesService.findEntityLikeStatus(CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId(), hostHolder.getUser().getUid());
                commentVo.put("likeStatus", likeStatus);
                // 回复列表
                List<Comment> replyList = commentService.findCommentsByEntity(
                        CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        // 回复
                        replyVo.put("reply", reply);
                        // 作者
                        replyVo.put("user", loginService.findUserById(reply.getUid()));
                        // 回复目标
                        System.out.println(reply.getTarget_id()==0);
                        User target = reply.getTarget_id() == 0 ? null : loginService.findUserById(reply.getTarget_id());
                        System.out.println(loginService.findUserById(reply.getTarget_id()));
                        replyVo.put("target", target);

                        //点赞数量
                        likeCount = likesService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_COMMENT, reply.getId());
                        replyVo.put("likeCount", likeCount);
                        //点赞状态,需要判断当前用户是否登录，没有登录无法点赞
                        likeStatus = hostHolder.getUser() == null ? 0 : likesService.findEntityLikeStatus(CommunityConstant.ENTITY_TYPE_COMMENT, reply.getId(), hostHolder.getUser().getUid());
                        replyVo.put("likeStatus", likeStatus);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys", replyVoList);

                // 回复数量
                int replyCount = commentService.findCommentCount(CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);
            }
        }

        map.put("comments",commentVoList);

        return Result.success("返回帖子数据",map);

    }

    @ApiOperation("置顶帖子")
    @PostMapping("/topPost")
    public Result topPost(int id){

        postService.updateType(id,1);

        return Result.success("置顶成功");
    }

    @ApiOperation("精华帖子")
    @PostMapping("/elitetePost")
    public Result elitePost(int id){

        postService.updateStatus(id,1);

        return Result.success("精华成功");
    }

    @ApiOperation("删除帖子")
    @PostMapping("/deletePost")
    public Result deletePost(int id){

        postService.updateStatus(id,2);

        return Result.success("删除成功");
    }



}
