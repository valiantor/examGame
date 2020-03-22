package com.valiantor.dao;

import com.valiantor.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User findUserByUIdAndPassword(@Param("uId") String uId,@Param("password") String password);
    public User findUserByUId(@Param("uId") String uId);
    public int  addUser(@Param("user") User user);
}
