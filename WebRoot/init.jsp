<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%String contextPath = request.getContextPath();%>
<%--引入EasyUi --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" />
   
<%--引入jquery --%>   
    
<%-- 引入jQuery --%>
<%
//可以通过JS判断浏览器版本再决定使用哪个版本的jquery
String User_Agent = request.getHeader("User-Agent");
if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1 && (
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 4") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 5") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 5.0") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 4.0") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6.0") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7.0") > -1 || 
		StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8.0") > -1)) {
	out.println("<script src='" + contextPath + "/js/jquery-1.8.0.min.js' type='text/javascript' charset='utf-8'></script>");
} else {
	out.println("<script src='" + contextPath + "/js/jquery-2.0.0.min.js' type='text/javascript' charset='utf-8'></script>");
}
%>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/easyui-loading.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/showloading/jquery.showLoading.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/resizeWindow.js" ></script>