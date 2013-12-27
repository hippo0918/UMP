<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
    <title>权限管理</title>
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
	
  </head>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/datagrid.extendsion.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_role/role_menu.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
	<body style="width:100%;height:100%;overflow-x:hidden;text-align: center">
	<div class="easyui-layout" border='false' fit="true">
		<div region="north" title="角色可访问菜单保存" headerCls="header" style="height:120px;overflow: hidden" border="false" noheader="false" headerCls="text-align:center;" collapsible="false"   split="false" height="100px">
		    <div style="margin-top:20px;text-align: center" >
		    	<select id="rolesSelect" name="roleId" style="width: 250px">
		    	</select>
		    	<a class="easyui-linkbutton" id="save">保存角色访问菜单</a>
		    </div>
	    </div>
	    <div region="center" border='true' noheader="true">
	    	<table style="margin-left: auto;margin-right: auto" align="center" id="menusTable"></table>
	    </div>
    </div>
  	</body>
</html>
