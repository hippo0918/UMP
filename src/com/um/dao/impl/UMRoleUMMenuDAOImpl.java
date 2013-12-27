package com.um.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.common.easyui.DataGridDAOImpl;
import com.um.dao.IUMMenuDAO;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.domain.UMMenu;
import com.um.domain.UMRoleUMMenu;
import com.um.domain.UMRoleUMMenuUMPermission;
import com.um.exception.DaoException;
import com.um.util.StringUtil;

@Repository(IUMRoleUMMenuDAO.SERVICE_NAME)
public class UMRoleUMMenuDAOImpl extends DataGridDAOImpl<UMRoleUMMenu> implements
		IUMRoleUMMenuDAO {

	@Resource(name = IUMRoleUMMenuDAO.SERVICE_NAME)
	private IUMRoleUMMenuDAO role_menu_dao;
	
	@Resource(name = IUMMenuDAO.SERVICE_NAME)
	private IUMMenuDAO menu_dao;
	
	@Resource(name = IUMRoleUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMRoleUMMenuUMPermissionDAO role_menu_permission_dao;
	
	@Override
	public List<UMMenu> findMenusByRoleIds(String[] roleIds) {
		String[] ids = new String[roleIds.length];
		for(int i=0; i<roleIds.length; i++) {
			ids[i] = "'" + roleIds[i] + "'";
		}
		String _ids = StringUtil.arrayToString(ids, ",");
		Query q = getCurrentSession().createQuery("from UMMenu t where exists " +
				"(select t1.menuId from " +
				"UMRoleUMMenu t1 where t1.roleId in ("+_ids+") and t.id = t1.menuId)");
		List<UMMenu> menus = q.list();
		return menus;
	}

	@Override
	public boolean updateOrSaveUMRoleUMMenuByUMRole(String roleId, String menusId) {
		boolean flag = true;
		List<UMRoleUMMenu> _role_menu = new ArrayList<UMRoleUMMenu>();
		Query q = getCurrentSession().createQuery("from UMRoleUMMenu m where m.roleId = " + "'" + roleId + "'");
		_role_menu = q.list();
		//删除此角色在此菜单下的权限映射
		for(UMRoleUMMenu rm : _role_menu) {
			UMRoleUMMenuUMPermission rmp = new UMRoleUMMenuUMPermission();
			rmp.setRoleId(roleId);
			List<UMRoleUMMenuUMPermission> rmpList = (List<UMRoleUMMenuUMPermission>) role_menu_permission_dao.getBeansByBean(rmp, MatchMode.EXACT);
			role_menu_permission_dao.deleteByCollection(rmpList);
		}
		//删除此角色关联的菜单，重新插入
		deleteByCollection(_role_menu);
		
		_role_menu.clear();
		try {
			for(String menuId : menusId.split(",")) {
				UMRoleUMMenu _m = new UMRoleUMMenu();
				_m.setMenuId(menuId);
				_m.setRoleId(roleId);
				makePersistent(_m, false);
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			flag = false;
			throw new DaoException("保存角色关联菜单操作发生错误！"
					+ ex.getMessage());
		}
		return flag;
	}
	
}
