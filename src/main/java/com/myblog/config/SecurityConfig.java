package com.myblog.config;

import com.myblog.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http
        // .csrf().disable() // cross-site request forgery(CSRF) is a security measure
        // that prevents
        // //the making of unauthorized request from the malicious websites on behalf of
        // an authenticated user.
        // // 1. By disabling it, give the permission to that request without requiring
        // csrf tokens.
        // // 2. By enabling it, prevents to access that request & it requires csrf
        // tokens.
        // .authorizeRequests()
        // .anyRequest() // this permits any of the coming request
        // .authenticated()
        // .and()
        // .httpBasic();

        // -----------------For In Memory
        // Authentication-------------------------------------------------

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll() // This line of code permits all the user/admin to
                                                                    // access the url path followed by the given url
                                                                    // pattern who is using the GET method and this
                                                                    // doesn't require the username & password.
                // .antMatchers(HttpMethod.GET, "/api/**").hasRole("USER") // this line of code
                // allows only user can access the url path (i.e. role specific)
                // .antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN") // can only be
                // accessed to an admin with the "USER" role
                // .antMatchers(HttpMethod.DELETE, "/api/auth/**").hasAnyRole("USER","ADMIN") //
                // can be accessed by anyone from the given role(MULTIPLE ROLE)
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .antMatchers("/sms/**").permitAll()
                .anyRequest() // this permits any of the coming request
                .authenticated()
                .and()
                .httpBasic();

        // -------------------------- implements logout
        // feature-----------------------------------------------------------
        // .and()
        // .logout() // Add a logout configuration
        // .logoutUrl("/api/auth/logout") // Customize the logout URL
        // .logoutSuccessUrl("/api/auth/logout-success") // Redirect after successful
        // logout
        // .invalidateHttpSession(true) // Invalidate the HTTP session
        // .deleteCookies("JSESSIONID"); // Delete cookies upon logout

        // In this updated configuration, we've added a logout configuration to your
        // HttpSecurity. It specifies the following:

        // logoutUrl: The URL where users can initiate the logout process.
        // logoutSuccessUrl: The URL to redirect to after a successful logout.
        // invalidateHttpSession: Set to true to invalidate the HTTP session upon
        // logout.
        // deleteCookies: Specify which cookies to delete upon logout. In this case,
        // we're deleting the JSESSIONID cookie.

    }

    // -----------------In Memory Authentication------------->its a very useful when
    // we want to perform testing of your security
    // //--------------------------------------------------- implementation without
    // even implementing the db code------------------------
    // ---------------------------------It doesn't require
    // db-------------------------------------------------------------
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("vivek")
                .password(passEncoder().encode("pass")).roles("USER").build();

        UserDetails admin = User.builder().username("admin")
                .password(passEncoder().encode("admin")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // -------------------------For
    // SignIn-----------------------------------------------------------------------------------
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        // .passwordEncoder(passEncoder())
        ;
    }
    // userDetailsService(userDetailsService).passwordEncoder(passEncoder());
    // ----------------------------------------------------------------------------------------------------------------------
}