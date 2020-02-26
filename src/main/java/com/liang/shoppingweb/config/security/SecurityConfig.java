package com.liang.shoppingweb.config.security;

import com.liang.shoppingweb.common.AuthorityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        String userQuery = "select username,password,enable from tbl_user where username = ?";
        String roleQuery = "select username,role from tbl_user where username = ?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/index", "/").permitAll()
                .mvcMatchers("/cert/*").hasAnyAuthority(AuthorityConstant.shop, AuthorityConstant.user)
                .mvcMatchers("/order/*").hasAnyAuthority(AuthorityConstant.shop, AuthorityConstant.user)
                .mvcMatchers("/user/*").hasAnyAuthority(AuthorityConstant.shop, AuthorityConstant.user);
        //todo 店家绑定在用户上
        http.formLogin().loginPage("/userLogin").successHandler(myLoginSuccessHandler);
        http.logout();
        http.rememberMe().rememberMeParameter("isRemember");
        http.csrf().disable();
    }


}