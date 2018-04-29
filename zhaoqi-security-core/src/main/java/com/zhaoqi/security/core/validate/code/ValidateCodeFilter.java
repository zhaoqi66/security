package com.zhaoqi.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zhaoqi.security.core.properties.SecurityProperties;



public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private Set<String> urls = new HashSet();
	
	private SecurityProperties securityProperties;
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		for (int i = 0; i < configUrls.length; i++) {
			urls.add(configUrls[i]);
		}
		urls.add("/authentication/form");
	}
	
	
	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		if (StringUtils.equals("/authentication/form", request.getRequestURI())
				&& StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
			try {
				validate(new ServletWebRequest(request));
			} catch (VlidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void validate(ServletWebRequest request) throws ServletException {
		
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,ValidateCodeController.SESSIOON_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		
		if (StringUtils.isBlank(codeInRequest)) {
			throw new VlidateCodeException("验证码的值不能为空");
		}
		
		if (codeInSession == null) {
			throw new VlidateCodeException("验证码不存在");
		}
		
		if (codeInSession.isExpried()){
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSIOON_KEY);
			throw new VlidateCodeException("验证码已过期");
		}
		
		if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)) {
			throw new VlidateCodeException("验证码不匹配");
		}
		
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSIOON_KEY);
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	
	
}
