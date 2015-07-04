package com.ricex.aft.servlet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ricex.aft.servlet.auth.AFTTokenAuthenticationFilter;
import com.ricex.aft.servlet.auth.APIUserAuthenticator;
import com.ricex.aft.servlet.auth.TokenManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/** Logger instance */
	private static Logger log = LoggerFactory.getLogger(WebSecurityConfigurerAdapter.class);
	
	/** The application config */
	@Autowired
	public ApplicationConfig applicationConfig;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.userDetailsService(userDetailsService()).passwordEncoder(applicationConfig.passwordEncoder());
	}
	
	/** Configures the http security used
	 * 
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				//permit access to the static resources, css, js, and images
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()
				//permit access to login, and register pages
				.antMatchers("/login", "/register").permitAll()
				//permit access to the registration functions
				.antMatchers("/api/user/isAvailable*", "/api/user/register", "/api/user/login/*").permitAll()
				.anyRequest().authenticated()
				.and()	
			.addFilterBefore(aftTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login?logout")
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));			
	}	
	
	/** The user Details Service to use for spring security
	 * 
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		try {
			return applicationConfig.userManager();
		}
		catch (Exception e) {
			log.error("Error loading UserDetails Service", e);
			//return the default userDetailsService
			return super.userDetailsService();
		}
	}
	
	@Bean
	public AFTTokenAuthenticationFilter aftTokenAuthenticationFilter() throws Exception {
		AFTTokenAuthenticationFilter filter = new AFTTokenAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setTokenManager(tokenManager());
		return filter;
	}
	
	/** Create the Token Manager for the AFT Token Authentication
	 *
	 */
	@Bean
	public TokenManager tokenManager() {
		TokenManager tokenManager = new TokenManager();
		return tokenManager;
	}
	
	/** Create the APIUserAuthenticator bean
	 * 
	 */
	@Bean
	public APIUserAuthenticator userAuthenticator() throws Exception {
		APIUserAuthenticator userAuthenticator = new APIUserAuthenticator();
		userAuthenticator.setAuthenticationManager(authenticationManagerBean());
		userAuthenticator.setTokenManager(tokenManager());
		userAuthenticator.setUserAuthenticationTokenManager(applicationConfig.userAuthenticationTokenManager());
		return userAuthenticator;
	}

}
