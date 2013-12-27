package com.um.dao;

import com.common.easyui.datagrid.IDataGridDAO;
import com.um.domain.UMMenuUMPermission;

public interface IUMMenuUMPermissionDAO  extends IDataGridDAO<UMMenuUMPermission> {

	public static final String SERVICE_NAME = "com.um.dao.impl.UMMenuUMPermissionDAOImpl";
}
