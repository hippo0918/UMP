package com.um.dao;

import java.util.List;

import com.um.domain.UMMenu;
import com.um.domain.UMRoleUMMenu;

public interface IUMRoleUMMenuDAO extends IBaseDAO<UMRoleUMMenu> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMRoleUMMenuDAOImpl";
	
	public List<UMMenu> findMenusByRoleIds(String[] roleIds);
	
	public boolean updateOrSaveUMRoleUMMenuByUMRole(String roleId, String menusId);
}
