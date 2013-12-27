package com.um.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.easyui.datagrid.DataGridDTO;
import com.common.easyui.datagrid.DataGridJsonDTO;
import com.um.dao.IUMMenuDAO;
import com.um.dao.IUMMenuUMPermissionDAO;
import com.um.dao.IUMPermissionDAO;
import com.um.dao.IUMRoleDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.dao.IUMUserDAO;
import com.um.domain.UMMenuUMPermission;
import com.um.domain.UMPermission;
import com.um.domain.UMRoleUMMenuUMPermission;
import com.um.exception.DaoException;
import com.um.service.IUMPermissionService;
import com.um.util.ServiceUtil;
import com.um.util.TimeUtil;

@Service(IUMPermissionService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMPermissionServiceImpl implements IUMPermissionService{

	
	@Resource(name = ServiceUtil.SERVICE_NAME)
	ServiceUtil serviceUtil;
	
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
	
	@Resource(name = IUMMenuDAO.SERVICE_NAME)
	private IUMMenuDAO menu_dao;
	
	@Resource(name = IUMPermissionDAO.SERVICE_NAME)
	private IUMPermissionDAO permission_dao;
	
	@Override
	public DataGridJsonDTO<UMPermission> find(UMPermission umPermission,
			DataGridDTO datagrid) {
		permission_dao.addOrder(org.hibernate.criterion.Order.desc("createDate"));
		permission_dao.addOrder(org.hibernate.criterion.Order.desc("createTime"));
		DataGridJsonDTO<UMPermission> dgJson = permission_dao.getBeansByBeanForPager(umPermission, MatchMode.ANYWHERE, datagrid, false);
		return dgJson;
	}

	@Override
	public UMPermission findByID(String permissionId) {
		// TODO Auto-generated method stub
		UMPermission permission = permission_dao.getBeanByID(UMPermission.BEAN_NAME, permissionId, false);
		return permission;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(String[] ids) {
		int count = 0;
		try {
			for(String id : ids) {
				//删除有此权限的映射
				UMMenuUMPermission mp = new UMMenuUMPermission();
				mp.setPermissionId(id);
				List<UMMenuUMPermission> mpList = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(mp, MatchMode.EXACT);
				for(UMMenuUMPermission _mp : mpList) {
					//角色对应的权限映射
					UMRoleUMMenuUMPermission rmp = new UMRoleUMMenuUMPermission();
					rmp.setMenuPermissionId(_mp.getId());
					List<UMRoleUMMenuUMPermission> rmpList = (List<UMRoleUMMenuUMPermission>) role_menu_permission_dao.getBeansByBean(rmp, MatchMode.EXACT);
					role_menu_permission_dao.deleteByCollection(rmpList);
				}
				menu_permission_dao.deleteByCollection(mpList);
				UMPermission p = permission_dao.getBeanByID(UMPermission.BEAN_NAME, id, false);
				p.setValidate(false);
				permission_dao.makePersistent(p, true);
			}
			count = ids.length;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public Serializable save(UMPermission permission) {
		String id = null;
		try {
			permission.setCreateDate(TimeUtil.getCurDate("yyyyMMdd"));
			permission.setCreateTime(TimeUtil.getCurDate("HHmmss"));
			permission.setValidate(true);
			permission_dao.makePersistent(permission, false);
			id = permission.getId();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean update(UMPermission permission) {
		boolean flag = true;
		try {
			UMPermission p = permission_dao.getBeanByID(UMPermission.BEAN_NAME, permission.getId(), true);
			p.setCode(permission.getCode());
			p.setDescription(permission.getDescription());
			p.setIconCls(permission.getIconCls());
			p.setName(permission.getName());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<UMPermission> findPermissionByMenuId(String menuId) {
		UMMenuUMPermission mp = new UMMenuUMPermission();
		mp.setMenuId(menuId);
		List<UMMenuUMPermission> mpList = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(mp, MatchMode.EXACT);
		List<UMPermission> pList = new ArrayList<UMPermission>();
		for(UMMenuUMPermission _mp : mpList) {
			UMPermission _p = permission_dao.getBeanByID(UMPermission.BEAN_NAME, _mp.getPermissionId(), false);
			pList.add(_p);
		}
		return pList;
	}

	@Override
	public DataGridJsonDTO<UMPermission> findByMenuId(String menuId,
			DataGridDTO datagrid) {
		UMMenuUMPermission bean =  new UMMenuUMPermission();
		bean.setMenuId(menuId);
		//DataGridJsonDTO<UMPermission> dgJson = menu_permission_dao.getBeansByBean(umMenuPermission, MatchMode.ANYWHERE);
		List<UMMenuUMPermission> list = (List<UMMenuUMPermission>) menu_permission_dao.getBeansByBean(bean, MatchMode.EXACT);
		List<UMPermission> rows = new LinkedList<UMPermission>();
		for(UMMenuUMPermission _mp : list) {
			UMPermission p = permission_dao.getBeanByID(UMPermission.BEAN_NAME, _mp.getPermissionId(), false);
			rows.add(p);
		}
		//构造Datagrid
		DataGridJsonDTO<UMPermission> dgJson = new DataGridJsonDTO<UMPermission>();
		dgJson.setRows(rows);
		dgJson.setTotal(rows.size());
		return dgJson;
	}

	
	
}
