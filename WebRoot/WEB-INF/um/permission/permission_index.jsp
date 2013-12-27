<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>权限查询</title>

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
	src="${pageContext.request.contextPath }/js/um/um_permission/permission_index.js"></script>
<body style="width:100%;height:100%;">
	<div class="easyui-layout" border='false' fit="true">
	<div region="center" border='true' noheader="true">
	<table id="tablePermission"></table>
	</div>
	<div region="north" title="权限查询窗口(过滤条件)" headerCls="header" style="height:120px;overflow: hidden" border="false" noheader="false" headerCls="text-align:center;" collapsible="false"   split="false" height="100px">
   		<form method="post" id="form" style="margin-top: 0px">
	    <input type="hidden" id="action2" name="action">
	    <table style="width:100%;height:100%;padding-top:5px;padding-bottom:5px;margin:0px;background-color: #F5F5F5;" align="center">
	    <tr>
		    <td style="letter-spacing: 5px;font-size: 15px;color:  #4682B4 ">
		                     权限名称:<input type="text" name="name" id="name" style="width:120px;vertical-align:middle;">&nbsp;
		                     权限代码:<input type="text" name="code" id="code" style="width:120px;vertical-align:middle;">&nbsp;
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
</body>
</html>
