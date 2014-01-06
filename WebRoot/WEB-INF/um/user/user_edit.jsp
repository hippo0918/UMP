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
    <title>用户编辑页面</title>
    
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js" ></script>
  </head>
  	<script>
  	//此用户关联的角色,有则选中。
  	var roles_json = '<s:property value="%{#request.roles_json}" escape="false"/>';
  	//用户性别
  	var gender = '<s:property value="%{gender}" />';
  	</script>
  <script type="text/javascript">
  	$(function() {
  		$("#roles_id").combobox({
  			multiple:true,
  			panelHeight:150,
  			width:225,
			editable : false,
			valueField : 'id',
			textField : 'name'
		});
  		
  		if(roles_json.length == 0) {
  			$("#roles_id").combobox('reload', 'um/umUserAction_doNotNeedSecurityFindRole.do?action=find_role');
  		} else {
  			$("#roles_id").combobox("loadData",$.parseJSON($.trim(roles_json)));
  		}
  		
  		if(gender == '男') {
  			$("#male").attr({
  				checked:true
  			});
  		} else {
  			$("#female").attr({
  				checked:true
  			});
  		}
  	});
  	
  </script>
  <body style="text-align: center; overflow-x : hidden;">
  <div style="margin-left: 6%;margin-right: auto;vertical-align:middle;text-align: center">
   <form method="post" id="form" action="um/umUserAction_${requestScope.action}.do">
	<input type="hidden" name="action" id="action" value="${requestScope.action}"/>
	<input type="hidden" name="id" value="<s:property value="%{id}" />">
	<input type="hidden" name="admin" value="NO">
	<input type="hidden" name="formAction" id="formAction" value="um/umUserAction_${requestScope.action}.do">
	<!-- 表跟边框之间的间距：padding-left -->
		<table  id="user" style="margin-top:2px;width:80%;height:80%;" align="center" >
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td colspan="3" align="center">
				<font style="font-size: 18px" color="#6495ED" id="title">---------员工信息---------&nbsp;&nbsp;&nbsp;</font>
			</td>
		</tr>
		<tr id="tr_userno" class="input">
			<td align="center">
				<font style="font-size: 14px">工  号:</font>
			</td> 
			<td align="left" colspan="2">
				<input style="width: 130px" id="txt_userno" name="userno" class="easyui-validatebox" value="<s:property value="%{userno}"/>" readonly="readonly"></input>
				<font style="color: red">(系统默认)</font>
			</td>
		</tr>
		<tr id="tr_name" class="input">
			<td align="center">
				<font style="font-size: 14px">员工姓名:</font>
			</td>
			<td align="left" colspan="2">
				<input style="width: 130px" id="txt_name" name="name" class="easyui-validatebox" value="<s:property value="%{name}" />">
				<font style="color: red">*</font>
			</td>
		</tr>
		<tr id="tr_password">
			<td align="center"><font style="font-size: 14px">密码:</font></td>
			<td align="left"><input readonly="readonly" id="txt_password"
				name="password" class="easyui-validatebox"
				data-options="validType:'minLength[6]',invalidMessage:'密码要6位以上'"
				type="password"/>
			<font style="color: red">*</font>
			<a href="javascript:void(0)" id="btn_edit_password" style="text-decoration: none;">修改</a>
			</td>
		</tr>
		<tr id="tr_passwordAgain">
			<td align="center"><font style="font-size: 14px">确认密码:</font></td>
			<td align="left"><input id="txt_passwordAgain" name="passwordAgain"
				id="txt_passwordAgain" class="easyui-validatebox"
				data-options="validType:'minLength[6]',invalidMessage:'密码要6位以上'"
				type="password"></input>
			<font style="color: red">*</font>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center" style="letter-spacing: 5px;font-size: 14px">性别:</td>
			<td colspan="2" align="left" colspan="2">
				<label for="male">男:</label>
				<input id="male" type="radio" name="gender" value="男" checked="checked">
				<label for="female">女:</label>
				<input id="female" type="radio" name="gender" value="女">
			</td>
		</tr>
		<tr >
			<td align="center" style="letter-spacing: 5px;font-size: 14px">所属角色:</td>
			<td colspan="2"> 
			<select style="width: 130px" class="easyui-combobox" name="roles_id" id="roles_id" >
			</select><font style="color: red">*</font>
			</td>
		</tr>
		</table>
	</form>
	</div>
  </body>
</html>
