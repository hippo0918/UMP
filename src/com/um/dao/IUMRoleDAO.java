package com.um.dao;


import com.common.easyui.datagrid.IDataGridDAO;
import com.um.domain.UMRole;


public interface IUMRoleDAO extends IDataGridDAO<UMRole> {
	
	public static final String SERVICE_NAME = "com.um.dao.impl.UMRoleDAOImpl";
	
	public UMRole findRoleByName(UMRole role);
		
}
