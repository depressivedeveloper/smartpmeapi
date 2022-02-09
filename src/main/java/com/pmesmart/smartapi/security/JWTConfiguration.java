package com.pmesmart.smartapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.pmesmart.smartapi.service.UserServiceImplementation;

@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {
	
	private final UserServiceImplementation userServiceImpl;
	private final PasswordEncoder encoder;
	
	public JWTConfiguration(UserServiceImplementation userServiceImpl, PasswordEncoder encoder) {
		this.userServiceImpl = userServiceImpl;
		this.encoder = encoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(HttpMethod.POST,"/login")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTValidateFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		return source;
	}
	
}
