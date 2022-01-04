package com.k.bootweb.service;

import com.k.bootweb.pojo.dao.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    /**
     * 更新用户健康状况
     */
    int updatePsystatus(int uid,double psystatus);

}
