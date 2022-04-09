package com.test.demo.config;

import com.test.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable() //token 사용하므로  basic인증은 disable
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session 기반이 아님을 선언
                .and()
                .authorizeRequests()
                    .antMatchers("/","/auth/**").permitAll()
                .anyRequest()
                    .authenticated();
        //filter 등록 , 매 요청마다 corsFilter 실행 후 jwtauthenticationFilter 실행
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }
}
