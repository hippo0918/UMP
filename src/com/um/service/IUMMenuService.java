package com.um.service;

import java.io.Serializable;
import java.util.Collection;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridJsonDTO;
import com.google.gson.JsonArray;
import com.um.domain.UMMenu;

public interface IUMMenuService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMMenuServiceImpl";
	
	public DataGridJsonDTO<UMMenu> find(UMMenu umMenuQUeryModel, DataGridDTO datagrid);
	
	public UMMenu findByID(String id);
	
	public Collection<UMMenu> getMenusForSelect();
	
	public Serializable save(UMMenu menu);
	
	public int delete(String[] ids);
	
	public boolean update(UMMenu menu); 
	
	public JsonArray findPermissionByMenuId(String menuId);
	
	public boolean saveMenuPermission(String menuId, String[] permissionIds);
	
}
