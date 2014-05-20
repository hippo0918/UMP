var menuId = "";
var permissions = new Array();
$(function() {
	var menuTree = undefined;
	if(roleId && roleId.length != 0) {
		menuTree = $("#menuTree").tree({
			url:"um/umRoleAction_doNotNeedSecurityGetRoleMenuPerByRoleId.do?roleId=" + roleId,
			lines : true,
			animate : true,
			parentField : "pid",
			textFiled : "name",
			idFiled : "id",
			iconCls : "iconCls",
			onCollapse : function(node) {
				menuTree.tree("unSelectAll").tree("collapseAll");
			},
			onSelect : function(node) {
				menuId = menuTree.tree("getSelected").id;
				permissionTable.datagrid("reload", {
					menuId : menuId
				});
				if (node.attributes && node.attributes.permissions
						&& node.attributes.permissions.length != 0) {
					permissions = node.attributes.permissions;
				} else {//很大的bug
					permissions = new Array();
				}
			},
			onLoadSuccess : function(node, data) {
				//menuTree.tree("unSelectAll").tree("collapseAll");
				if(!data || data.length == 0) {
					jq.messager.alert("警告", "此角色没有访问的菜单", 'waring');
				}
			},
			loadFilter : function (role, parent) {
				var data = role.menus;
				//data包括角色可见的菜单，及所在菜单的权限
				var opt = $(this).data().tree.options;
				var idFiled,textFiled,parentField;
				idFiled = opt.idFiled || 'id';
				textFiled = opt.textFiled || 'text';
				parentField = opt.parentField;
				
				var i,l,treeData = [],tmpMap = [];
				
				for (i = 0, l = data.length; i < l; i++) {
					tmpMap[data[i][idFiled]] = data[i];
				}
				
				for (i = 0, l = data.length; i < l; i++) {
					if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
						if (!tmpMap[data[i][parentField]]['children'])
							tmpMap[data[i][parentField]]['children'] = [];
						data[i]['text'] = "&nbsp;" + data[i][textFiled];
						tmpMap[data[i][parentField]]['children'].push(data[i]);
					} else {
						data[i]['text'] = "&nbsp;" + data[i][textFiled];
						treeData.push(data[i]);
					}
					$.extend(data[i],{
						attributes: {
							permissions : data[i].permissions,
							url : data[i].url,
							validate : data[i].validate
						}
					});
				}
				return treeData;
			}
		});
	};
	var permissionTable = $("#permission").datagrid({
		url : 'um/umRoleAction_doNotNeedSecurityGetpermissionsByMenuId.do',
		fitColumns : true,
		queryParams:{
			menuId : menuId
		},
		fit : true,
		headerCls : "header",
		singleSelect : false,// 单选模式
		width:750,
		columns : [ [{
			field : 'name',
			title : '权限名称', 
			width : 50,
			align : 'center'
		} ] ],
		// 字体智能换行
		nowrap : true,
		border : false,
		// 是否可以伸缩
		collapsible : false,
		loadMsg : '数据加载中请稍后……',
		noheader:true,
		pagination : false,
		rownumbers : true,
		idField : "id",
		onLoadSuccess : function(data) {
			//easyui有缓存。。。
			$(this).datagrid("unselectAll");
			selectNode(data.rows, permissions);
		},toolbar : [{
			text : "保 存 权 限",
			iconCls : "icon-user-earth",
			handler : function() {
				//openDialog("save");
				saveRoleMenuPer(menuTree);
			}
		}]
	});
	
	var saveRoleMenuPer = function(menuTree) {
		var checkedRows = permissionTable.datagrid("getSelections");
		if(menuTree.tree("getSelected") == null || menuTree.tree("getSelected") == "undefined" || menuTree.tree("getSelected").length == 0) {
			permissionTable.datagrid("unselectAll");
			jq.messager.alert("提示", "请选中一个所属菜单菜单!", 'waring');
			return;
		} else {
			menuId = menuTree.tree("getSelected").id;
		}
		var permissionsId = new Array();
		for(var i in checkedRows) {
			permissionsId[i] = checkedRows[i].id;
		};
		
		$.ajax({
			url : 'um/umRoleAction_savePermission.do',
			data : {
				'roleId':roleId,
				'permissionsId':permissionsId,
				'menuId':menuId
			},
			timeout:10000,
			beforeSend:function() {
				jq.showLoading({
					message : '正在保存数据...'
				});
			},
			async:false,
			dataType : 'json',
			type:'post',
			success : function(r) {
				jq.messager.alert("提示", r.msg, 'waring');
				window.location.reload();
			}
		});
	};
	
	//data：menu所具有的权限，permissions：角色拥有的权限
	function selectNode(data, permissions) {
		for (var i in permissions) {
			for (var j in data) {
				if (permissions[i].id == data[j].id) {
					permissionTable.datagrid("selectRow", j);
				}
			}
		}
	}
	
	
});