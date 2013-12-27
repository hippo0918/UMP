<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>分配菜单权限</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" >
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" >
	<style type="text/css">
		.header {
			text-align: center;
			letter-spacing: 9px;
			font-size: 16px
		}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/easyui-loading.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_menu/menu_permission.js" ></script>
  </head>
  <script>
  var menuId = "${requestScope.menuId}";
  </script>
  <body style="width:100%; height: 100%; overflow-x: hidden;margin: 0px;padding: 0px">
		<table id="permissionTable"></table>
		<input type="hidden" name="menuId" id="menuId" value="${requestScope.menuId}"/>
  </body>
</html>
