package com.zhaoqi.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhaoqi.dto.User;
import com.zhaoqi.dto.User.UserDetalView;
import com.zhaoqi.dto.User.UserSimpleView;
import com.zhaoqi.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	
	@GetMapping
	@JsonView(UserSimpleView.class)
	@ApiOperation(value="查询所有用户")
	public List<User> query(UserQueryCondition userQueryCondition,Pageable pageable){
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	
	@GetMapping("/{id:\\d+}")
	@JsonView(UserDetalView.class)
	@ApiOperation(value="查询用户")
	public User getInfo(@PathVariable  String id){
		//throw new UserNotExistException(id);
		User user = new User();
		user.setUsername("tom");
		return user;
	}
	
	@PostMapping
	//@JsonView(UserSimpleView.class)
	@ApiOperation(value="新建用户")
	public User create(@Valid @RequestBody User user ,BindingResult erros){
		
		if (erros.hasErrors()) {
			erros.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
		}
		
		System.out.println(user.getBirthday());
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		user.setId("1");
		return user;
	}
	
	@PutMapping("/{id:\\d+}")
	@ApiOperation(value="修改用户")
	public User update(@Valid @RequestBody User user ,BindingResult erros){
		
		if (erros.hasErrors()) {
			erros.getAllErrors().stream().forEach(error -> {
				FieldError fieldError = (FieldError) error;
				String message = fieldError.getField() + error.getDefaultMessage();
				System.out.println(message);
			});
		}
		
		System.out.println(user.getBirthday());
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		user.setId("1");
		return user;
	}
	
	@DeleteMapping("/{id:\\d+}")
	@ApiOperation(value="删除用户")
	public void delete(@ApiParam(value="用户id") @PathVariable String id) {
		
		System.out.println(id);
	}
	
	@GetMapping("/me1")
	@ApiOperation(value="获取用户名方式一")
	private Object getName1() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	//MVC自动封装了
	@GetMapping("/me2")
	@ApiOperation(value="获取用户名方式二")
	private Object getName2(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("/me3")
	@ApiOperation(value="获取用户名方式三")
	private Object getName3(@AuthenticationPrincipal UserDetails user) {
		return user;
	}
	
	
}
