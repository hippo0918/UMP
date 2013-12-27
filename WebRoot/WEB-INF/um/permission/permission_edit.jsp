<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限编辑页面</title>
    
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/Easyui-1.3.4/datagrid.extendsion.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/util.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/um/um_permission/permission_edit.js"></script>
</head>
  <script>
  	var iconSelectValue = "${requestScope.permissionModel.iconCls}";
  </script>
  <body style="overflow: hidden">
   <form method="post" id="form">
    <input type="hidden" name="action" id="action" value="${requestScope.action}"/>
	<input type="hidden" name="id" value="${requestScope.permissionModel.id }" />
	<input type="hidden" name="formAction" id="formAction" value="um/umPermissionAction_${requestScope.action}.do">
   		<table cellpadding="3" cellspacing="2">
   			<tr>
   				<td><strong>权限名称:</strong></td>
   				<td><input type="text" class="easyui-validatebox" name="name" id="txt_name" value="${requestScope.permissionModel.name}"></td>
   				<td><strong>权限代码:</strong></td>
   				<td><input type="text" class="easyui-validatebox" name="code" id="txt_code" maxlength="32" value="${requestScope.permissionModel.code}"></td>
   			</tr>
   			<tr>
   				<td><strong>权限图标:</strong></td>
   				<td>
   				<select name="iconCls" id="iconClsSelect" style="width: 200px"></select>
   				</td>
   			</tr>
   			<tr>
   				<td><strong>权限描述:</strong></td>
   				<td colspan="3">
   				<textarea rows="3" cols="52" name="description" style="resize:none">${requestScope.permissionModel.description}</textarea>
   				</td>
   			</tr>
   		</table>
   </form>
  </body>
</html>
