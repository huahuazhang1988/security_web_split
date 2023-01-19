package com.example.web_split.dao;


import com.example.web_split.entity.Role;
import com.example.web_split.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public User loadUserByUsername(String username) ;
    List<Role> getRolesByUid(Integer uid);
}
