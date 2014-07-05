package com.ricex.aft.servlet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.ricex.aft.servlet.manager.UserManager;

/** Java Config for Spring Security
 * 
 * @author Mitchell Caisse
 *
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(UserManager.INSTANCE);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(digestAuthenticationEntryPoint()).and()
			.authorizeRequests().antMatchers("/manager/**").hasRole("USER")
			.anyRequest().authenticated()
			.and().addFilter(digestAuthenticationFilter());
			
	}
	
	public DigestAuthenticationFilter digestAuthenticationFilter() {
		DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
		filter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
		filter.setUserDetailsService(super.userDetailsService());
		return filter;
	}
	
	@Bean
	public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
		DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
		entryPoint.setKey("ricex1055AFT");
		entryPoint.setRealmName("PushFile");
		return entryPoint;
	}
}
