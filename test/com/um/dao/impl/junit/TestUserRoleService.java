package com.um.dao.impl.junit;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.service.IUMRoleUMUserService;

public class TestUserRoleService {

	private static IUMRoleUMUserService service;
	
	static {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-hibernate.xml");
		//获取Dao的实现类bean
		service = (IUMRoleUMUserService)ac.getBean(IUMRoleUMUserService.SERVICE_NAME);
	}
	
	public void test_save() {
		UMRoleUMUser role_user = new UMRoleUMUser();
		UMUser user = new UMUser();
		user.setId("ff8080813f8d8ff3013f8d8ff5460000");
		UMRole role = new UMRole();
		role.setId("ff8080813f8d8bcb013f8d8bcde00000");
		role_user.setRole(role);
		role_user.setUser(user);
		service.save(role_user);
	}
	
	public void test_getUsersByRole() {
		UMRoleDTO roleDto = new UMRoleDTO();
		roleDto.setId("ff8080813f8d8ac2013f8d8ac4db0000");
		List<UMUserDTO> users = service.findUsersByRole(roleDto);
		for(UMUserDTO user : users) {
			System.out.println(user.getName());
		}
		
	}
	
	@Test
	public void test_getRolesByUser() {
		UMUserDTO userDto = new UMUserDTO();
		userDto.setId("ff8080813f8d8ec4013f8d8ec68c0000");
		List<UMRoleDTO> roles = service.findRolesByUser(userDto);
		for(UMRoleDTO role : roles) {
			System.out.println(role.getName());
		}
		
	}
	
	
}
