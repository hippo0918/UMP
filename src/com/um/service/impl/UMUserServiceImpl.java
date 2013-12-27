package com.um.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.BeanUtils;
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
import com.um.domain.UMRole;
import com.um.domain.UMRoleUMUser;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.exception.UMUserException;
import com.um.service.IUMUserService;
import com.um.util.MD5;
import com.um.util.ServiceUtil;
import com.um.util.UserNoCreateUtil;
import com.um.util.json.GsonUtil;

@Service(IUMUserService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMUserServiceImpl implements IUMUserService {

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
	
	
	/**  
	* @Name: save
	* @Description: 增加员工并保存
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMUserDto:员工域模型
	* @Return: Serializable：新插入员工的id主键
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public Serializable save(UMUserDTO userDto) {
		String password = userDto.getPassword();
		String passwordAgain = userDto.getPasswordAgain();
		String confirmPassword = "123456";
		//判断两次密码是否相等
		if(null != passwordAgain && !"".equals(passwordAgain)) {
			if(!password.equals(passwordAgain)) {
				return null;
			} else {
				confirmPassword = MD5.getMD5Instace().getMD5ofStr(password);
			}
		}
		UMUser user = new UMUser();
		user.setCreateDate(new Date());
		user.setGender(userDto.getGender());
		user.setName(userDto.getName());
		user.setUserno(userDto.getUserno());
		user.setPassword(confirmPassword);
		user.setAdmin(userDto.getAdmin());
		user_dao.makePersistent(user, true);
		if(userDto.getRoleDtos() != null && userDto.getRoleDtos().size() != 0) {
			//roles_id[0] ="123,123"
			for(UMRoleDTO roleDto : userDto.getRoleDtos()) {
				UMRole role = new UMRole();
				role.setId(roleDto.getId());
				UMRoleUMUser role_user = new UMRoleUMUser();
				role_user.setRole(role);
				role_user.setUser(user);
				role_user_dao.makePersistent(role_user, false);
			}
		}
		return user.getId();
	}

	/**  
	* @Name: find
	* @Description: 获取员工信息列表
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: EasyuiDataGridDto:存放pageNo，pageSize, UMUserDto:搜索员工的过滤条件
	* @Return: EasyuiDataGridJsonDto<UMUserDto>：符合EasyuiDatagrid格式的数据:{total:12,rows:[{},{}]}
	*/
	@Override
	@Transactional(readOnly=true)
	public DataGridJsonDTO<UMUserDTO> find(DataGridDTO datagrid, UMUserDTO userDto) {
		UMUser u = new UMUser();
		BeanUtils.copyProperties(userDto, u);
		u.setValidate(true);
		DataGridJsonDTO<UMUserDTO> dg = new DataGridJsonDTO<UMUserDTO>();
		DataGridJsonDTO<UMUser> _dg = user_dao.getBeansByBeanForPager(u, MatchMode.ANYWHERE, datagrid, false);
		for(UMUser r : _dg.getRows()) {
			UMUserDTO _u = new UMUserDTO();
			BeanUtils.copyProperties(r, _u);
			//此员工关联的角色
			Set<UMRoleDTO> roles = new HashSet<UMRoleDTO>(role_user_dao.finRolesByUserId(r.getId()));
			_u.setRoleDtos(roles);
			dg.getRows().add(_u);
		}
		dg.setTotal(_dg.getTotal());
		return dg;
	
	}

	/**  
	* @Name: findByID
	* @Description: 通过id查找员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: String：员工的id主键
	* @Return: 返回员工域模型
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public UMUserDTO findByID(String id) {
		UMUser user = user_dao.getBeanByID(UMUser.BEAN_NAME, id, false);
		UMUserDTO userDto = new UMUserDTO();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}


	/**  
	* @Name: update
	* @Description: 通过id查找员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: UMUserDto：员工的域模型(String id,String name,String userno,List<UMRoleDto> roleDtos(关联角色集合))
	* @Return: boolean：判断插入是否成功
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean update(UMUserDTO userDto) {
		// TODO Auto-generated method stub
		Set<UMRoleDTO> roleDtos = userDto.getRoleDtos();
		//关联的 user
		UMUser user = user_dao.getBeanByID(UMUser.BEAN_NAME, userDto.getId(), false);
		if(!user.getGender().equals(userDto.getGender())) {
			user.setGender(userDto.getGender());
		}
		if(!user.getName().equals(userDto.getName())) {
			user.setName(userDto.getName());
		}
		if(!user.getUserno().equals(userDto.getUserno())) {
			user.setUserno(userDto.getUserno());
		}
		String password = userDto.getPassword();
		String passwordAgain = userDto.getPasswordAgain();
		String confirmPassword = "123456";
		//判断两次密码是否相等
		if(null != passwordAgain && !"".equals(passwordAgain)) {
			if(!password.equals(passwordAgain)) {
				return false;
			} else {
				confirmPassword = MD5.getMD5Instace().getMD5ofStr(password);
			}
		} else {
			confirmPassword = user.getPassword();
		}
		user.setPassword(confirmPassword);
		//先把此员工在关系表中的关联数据删掉
		Collection<UMRoleUMUser> role_users = user.getUmRoleumUser();
		for(UMRoleUMUser m : role_users) {
			role_user_dao.makeTransient(m);
		}
		for(UMRoleDTO roleDto : roleDtos) {
			UMRoleUMUser _role_user = new UMRoleUMUser();
			UMRole role = new UMRole();
			role.setId(roleDto.getId());
			_role_user.setRole(role);
			_role_user.setUser(user);
			//再把关联添加到关系表里面
			role_user_dao.makePersistent(_role_user, true);
		}
		//更新员工信息
		user_dao.makePersistent(user, true);
		return true;
	}

	/**  
	* @Name: delete
	* @Description: 根据员工的id删除员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: String[]:用于集合id
	* @Return: int:返回删除行数
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(String[] ids) {
		//删除此员工在关系表中的数据
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("ids", ids);
		role_user_dao.deleteOrUpdateByParams("deleteRoleUserMappingByUserId", paraMap);
		paraMap.clear();
		//删除此员工在关系表里面的数据 
		user_dao.deleteByIDs(UMUser.BEAN_NAME, ids);
		return ids.length;
	}

	
	/**  
	* @Name: findLastUserno
	* @Description: 查找公司最后一名员工的工号
	* @Author: 李泽彬
	* @Version: V1.00
	* @Create Date: 2013-07-05
	* @Parameters: 无
	* @Return: 最后入职的员工userno
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public String findLastUserno() {
		String userno = UserNoCreateUtil.COMPANY_NAME + UserNoCreateUtil.DEPARTMENT_NO + 1;
		List<UMUser> users = new ArrayList<UMUser>();
		user_dao.addOrder(org.hibernate.criterion.Order.desc("createDate"));
		UMUser _u = new UMUser();
		_u.setValidate(true);
		users = (List<UMUser>) user_dao.getBeansByBean(_u, MatchMode.ANYWHERE);
		if(users.size() != 0) {
			userno = users.get(0).getUserno();
			userno = UserNoCreateUtil.getInstance().createUserno(userno);
		}
		return userno;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<UMUser> findAll() {
		List<UMUser> list = (List<UMUser>) user_dao.getBeansByBean(new UMUser(), MatchMode.ANYWHERE);
		return list;
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=true)
	public UMUserDTO login(UMUserDTO userDto) throws UMUserException {
		String userno = userDto.getUserno();
		String password= userDto.getPassword();
		UMUserDTO _userDto = null;
		if(StringUtils.isNotBlank(userno) && StringUtils.isNotBlank(password)) {
			UMUser u = new UMUser();
			u.setUserno(userno);
			//是否有该用户名的用户
			List<UMUser> users = (List<UMUser>) user_dao.getBeansByBean(u, MatchMode.EXACT);
			if(users.size() == 0) {
				throw new UMUserException("此工号的员工不存在！");
			}
			if(users != null && users.size() != 0) {
				u = null;
				for(UMUser _u : users) {
					if(_u.getPassword().equals(MD5.getMD5Instace().getMD5ofStr(password))) {//密码正确
						if(_u.getAdmin().equalsIgnoreCase(userDto.getAdmin())) {
							ArrayList<String> userId = new ArrayList<String>();
							//userId.add(_u.getId());
Object[] objects = user_dao.getObjectsByParams("testGetObjectsByParams1", userId);
							u = _u;
							break;
						} else {
							throw new UMUserException("用户角色选择错误！");
						}
					}
				}
				if(u != null) {
					_userDto = new UMUserDTO();
					BeanUtils.copyProperties(u, _userDto);
				} else {
					throw new UMUserException("密码错误！");
				}
				
				//加载此用户的角色,菜单，权限
				_userDto = serviceUtil.getUserRoleMenuPerByUserId(_userDto.getId());
			} 
		}
		return _userDto;
	}

	@Override
	public UMUserDTO getUserRoleMenuPer(String userId) {
		UMUserDTO _userDto = serviceUtil.getUserRoleMenuPerByUserId(userId);
		return _userDto;
	}

	@Override
	public DataGridJsonDTO<UMUserDTO> findByRoleIds(DataGridDTO datagrid,
			String... ids) {
		DataGridJsonDTO<UMUserDTO> dg = new DataGridJsonDTO<UMUserDTO>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("ids", ids);
		List<UMUser> rows = new ArrayList<UMUser>(user_dao.getBeansByParamsForPager("queryUserByRoleIds", paraMap, datagrid));
		long count = user_dao.getBeansCount("queryUserByRoleIdsCount", paraMap);
		for(UMUser r : rows) {
			UMUserDTO _u = new UMUserDTO();
			BeanUtils.copyProperties(r, _u);
			//此员工关联的角色
			Set<UMRoleDTO> roles = new HashSet<UMRoleDTO>(role_user_dao.finRolesByUserId(r.getId()));
			_u.setRoleDtos(roles);
			dg.getRows().add(_u);
		}
		dg.setTotal(count);
		return dg;
	};
}
