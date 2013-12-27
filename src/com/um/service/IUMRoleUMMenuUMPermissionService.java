package com.um.service;

public interface IUMRoleUMMenuUMPermissionService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMRoleUMMenuUMPermissionServiceImpl";

	public boolean saveRoleMenuPer(String roleId, String menuId, String[] permissionsId);
}
