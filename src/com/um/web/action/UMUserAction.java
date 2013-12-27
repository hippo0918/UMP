package com.um.web.action;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridDTOFactory;
import com.common.easyui.DataGridJsonDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.um.domain.UMUser;
import com.um.domain.model.dto.JsonDTO;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.exception.UMUserException;
import com.um.service.IUMRoleService;
import com.um.service.IUMRoleUMUserService;
import com.um.service.IUMUserService;
import com.um.util.json.GsonUtil;
import com.um.web.constant.ConstantValues;


@Controller("umUserAction")
@Scope("prototype")
public class UMUserAction extends BaseAction implements ModelDriven<UMUserDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//接收角色id（数组）
	private String[] roles_id;
	public String[] getRoles_id() {
		return roles_id;
	}

	public void setRoles_id(String[] roles_id) {
		this.roles_id = roles_id;
	}

	//接收列表显示的（page(页码),rows(每页显示条目数量),sortName(需要排序的字段名称),order(asc or desc)）
	private DataGridDTO datagridDto;
	
	//每次请求的动作
	private String action;
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private UMUserDTO userDto = new UMUserDTO();
	
	@Resource(name=IUMUserService.SERVICE_NAME)
	private IUMUserService user_service;
	
	@Resource(name=IUMRoleService.SERVICE_NAME)
	private IUMRoleService role_service;
	
	@Resource(name=IUMRoleUMUserService.SERVICE_NAME)
	private IUMRoleUMUserService role_user_service;
	
	@Override
	public UMUserDTO getModel() {
		return userDto;
	}
	
	/**  
	* @Name: index
	* @Description: 转发到员工页面
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: String:"index"
	*/
	public String index() {
		return "index";
	}
	
	/**  
	* @Name: query
	* @Description: 在员工页面显示员工列表
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无
	*/
	public String query() {
		//getListByRolesId();
		if(action != null && !"".equals(action) && "loadDataGrid_user".equals(action)) {
			datagridDto = DataGridDTOFactory.getInstance().getDataGridDTO(request);
			DataGridJsonDTO<UMUserDTO> dg = user_service.find(datagridDto, userDto);
			String json = GsonUtil.dataGridJsonToString(dg);
			writeJson(json);
		} else {
			writeJson("{total:0,rows:[]}");
		}
		return null;
	}
	
	/**  
	* @Name: edit
	* @Description: 在页面弹出编辑窗口，如果是保存员工就不加载员工信息，如果是更新的话，要通过员工id获取员工信息
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无
	*/
	public String doNotNeedSecurityEdit() {
		if("save".equals(action)) {
			//设置编辑页面的表单动作
			request.setAttribute(ConstantValues.ACTION,ConstantValues.ACTION_SAVE);
			//自动分配工号
			String userno = user_service.findLastUserno();
			UMUser _user = new UMUser();
			_user.setUserno(userno);
			//把user对象压到栈顶
			ActionContext.getContext().getValueStack().pop();
			ActionContext.getContext().getValueStack().push(_user);
		}
		if("update".equals(action)) {
			request.setAttribute(ConstantValues.ACTION, ConstantValues.ACTION_UPDATE);
			userDto = user_service.findByID(this.userDto.getId());
			//获取所有角色
			List<UMRoleDTO> roles = role_service.find();
			//此员工所属的角色添加selected:true属性,方便在页面的复选框显示,被选中角色
			JsonArray roles_json = new JsonArray();
			//此员工关联的角色
			List<UMRoleDTO> roleDtos = role_user_service.findRolesByUser(userDto);
			//是否找到匹配角色
			boolean a = false;
			for(UMRoleDTO role : roles) {
				for(int i=0; i<roleDtos.size(); i++) {
					if(roleDtos.get(i).getId().equals(role.getId())) {
						JsonObject json = GsonUtil.parseJsonObject(role);
						json.addProperty("selected", true);
						roles_json.add(json);
						a = true;
					}
				}
				if(!a) {
					roles_json.add(GsonUtil.parseJsonObject(role));
				} else {
					a = false;
				}
			}
			//直接转成字符串让jQuery解析成字符串
			request.setAttribute("roles_json", roles_json.toString());
			//把user对象压到栈顶
			ActionContext.getContext().getValueStack().pop();
			ActionContext.getContext().getValueStack().push(userDto);
		}
		return "edit";
	}
	
	/**
	 * 用户登录
	 * */
	public String login() {
		UMUserDTO userDto;
		JsonDTO json = new JsonDTO();
		try {
			userDto = user_service.login(this.userDto);
			String projectName = request.getContextPath();
			if(userDto != null) {
				sessionPut(ConstantValues.USER_LOGIN, userDto);
				getSession().setMaxInactiveInterval(60000);
				json.setObj(projectName + ConstantValues.DIR_INDEX);
				json.setSuccess(true);
				json.setMsg("登录成功");
			} else {
				json.setSuccess(false);
				json.setMsg("登录失败");
			}
		} catch (UMUserException e) {
			// TODO Auto-generated catch block
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		writeJson(GsonUtil.parseJsonObject(json).toString());
		return null;
	}
	
	/**
	 * 根据用户id获得角色，可见菜单，权限
	 * */
	public String doNotNeedSecurityGetUserRoleMenuPer() {
		String userId = this.userDto.getId();
		JsonDTO json = new JsonDTO();
		if(StringUtils.isNotBlank(userId)) {
			UMUserDTO userDto = user_service.getUserRoleMenuPer(userId);
			if(userDto != null) {
				json.setSuccess(true);
				json.setMsg(GsonUtil.parseJsonObject(userDto).toString());
			} else {
				json.setSuccess(false);
				json.setObj(request.getContextPath() + ConstantValues.DIR_INDEX);
				json.setMsg("加载登录用户相关信息失败");
			}
		} else {
			json.setSuccess(false);
			json.setObj(request.getContextPath() + ConstantValues.DIR_LOGIN);
			json.setMsg("加载登录用户相关信息失败");
		}
		writeJson(GsonUtil.parseJsonObject(json).toString());
		return null;
	}
	/**  
	* @Name: save
	* @Description: 保存员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面提示插入成功的消息)
	*/
	public String save() {
		getListByRolesId();
		Serializable id = user_service.save(userDto);
		JsonDTO j = new JsonDTO();
		if(null != id) {
			j.setMsg("员工添加成功");
			j.setSuccess(true);
		} else {
			j.setMsg("员工添加失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	/**  
	* @Name: update
	* @Description: 更新员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面显示更新成功的消息)
	*/
	public String update() {
		getListByRolesId();
		JsonDTO j = new JsonDTO();
		try {
			user_service.update(userDto);
			j.setMsg("员工更新成功");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("员工更新失败");
			j.setSuccess(false);
			e.printStackTrace();
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	/**  
	* @Name: update
	* @Description: 删除员工,参数ids="23,23,123,123,123,"
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面显示删除成功的消息)
	*/
	public String delete() {
		JsonDTO j = new JsonDTO();
		String ids = request.getParameter("ids");
		if("delete".equals(action) && !"".equals(ids)) {
			//临时变量
			String[] _ids = ids.split(",");
			int id = 0;
			try {
				id = user_service.delete(_ids);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(id > 0) {
				j.setMsg("删除了" + id + "数据");
				j.setSuccess(true);
			} else {
				j.setMsg("删除数据失败");
				j.setSuccess(false);
			}
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	/**  
	* @Name: getListByRolesId
	* @Description:设置员工所关联的角色集合
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无
	*/
	public void getListByRolesId() {
		if(roles_id != null) {
			for(String role_id : roles_id) {
				UMRoleDTO roleDto = new UMRoleDTO();
				roleDto.setId(role_id);
				userDto.getRoleDtos().add(roleDto);
			}
		}
	}
	
	/**
	 * 用户退出
	 * */
	public String doNotNeedSecurityLogout() {
		if(null != action && !"".equals(action) && action.equalsIgnoreCase("quit")) {
			JsonObject json = new JsonObject();
			getSession().invalidate();
			json.addProperty("success", true);
			json.addProperty("url", request.getContextPath() + ConstantValues.DIR_LOGIN);
			writeJson(json.toString());
			return null;
		}
		return ConstantValues.ACTION_LOGIN;
	}
	
	/**  
	* @Name: find
	* @Description: 获取所有角色集合
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: String:(Json字符串)
	*/
	public String doNotNeedSecurityFindRole() {
		List<UMRoleDTO> roles = null;
		if(null != action && !"".equals(action) && action.equals("find_role")) {
			roles = role_service.find();
			if(roles != null && roles.size() != 0) {
				writeJson(GsonUtil.parseJsonArray(roles).toString());
			}
		}
		return null;
	}
}
