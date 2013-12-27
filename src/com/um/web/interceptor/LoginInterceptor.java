package com.um.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.um.domain.model.dto.UMUserDTO;
import com.um.web.action.BaseAction;
import com.um.web.constant.ConstantValues;

public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//请求路径
		String path = request.getRequestURI().substring(request.getContextPath().length());
		HttpServletResponse response = ServletActionContext.getResponse();
		Object user =  BaseAction.sessionGet(ConstantValues.USER_LOGIN);
		if(path.equals(ConstantValues.DIR_LOGIN) || path.equals(ConstantValues.ACTION_LOGIN) || path.contains("umUserAction_login.do") || path.contains(ConstantValues.ACTION_LOGOUT)) {
			return actionInvocation.invoke();
		} else {
			if(user == null) {
				//拦截异步请求
				if (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").equalsIgnoreCase(     
						"XMLHttpRequest")) {  
						response.setCharacterEncoding("text/html;charset=utf-8");
						response.setContentType("text/html;charset=utf-8");
						PrintWriter pw = ServletActionContext.getResponse().getWriter();
						pw.write("sessionTimeOut");
						pw.flush();
						pw.close();
						return null;
				}
				return ConstantValues.ACTION_LOGIN;
			} 
			return actionInvocation.invoke();
		}
	}

}
