<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="width:100%;height:100%;">
 <head>
    <title>菜单管理</title>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_menu/menu_index.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
	<body style="width:100%;height:100%;overflow-x:hidden;">
		<div class="easyui-layout" border='false' fit="true">
			<div region="center" border='true' noheader="true">
				<table toolbar="#tb" style="margin-left: auto;margin-right: auto" align="center" id="menusTable"></table>
	    	</div>
	    	<div region="north" title="菜单查询窗口(过滤条件)" headerCls="header" style="height:110px;overflow: hidden" border="false" noheader="false" headerCls="text-align:center;" collapsible="false"   split="false" height="100px">
	    		<form method="post" id="form" style="margin-top: 0px">
			    <input type="hidden" id="action2" name="action">
			    <table style="width:100%;height:100%;padding-top:5px;padding-bottom:5px;margin:0px;background-color: #F5F5F5;" align="center">
			    <tr>
				    <td style="letter-spacing: 5px;font-size: 15px;color:  #4682B4 ">
				                     菜单名称:<input type="text" name="name" id="name" style="width:120px;vertical-align:middle;">&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="1">
			        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn_search">&nbsp;查&nbsp;找</a>  
			        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" id="btn_clear">&nbsp;清&nbsp;空</a>  
				    </td>
				</tr> 
				</table>    
				</form>
	    	</div>
		</div>
   <!--  <div>权限管理</div>
    <div style="margin-top:20px">
    	<select id="rolesSelect" name="roleId" style="width: 200px">
    	<option value="---------------请选择角色---------------" selected="selected">---------------请选择角色---------------</option>
    	</select>
    	<a class="easyui-linkbutton" id="save">保存角色权限</a>
    </div>
    <div style="text-align: center;margin-top:100px;margin-left: auto;margin-right: auto;width: 800px;height: 400px">
    	<table style="margin-left: auto;margin-right: auto" align="center" id="menusTable"></table>
    </div>
    <div style="text-align: center;margin-top:100px;margin-left: auto;margin-right: auto;width: 800px;height: 400px">
    	<table style="margin-left: auto;margin-right: auto" align="center"  id="usersTable"></table>
    </div> -->
  	</body>
</html>
