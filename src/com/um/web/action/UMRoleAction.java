package com.um.web.action;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridDTOFactory;
import com.common.easyui.DataGridJsonDTO;
import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.um.domain.UMMenu;
import com.um.domain.UMPermission;
import com.um.domain.model.dto.JsonDTO;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.service.IUMMenuService;
import com.um.service.IUMPermissionService;
import com.um.service.IUMRoleService;
import com.um.service.IUMRoleUMMenuService;
import com.um.service.IUMRoleUMMenuUMPermissionService;
import com.um.service.IUMUserService;
import com.um.util.json.GsonUtil;

@Controller("umRoleAction")
@Scope("prototype")
public class UMRoleAction extends BaseAction implements ModelDriven<UMRoleDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataGridDTO datagrid;
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private UMRoleDTO roleDto = new UMRoleDTO();
	
	@Resource(name=IUMRoleService.SERVICE_NAME)
	private IUMRoleService role_service;
	
	@Resource(name=IUMMenuService.SERVICE_NAME)
	private IUMMenuService menu_service;
	
	@Resource(name=IUMRoleUMMenuService.SERVICE_NAME)
	private IUMRoleUMMenuService role_menu_service;
	
	@Resource(name=IUMRoleUMMenuUMPermissionService.SERVICE_NAME)
	private IUMRoleUMMenuUMPermissionService role_menu_permission_service;
	
	@Resource(name=IUMUserService.SERVICE_NAME)
	private IUMUserService user_service;
	
	@Resource(name=IUMPermissionService.SERVICE_NAME)
	private IUMPermissionService permission_service;
	
	@Override
	public UMRoleDTO getModel() {
		// TODO Auto-generated method stub
		return roleDto;
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
	public String doNotNeedSecurityFind() {
		List<UMRoleDTO> roles = null;
		if(null != action && !"".equals(action) && action.equals("find_role")) {
			roles = role_service.find();
			if(roles != null && roles.size() != 0) {
				writeJson(GsonUtil.parseJsonArray(roles).toString());
			}
		}
		return null;
	}
	
	/**  
	* @Name: query
	* @Description: 在员工页面显示角色列表
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无
	*/
	public String query() {
		if(action != null && !"".equals(action) && "loadDataGrid_role".equals(action)) {
			datagrid = DataGridDTOFactory.getInstance().getDataGridDTO(request);
			DataGridJsonDTO<UMRoleDTO> dg = role_service.find(datagrid, roleDto);
			String json = GsonUtil.dataGridJsonToString(dg);
			writeJson(json);
		} else {
			writeJson("{total:0,rows:[]}");
		}
		return null;
	}
	
	/**  
	* @Name: index
	* @Description: 转发到角色页面
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
	* @Name: findUsersByRole
	* @Description: 查找该角色的所有员工
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: String:"index",request保存着角色id
	*/
	public String view() {
		//会把角色ID转发过去
		return "view";
	}
	
	/**  
	* @Name: edit
	* @Description: 在页面弹出编辑窗口，如果是保存角色就不加载角色信息，如果是更新的话，要通过角色id获取角色信息
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无
	*/
	public String doNotNeedSecurityEdit() {
		if("save".equals(action)) {
			//设置编辑页面的表单动作
			request.setAttribute("action","save");
		}
		if("update".equals(action)) {
			request.setAttribute("action","update");
			//接收角色id
			roleDto = role_service.findByID(roleDto.getId());
			//压入栈顶
			ActionContext.getContext().getValueStack().pop();
			ActionContext.getContext().getValueStack().push(roleDto);
		}
		return "edit";
	}
	
	/**  
	* @Name: save
	* @Description: 保存角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面提示插入成功的消息)
	*/
	public String save() {
		if("save".equals(action)) {
			//新增角色之前先查询角色名称是否存在，若false，则插入
			UMRoleDTO roleDto = role_service.findRoleByName(this.roleDto);
			JsonDTO j = new JsonDTO();
			Serializable id = null;
			if(roleDto == null) {
				id = role_service.save(this.roleDto);
				if(null != id) {
					j.setMsg("角色添加成功");
					j.setSuccess(true);
				} else {
					j.setMsg("角色添加失败");
					j.setSuccess(false);
				}
			} else {
				j.setMsg("角色名称已存在，插入失败");
				j.setSuccess(false);
			}
			writeJson(GsonUtil.parseJsonObject(j).toString());
		}
		return null;
	}
	
	/**  
	* @Name: update
	* @Description: 更新角色
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面显示更新成功的消息)
	*/
	public String update() {
		if("update".equals(action)) {
			JsonDTO j = new JsonDTO();
			//检查此更新角色更新的名称是否跟前面的角色名称相同
			UMRoleDTO roleDto = role_service.findRoleByName(this.roleDto);
			//若为null，则此名称的角色不存在
			if(roleDto == null) {
				role_service.update(this.roleDto);
				j.setMsg("角色更新成功");
				j.setSuccess(true);
			} else if(roleDto != null && roleDto.getId().equals(this.roleDto.getId())){
				role_service.update(this.roleDto);
				j.setMsg("角色更新成功");
				j.setSuccess(true);
			} else {
				j.setMsg("角色名称已存在，更新失败");
				j.setSuccess(false);
			}
			writeJson(GsonUtil.parseJsonObject(j).toString());
		}
		return null;
	}
	
	/**  
	* @Name: delete
	* @Description: 删除角色，根据角色id
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: 无(在页面显示删除成功的消息)
	*/
	public String delete() {
		JsonDTO j = new JsonDTO();
		if("delete".equals(action)) {
			try {
				role_service.delete(roleDto);
				j.setMsg("删除角色成功");
				j.setSuccess(true);
			} catch (Exception e) {
				j.setMsg("删除角色失败");
				j.setSuccess(false);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writeJson(GsonUtil.parseJsonObject(j).toString());
		}
		return null;
	}
	
	public String doNotNeedSecurityMngVisableMenu() {
		return "doNotNeedSecurityMngVisableMenu";
	}
	
	/**
	 * 进行权限管理，角色的变化，对应的菜单也要变化
	 * 
	 * */
	public String doNotNeedSecurityGetMenusByRoles() {
		//只有一个角色
		String roleId = ((String[])requestGet("roleId"))[0];
		String[] ids = new String[]{roleId};
		JsonArray menuDtos = role_menu_service.getMenusByRoles(ids);
		writeJson(menuDtos.toString());
		return null;
	}
	
	/**
	 * 更新角色对应的权限
	 * */
	public String saveMenu() {
		String[] rolesId = (String[])requestGet("rolesId");
		//这里是多个id
		String[] menusId = (String[])requestGet("menusId");
		//menusId[0] = 'id1','id2'
		boolean flag = role_menu_service.updateOrSaveUMRoleUMMenuByUMRole(rolesId[0], menusId[0]);
		JsonDTO j = new JsonDTO();
		if(flag) {
			j.setMsg("角色所属菜单修改成功");
			j.setSuccess(true);
		} else {
			j.setMsg("角色所属修改失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	/**
	 * 给角色分配权限
	 * */
	public String viewPermission() {
		String roleId = "";
		if(requestGet("id") != null) {
			roleId = ((String[])requestGet("id"))[0];
		}
		requestPut("roleId", roleId);
		return "viewPermission";
	}
	
	public String doNotNeedSecurityGetRoleMenuPerByRoleId() {
		String roleId = "";
		if(requestGet("roleId") != null) {
			roleId = ((String[])requestGet("roleId"))[0];
		}
		UMRoleDTO list = role_service.getRoleMenuPerByRoleId(roleId);
		writeJson(GsonUtil.parseJsonObject(list).toString());
		return null;
	}
	
	/**
	 * 保存角色权限SAVEPERMISSION
	 * */
	public String savePermission() {
		String roleId = "";
		if(requestGet("roleId") != null) {
			roleId = request.getParameter("roleId");
		}
		String menuId = "";
		if(requestGet("menuId") != null) {
			menuId = request.getParameter("menuId");
		}
		String[] permissionsId = null;
		if(requestGet("permissionsId") != null) {
			permissionsId = ((String[])requestGet("permissionsId"));
		}
		boolean flag = role_menu_permission_service.saveRoleMenuPer(roleId, menuId, permissionsId);
		JsonDTO j = new JsonDTO();
		if(flag) {
			j.setMsg("角色权限修改成功");
			j.setSuccess(true);
		} else {
			j.setMsg("角色权限修改失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	public String doNotNeedSecurityQueryUser() {
		if(action != null && !"".equals(action) && "loadDataGrid_user".equals(action)) {
			datagrid = DataGridDTOFactory.getInstance().getDataGridDTO(request);
			DataGridJsonDTO<UMUserDTO> dg = user_service.findByRoleIds(datagrid, roleDto.getId());
			String json = GsonUtil.dataGridJsonToString(dg);
			writeJson(json);
		} else {
			writeJson("{total:0,rows:[]}");
		}
		return null;
	}
	
	/**  
	* @Name: find
	* @Description: 获取所有菜单集合
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: String:(Json字符串)
	*/
	public String viewMenu() {
		DataGridDTO dg = DataGridDTOFactory.getInstance().getDataGridDTO(request);
		DataGridJsonDTO<UMMenu> menus = menu_service.find(new UMMenu(), dg);
		//menus中应该包含状态
		writeJson(GsonUtil.parseJsonObject(menus).toString());
		return null;
	}
	
	public String doNotNeedSecurityGetpermissionsByMenuId() {
		String menuId = request.getParameter("menuId");
		datagrid = DataGridDTOFactory.getInstance().getDataGridDTO(request);
		DataGridJsonDTO<UMPermission> permissions = permission_service.findByMenuId(menuId, datagrid);
		writeJson(GsonUtil.parseJsonObject(permissions).toString());
		return null;
	}
}
