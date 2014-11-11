package com.ricex.aft.servlet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.inMemoryAuthentication().withUser("mitchell").password("password").roles("USER");
	}
	
	/** Configures the http security used
	 * 
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//permit access to the static resources, css, js, and images
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()
				//permit access to login, and register pages
				.antMatchers("/login", "/register").permitAll()
				//permit access to the registration functions
				.antMatchers("/api/user/isAvailable*", "/api/user/register").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll();
			
	}
}
