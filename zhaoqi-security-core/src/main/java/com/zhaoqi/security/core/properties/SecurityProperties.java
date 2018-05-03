package com.zhaoqi.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="zhaoqi.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();

	private ValidateCodePropertites code =  new ValidateCodePropertites();
	
	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodePropertites getCode() {
		return code;
	}

	public void setCode(ValidateCodePropertites code) {
		this.code = code;
	}

	
	
}
