package com.example.pac4jtest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-12 16:02
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //token认证配置
    @Bean
    public MyAuthenticationProvider myAuthenticationProvider() {
        System.out.println("即将创建MyAuthenticationProvider");
        return new MyAuthenticationProvider();
    }

    //过滤器配置
    @Bean
    public AuthenticationProcessingFilter authenticationProcessingFilter(AuthenticationManager authenticationManager) {
        AuthenticationProcessingFilter authenticationProcessingFilter = new AuthenticationProcessingFilter();
        //为过滤器添加认证器
        authenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        //重写认证失败时的跳转页面
        authenticationProcessingFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/api/bas/test/error"));
        return authenticationProcessingFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/api/bas/test/login", "/api/bas/test/error").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin().loginPage("/api/bas/test/login").permitAll()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                ;


        //设置filter调用顺序
        http.addFilterBefore(authenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    //添加认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider());
    }

    @Bean
    public MySuccessHandler successHandler(){
        return new MySuccessHandler();
    }

    @Bean
    public MyFailureHandler failureHandler(){
        return new MyFailureHandler();
    }

}
