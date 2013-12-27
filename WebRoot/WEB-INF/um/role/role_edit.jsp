<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>角色编辑页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" >
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" >
  	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_role/role_edit.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
  </head>
  <body style="text-align: center; overflow-x : hidden;">
  <div style="margin-left: 6%;margin-right: auto;vertical-align:middle;text-align: center">
   <form method="post" id="form" action="um/umRoleAction_${requestScope.action}.do">
	<input type="hidden" name="action" id="action" value="${requestScope.action}"/>
	<input type="hidden" name="id" value="<s:property value="%{id}" />">
	<input type="hidden" name="formAction" id="formAction" value="um/umRoleAction_${requestScope.action}.do">
	<!-- 表跟边框之间的间距：padding-left -->
		<table  id="user" style="margin-top:12px;width:90%;height:80%;" align="center" >
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td colspan="3" align="center">
				<font style="font-size: 18px" color="#6495ED" id="title">---------角色信息---------&nbsp;&nbsp;&nbsp;</font>
			</td>
		</tr>
		<tr id="tr_name" class="input">
			<td align="center">
				<font style="font-size: 14px">角 色 名 称:</font>
			</td> 
			<td align="left" colspan="2">
				<input style="width: 130px" id="txt_name" name="name" class="easyui-validatebox" value="<s:property value="%{name}"/>" ></input>(必填)
			</td>
		</tr>
		<tr id="tr_description" class="input">
			<td align="center">
				<font style="font-size: 14px">角 色 描 述:</font>
			</td>
			<td align="left" colspan="2">
			<!-- 不能换行，换行会有空格  -->
				<textarea rows="4" cols="25" id="txt_description" name="description" ><s:property value="%{description}" /></textarea>(必填)
			</td>
		</tr>
		<tr>
			<td colspan="3" style="text-align: center">
				<div id="bbar" style="margin-top: 30px">
					<a href="javascript:void(0)" iconCls="icon-edit" class="easyui-linkbutton" id="update">更&nbsp;新</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton" id="save">保&nbsp;存</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton" id="close">关&nbsp;闭</a>
				</div>
			</td>
		</tr>
		</table>
	</form>
	</div>
  </body>
</html>
