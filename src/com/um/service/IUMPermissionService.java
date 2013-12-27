package com.um.service;

import java.io.Serializable;
import java.util.List;

import com.common.easyui.datagrid.DataGridDTO;
import com.common.easyui.datagrid.DataGridJsonDTO;
import com.um.domain.UMPermission;

public interface IUMPermissionService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMPermissionServiceImpl";

	public DataGridJsonDTO<UMPermission> find(UMPermission umPermission,
			DataGridDTO datagrid);
	
	public DataGridJsonDTO<UMPermission> findByMenuId(String menuId,
			DataGridDTO datagrid);
	
	public UMPermission findByID(String permissionId);
	
	public int delete(String[] ids);
	
	public Serializable save(UMPermission permission);
	
	public boolean update(UMPermission permission);
	
	public List<UMPermission> findPermissionByMenuId(String menuId); 
}
