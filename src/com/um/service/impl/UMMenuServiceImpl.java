package com.um.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridJsonDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.um.dao.IUMMenuDAO;
import com.um.dao.IUMMenuUMPermissionDAO;
import com.um.dao.IUMPermissionDAO;
import com.um.dao.IUMRoleDAO;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.domain.UMMenu;
import com.um.domain.UMMenuUMPermission;
import com.um.domain.UMPermission;
import com.um.domain.UMRoleUMMenu;
import com.um.domain.UMRoleUMMenuUMPermission;
import com.um.domain.model.dto.UMMenuDTO;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.exception.DaoException;
import com.um.service.IUMMenuService;
import com.um.util.ServiceUtil;
import com.um.util.TimeUtil;
import com.um.util.json.GsonUtil;

@Service(IUMMenuService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMMenuServiceImpl implements IUMMenuService {

	@Resource(name = ServiceUtil.SERVICE_NAME)
	ServiceUtil serviceUtil;
	
	@Resource(name = IUMMenuDAO.SERVICE_NAME)
	private IUMMenuDAO menu_dao;
	
	@Resource(name = IUMPermissionDAO.SERVICE_NAME)
	private IUMPermissionDAO permission_dao;
	
	@Resource(name=IUMRoleDAO.SERVICE_NAME)
	private IUMRoleDAO role_dao;
	
	@Resource(name = IUMRoleUMUserDAO.SERVICE_NAME)
	private IUMRoleUMUserDAO role_user_dao;
	
	@Resource(name = IUMRoleUMMenuDAO.SERVICE_NAME)
	private IUMRoleUMMenuDAO role_menu_dao;

	@Resource(name = IUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMMenuUMPermissionDAO menu_permission_dao;
	
	@Resource(name = IUMRoleUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMRoleUMMenuUMPermissionDAO role_menu_permission_dao;
	
	@Override
	public DataGridJsonDTO<UMMenu> find(UMMenu umMenuQUeryModel, DataGridDTO datagrid) {
		menu_dao.addOrder(org.hibernate.criterion.Order.desc("createDate"));
		menu_dao.addOrder(org.hibernate.criterion.Order.desc("createTime"));
		umMenuQUeryModel.setValidate("1");
		DataGridJsonDTO<UMMenu> dataGridJson = menu_dao.getBeansByBeanForPager(umMenuQUeryModel, MatchMode.ANYWHERE, datagrid, true);
		return dataGridJson;
	}

	@Override
	public UMMenu findByID(String id) {
		UMMenu menu = menu_dao.getBeanByID(UMMenu.BEAN_NAME, id, true);
		return menu;
	}

	@Override
	public Collection<UMMenu> getMenusForSelect() {
		UMMenu m = new UMMenu();
		m.setValidate("1");
		Collection<UMMenu> c = menu_dao.getBeansByBean(m, MatchMode.ANYWHERE);
		return c;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public Serializable save(UMMenu menu) {
		menu.setCreateDate(TimeUtil.getCurDate("yyyyMMdd"));
		menu.setCreateTime(TimeUtil.getCurDate("HHmmss"));
		menu_dao.makePersistent(menu, false);
		return menu.getId();
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(String[] ids) {
		int count = 0;
		try {
			UMMenu menu = null;
			for(String id : ids) {
				//删除菜单对应的角色
				UMRoleUMMenu rm = new UMRoleUMMenu();
				rm.setMenuId(id);
				List<UMRoleUMMenu> list = (List<UMRoleUMMenu>) role_menu_dao.getBeansByBean(rm, MatchMode.EXACT);
				role_menu_dao.deleteByCollection(list);
				
				//删除对应的权限
				UMMenuUMPermission mp = new UMMenuUMPermission();
				mp.setMenuId(id);
				List<UMMenuUMPermission> l = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(mp, MatchMode.EXACT);
				for(UMMenuUMPermission _mp : l) {
					//角色对应的权限映射
					UMRoleUMMenuUMPermission rmp = new UMRoleUMMenuUMPermission();
					rmp.setMenuPermissionId(_mp.getId());
					List<UMRoleUMMenuUMPermission> rmpList = (List<UMRoleUMMenuUMPermission>) role_menu_permission_dao.getBeansByBean(rmp, MatchMode.EXACT);
					role_menu_permission_dao.deleteByCollection(rmpList);
				}
				menu_permission_dao.deleteByCollection(l);
				
				//删除菜单
				menu = menu_dao.getBeanByID(UMMenu.BEAN_NAME, id, true);
				menu.setValidate("0");
				menu_dao.makePersistent(menu, false);
			}
			count = ids.length;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean update(UMMenu menu) {
		//String id = menu.getId();
		UMMenu _m = menu_dao.getBeanByID(UMMenu.BEAN_NAME, menu.getId(), true);
		_m.setIconCls(menu.getIconCls());
		_m.setName(menu.getName());
		_m.setPid(menu.getPid());
		_m.setUrl(menu.getUrl());
		
		//如果菜单的父菜单变化了，此菜单的所有与角色关联关系映射都要删除
		boolean flag = true;
		try {
			menu_dao.makePersistent(_m, true);
		} catch (DaoException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public JsonArray findPermissionByMenuId(String menuId) {
		List<UMPermission> checkedPer = permission_dao.findPermissionByMenuId(menuId);
		List<UMPermission> allPer = (List<UMPermission>) permission_dao.getBeansByBean(new UMPermission(), MatchMode.ANYWHERE);
		JsonArray retJsonArray = new JsonArray();
		JsonObject j = new JsonObject();
		for(UMPermission _allPer : allPer) {
			j = GsonUtil.parseJsonObject(_allPer);
			j.addProperty("checked", false);
			boolean flag = false;
			for(UMPermission _checkedPer : checkedPer) {
				if(_checkedPer.getId().equals(_allPer.getId())) {
					j.remove("checked");
					j.addProperty("checked", true);
					retJsonArray.add(j);
					flag = true;
				} 
			}
			if(!flag) {
				retJsonArray.add(j);
			}
		}
		return retJsonArray;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean saveMenuPermission(String menuId, String[] permissionIds) {
		//删除菜单的权限映射
		UMMenuUMPermission mp = new UMMenuUMPermission();
		mp.setMenuId(menuId);
		List<UMMenuUMPermission> mpList = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(mp, MatchMode.EXACT);
		//临时记录角色在此菜单下的权限
		Set<UMRoleDTO> roleDtos = new HashSet<UMRoleDTO>();
		boolean flag = true;
		try {
			//先删除所有角色所在菜单的权限
			for(UMMenuUMPermission _mp : mpList) {
				UMRoleUMMenuUMPermission _rmp = new UMRoleUMMenuUMPermission();
				_rmp.setMenuPermissionId(_mp.getId());
				List<UMRoleUMMenuUMPermission> _rmpList = (List<UMRoleUMMenuUMPermission>) role_menu_permission_dao.getBeansByBean(_rmp, MatchMode.EXACT);
				for(UMRoleUMMenuUMPermission rmp : _rmpList) {
					UMRoleDTO _r = serviceUtil.getRoleMenuPerByRoleId(rmp.getRoleId());
					roleDtos.add(_r);
				}
				role_menu_permission_dao.deleteByCollection(_rmpList);
			}
			menu_permission_dao.deleteByCollection(mpList);
			
			//再保存菜单现在关联的权限
			if(permissionIds != null && permissionIds.length != 0) {
				for(String permissionId : permissionIds) {
					UMMenuUMPermission _mp = new UMMenuUMPermission();
					_mp.setMenuId(menuId);
					_mp.setPermissionId(permissionId);
					menu_permission_dao.makePersistent(_mp, false);
					//恢复此角色在菜单下的权限
					List<UMRoleUMMenuUMPermission> rmpList = new ArrayList<UMRoleUMMenuUMPermission>();
					for(UMRoleDTO _r : roleDtos) {
						for(UMMenuDTO m : _r.getMenus()) {
							if(m.getId().equals(menuId)) {
								for(UMPermission p : m.getPermissions()) {
									if(p.getId().equals(permissionId)) {
										UMRoleUMMenuUMPermission rmp = new UMRoleUMMenuUMPermission();
										rmp.setMenuPermissionId(_mp.getId());
										rmp.setRoleId(_r.getId());
										rmpList.add(rmp);
									}
								}
							}
						}
					}
					role_menu_permission_dao.batchInsert(rmpList);
				}
			}
		} catch (DaoException e) {
			flag = false;
			e.printStackTrace();
		}
		
		return flag;
	}

}
