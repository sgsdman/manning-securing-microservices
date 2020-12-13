package com.example.twe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.example.twe.service.DBUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired DBUserDetailsService dbUserDetailsService;
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.csrf(c -> c.ignoringAntMatchers(
	            "/user/**", "/client/**"
	    ));

	    http.authorizeRequests()
	            .mvcMatchers("/user/**").permitAll()
	            .mvcMatchers("/client/**").permitAll();

	    super.configure(http);
	    // http.formLogin();
	  }
	
	/*
	@Bean
	public UserDetailsService uds() {
		 
		UserDetailsManager uds = new InMemoryUserDetailsManager();
		UserDetails u = User.withUsername("john")
							.password("12345")
							.authorities("read")
							.build();
		uds.createUser(u);
		return uds;
	} 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	  */
	@Bean
	  public AuthenticationProvider authProvider() {
	    DaoAuthenticationProvider provider = 
	      new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(dbUserDetailsService);
	    
	    return provider;
	  }
	
	@Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
		
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
