package com.um.dao.impl.junit;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.um.domain.UMRole;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.service.IUMRoleService;

public class TestRoleService {

	private static IUMRoleService service;
	
	static {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-hibernate.xml");
		//获取Dao的实现类bean
		service = (IUMRoleService)ac.getBean(IUMRoleService.SERVICE_NAME);
	}
	
	public void test_save() {
		UMRole role = new UMRole();
		role.setDescription("角色006");
		role.setName("角色名称 006");
		role.setValidate(true);
		//service.save(role);
	}
	
	
	public void find() {
		List<UMRoleDTO> role = new ArrayList<UMRoleDTO>();
		role = service.find();
		System.out.println(role.get(0).getName());
	}
	
	@Test
	public void test() {
		String[] a = new String[]{};
		String b = new String();
		System.out.println(a.getClass());
		System.out.println(b.getClass());
	}
}
