package com.um.dao;

import com.common.easyui.IDataGridDAO;
import com.um.domain.UMUser;

public interface IUMUserDAO extends IDataGridDAO<UMUser> {
	public static final String SERVICE_NAME = "com.um.dao.impl.UMUserDAOImpl";
	
	
}
