package com.um.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.um.dao.IUMMenuDAO;
import com.um.dao.IUMMenuUMPermissionDAO;
import com.um.dao.IUMPermissionDAO;
import com.um.dao.IUMRoleDAO;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.dao.IUMUserDAO;
import com.um.domain.UMMenu;
import com.um.domain.UMPermission;
import com.um.domain.UMRole;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMMenuDTO;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;

@Service(ServiceUtil.SERVICE_NAME)
public class ServiceUtil {

	public static final String SERVICE_NAME = "com.um.util.ServiceUtil";
	
	@Resource(name = IUMRoleUMUserDAO.SERVICE_NAME)
	private IUMRoleUMUserDAO role_user_dao;
	
	@Resource(name = IUMUserDAO.SERVICE_NAME)
	private IUMUserDAO user_dao;
	
	@Resource(name = IUMRoleDAO.SERVICE_NAME)
	private IUMRoleDAO role_dao;
	
	@Resource(name = IUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMMenuUMPermissionDAO menu_permission_dao;
	
	@Resource(name = IUMRoleUMMenuUMPermissionDAO.SERVICE_NAME)
	private IUMRoleUMMenuUMPermissionDAO role_menu_permission_dao;
	
	@Resource(name = IUMRoleUMMenuDAO.SERVICE_NAME)
	private IUMRoleUMMenuDAO role_menu_dao;
	
	@Resource(name = IUMMenuDAO.SERVICE_NAME)
	private IUMMenuDAO menu_dao;
	
	@Resource(name = IUMPermissionDAO.SERVICE_NAME)
	private IUMPermissionDAO permission_dao;	
	
	/**
	 * 用户--角色--菜单--权限
	 * 根据用户id查询对应的角色，该角色对应的菜单，以及该角色在此菜单下的权限
	 * 
	 * */
	public UMUserDTO getUserRoleMenuPerByUserId(String userId) {
		UMUserDTO userDto = new UMUserDTO();
		UMUser user = user_dao.getBeanByID(UMUser.BEAN_NAME, userId, false);
		BeanUtils.copyProperties(user, userDto);
		
		Set<UMRole> roles = new HashSet<UMRole>();
		if(userDto.getAdmin().equalsIgnoreCase("NO")) {
			List<UMRoleDTO> roleDtos = new ArrayList<UMRoleDTO>();
			roleDtos = role_user_dao.finRolesByUserId(userId);
			for(UMRoleDTO _roleDto : roleDtos) {
				UMRole r = new UMRole();
				BeanUtils.copyProperties(_roleDto, r);
				roles.add(r);
			}
		} else {
			UMRole r = new UMRole();
			r.setValidate(true);
			roles = new HashSet<UMRole>(role_dao.getBeansByBean(r, MatchMode.EXACT));
		}
		Iterator<UMRole> i = roles.iterator();
		while(i.hasNext()) {
			UMRole _r = i.next();
			UMRoleDTO _roleDto = new UMRoleDTO();
			_roleDto = getRoleMenuPerByRoleId(_r.getId());
			userDto.getRoleDtos().add(_roleDto);
			i.remove();
		}
		return userDto;
	}
	
	public UMRoleDTO getRoleMenuPerByRoleId(String roleId) {
		UMRole _r = role_dao.getBeanByID(UMRole.BEAN_NAME, roleId, false);
		UMRoleDTO _roleDto = new UMRoleDTO();
		BeanUtils.copyProperties(_r, _roleDto);
		
		ArrayList<String> paraList = new ArrayList<String>();
		paraList.add(roleId);
		List<UMMenu> menus = (List<UMMenu>) menu_dao.getBeansByParams("menu_getRoleMenuPerByRoleId", paraList);
		paraList.clear();
		List<UMMenuDTO> menusDto = new ArrayList<UMMenuDTO>();
		for(UMMenu m : menus) {
			UMMenuDTO menuDto = new UMMenuDTO();
			BeanUtils.copyProperties(m, menuDto);
			paraList.clear();
			paraList.add(menuDto.getId());
			paraList.add(roleId);
			List<UMPermission> permissions = (List<UMPermission>) permission_dao.getBeansByParams("permission_getRoleMenuPerByRoleId", paraList);
			menuDto.setPermissions(new HashSet<UMPermission>(permissions));
			menusDto.add(menuDto);
		}
		_roleDto.setMenus(new HashSet<UMMenuDTO>(menusDto));
		return _roleDto;
	}
}
