package com.zhaoqi.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhaoqi.validator.MyConstraint;

import io.swagger.annotations.ApiModelProperty;

public class User {

	public interface UserSimpleView{};
	
	public interface UserDetalView extends UserSimpleView {} ;
	
	@ApiModelProperty(value="id")
	private String id;
	
	@MyConstraint(message="这是一个测试")
	@ApiModelProperty(value="用户名称")
	private String username;
	
	@NotBlank(message="密码不能为空")
	@ApiModelProperty(value="密码")
	private String password;
	
	@Past(message="生日必须为过去的时间")
	private Date birthday;

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonView(UserDetalView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
}
