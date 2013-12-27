package com.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.domain.UMMenu;
import com.um.service.IUMRoleUMMenuService;
import com.um.util.json.GsonUtil;

@Service(IUMRoleUMMenuService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMRoleUMMenuServiceImpl implements IUMRoleUMMenuService {

	@Resource(name = IUMRoleUMMenuDAO.SERVICE_NAME)
	private IUMRoleUMMenuDAO role_menu_dao;
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public JsonArray getMenusByRoles(String[] ids) {
		List<UMMenu> menuDtos = role_menu_dao.findMenusByRoleIds(ids);
		return GsonUtil.parseJsonArray(menuDtos);
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean updateOrSaveUMRoleUMMenuByUMRole(String roleId, String menusId) {
		return role_menu_dao.updateOrSaveUMRoleUMMenuByUMRole(roleId, menusId);
	}

}
