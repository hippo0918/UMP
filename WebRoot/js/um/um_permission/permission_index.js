var permissionTable = undefined;
var iframe = $("<iframe width=\"100%\" height=\"100%\" name='iframe' id=\"iframe\" frameborder=\"0\"/>");
var window = undefined;
//ajax传数组使用传统模式
function openDialog(operation) {
	var jsp = "";
	var title = "";
	var op = new Array();
	op = operation.split(":");
	if (op[0] == "save") {
		title = "新增权限";
		jsp = "um/umPermissionAction_doNotNeedSecurityEdit.do?action=" + op[0];
	} else if (op[0] == "update") {
		title = "更新权限";
		jsp = "um/umPermissionAction_doNotNeedSecurityEdit.do?action=" + op[0] + "&" + "id=" + op[1];
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
		height : 350,
		width : 580,
		minimizable : false,
		modal : true,
		collapsible : false,
		maximizable : false,
		collapsible : true,
		draggable : true,
		resizable : true,
		contents : iframe,
		winId : 'permission_edit',
		onClose : function() {
			permissionTable.datagrid('unselectAll');
		},
		onComplete : function() {
			jq(this).mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout : 2000
			});
		},
		buttons:[{
				iconCls:'icon-save',
				text:'保存',
				handler:function(){
					formSubmit();
				}
		}]
	});
}
function formSubmit() {
	var form = iframe.contents().find("#form");
	form.form('submit', {
			url:iframe.contents().find("#formAction").val(),
			onSubmit : function() {
				//表单验证 ,要去掉中文的空格 
				var name = $.trim(iframe.contents().find("#txt_name").val());
				if(name.length != 0) {
					iframe.contents().find("#txt_name").val(name);				
				} else {
					jq.messager.alert("警告","请输入权限名称", 'waring');
					iframe.contents().find("#txt_name").val("");
					return false;
				}
				//code
				var code = $.trim(iframe.contents().find("#txt_code").val());
				if(code.length != 0) {
					iframe.contents().find("#txt_code").val(code);				
				} else {
					jq.messager.alert("警告","请输入权限代码", 'waring');
					iframe.contents().find("#txt_code").val("");
					return false;
				}
				return true;
			},
			success : function(data) {
				var contined = easyuiFormSubmitValidate(data);
				if(contined < 0) {
					jq("#permission_edit", parent.document).window("close");
					permissionTable.datagrid('reload');
					return;
				}
				var r = $.parseJSON(data);
				if(r.success) {
					$.messager.show({title:"提示", msg:r.msg, showType:'slide'});
					if(iframe.contents().find("#action").val() == "save") {
						permissionTable.datagrid('load');
					} else {
						permissionTable.datagrid('reload');
					}
					//表格加载完了再关闭窗口
					jq("#permission_edit", parent.document).window("close");
				} else {
					jq.messager.alert("警告", r.msg, 'waring');
				}
			}
	});
}
$(function() {
	permissionTable = $("#tablePermission").datagrid({
		url : 'um/umPermissionAction_query.do',
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
			title : '权限名称', 
			width : 150,
			align : 'center'
		}, {
			title : '权限代码',
			width : 200,
			align : 'center',
			field : 'code'
		}, {
			title : '描述',
			width : 200,
			align : 'center',
			field : 'description'
		}, {
			field : 'iconCls',
			title : '权限图标',
			width : 100,
			align : 'center',
			formatter: function(value,row,index){
				return '<span class="'+value+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>';
			}
		} ] ],
		// 字体智能换行
		nowrap : true,
		border : false,
		// 是否可以伸缩
		collapsible : false,
		loadMsg : '数据加载中请稍后……',
		title : '权限信息列表',
		pagination : true,
		rownumbers : true,
		idField : "id",
		heigth:900,
		onLoadSuccess : function(data) {
			if(data.total == 0) {
				jq.messager.show({
					msg : "没有相应的权限",
					title : '提示'
				});
			}
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
				rows = permissionTable.datagrid('getSelections');
				if (rows.length == 0 || rows.length > 1) {
					$.messager.show({
						msg : '请选择一条信息修改',
						title : '提示'
					});
					permissionTable.datagrid('unselectAll');
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
				$("#form").form('clear');
				permissionTable.datagrid('unselectAll');
			}
		} ]
	});
	
	$("#btn_search").on("click", function() {
		if($("#name").val().length == 0) {
			$("#name").val("");
		}
		if($("#code").val().length == 0) {
			$("#code").val("");
		}
		permissionTable.datagrid("load",serializeForm($("#form")));
		return false;
	});
	
	$("#btn_clear").on("click", function() {
		$("#form").form("clear");
		permissionTable.datagrid("unselectAll");
	});
	
	$('#form input').keyup(function(event){
		if(event && event.which==13){
			$("#btn_search").click();
			$('#form input').blur();
		} 
		return false;
	}); 
});
//作废
function zuoFei(){
	var rows = permissionTable.datagrid('getSelections');
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
					url:'um/umPermissionAction_delete.do',
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
							permissionTable.datagrid('reload');
							permissionTable.datagrid('unselectAll');
						} else {
							jq.messager.alert("提示", r.msg, 'waring');
							permissionTable.datagrid('unselectAll');
						}
					}
				});
			} else {
				permissionTable.datagrid('unselectAll');
			}
		});
	} else {
		jq.messager.alert("警告", "请选择一条记录!", 'waring');
		permissionTable.datagrid('unselectAll');
	}
};
