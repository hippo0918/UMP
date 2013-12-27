var onlyOpenTitle = "欢迎使用";
var opentabs = 4;
var menusTree = new Array();
//表示window加载完成
var loadedWindow = false;
//窗体加载完成
//var loadedPanel = false;
var _menus = {
	menus : [ {
		"menusTree":menusTree,
		"menuName" : "用户管理模块",
		"menuIcon" : "icon-user-group"
	} ]
};
window.onload = function() {
	jq.hideLoading();
	loadedWindow = true;
};
$(function() {
	$(window).resizeWindow(function() {
		if(!loadedWindow) {
			jq.hideLoading();
			jq.showLoading();
		}
	});
	$.ajax({
		url : 'um/umUserAction_doNotNeedSecurityGetUserRoleMenuPer.do',
		async:false,
		data : {
			'id':userId
		},
		timeout:8000,
		beforeSend:function() {
			jq.showLoading();
		},
		dataType : 'json',
		type:'post',
		success : function(rsp) {
			if(rsp.success) {
				var roles = $.parseJSON(rsp.msg).roleDtos;
				if(roles && roles.length == 0) {
					jq.messager.alert('操作提示','您还没有任何权限访问本系统，请联系初始化管理员，并重新登录！','warning',function() {	
						window.parent.location='#';
						top.location="login.jsp";
					});
				}
 				for (var i in $.parseJSON(rsp.msg).roleDtos) {
					var menus = roles[i].menus;
					for (var j in menus) {
						if(menus[j].pid == 0) {
							$.extend(menus[j],{
								"attributes":{
									"url" : menus[j].url,
									"icon" : menus[j].iconCls
								},
								"state":"closed"
							});
						} else {
							var isLeaf = true;
							for(var k in menus) {
								if(menus[k].pid == menus[j].id) {
									isLeaf = false;
								}
							}
							if(isLeaf) {
								$.extend(menus[j],{
									"attributes":{
										"url" : menus[j].url,
										"icon" : menus[j].iconCls
									},
									"state":"open"
								});
							} else {
								$.extend(menus[j],{
									"attributes":{
										"url" : menus[j].url,
										"icon" : menus[j].iconCls
									},
									"state":"closed"
								});
							}
						}
						var isExist = false;
						for(var m in menusTree) {
							if(menusTree[m].id == menus[j].id) {
								isExist = true;
								break;
							}
						}
						if(!isExist) {
							menusTree.push(menus[j]);
						} else {
							continue;
						}
					}
				}
				menusTree.sort(menusTreeComparator("id"));
				LeftMenuInit();
				tabInit();
			} else {
				window.location = rsp.obj;
			}
		}
	});
	//初始化退出链接
	$("#logout").on('click', function() {
		$.messager.confirm('确认框', '确定要退出系统吗?', function(r){
			if (r){
				$.ajax({
					url : 'um/umUserAction_doNotNeedSecurityLogout.do',
					data:{action : "quit"},
					dataType:'json',
					type : 'post',
					beforeSend:function() {
						$.showLoading();
					},
					success : function(data){
						if(data.success) {
							window.parent.location='#';
							$.hideLoading();
							top.location=data.url;
						}
					}
				});
			}
		});
	});
});
function LeftMenuInit() {
	$.each(_menus.menus, function(i, n) {
				var menuTree = $("<ul id='menuTree" + i + "'></ul>");
				menuTree = menuTree.tree({
							lines : true,
							animate : true,
							data : n.menusTree,
							parentField : "pid",
							textFiled : "name",
							idFiled : "id",
							iconCls : "iconCls",
							onCollapse : function(node) {
								menuTree.tree("unSelectAll").tree("collapseAll");
							},
							onClick : function(node) {
								if (node.attributes && node.attributes.url
										&& node.attributes.url.length != 0 && node.attributes.url != "#") {
									add_tab(node.text, node.attributes.url,
											node.attributes.icon);
								}
								$(this).tree("unSelectAll");
							},
							onLoadSuccess : function(node, data) {
								menuTree.tree("unSelectAll").tree("expandAll");
							}
						});
				$("#west").append(menuTree);
			});
}
function tabInit() {

}
function add_tab(subtitle, url, icon) {
	var s = $('<iframe scrolling="auto" class="iframe" name="iframe" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>');
	var tabCount = $('#tabs').tabs('tabs').length;
	var hasTab = $('#tabs').tabs('exists', subtitle);
	var add = function() {
		if (!hasTab) {
			$('#tabs').tabs('add', {
				title : subtitle,
				content : s,
				closable : true,
				icon : icon,
				selected : true,
				tools : [{
					iconCls : 'icon-mini-refresh',
					handler : function() {
						var currentTab = $('#tabs').tabs('getSelected');
						tab_reflesh(currentTab);
					}
				}]
			});
			var panel = $('#tabs').tabs("getSelected");
			var panelOptions = panel.panel("options");
			var loadedPanel = false;
			$.extend(panelOptions, {
				onResize:function(width, height) {
					if(!loadedPanel) {
						panel.mask("hide");
						panel.mask({
							maskMsg : "页面加载中...",
							opacity : 0.9,
							timeout : 4000
						});	
					}
				}
			});
			panel.css({"overflow-y" : "hidden"});
			panel.mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout : 4000
			});
			s.attr({"src" : url});
			if (s[0].attachEvent) {
				s[0].attachEvent("onload", function() {
					panel.mask("hide");
					loadedPanel = true;
				});
			} else {
				s[0].onload = function() {
					panel.mask("hide");
					loadedPanel = true;
				};
			}
		} else {
			$('#tabs').tabs('select', subtitle);
		}
	};
	if (tabCount > opentabs && !hasTab) {
		var msg = '<b>您当前打开了太多的页面,如果继续打开,会造成程序运行缓慢,无法流畅操作！</b>';
		$.messager.confirm("系统提示", msg, function(b) {
					if (b)
						add();
					else
						return false;
				});
	} else {
		add();
	};

	//tab_close();
}
function tab_reflesh(curTab) {
	var panel = curTab.panel('panel');
	var panelOptions = curTab.panel('options');
	/*var panelLoaded = false;
	$.extend(panelOptions, {
		onResize:function(width, height) {
			if(!panelLoaded) {
				panel.mask("hide");
				panel.mask({
					maskMsg : "页面加载中...",
					opacity : 0.8,
					timeout : 1500
				});	
			}
		}
	});*/
	panel.mask({
		maskMsg : "页面正在加载中...",
		opacity : 0.8,
		timeout:2000
	});	
	$('#tabs').tabs('update', {
		tab : curTab,
		options : curTab.panel('options')
	});
	//panelLoaded = true;
}
/*function tab_close() {
	 双击关闭TAB选项卡 
	$(".tabs-inner").dblclick(function() {
				var subtitle = $(this).children(".tabs-closable").text();
				$(this).find('#' + subtitle + '_iframe').attr({
							src : ""
						});
				$('#tabs').tabs('close', subtitle);
			});
	 为选项卡绑定右键 
	$(".tabs-inner").bind('contextmenu', function(e) {
				$('#mm').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
				var subtitle = $(this).children(".tabs-closable").text();
				$('#mm').data("currtab", subtitle);
				$('#tabs').tabs('select', subtitle);
				return false;
	});
}*/


function menusTreeComparator(propertyName) {
	return function(o1, o2) {
		var val1 = o1[propertyName];
		var val2 = o2[propertyName];
		
		if(val1 < val2) {
			return -1;
		} else if(val1 > val2) {
			return 1;
		} else {
			return 0;
		}
		
	};
}