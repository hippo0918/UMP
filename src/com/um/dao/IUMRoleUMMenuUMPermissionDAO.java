package com.um.dao;

import com.common.easyui.IDataGridDAO;
import com.um.domain.UMRoleUMMenuUMPermission;


public interface IUMRoleUMMenuUMPermissionDAO extends IDataGridDAO<UMRoleUMMenuUMPermission> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMRoleUMMenuUMPermissionDAOImpl";
	
	//public List<UMRoleUMMenuUMPermission> findRoleMenuPermissionByRolesId(String[] rolesId);
}