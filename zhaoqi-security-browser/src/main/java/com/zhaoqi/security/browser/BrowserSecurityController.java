package com.zhaoqi.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoqi.security.browser.support.SimpleResponse;
import com.zhaoqi.security.core.properties.SecurityProperties;

@RestController
public class BrowserSecurityController {
	
	//日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	//安全缓存
	private RequestCache requestCache = new HttpSessionRequestCache();
	//spring重定向
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties secutiryProperties;
	
	
	
	//当需要身份认证时，跳转到这里
	@RequestMapping("/suthentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse RequiresAuthentication(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl =  savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是： "+targetUrl);
			redirectStrategy.sendRedirect(request, response, secutiryProperties.getBrowser().getLoginPage());
		}
		return new SimpleResponse("访问的服务需要认证，请引导用户到登陆页");
	}
}
