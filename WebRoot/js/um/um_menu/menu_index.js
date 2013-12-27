var menusTable = undefined;
var rolesSelect = undefined;
var iframe = $("<iframe width=\"100%\" height=\"99%\" name='iframe' id=\"iframe\" frameborder=\"0\"/>");
//ajax传数组使用传统模式
jQuery.ajaxSettings.traditional = true;
function openDialog(operation) {
	var jsp = "";
	var title = "";
	var op = new Array();
	op = operation.split(":");
	if (op[0] == "save") {
		title = "新增菜单";
		jsp = "um/umMenuAction_doNotNeedSecurityEdit.do?action=" + op[0];
	} else if (op[0] == "update") {
		title = "更新菜单";
		jsp = "um/umMenuAction_doNotNeedSecurityEdit.do?action=" + op[0] + "&" + "id=" + op[1];
	} else if (op[0] == "permissionDispatch") {
		title = "[" + op[2] + "]" + "分配权限";
		jsp = "um/umMenuAction_viewPermission.do?action=" + op[0] + "&" + "id=" + op[1];
	} else {
		return;
	}

	iframe.attr({
		src :  jsp,
		width : '100%',
		height : '100%',
		frameborder : '0',
		name : op[0],
		id : "iframe"
	});
	window = jq.window({
		title : title,
		height : 500,
		width : 600,
		minimizable : false,
		modal : true,
		collapsible : false,
		maximizable : false,
		collapsible : true,
		draggable : true,
		resizable : true,
		contents : iframe,
		winId : 'menu_edit',
		onClose : function() {
			menusTable.datagrid('unselectAll');
		},
		onComplete : function() {
			jq(this).mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout : 2500
			});
		},
		buttons:[{
				id:op[0],
				iconCls:'icon-save',
				text:'保存',
				handler:function(){
					if(op[0] == "permissionDispatch") {
						saveMenuPermission();
					} else if(op[0] == "save"){
						formSubmit();
					} else if(op[0] == "update") {
						formSubmit();
					} else {
						return false;
					}
				}
		}]
	});
}
function saveMenuPermission() {
	var permissionIds = iframe[0].contentWindow.getCheckedRows();;
	var menuId = iframe.contents().find("#menuId").val();
	$.ajax({
		url : "um/umMenuAction_savePermission.do",
		data : {
			permissionIds : permissionIds,
			id : menuId
		},
		dataType:"json",
		type:"post",
		async:false,
		success:function(data) {
			if(data.success) {
				$.messager.show({title:"提示", msg:data.msg, showType:'slide'});
			} else {
				jq.messager.alert("提示", data.msg, 'waring');
			}
			jq("#menu_edit", parent.document).window("close");
		}
	});
}
function formSubmit() {
	var form = iframe.contents().find("#form");
	form.form('submit', {
			url:iframe.contents().find("#formAction").val(),
			onSubmit : function() {
				var name = $.trim(iframe.contents().find("#txt_name").val());
				if(name.length != 0) {
					iframe.contents().find("#txt_name").val(name);
				} else {
					jq.messager.alert("警告", "请输入菜单名称", 'waring');
					iframe.contents().find("#txt_name").val("");
					return false;
				}
				var action = $.trim(iframe.contents().find("#txt_action").val());
				if(action.length != 0) {
					iframe.contents().find("#txt_action").val(action);
				} else {
					jq.messager.alert("警告", "请输入菜单名称", 'waring');
					iframe.contents().find("#txt_action").val("");
					return false;
				}
				//父菜单
				var pid = $.trim(iframe.contents().find("#pid").val());
				if(pid.length != 0) {
					iframe.contents().find("#pid").val(pid);
				} else {
					jq.messager.alert("警告", "请输入父菜单名称", 'waring');
					iframe.contents().find("#pid").val("");
					return false;
				}
				
				return true;
			},
			success : function(data) {
				var contined = easyuiFormSubmitValidate(data);
				if(contined < 0) {
					jq("#menu_edit", parent.document).window("close");
					menusTable.datagrid('reload');
					return;
				}
				var r = $.parseJSON(data);
				//添加一个角色之后，要重定向到主页，那样就看不到添加成功的信息了。。。
				if(r.success) {
					$.messager.show({title:"提示", msg:r.msg, showType:'slide'});
					if(iframe.contents().find("#action").val() == "save") {
						menusTable.datagrid('load');
					} else {
						menusTable.datagrid('reload');
					}
					//表格加载完了再关闭窗口
					jq("#menu_edit", parent.document).window("close");
				} else {
					jq.messager.alert("警告", r.msg, 'waring');
				}
			}
	});
}
$(function() {
	menusTable = $("#menusTable").datagrid({
		url : 'um/umMenuAction_query.do',
		fitColumns : true,
		fit : true,
		headerCls : "header",
		singleSelect : false,// 单选模式
		width:750,
		columns : [ [ {
			title : '选择',
			checkbox : true
		},{
			field : 'name',
			title : '菜单名称',
			width : 120,
			align : 'center'
		}, {
			field : 'pid',
			hidden : true
		}, {
			title : '菜单链接',
			width : 200,
			align : 'center',
			field : 'url'
		}, {
			field : 'iconCls',
			title : '菜单图标',
			width : 50,
			align : 'center',
			formatter: function(value,row,index){
				return '<span class="'+value+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>';
			}
		},{
			field : "opt",
			title : '操          作',
			width : 50,
			align : 'center',
			formatter: function(value,row,index){
				var disabled = false;
			 	var p = '<a href="#" id="'+row.id+'" data-options="disabled:'+disabled+'" iconCls="icon-user-earth" style="margin-top:2px;margin-bottom:2px;" class="easyui-linkbutton" onclick=per(event,"'+row.id+'","'+row.name+'")>查看授权</a>';
				if(row.url == '#') {
					disabled = true;
					p = '<a href="#" id="'+row.id+'" data-options="disabled:'+disabled+'" iconCls="icon-user-earth" style="margin-top:2px;margin-bottom:2px;" class="easyui-linkbutton" onclick=per(event,"'+row.id+'","'+row.name+'")>查看授权</a>';
				}
			 	return p;
		}} ] ],
		// 字体智能换行
		nowrap : true,
		border : false,
		// 是否可以伸缩
		collapsible : false,
		loadMsg : '数据加载中请稍后……',
		title : '菜单信息列表',
		pagination : true,
		rownumbers : true,
		idField : "id",
		heigth:900,
		onLoadSuccess : function(data) {
			if(data.total == 0) {
				jq.messager.show({
					msg : "没有相应的菜单",
					title : '提示'
				});
			};
			$(".easyui-linkbutton").linkbutton();
		},
		toolbar : [{
			text : "新　增",
			iconCls : "icon-add",
			handler : function() {
				openDialog("save");
			}
		}, '-', {
			text : "修　改",
			iconCls : "icon-edit",
			handler : function() {
				rows = menusTable.datagrid('getSelections');
				if (rows.length == 0 || rows.length > 1) {
					$.messager.show({
						msg : '请选择一条信息修改',
						title : '提示'
					});
					menusTable.datagrid('unselectAll');
				} else if (rows.length == 1) {
					openDialog("update:" + rows[0].id);
				}
			}
		}, '-', {
			text : "删    除",
			iconCls : "icon-remove",
			handler : function() {
				zuoFei();
				$("#form").form('clear');
			}
		}, '-', {
			text : "取消选择",
			iconCls : "icon-redo",
			handler : function() {
				menusTable.datagrid('unselectAll');
			}
		} ]
	});
		
	$("#btn_search").on("click", function() {
		menusTable.datagrid("load",serializeForm($("#form")));
	});
	
	$("#btn_clear").on("click", function() {
		$("#form").form("clear");
		menusTable.datagrid("unselectAll");
	});
	
	$('#form input').keyup(function(e){
		if(e.which==13){
			$("#btn_search").click();
			$('#form input').blur();
		} 
		return false;
	}); 
});

//作废
function zuoFei(){
	var rows = menusTable.datagrid('getSelections');
	if (rows.length > 0) {
		jq.messager.confirm(
				'确认框',
				'你确定要作废记录吗？',
				function(r){
			if (r){
				var ids = [];
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					type:"post",
					url:'um/umMenuAction_delete.do',
					async:false,
					//传到后台的参数
					data:{
						//每一个元素用“，”隔开
						ids:ids.join(',')
					},
					dataType:'json',
					success:function(r){
						if(r.success) {
							$.messager.show({
								title:'提示窗口',
								msg:r.msg
							});
							menusTable.datagrid('reload');
							menusTable.datagrid('unselectAll');
						} else {
							jq.messager.alert("提示", r.msg, 'waring');
							menusTable.datagrid('unselectAll');
						}
					}
				});
			} else {
				menusTable.datagrid('unselectAll');
			}
		});
	} else {
		jq.messager.alert("警告", "请选择一条记录!", 'waring');
		menusTable.datagrid('unselectAll');
	}
};
//菜单分配权限
var per = function(event, menuId, name) {
	event.stopPropagation(); 
	var opt = "permissionDispatch:" + menuId + ":" + name;
	openDialog(opt);
	menusTable.datagrid('unselectAll');
	return false;
};