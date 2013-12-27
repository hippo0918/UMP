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
    <title>查看角色详细信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" >
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" >
  </head>
  <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
 <script>
	 var url = "";
	 window.onload = function() {
		 url = 'um/umRoleAction_doNotNeedSecurityQueryUser.do';
	 };
 	$(function() {
 		$("#table_usersByRole").datagrid({
 			url : 'um/umRoleAction_doNotNeedSecurityQueryUser.do',
			queryParams : {
				//要把角色ID传回去
				action : 'loadDataGrid_user',
				id:'<s:property value="%{#request.id}" escape="false"/>'
			},
			fit : true,
			fitColumns : true,
			border:true,
			columns : [ [ {
				field : 'id',
				hidden : true
			}, {
				field : 'userno',
				title : '工    号',
				width : 15,
				align : 'center',
				sortable:true
			}, {
				field : 'name',
				title : '姓    名',
				width : 15,
				align : 'center',
				sortable:true
			}, {
				field : 'gender',
				title : '性    别',
				width : 10,
				align : 'center'
			}, {
				field : 'roles_name',
				title : '所   属  角  色',
				width : 60,
				align : 'center',
				formatter: function(value,row,index){
					//拼接此用户关联的角色名称
					var role_name = new Array();
					if(row.roleDtos.length > 0) {
						for(var i=0; i<row.roleDtos.length; i++) {
							role_name[i] = row.roleDtos[i].name;
						};
					} ;
					return role_name.join(",");
				}
			}] ],
			// 字体智能换行
			nowrap : true,
			striped : true,// 看文档
			// 是否可以伸缩
			collapsible : false,
			border : false,
			pageSize : 10,
			pageList : [ 10, 20, 30,40],
			loadMsg : '数据加载中请稍后……',
			pagination : true,
			rownumbers : true,
			headerCls:"header",
			onLoadSuccess : function(data) {
				if(data.total == 0 || data.rows.length == 0) {
					$.messager.show({
						msg : "没有相应的员工记录",
						title : '提示'
					});
				}
			}
 		});
 	});
 </script>
 
  <body>
    <table id="table_usersByRole"></table>
  </body>
</html>
