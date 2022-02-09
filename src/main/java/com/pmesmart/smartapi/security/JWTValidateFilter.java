package com.pmesmart.smartapi.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTValidateFilter extends BasicAuthenticationFilter{
	public static final String HEADER_ATRIBUTE = "Authorization";
	public static final String HEADER_PREFIX = "Bearer ";
	
	
	public JWTValidateFilter(AuthenticationManager authManager) {
		super(authManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
										HttpServletResponse response, FilterChain chain)
											throws IOException, ServletException {
		String atribute = response.getHeader(HEADER_ATRIBUTE);
		
		if (atribute == null) {
			chain.doFilter(request, response);
			return;
		}
		
		if(!atribute.startsWith(HEADER_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = atribute.replace(HEADER_PREFIX, "");
		
		UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(token);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token ) {
		String user = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD))
				.build()
				.verify(token)
				.getAlgorithm();
		
		if(user == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	}

}









