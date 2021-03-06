package com.zhaoqi.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryCondition {

	private String username;
	@ApiModelProperty(value="起始年龄")
	private String age;
	@ApiModelProperty(value="终止年龄")
	private String ageTo;
	private String xxx;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAgeTo() {
		return ageTo;
	}
	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}
	public String getXxx() {
		return xxx;
	}
	public void setXxx(String xxx) {
		this.xxx = xxx;
	}
	
}
