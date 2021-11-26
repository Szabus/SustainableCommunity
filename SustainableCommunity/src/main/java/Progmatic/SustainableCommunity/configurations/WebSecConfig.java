package Progmatic.SustainableCommunity.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JWTAuthorizationFilter jwtAuthenticationFilter() {
        return new JWTAuthorizationFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //.antMatchers("/**").permitAll()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();



        
/*
                .antMatchers(
                            "/home",
                            "/register",
                            "/login",
                            "/share",
                            "/read_item_information").permitAll()
                .antMatchers("/users").hasAuthority("ADMIN")
                .antMatchers("/reserve_item", "/read_contact_information").hasAuthority("ADMIN", "CUSTOMER")
                .antMatchers("/upload_item").hasAuthority("SELLER", "ADMIN")
                .antMatchers("/delete_self").hasAuthority("CUSTOMER", "SELLER", "ADMIN")
                .antMatchers("/delete_all").hasAuthority("ADMIN")
                .antMatchers("/edit_self").hasAuthority("CUSTOMER", "SELLER", "ADMIN")
                .antMatchers("/edit_all").hasAuthority("ADMIN")
                .antMatchers("/rating").hasAuthority("ADMIN","SELLER", "CUSTOMER");*/





    }
}
