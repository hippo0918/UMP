package com.um.web.action;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridDTOFactory;
import com.common.easyui.DataGridJsonDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.um.domain.UMPermission;
import com.um.domain.model.dto.JsonDTO;
import com.um.service.IUMPermissionService;
import com.um.util.json.GsonUtil;

@Controller("umPermissionAction")
@Scope("prototype")
public class UMPermissionAction extends BaseAction  implements ModelDriven<UMPermission>  {

	private UMPermission permissionModel = new UMPermission();
	private DataGridDTO datagrid;
	private static final long serialVersionUID = 1L;
	private String action;

	@Resource(name=IUMPermissionService.SERVICE_NAME)
	private IUMPermissionService permission_service;

	@Override
	public UMPermission getModel() {
		// TODO Auto-generated method stub
		return permissionModel;
	}

	public String index() {
		return "index";
	}
	
	/**
	 * 获得所有权限
	 * */
	public String query() {
		datagrid = DataGridDTOFactory.getInstance().getDataGridDTO(request);
		DataGridJsonDTO<UMPermission> permissions = permission_service.find(permissionModel, datagrid);
		//menus中应该包含状态
		writeJson(GsonUtil.parseJsonObject(permissions).toString());
		return null;
	}
	
	public String doNotNeedSecurityEdit() {
		if("save".equals(action)) {
			//设置编辑页面的表单动作
			request.setAttribute("action","save");
		}
		if("update".equals(action)) {
			request.setAttribute("action","update");
			//接收角色id
			permissionModel = permission_service.findByID(permissionModel.getId());
			requestPut("permissionModel", permissionModel);
		}
		return "edit";
	}
	
	public String delete() {
		String[] ids = (((String[])requestGet("ids"))[0]).split(",");
		JsonDTO j = new JsonDTO();
		int length = permission_service.delete(ids);
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
	
	public String save() {
		//新增角色之前先查询角色名称是否存在，若false，则插入
		//UMRoleDTO roleDto = role_service.findRoleByName(this.roleDto);
		JsonDTO j = new JsonDTO();
		Serializable id = null;
		id = permission_service.save(this.permissionModel);
		if(null != id) {
			j.setMsg("权限添加成功");
			j.setSuccess(true);
		} else {
			j.setMsg("权限添加失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	public String update() {
		JsonDTO j = new JsonDTO();
		boolean flag = permission_service.update(permissionModel);
		if(flag) {
			j.setMsg("权限更新成功");
			j.setSuccess(true);
		} else {
			j.setMsg("权限更新失败");
			j.setSuccess(false);
		}
		writeJson(GsonUtil.parseJsonObject(j).toString());
		return null;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
