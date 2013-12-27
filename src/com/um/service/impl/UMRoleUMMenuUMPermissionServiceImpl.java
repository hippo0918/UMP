package com.um.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.um.dao.IUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleDAO;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.domain.UMMenuUMPermission;
import com.um.domain.UMRoleUMMenuUMPermission;
import com.um.exception.DaoException;
import com.um.service.IUMRoleUMMenuUMPermissionService;
import com.um.util.ServiceUtil;
import com.um.util.json.GsonUtil;

@Service(IUMRoleUMMenuUMPermissionService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMRoleUMMenuUMPermissionServiceImpl implements
		IUMRoleUMMenuUMPermissionService {

	@Resource(name = ServiceUtil.SERVICE_NAME)
	ServiceUtil serviceUtil;
	
	@Resource(name = IUMRoleUMUserDAO.SERVICE_NAME)
	private IUMRoleUMUserDAO role_user_dao;
	
	@Resource(name=IUMRoleDAO.SERVICE_NAME)
	private IUMRoleDAO role_dao;
	
	@Resource(name = IUMRoleUMMenuDAO.SERVICE_NAME)
	private IUMRoleUMMenuDAO role_menu_dao;

	@Resource(name = IUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMMenuUMPermissionDAO menu_permission_dao;
	
	@Resource(name = IUMRoleUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMRoleUMMenuUMPermissionDAO role_menu_permission_dao;
	
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean saveRoleMenuPer(String roleId, String menuId,
			String[] permissionsId) {
		boolean flag = true;
		ArrayList<String> paraList = new ArrayList<String>();
		paraList.add(roleId);
		paraList.add(menuId);
		role_menu_permission_dao.executeUpdateHql("deleteMenuPerByRole", paraList);
		//此菜单下的所有权限
		List<UMRoleUMMenuUMPermission> s = new ArrayList<UMRoleUMMenuUMPermission>();
		if(permissionsId != null) {
			try {
				for(String _permissionId : permissionsId) {
					UMRoleUMMenuUMPermission rmp = new UMRoleUMMenuUMPermission();
					rmp.setRoleId(roleId);
					UMMenuUMPermission mp = new UMMenuUMPermission();
					mp.setMenuId(menuId);
					mp.setPermissionId(_permissionId);
					List<UMMenuUMPermission> _mpList = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(mp, MatchMode.EXACT);
					for(UMMenuUMPermission _mp : _mpList) {
						rmp.setMenuPermissionId(_mp.getId());
						s.add(rmp);
					}
				}
				role_menu_permission_dao.batchInsert(s);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			}
		}
		return flag;
	}
}
