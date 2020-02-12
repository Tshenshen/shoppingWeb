package com.liang.shoppingweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

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
//        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/index","/","/index.html")
//                .hasAnyAuthority(AuthorityConstant.user,AuthorityConstant.shop);
        http.authorizeRequests().antMatchers("/index", "/", "/index.html").permitAll();
        http.formLogin().loginPage("/userLogin");
        http.logout();
        http.rememberMe().rememberMeParameter("isRemember");
    }


}
