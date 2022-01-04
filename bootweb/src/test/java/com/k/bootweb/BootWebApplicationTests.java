package com.k.bootweb;

import com.k.bootweb.mapper.CommentMapper;
import com.k.bootweb.mapper.PostMapper;
import com.k.bootweb.mapper.UserMapper;
import com.k.bootweb.pojo.dao.Page;
import com.k.bootweb.pojo.dao.Post;
import com.k.bootweb.service.CommentService;
import com.k.bootweb.service.PostService;
import com.k.bootweb.service.UserService;
import com.k.bootweb.utils.Assessment;
import com.k.bootweb.utils.FastDFSClientWrapper;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BootWebApplicationTests {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @Autowired
    private Assessment assessment;

    @Value("${upload.base-url}")
    private String baseUrl;


    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("mykey","k");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }

    @Test
    void test2() throws IOException {
        String content=postService.queryPostcontentByUid(1005);
        System.out.println(assessment.getassessment(content));

    }

    @Test
    void Test1(){
        try {
            //1.加载配置文件
            ClientGlobal.init("fdfs_client.conf");
            //2.创建一个TrackerClient对象
            TrackerClient trackerClient=new TrackerClient();
            //3.使用TrackerClient对象获得Trackerserver对象
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            //4。创建一个StorageClient对象
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String path=System.getProperty("user.dir")+ File.separator+"1.jpg";
            //5.使用StorageClient对象下载文件
            storageClient.download_file("group2","M00/00/00/eBjkT2GWWseAS9w2AABl_iGIHSc896.jpg",path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void Test2(){
        try {
            //1、加载配置文件
            ClientGlobal.init("fdfs_client.conf");
            //2、创建一个TrackerClient对象。
            TrackerClient trackerClient = new TrackerClient();
            //3、使用TrackerClient对象获得trackerserver对象。
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            //4、创建一个StorageClient对象。trackerserver、StorageServer两个参数。
            StorageClient storageClient = new StorageClient(trackerServer, null);
            String path = System.getProperty("user.dir") + File.separator + "1.png";
            //5、使用StorageClient对象上传文件。
            String[] strings = storageClient.upload_file(path, "png", null);
            //返回的数组包含两个元素分别类似于：
            //group1
            //M00/00/00/wKgDCmDtis2AAsj9AAW3ED-O1WQ151.jpg
            for (String string : strings) {
                System.out.println(string);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test3()  {

        dfsClient.deleteFile("http:/139.196.97.69:8000/group3/M00/00/00/J2uKpmGkd6OAVE4YAACZpLs89qU767.jpg");
    }

    @Test
    void testpy() throws IOException {
        File directory = new File("");// 参数为空
        String path_python = directory.getCanonicalPath()+"\\src\\main\\python";
        String path_py = path_python+"\\useDieSvm.py";
//        System.out.println("path2: "+courseFile+"\\src\\main\\python\\a.py");
        try {
            String[] args = new String[]{"C:\\Python1\\python", path_py, "A",path_python};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = in.readLine();
            int index1,index2;
            index1 = line.indexOf(" ",0);
            index2 = line.indexOf("]",0);
            double result=Double.parseDouble(line.substring(index1,index2));
            BigDecimal temp = new BigDecimal(result);
            result= temp.setScale(6, RoundingMode.HALF_UP).doubleValue();
            System.out.println(result);
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

