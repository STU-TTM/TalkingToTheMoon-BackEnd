package com.k.bootweb.controller;

import com.k.bootweb.utils.FastDFSClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
//public class testController {
//    @Autowired
//    private FastDFSClientWrapper fastDFSClientWrapper;
//    @PostMapping("/upload")
//    @ResponseBody
//    public Map<String,Object> upload(MultipartFile file) throws IOException {
//        Map<String, Object> map =new HashMap<>();
//        String path = this.fastDFSClientWrapper.uploadFile(file);
//        map.put("path",path);
//        return map;
//    }
//
//    @PostMapping("/test1")
//    @ResponseBody
//    public void test1(@RequestBody Map<String,String> map){
//        System.out.println(map);
//    }
//}
