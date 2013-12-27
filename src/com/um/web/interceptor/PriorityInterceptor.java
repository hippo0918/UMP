package com.um.web.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.um.domain.UMPermission;
import com.um.domain.model.dto.UMMenuDTO;
import com.um.domain.model.dto.UMRoleDTO;
import com.um.domain.model.dto.UMUserDTO;
import com.um.util.json.GsonUtil;
import com.um.web.action.BaseAction;
import com.um.web.constant.ConstantValues;

public class PriorityInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String url = request.getRequestURI();
		//请求路径
		int position = StringUtils.lastIndexOf(url, "/");
		String path = url.substring(position + 1);
		String[] act_opt = (path.substring(0, (path.length()) - 3)).split("_");
		String act = act_opt[0];
		String opt = act_opt[1];
		Object user =  BaseAction.sessionGet(ConstantValues.USER_LOGIN);
		UMUserDTO userDto = (UMUserDTO)user;
		if(opt.equalsIgnoreCase(ConstantValues.ACTION_LOGIN) || opt.equalsIgnoreCase(ConstantValues.ACTION_LOGOUT)) {//登录操作
			return actionInvocation.invoke();
		} 
		if("YES".equalsIgnoreCase(userDto.getAdmin())) {//管理员
			return actionInvocation.invoke();
		} else {
			if(StringUtils.indexOf(opt, "doNotNeedSecurity") == 0) {//doNotNeedSecurity
				return actionInvocation.invoke();
			} else {
				//加载User能访问的菜单
				Set<UMMenuDTO> visitedMenu = new HashSet<UMMenuDTO>();
				for(UMRoleDTO r : userDto.getRoleDtos()) {
					visitedMenu.addAll(r.getMenus());
				}
				for(UMMenuDTO m : visitedMenu) {
					if(m.getAct().equalsIgnoreCase((act))) {//拥有访问此菜单的权限
						Set<UMPermission> permissions = m.getPermissions();
						for(UMPermission p : permissions) {
							if(p.getCode().equalsIgnoreCase(opt)) {
								return actionInvocation.invoke();								
							}
						}
					} 
				}
				
				
			}
		}
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").equalsIgnoreCase(     
				"XMLHttpRequest")) {  
			response.setCharacterEncoding("text/html;charset=utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(ConstantValues.NOT_PRIORITY);
			pw.flush();
			pw.close();
			return null;
		}
		return ConstantValues.NOT_PRIORITY;
	}
}
