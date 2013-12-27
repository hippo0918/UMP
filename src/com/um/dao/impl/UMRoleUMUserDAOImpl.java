package com.um.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.common.easyui.DataGridDAOImpl;
import com.common.easyui.IDataGridDAO;
import com.um.dao.IUMRoleUMUserDAO;
import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;

@Repository(IUMRoleUMUserDAO.SERVICE_NAME)
public class UMRoleUMUserDAOImpl extends DataGridDAOImpl<UMRoleUMUser> implements IUMRoleUMUserDAO,IDataGridDAO<UMRoleUMUser> {

	/**  
	* @Name: findUsersByRole
	* @Description: 根据角色id查找自己所关联的员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleDto:角色域模型(String id)
	* @Return: List<UMUserDto> : 该角色的员工集合
	*/
	@Override
	public List<UMUserDTO> findUsersByRoleId(String roleId) {
		// TODO Auto-generated method stub
		Query q = getCurrentSession().createQuery("from UMUser t where exists " +
				"(select t1.user.id from " +
				"UMRoleUMUser t1 where role.id = :id and t.id = t1.user.id)");
		q.setParameter("id", roleId);
		List<UMUser> users = q.list();
		List<UMUserDTO> userDtos = new ArrayList<UMUserDTO>();
		for(UMUser u : users) {
			UMUserDTO _u = new UMUserDTO();
			//属性拷贝
			BeanUtils.copyProperties(u, _u);
			userDtos.add(_u);
		}
		return userDtos;
	}

	/**  
	* @Name: findRolesByUser
	* @Description: 根据员工的id查找自己所属的角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMUserDto:员工域模型
	* @Return: List<UMRoleDto>:角色域模型集合
	*/
	@Override
	public List<UMRoleDTO> finRolesByUserId(String userId) {
		// TODO Auto-generated method stub
		Query q = this.getCurrentSession().createQuery("from UMRole t where exists " +
				"(select t1.role.id from UMRoleUMUser t1 " +
				"where t1.role.id = t.id and t1.user.id = :id)");
		q.setParameter("id", userId);
		List<UMRole> roles = q.list();
		List<UMRoleDTO> roleDtos = new ArrayList<UMRoleDTO>();
		for(UMRole r : roles) {
			UMRoleDTO _r = new UMRoleDTO();
			BeanUtils.copyProperties(r, _r);
			roleDtos.add(_r);
		}
		return roleDtos;
	}

}
