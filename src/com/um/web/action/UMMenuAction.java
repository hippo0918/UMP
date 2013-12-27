package com.um.web.action;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.easyui.datagrid.DataGridDTO;
import com.common.easyui.datagrid.DataGridDTOFactory;
import com.common.easyui.datagrid.DataGridJsonDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ModelDriven;
import com.um.domain.UMMenu;
import com.um.domain.model.dto.JsonDTO;
import com.um.service.IUMMenuService;
import com.um.service.IUMPermissionService;
import com.um.service.IUMRoleService;
import com.um.util.json.GsonUtil;

@Controller("umMenuAction")
@Scope("prototype")
public class UMMenuAction extends BaseAction implements ModelDriven<UMMenu> {

	private UMMenu menuModel = new UMMenu();
	private static final long serialVersionUID = 1L;

	@Resource(name=IUMMenuService.SERVICE_NAME)
	private IUMMenuService menu_service;
	
	@Resource(name=IUMRoleService.SERVICE_NAME)
	private IUMRoleService role_service;
	
	@Resource(name=IUMPermissionService.SERVICE_NAME)
	private IUMPermissionService permission_service;
	
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public UMMenu getModel() {
		// TODO Auto-generated method stub
		return menuModel;
	}
	
	/**
	 * 初始化权限管理页面
	 * */
	public String query() {
		DataGridDTO dg = DataGridDTOFactory.getInstance().getDataGridDTO(request);
		DataGridJsonDTO<UMMenu> menus = menu_service.find(menuModel, dg);
		//menus中应该包含状态
		writeJson(GsonUtil.parseJsonObject(menus).toString());
		return null;
	}
	
	public String index() {
		return "index";
	}

	public String doNotNeedSecurityEdit() {
		if("save".equals(action)) {
			//设置编辑页面的表单动作
			request.setAttribute("action","save");
		}
		if("update".equals(action)) {
			request.setAttribute("action","update");
			//接收角色id
			menuModel = menu_service.findByID(menuModel.getId());
			requestPut("menuModel", menuModel);
		}
		return "edit";
	}
	
	public String save() {
		//新增角色之前先查询角色名称是否存在，若false，则插入
		//UMRoleDTO roleDto = role_service.findRoleByName(this.roleDto);
		JsonDTO j = new JsonDTO();
		Serializable id = null;
		id = menu_service.save(this.menuModel);
		if(null != id) {
			j.setMsg("菜单添加成功");
			j.setSuccess(true);
		} else {
			j.setMsg("菜单添加失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	public String doNotNeedSecurityGetMenusForSelect() {
		Collection<UMMenu> c = menu_service.getMenusForSelect();
		writeJson(GsonUtil.parseJsonArray(c).toString());
		return null;
	}
	
	
	public String delete() {
		String[] ids = (((String[])requestGet("ids"))[0]).split(",");
		JsonDTO j = new JsonDTO();
		int length = menu_service.delete(ids);
		if(length > 0) {
			j.setMsg("删除了" + length + "数据");
			j.setSuccess(true);
		} else {
			j.setMsg("删除数据失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	public String update() {
		JsonDTO j = new JsonDTO();
		boolean flag = menu_service.update(menuModel);
		if(flag) {
			j.setMsg("菜单更新成功");
			j.setSuccess(true);
		} else {
			j.setMsg("菜单更新失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	//分配菜单权限
	public String viewPermission() {
		if(StringUtils.isNotBlank(action) && "permissionDispatch".equals(action)) {
			String menuId = menuModel.getId();
			requestPut("menuId", menuId);
			return "permissionDispatch";
		}
		if(StringUtils.isNotBlank(action) && "findPermissionByMenuId".equals(action)) {
			String menuId = menuModel.getId();
			JsonArray array = menu_service.findPermissionByMenuId(menuId);
			JsonObject j = new JsonObject();
			j.add("rows", array);
			j.addProperty("total", array.size());
			writeJson(j.toString());
			return null;
		}
		return "permissionDispatch";
	}
	
	//保存菜单对应的权限
	public String savePermission() {
		String[] permissionIds = (((String[])requestGet("permissionIds")));
		String menuId = menuModel.getId();
		boolean flag = menu_service.saveMenuPermission(menuId, permissionIds);
		JsonDTO j = new JsonDTO();
		if(flag) {
			j.setMsg("菜单权限更新成功");
			j.setSuccess(true);
		} else {
			j.setMsg("菜单权限更新失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
}
