<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>分配角色权限</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css">
<style type="text/css">
.header {
	text-align: center;
	letter-spacing: 9px;
	font-size: 16px
}
</style>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/tree.extendsion.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_role/role_permission.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js"></script>
<script type="text/javascript">
	var roleId = "${requestScope.roleId}";
</script>
<body class="easyui-layout" border='false' fit="true">
		<div region="center" split="true" border='false' title="角色权限" noheader="false" headerCls="header" style="width:40px;text-align: center;;overflow-x: hidden;" minWidth="40"  maxWidth="40">
			<!-- <form id="form" method="post">
		   		<input  type="hidden" name="roleId" value=""/>
		   		<input  type="hidden" name="menuId" value=""/>
		   		<label for="add">增&nbsp;加:</label><input type="checkbox" name="permissionIds" id="add" style="margin-top: 90px"/><br>
		   		<label for="delete">删&nbsp;除:</label><input type="checkbox" name="permissionIds" id="delete" style="margin-top: 10px"/><br>
		   		<label for="select">选&nbsp;择:</label><input type="checkbox" name="permissionIds" id="select" style="margin-top: 10px"/><br>
		   		<label for="update">更&nbsp;新:</label><input type="checkbox" name="permissionIds" id="update" style="margin-top: 10px"/><br>
    			<a class="easyui-linkbutton" iconCls="icon-user-earth" id="save" style="margin-top: 120px">保存权限</a><br>
    			<a class="easyui-linkbutton" iconCls="icon-delete" id="cancel" style="margin-top: 10px">取消</a>
    		</form> -->
    		<table id="permission"></table>
    	</div>
    	<div region="west"  title="角色访问菜单" headerCls="header" 
    	style="width:240px;overflow-x: hidden;" minWidth="240" maxWidth="240"
    	border="false" noheader="false"  
    	collapsible="false"  split="true">
			<ul id="menuTree">
			</ul>
		</div>
</body>
</html>
