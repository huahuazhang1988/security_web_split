package com.example.web_split.service;

import com.example.web_split.dao.UserDao;
import com.example.web_split.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserdetails implements UserDetailsService {
    @Autowired
    private UserDao dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.loadUserByUsername(username);
        if(user== null){
            throw  new RuntimeException("user does not exit");
        }
        user.setRoleList(dao.getRolesByUid(user.getId()));
        return user;
    }
}
