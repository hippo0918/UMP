package com.um.service;

import com.google.gson.JsonArray;

public interface IUMRoleUMMenuService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMRoleUMMenuServiceImpl";
	
	public JsonArray getMenusByRoles(String[] ids);
	
	public boolean updateOrSaveUMRoleUMMenuByUMRole(String roleId, String menusId);
}
