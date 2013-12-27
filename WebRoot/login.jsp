<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限管理系统登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/json2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/easyui-loading.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/tree.extendsion.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/window.extendsion.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/login.js" ></script> 

  </head>
  
  <body>
        <body style="width: 100%;height: 100%;overflow: hidden;padding: 0;margin: 0;">
      <form id="form-body" style="display: none;">
            <ul>
                <li><input id="user" class="form-radio-other-input" type="radio" name="admin" value="NO" checked="checked" style="vertical-align:bottom;"/>
                <label for="user">普通用户 </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input id="admin" class="form-radio-other-input" type="radio" name="admin" value="YES" style="vertical-align:bottom;"/> 
                <label for="admin">初始化管理员</label></li>
                <li><label>工	号 </label> <input class="easyui-validatebox account form-textbox" type="text" name="userno" required="required" /></li>
                <li><label>密	码 </label> <input class="easyui-validatebox  password form-textbox" type="password" name="password" required="required"/></li>
            </ul>
     </form>
       <div id="logo"  style="display: none;">
           <h1>权限管理演示系统</h1>
       </div>
    </body>

  </body>
</html>
