package com.k.bootweb.mapper;

import com.k.bootweb.pojo.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    int updateUserInfo(User user);


    User getUserInfoByCond(@Param("username")String username, @Param("password")String password);

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateUsername(@Param("uid") int id,@Param("username") String username);

    int updatePassword(@Param("uid") int id,@Param("password") String password);

    int updatePsystatus(@Param("uid") int id,@Param("psystatus")double psystatus);
}
