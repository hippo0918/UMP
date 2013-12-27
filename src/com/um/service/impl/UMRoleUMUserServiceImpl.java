package com.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.um.dao.IUMRoleUMUserDAO;
import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.service.IUMRoleUMUserService;

@Service(IUMRoleUMUserService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMRoleUMUserServiceImpl implements IUMRoleUMUserService {

	@Resource(name=IUMRoleUMUserDAO.SERVICE_NAME)
	private IUMRoleUMUserDAO dao;
	
	/**  
	* @Name: save
	* @Description: 插入关系对象
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleUMUser:关系POJO模型
	* @Return: 无
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(UMRoleUMUser entity) {
		// TODO Auto-generated method stub
		dao.makePersistent(entity, false);
	}

	/**  
	* @Name: findUsersByRole
	* @Description: 根据角色查找自己所关联的员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMRoleDto:角色域模型
	* @Return: List<UMUserDto> : 该角色的员工集合
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<UMUserDTO> findUsersByRole(UMRoleDTO roleDto) {
		// TODO Auto-generated method stub
		return dao.findUsersByRoleId(roleDto.getId());
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
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<UMRoleDTO> findRolesByUser(UMUserDTO userDto) {
		return dao.finRolesByUserId(userDto.getId());
	}

}
