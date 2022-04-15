package com.itsol.recruit_managerment.config;


import com.itsol.recruit_managerment.security.AuthenFilter;
import com.itsol.recruit_managerment.security.AuthorFilter;
import com.itsol.recruit_managerment.serviceimpl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserServiceimpl appUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/signup", "/jobs/getAllPage","/jobs/search","/jobs/getJob/{id}").permitAll();
        http.authorizeRequests().antMatchers("/active/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/**,").hasAnyAuthority("ROLE_JE", "ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/admin/**").hasAnyAuthority("ROLE_JE","ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/**").hasAnyAuthority("ROLE_JE", "ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
//        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(new AuthenFilter(authenticationManager(), appUserService));
        http.addFilterBefore(new AuthorFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin()
                .loginProcessingUrl("/login")
                .isCustomLoginPage();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.MULTI_STATUS));
    }
}
