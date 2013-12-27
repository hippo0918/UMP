package com.um.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.easyui.datagrid.DataGridDAOImpl;
import com.um.dao.IUMUserUMMenuUMPermissionDAO;
import com.um.domain.UMUserUMMenuUMPermission;

@Repository(IUMUserUMMenuUMPermissionDAO.SERVICE_NAME)
public class UMUserUMMenuUMPermissionDAOImpl extends DataGridDAOImpl<UMUserUMMenuUMPermission> implements
		IUMUserUMMenuUMPermissionDAO {
	
}
