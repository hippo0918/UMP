<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人员管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/bootstrap/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/Easyui-1.3.4/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/showloading/bootstrap/showLoading.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/easyui-loading.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/tree.extendsion.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/Easyui-1.3.4/window.extendsion.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/resizeWindow.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/showloading/jquery.showLoading.js" ></script>
	<script type="text/javascript" src='${pageContext.request.contextPath }/js/index-1.3.4.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/util.js"></script>
	<script type="text/javascript">
		var userId = "${sessionScope.loginUser.id}";
	</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true" scroll="no">
    <div region="north" split="false" noheader="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
        <font style="color: white">欢迎:</font>&nbsp;
        <label id="un">${sessionScope.loginUser.name}</label>
        &nbsp;&nbsp;
		<label style="color: white">|</label>
		&nbsp;&nbsp;
        <a href="#" id="logout" style="color: white;text-decoration: none;">安全退出</a>
        </span>
        <span style="padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 权限管理系统</span>
    </div>
    <div region="south" border="false" split="false" doSize="false" style="height: 40px; background: #F2F2F2;text-align: center">
        <div class="footer">Copyright:lizebin 邮箱:295612701@qq.com</div> 
    </div>
    <div style="width:180px;" id="west" data-options="iconCls:'icon-house',title:'&nbsp;导航菜单',headerCls:'layout_header',region:'west',split:true,minWidth:'180',maxWidth:'180'"></div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:#7D9EC0; font-size: 50px" >
				<!-- 实习练习演示，如有问题请联系：<br>295612701@qq.com -->
			</div>
		</div>
    </div>
   
<!-- tab右键菜单 -->
	<!-- <div id="mm" class="easyui-menu" style="width:150px;">
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
	</div> -->
</body>
</html>
