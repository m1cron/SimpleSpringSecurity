package ru.micron.spring.security.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(userBuilder.username("m1cron").password("123").roles("EMPLOYEE"))
                .withUser(userBuilder.username("maxim").password("123").roles("HR"))
                .withUser(userBuilder.username("admin").password("123").roles("MANAGER", "HR"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                                .antMatchers("/hr_info").hasRole("HR")
                                .antMatchers("/manager_info/**").hasRole("MANAGER")
                        .and().formLogin().permitAll();
    }

}
