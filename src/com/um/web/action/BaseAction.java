package com.um.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public void writeJson(String json) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void applicationPut(String objectName, Object object) {
		ActionContext.getContext().getApplication().put(objectName, object);
	}

	public static Object applicationGet(String objectName) {
		return ActionContext.getContext().getApplication().get(objectName);
	}
	
	public static void sessionPut(String objectName, Object object) {
		ActionContext.getContext().getSession().put(objectName, object);
	}

	public static Object sessionGet(String objectName) {
		return ActionContext.getContext().getSession().get(objectName);
	}
	
	public static void requestPut(String objectName, Object object) {
		getRequest().setAttribute(objectName, object);
	}

	public static Object requestGet(String objectName) {
		return ActionContext.getContext().getParameters().get(objectName);
	}
	
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public static HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	public static HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}

}
