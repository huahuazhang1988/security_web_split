package com.example.web_split.config;

import com.example.web_split.service.MyUserdetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserdetails myUserdetails;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("admin").password("{noop}123").roles("admin").build());
//        return manager;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     //   auth.userDetailsService(userDetailsService());
        auth.userDetailsService(myUserdetails);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setPasswordParameter("password");
    loginFilter.setUsernameParameter("username");
    loginFilter.setFilterProcessesUrl("/dologin");
    loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
         Map<String,Object> map = new HashMap<>();
         map.put("msg","login successfully");
         map.put("status",200);
         map.put("authentication",authentication);
         response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(map);
            response.getWriter().write(s);
        }
    });
    loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            Map<String,Object> map = new HashMap<>();
            map.put("msg","login fails");
            map.put("status",exception.toString());
            response.setContentType("application/json;charset=utf-8");
            String s = new ObjectMapper().writeValueAsString(map);
            response.getWriter().write(s);
        }
    });
    loginFilter.setAuthenticationManager(authenticationManagerBean());

 return loginFilter;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.formLogin();
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
