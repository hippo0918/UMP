package com.um.service;

import java.util.List;

import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;

public interface IUMRoleUMUserService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMRoleUMUserServiceImpl";
	
	public void save(UMRoleUMUser entity);
	
	public List<UMUserDTO> findUsersByRole(UMRoleDTO roleDto);
	
	public List<UMRoleDTO> findRolesByUser(UMUserDTO userDto);
}
