package com.um.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.easyui.datagrid.DataGridDTO;
import com.common.easyui.datagrid.DataGridJsonDTO;
import com.um.dao.IUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleDAO;
import com.um.dao.IUMRoleUMMenuDAO;
import com.um.dao.IUMRoleUMMenuUMPermissionDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.service.IUMRoleService;
import com.um.util.ServiceUtil;

@Service(IUMRoleService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMRoleServiceImpl implements IUMRoleService {
	
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
	
	/**  
	* @Name: save
	* @Description: 增加角色并保存
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleDto:角色域模型
	* @Return: Serializable：新插入角色的id主键
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public Serializable save(UMRoleDTO roleDto) {
		//实例化持久化 对象 
		UMRole role = new UMRole();
		role.setDescription(roleDto.getDescription());
		role.setName(roleDto.getName());
		role.setValidate(true);
		role_dao.makePersistent(role, false);
		return role.getId();
	}

	/**  
	* @Name: find
	* @Description: 获取所有角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: List<UMRoleDto>:返回所有角色
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<UMRoleDTO> find() {
		// TODO Auto-generated method stub
		List<UMRoleDTO> roleDtos = new ArrayList<UMRoleDTO>();
		Collection<UMRole> roles = role_dao.getBeansByBean(new UMRole(), MatchMode.ANYWHERE);
		for(UMRole r : roles) {
			//临时变量
			UMRoleDTO _roleDto = new UMRoleDTO();
			BeanUtils.copyProperties(r, _roleDto);
			roleDtos.add(_roleDto);
		}
		return roleDtos;
	}

	/**  
	* @Name: find
	* @Description: 获取角色信息列表(EasyuiDatagrid显示)
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: EasyuiDataGridDto:存放pageNo，pageSize, UMRoleDto:搜索角色的过滤条件
	* @Return: DataGridJsonDTO<UMRoleDto>：符合EasyuiDatagrid格式的数据:{total:12,rows:[{},{}]}
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public DataGridJsonDTO<UMRoleDTO> find(DataGridDTO dg, UMRoleDTO roleDto) {
		//对于一个字段的排序 
		if(dg.getOrder() != null && dg.getOrder().size() != 0) {
			Set<Map.Entry<String, String>> entrySet = dg.getOrder().entrySet();
			Iterator<Map.Entry<String, String>> i = entrySet.iterator();
			while(i.hasNext()) {
				Map.Entry<String, String> entry = i.next();
				String sortName = entry.getKey();
				String order = entry.getValue();
				if(StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(order)) {
					if("DESC".equalsIgnoreCase(order)) {
						role_dao.addOrder(Order.desc(sortName));
					} else if ("ASC".equalsIgnoreCase(order)) {
						role_dao.addOrder(Order.asc(sortName));
					}
				}
			}
		}
		//返回对象
		DataGridJsonDTO<UMRoleDTO> datagrid = new DataGridJsonDTO<UMRoleDTO>();
		UMRole umRole = new UMRole();
		BeanUtils.copyProperties(roleDto, umRole);
		umRole.setValidate(true);
		DataGridJsonDTO<UMRole> _datagrid = new DataGridJsonDTO<UMRole>();
		_datagrid = role_dao.getBeansByBeanForPager(umRole, MatchMode.ANYWHERE, dg, true);
		for(UMRole r : _datagrid.getRows()) {
			UMRoleDTO _r = new UMRoleDTO();
			BeanUtils.copyProperties(r, _r);
			datagrid.getRows().add(_r);
		}
		datagrid.setTotal(_datagrid.getTotal());
		return datagrid;
	}

	/**  
	* @Name: findByID
	* @Description: 通过id查找角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: String：角色的id主键
	* @Return: UMRoleDto：角色域模型
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public UMRoleDTO findByID(String id) {
		//获得持久化对象
		UMRole role = role_dao.getBeanByID(UMRole.BEAN_NAME, id, false);
		UMRoleDTO roleDto = new UMRoleDTO();
		BeanUtils.copyProperties(role, roleDto);
		return roleDto;
	}

	/**  
	* @Name: update
	* @Description: 通过id查找角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleDto：角色域模型
	* @Return: UMRole:若存在返回实际对象，不存在返回null
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public int update(UMRoleDTO roleDto) {
		//先检查此id的角色名称跟不同id的角色的名称是否有相同的，若有则不作修改
		UMRole role = new UMRole();
		role = role_dao.getBeanByID(UMRole.BEAN_NAME, roleDto.getId(), false);
		role.setId(roleDto.getId());
		role.setDescription(roleDto.getDescription());
		role.setName(roleDto.getName());
		role_dao.makePersistent(role, false);
		String roleId = role.getId();
		if(roleId != null && !"".equals(roleId)) {
			return 1;
		}
		return 0;
	}

	/**  
	* @Name: delete
	* @Description: 根据角色的id删除，先把关系表中的关联数据删除，再删除此角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleDto:角色域模型(存放角色id)
	* @Return: 无
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void delete(UMRoleDTO roleDto) {
		ArrayList<String> paraList = new ArrayList<String>();
		paraList.add(roleDto.getId());
		//先删除关系表中的角色数据 
		List<UMRoleUMUser> roles = (List<UMRoleUMUser>) role_user_dao.getBeansByParams("getRoleUserMapping", paraList);	//先删除关系表中的角色数据 
		for(UMRoleUMUser u : roles) {
			role_user_dao.makeTransient(u);
		}
		//删除角色可见菜单
		role_menu_dao.executeUpdateHql("deleteMenuByRoleId", paraList);
		//删除角色的权限
		role_menu_permission_dao.executeUpdateHql("deletePermissionByRoleId", paraList);
		UMRole umRole = new UMRole();
		umRole = role_dao.getBeanByID(UMRole.BEAN_NAME, roleDto.getId(), false);
		umRole.setValidate(false);
		role_dao.makePersistent(umRole, false);
	}
	
	/**  
	* @Name: findByProperTies
	* @Description: 根据页面传过来的角色姓名查找对应的角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* 	* @Parameters: UMRoleDTO roleDto:包含角色名称
	* @Return: UMRoleDTO:返回此名称的角色是否存在,null或者不为null但是id要跟域模型的id相同,则允许插入 
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public UMRoleDTO findRoleByName(UMRoleDTO roleDto) {
		UMRole role = new UMRole();
		BeanUtils.copyProperties(roleDto, role);
		role = role_dao.findRoleByName(role);
		//返回的角色域模型对象
		UMRoleDTO _roleDto = null;
		if(role != null) {
			_roleDto = new UMRoleDTO();
			BeanUtils.copyProperties(role, _roleDto);
			return _roleDto;
		}
		return _roleDto;
	}

	/**
	 * 树的数据结构返回，权限的放到attributes中
	 * */
	@Override
	public UMRoleDTO getRoleMenuPerByRoleId(String roleId) {
		UMRoleDTO roleDto = serviceUtil.getRoleMenuPerByRoleId(roleId);
		return roleDto;
	}

}
