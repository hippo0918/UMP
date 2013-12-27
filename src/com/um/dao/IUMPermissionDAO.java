package com.um.dao;

import java.util.List;

import com.common.easyui.datagrid.IDataGridDAO;
import com.um.domain.UMPermission;

public interface IUMPermissionDAO extends IDataGridDAO<UMPermission> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMPermissionDAOImpl";
	
	public List<UMPermission> findPermissionByMenuId(String menuId); 
}
