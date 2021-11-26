package Progmatic.SustainableCommunity.configurations;

import Progmatic.SustainableCommunity.models.UserAuthorities;
import Progmatic.SustainableCommunity.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@Configuration
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    private final UserService appUserService;
    private final PasswordEncoder passwordEncoder;

    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .and()
                .logout()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .antMatchers("user/create").permitAll()
                // a homepage-n és regen kívül minden oldalhoz autentikáció kell jelenleg
                .anyRequest()
                .authenticated();
        
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
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

}
