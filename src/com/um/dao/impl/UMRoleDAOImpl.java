package com.um.dao.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.common.easyui.datagrid.DataGridDAOImpl;
import com.um.dao.IUMRoleDAO;
import com.um.domain.UMRole;

@Repository(IUMRoleDAO.SERVICE_NAME)
public class UMRoleDAOImpl extends DataGridDAOImpl<UMRole> implements IUMRoleDAO {
	
	/**  
	* @Name: findByProperTies
	* @Description: 根据页面传过来的角色姓名查找对应的角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRole role:包含角色名称
	* @Return: UMRole:若存在返回实际对象，不存在返回null
	*/
	public UMRole findRoleByName(UMRole role) {
		UMRole _role = null;
		//员工代理
		Collection<UMRole> roles = new ArrayList<UMRole>();
		roles = this.getBeansByBean(role, MatchMode.EXACT);
		if(roles.size() != 0 && roles.size() == 1) {
			//有此名称角色，不允许插入
			Iterator<UMRole> i = roles.iterator();
			_role = i.next();
		}
		return _role;
		
	}
}
