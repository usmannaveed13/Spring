package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        // Permit All Requests inside the web application
//        http.authorizeRequests().anyRequest().permitAll()
//                .and().formLogin()
//                .and().httpBasic();
        // Deny All Requests inside the web application
//        http.authorizeRequests().anyRequest().denyAll()
//                .and().formLogin()
//                .and().httpBasic();
        // removing h2 rout because now using mysql


        http.csrf().ignoringAntMatchers("/saveMsg").ignoringAntMatchers("/public/**").and()
                .authorizeRequests()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/displayProfile").authenticated()
                .mvcMatchers("/updateProfile").authenticated()
                .mvcMatchers("/displayMessages").hasRole("ADMIN")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/holidays/**").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers("/courses").permitAll()
                .mvcMatchers("/about").permitAll()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
//                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().httpBasic();

        // comments it because now are not using h2 database
//        http.headers().frameOptions().disable();

    }

   // we are stop using inMemoryAuthentication and now we are taking user and password from database and authenticate it

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("12345").roles("USER")
//                .and()
//                .withUser("admin").password("54321").roles("ADMIN")
//                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
