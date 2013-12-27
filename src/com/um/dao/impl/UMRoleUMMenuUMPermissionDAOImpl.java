package com.um.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.easyui.datagrid.DataGridDAOImpl;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.domain.UMRoleUMMenuUMPermission;


@Repository(IUMRoleUMMenuUMPermissionDAO.SERVICE_NAME)
public class UMRoleUMMenuUMPermissionDAOImpl extends DataGridDAOImpl<UMRoleUMMenuUMPermission> implements
		IUMRoleUMMenuUMPermissionDAO {
	
}
