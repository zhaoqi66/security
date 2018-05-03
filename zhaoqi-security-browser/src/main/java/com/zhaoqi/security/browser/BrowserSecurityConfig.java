package com.zhaoqi.security.browser;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.zhaoqi.security.core.properties.SecurityProperties;
import com.zhaoqi.security.core.validate.code.ValidateCodeFilter;

@Component
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private SecurityProperties secutiryProperties;
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenctiationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenctiationFailureHandler);
		
		
		http
			.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.formLogin()
			.loginPage("/suthentication/require")    
			.loginProcessingUrl("/authentication/form")  
			.successHandler(imoocAuthenticationSuccessHandler)
			.failureHandler(imoocAuthenctiationFailureHandler)
	//		http.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers("/suthentication/require",
					secutiryProperties.getBrowser().getLoginPage(),
					"/code/image").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable();
	}

	
	
}
