package com.k.bootweb.controller;

import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.utils.FastDFSClientWrapper;
import com.k.bootweb.utils.HostHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api("图片接口")
@RestController
@CrossOrigin(origins = "*")
public class pictureController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    @ApiOperation("添加图片")
    @PostMapping("/uploadPicture")
    public Result uploadPicture(MultipartFile file) throws IOException {
        User user = hostHolder.getUser();
        if (user == null) {
            return Result.fail(403, "你还没有登录哦!");
        }
        Map<String, Object> map =new HashMap<>();
        String path = this.fastDFSClientWrapper.uploadFile(file);
        map.put("path",path);
        return Result.success("图片上传成功",map);
    }
}
