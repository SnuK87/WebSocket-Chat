package de.snuk.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().anyRequest().hasRole("USER").and().formLogin().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.inMemoryAuthentication().withUser("jason").password("jason123").roles("USER");
	auth.inMemoryAuthentication().withUser("freddy").password("freddy123").roles("USER");
	auth.inMemoryAuthentication().withUser("pennywise").password("pennywise123").roles("USER");
    }
}
