<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加菜单</title>
    
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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_menu/menu_edit.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
<script>
  var pidSelectValue = '${requestScope.menuModel.pid}';
  var iconSelectValue = '${requestScope.menuModel.iconCls}';
</script>
  <body style="overflow: hidden">
  <form id="form" method="post">
  	<input type="hidden" name="action" id="action" value="${requestScope.action}"/>
	<input type="hidden" name="id" value="${requestScope.menuModel.id }" />
	<input type="hidden" name="pid" value="" id="pid"/>
	<input type="hidden" name="formAction" id="formAction" value="um/umMenuAction_${requestScope.action}.do">
    <table id="table" cellpadding="3" cellspacing="2">
    	<tr>
    		<td width="75">菜单名称:</td>
    		<td colspan="1" style="text-align: left">
    			<input width="120px" maxlength="60" type="text" name="name" id="txt_name" class="easyui-validatebox" value='${requestScope.menuModel.name }'>
    		</td>
    		<td width="90">Action名称:</td>
    		<td colspan="1" style="text-align: left">
    			<input width="110px" maxlength="60" type="text" name="act" id="txt_action" class="easyui-validatebox" value='${requestScope.menuModel.act }'>
    		</td>
    	</tr>
    	<tr>
    		<td>上级菜单:</td>
    		<td>
    			<select style="width: 160px;" class="easyui-combotree" id="pidSelect"></select>
    		</td>
    		<td>菜单图标:</td>
    		<td>
    			<select style="width: 160px" class="easyui-combobox" id="iconSelect" name="iconCls"></select>
    		</td>
    	</tr>
    	<tr>
    		<td>菜单链接:</td>
    		<td colspan="3">
    			<textarea rows="3"  cols="50" id="txt_url" name="url" style="resize:none">${requestScope.menuModel.url}</textarea>
    		</td>
    	</tr>
    </table>
    </form>
  </body>
</html>
