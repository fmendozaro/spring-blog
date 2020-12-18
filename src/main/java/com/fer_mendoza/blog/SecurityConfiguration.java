package com.fer_mendoza.blog;

import com.fer_mendoza.blog.services.UserDetailsLoader;
import com.fer_mendoza.blog.services.UserWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserWithRoles.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsLoader userDetails;

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/") // user's home page, it can be any URL
                    .permitAll() // Anyone can go to the login page
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/logout", "/async-login", "/posts/remove-tag") // anyone can see the home and logout page
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout") // append a query string value
                .and()
                    .authorizeRequests()
                    .antMatchers("/posts/create", "/posts/?/edit", "/users/?/friend-request", "/users/friends") // only authenticated users can visit this urls
                    .authenticated()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}