<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="width:100%;height:100%;">
  <head>
    <title>员工信息管理</title>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/um/um_user/user_index.js" ></script>
	<body style="width:100%;height:100%;">
		<div class="easyui-layout" border='false' fit="true">
			<div region="center" border='true' noheader="true">
				<table  id="table_user" toolbar="#tb"></table>
	    	</div>
	    	<div region="north" title="员工查询窗口(过滤条件)" headerCls="header" style="height:120px;overflow: hidden" border="false" noheader="false" headerCls="text-align:center;" collapsible="false"   split="false" height="100px">
	    		<form method="post" id="form2" style="margin-top: 0px">
			    <input type="hidden" id="action2" name="action">
			    <table style="width:100%;height:100%;padding-top:5px;padding-bottom:5px;margin:0px;background-color: #F5F5F5;" align="center">
			    <tr>
				    <td style="letter-spacing: 5px;font-size: 15px;color:  #4682B4 ">
				                     工号:<input type="text" name="userno" id="userno" style="width:120px;vertical-align:middle;">&nbsp;
						姓名:<input type="text" name="name" id="name" style="width:120px;vertical-align:middle;">&nbsp;
						<!-- 查询台麻烦。暂时屏蔽 ,解决办法：这种多条件查询，可以使用Hibernate的filter，会麻烦一点，不过这是最灵活的-->
						<%--  
						角色名称:
						<select class="easyui-combobox" name="roles_id"
						id="select_role" style="width:320px;"></select>（可多选） --%>
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
