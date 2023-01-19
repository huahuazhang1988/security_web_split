package com.example.web_split.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginFilter  extends UsernamePasswordAuthenticationFilter {
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("authentication method not allowed");
        }
        if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            Map<String,String> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = map.get(getUsernameParameter());
            String password = map.get(getPasswordParameter());
            System.out.println("username is "+ username+" password is "+password);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            setDetails(request,token);
            return this.getAuthenticationManager().authenticate(token);

        }
        return super.attemptAuthentication(request,response);

    }
}
