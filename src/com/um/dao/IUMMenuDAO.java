package com.um.dao;

import com.common.easyui.IDataGridDAO;
import com.um.domain.UMMenu;

public interface IUMMenuDAO extends IDataGridDAO<UMMenu> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMMenuDAOImpl";
}
