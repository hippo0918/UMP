var menusTable = undefined;
var rolesSelect = undefined;
var treegridData = undefined;
$(function() {
	menusTable = $("#menusTable").treegrid({
		url : 'um/umRoleAction_viewMenu.do',
		fitColumns : true,
		treeField:'name',
		idField:'id',
		rownumbers:true,
		remoteSort:false,
		fitColumns:true,
		autoRowHeight:false,
		fit:true,
		noheader:false,
		title : "角色可访问管理",
		border:false,
		animate:false,
		singleSelect:false,
		headerCls : "header",
		columns : [ [{
			checkbox:true
		},{
			field : 'name',
			title : '名&nbsp;称',
			width : 60,
			align : 'center'
		}, {
			field : 'pid',
			hidden : true
		}, {
			title : '链&nbsp;接',
			width : 120,
			align : 'center',
			field : 'url'
		} ] ],
		onLoadSuccess : function(data) {
			
		},
		loadFilter : function(d, parentId) {
			treegridData = d.rows;
			var data = d.rows;
			var idFiled = "id",textFiled = "name",parentField = "pid";
			var i,l,treeData = [],tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idFiled]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				var isLeaf = true, state = "open";
				for(var j=0; j<data.length; j++) {
					if(data[i].id == data[j].pid) {
						isLeaf = false;
					}
				}
				if(!isLeaf) {
					state = "open";
				}
				$.extend(data[i], {
					"state" : state
				});
				if(data[i][parentField] == "0") {
					data[i][parentField] == parseInt("0");
				}
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children']) {
						tmpMap[data[i][parentField]]['children'] = [];
					}
					data[i]['text'] = "&nbsp;" + data[i][textFiled];
					data[i]['_parentId'] = parseInt(data[i][parentField]);
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					//父菜单
					data[i]['text'] = "&nbsp;" + data[i][textFiled];
					data[i]['_parentId'] = parseInt(data[i][parentField]);
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
	});
	
	rolesSelect = $("#rolesSelect").combobox({
		panelHeight:200,
		editable : false,
		valueField : 'id',
		textField : 'name',
		multiple:false,
		loader : function(param, success, error) {
			 $.post('um/umRoleAction_doNotNeedSecurityFind.do?action=find_role',function(data){
                data.push({
                 	id : "请选择角色",
					name : "请选择角色",
					selected : true
				});
				success(data);
            },'JSON').error(function(){
                $.messager.alert('警告','登录失败');
            });
		},
		onSelect : function(r) {
			menusTable.treegrid("unselectAll").treegrid("unCheckedAll");
			getMenuByRoleSelect(r.id);
		}
	});
	
	$("#save").on("click", function() {
		var checkedRows = menusTable.treegrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return;
		}
		var ids = new Array();
		for(var i in checkedRows) {
			//如果选择了子菜单也要选择父菜单
			var flag = false;
			if(checkedRows[i].pid != "0") {
				for(var k in checkedRows) {
					if(checkedRows[k].id == checkedRows[i].pid) {
						flag = true;
					}
				}
			} else {
				flag = true;
			}
			if(flag) {
				ids[i] = checkedRows[i].id;
			} else {
				jq.messager.alert("提示", '你选择了子菜单，但没有选择父菜单，请重新选择', 'waring');
				getMenuByRoleSelect(rolesSelect.combobox('getValue'));
				return false;
			}
		}
		if(rolesSelect.combobox('getValue') == "0" || rolesSelect.combobox('getValue') == "请选择角色") {
			return;
		}
		$.ajax({
			url : 'um/umRoleAction_saveMenu.do',
			data : {
				'rolesId':rolesSelect.combobox('getValue'),
				'menusId':ids.join(",")
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
				jq.messager.alert("提示", r.msg, 'info', function() {
					jq("#role_edit", parent.document).window("close");
				});
			}
		});
	});
	
	var getMenuByRoleSelect = function(id) {
		if(id == "请选择角色") {
			jq.messager.alert('警告','请选择角色');
			return;
		}
		$.ajax({
			url : 'um/umRoleAction_doNotNeedSecurityGetMenusByRoles.do',
			data : {
				'roleId':id
			},
			beforeSend:function() {
				jq.showLoading({
					message : '正在获取角色关联菜单...'
				});
			},
			timeout:10000,
			async:false,
			dataType : 'json',
			type:'post',
			success : function(r) {
				for(var j in r) {
					for(var i in treegridData) {
						if(treegridData[i].id == r[j].id) {
							menusTable.treegrid("selectRow",treegridData[i].id);						
						}
					}
				}
			}
		});
	};
});