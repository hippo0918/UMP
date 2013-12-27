package com.um.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.easyui.DataGridDAOImpl;
import com.um.dao.IUMPermissionDAO;
import com.um.domain.UMPermission;

@Repository(IUMPermissionDAO.SERVICE_NAME)
public class UMPermissionDAOImpl extends DataGridDAOImpl<UMPermission> implements IUMPermissionDAO {

	@Override
	public List<UMPermission> findPermissionByMenuId(String menuId) {
		ArrayList<String> paraList = new ArrayList<String>();
		paraList.add(menuId);
		List<UMPermission> pList = (List<UMPermission>) getBeansByParams("findPermissionByMenuId", paraList);
		return pList;
	}

}
