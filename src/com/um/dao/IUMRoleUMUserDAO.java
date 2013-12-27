package com.um.dao;

import java.util.List;

import com.common.easyui.IDataGridDAO;
import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;

public interface IUMRoleUMUserDAO extends IDataGridDAO<UMRoleUMUser> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMRoleUMUserDAOImpl";

	public List<UMUserDTO> findUsersByRoleId(String roleId);

	public List<UMRoleDTO> finRolesByUserId(String userId);
	
}
