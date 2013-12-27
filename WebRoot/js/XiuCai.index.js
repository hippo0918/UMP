var onlyOpenTitle = "欢迎使用";// 不允许关闭的标签的标题
var opentabs = 3; //允许打开的TAB数量
$(function() {
	InitLeftMenu();
	tab_close();
	tabCloseEven();
});

// 初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({
		animate : true,
		fit : true,
		border : false,
		selected : false,
		onCollapse : function() {
			$('.selected').removeClass("selected");
		}
	});
	var selectedPanelname = '';
	$.each(_menus.menus, function(i, n) {
		var menulist = '';
		menulist += '<ul class="navlist">';
		$.each(n.menus, function(j, o) {
			menulist += '<li><div ><a ref="' + o.menuid + '" href="#" rel="'
					+ o.url + '" ><span class="icon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div> ';
			/*第二级菜单
			if (o.child && o.child.length > 0) {
				menulist += '<ul class="third_ul">';
				$.each(o.child, function(k, p) {
					menulist += '<li><div><a ref="' + p.menuid
							+ '" href="#" rel="' + p.url
							+ '" ><span class="icon ' + p.icon
							+ '" >&nbsp;</span><span class="nav">' + p.menuname
							+ '</span></a></div> </li>';
				});
				menulist += '</ul>';
			}
			*/
			menulist += '</li>';
		});
		menulist += '</ul>';

		$('#nav').accordion('add', {
			title : n.menuname,
			content : menulist,
			border : false,
			iconCls : 'icon ' + n.icon,
			selected : false,
			onCollapse : function() {
				$('.selected').removeClass("selected");
			}
		});

		if (i == 0)
			selectedPanelname = n.menuname;

	});

	$('.navlist li a').on("click", function() {
		var tabTitle = $(this).children('.nav').text();
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = $(this).find('.icon').attr('class');
		/*
		var third = f(menuid);
		if (third && third.child && third.child.length > 0) {
			$('.third_ul').slideUp();
			var ul = $(this).parent().next();
			if (ul.is(":hidden"))
				ul.slideDown();
			else
				ul.slideUp();
			// 设置ul下面的所有Li都要隐藏掉
		} else {
		*/
		add_tab(tabTitle, url, icon);
		$('.navlist li div').removeClass("selected");
		$(this).parent().addClass("selected");
		//}
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});

	// 选中第一个
	// var panels = $('#nav').accordion('panels');
	// var t = panels[0].panel('options').title;
	// $('#nav').accordion('select', t);
}
// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		$.each(n.menus, function(j, o) {
			if (o.menuid == menuid) {
				icon += o.icon;
			}
		});
	});

	return icon;
}

function f(menuid) {
	var obj = null;
	$.each(_menus.menus, function(i, n) {
		$.each(n.menus, function(j, o) {
			if (o.menuid == menuid) {
				obj = o;
			}
		});
	});

	return obj;
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
				selected : true
			});
			var panel = $('#tabs').tabs("getSelected");
			panel.css({"overflow-y":"hidden"});
			panel.mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout:1500
			});	
			s.attr({"src":url});
			if (s[0].attachEvent){ 
				s[0].attachEvent("onload", function(){ 
					panel.mask("hide");
			    });
			} else {
		    	s[0].onload = function(){
					panel.mask("hide");
		    	};
			}
		} else {
			$('#tabs').tabs('select', subtitle);
			$('#mm-tabupdate').click();
		}
	};
	if (tabCount > opentabs && !hasTab) {
	        var msg = '<b>您当前打开了太多的页面,如果继续打开,会造成程序运行缓慢,无法流畅操作！</b>';
	        $.messager.confirm("系统提示", msg, function (b) {
	            if (b) add();
	            else return false;
	        });
    } else {
        add();
    };

	tab_close();
}

function tab_close() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$(this).find('#'+subtitle+'_iframe').attr({src:""});
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
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
}

// 绑定右键菜单事件
function tabCloseEven() {
	$('#mm').menu({
		onClick : function(item) {
			close_tab(item.id);
		}
	});

	return false;
}

function close_tab(action) {
	var alltabs = $('#tabs').tabs('tabs');
	var currentTab = $('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs, function(i, n) {
		allTabtitle.push($(n).panel('options').title);
	});

	switch (action) {
	case "close":
		var currtab_title = currentTab.panel('options').title;
		$(this).find('#'+currtab_title+'_iframe').attr({src:""});
		$('#tabs').tabs('close', currtab_title);
		break;
	case "closeall":
		$.each(allTabtitle, function(i, n) {
			if (n != onlyOpenTitle) {
				$(this).find('#'+n+'_iframe').attr({src:""});
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeother":
		var currtab_title = currentTab.panel('options').title;
		$.each(allTabtitle, function(i, n) {
			if (n != currtab_title && n != onlyOpenTitle) {
				$(this).find('#'+n+'_iframe').attr({src:""});
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeright":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

		if (tabIndex == alltabs.length - 1) {
			alert('左边的是主界面，不能关闭');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i > tabIndex) {
				if (n != onlyOpenTitle) {
					$(this).find('#'+n+'_iframe').attr({src:""});
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;
	case "closeleft":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			alert('左边的选项卡不能关闭！');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i < tabIndex) {
				if (n != onlyOpenTitle) {
					$(this).find('#'+n+'_iframe').attr({src:""});
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;
	case "exit":
		$('#closeMenu').menu('hide');
		break;
	}
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
