package com.example.web_split.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class User implements UserDetails {
   private Integer id;

    private String password;

   private String username;

   private boolean IsAccountNonExpired;

   private boolean IsAccountNonLocked;

   private boolean isCredentialsNonExpired;

  private   boolean isEnabled;
  private List<Role> roleList= new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
        roleList.forEach(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(simpleGrantedAuthority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return IsAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        IsAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        IsAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
