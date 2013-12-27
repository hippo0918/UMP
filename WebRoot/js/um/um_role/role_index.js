var jq = top.jQuery;
var iframe = $("<iframe width=\"100%\" height=\"100%\" name='iframe' id=\"iframe\" frameborder=\"0\"/>");
var window = jq('<div id="role_edit"></div>');
var datagrid = $("<table id='#table_role' />");
function openDialog(operation) {
	var jsp = "";
	//窗体标题
	var title = "";
	//窗体宽度
	var windowWidth = 480;
	//窗体高度
	var windowHeight = 350;
	var op = new Array();
	op = operation.split(":");
	if (op[0] == "save") {
		title = "新增角色";
		jsp = "um/umRoleAction_doNotNeedSecurityEdit.do?action=" + op[0];
		btnText = "保存";
	} else if (op[0] == "update") {
		title = "更新角色";
		jsp = "um/umRoleAction_doNotNeedSecurityEdit.do?action=" + op[0] + "&" + "id=" + op[1];
		btnText = "更新";
	} else if(op[0] == "view"){
		title = "查看角色详细信息";
		//id是角色id
		jsp = "um/umRoleAction_view.do?id=" + op[1];
		windowWidth = 860;
		windowHeight = 500;
	} else if (op[0] == "permission") {
		title = "对[<font color='#0081C2' style='font-size:14px'>"+ op[2] +"</font>]进行授权";
		//id是角色id
		jsp = "um/umRoleAction_viewPermission.do?id=" + op[1];
		windowWidth = 500;
		windowHeight = 480;
	} else if (op[0] == "mngVisableMenu") {
		title = "<font color='#0081C2' style='font-size:14px'>"+ "为角色分配可访问菜单" +"</font>";
		windowWidth = 860;
		windowHeight = 600;
		jsp = "um/umRoleAction_doNotNeedSecurityMngVisableMenu.do";
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
	
	//初始化窗口
	window = jq.window({
		title : title,
		height : windowHeight,
		width : windowWidth,
		minimizable : false,
		modal : true,
		collapsible : false,
		maximizable : false,
		collapsible : true,
		draggable : true,
		resizable : false,
		contents : iframe,
		winId : 'role_edit',
		onBeforeClose : function() {
			if(iframe.contents().find("#action").val() == "save") {
				datagrid.datagrid('load');
			} 
			if(iframe.contents().find("#action").val() == "update") {
				datagrid.datagrid('reload');
			}
			datagrid.datagrid('unselectAll');
		},
		onComplete : function() {
			jq(this).mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout : 2500
			});
		}
	});
	
}

$(function() {
	 datagrid = $('#table_role').datagrid({
			url : 'um/umRoleAction_query.do',
			queryParams : {
				action : 'loadDataGrid_role'
			},
			fit : true,
			fitColumns : true,
			border:false,
			columns : [ [ {
				field : 'id',
				hidden : true
			}, {
				field : 'name',
				title : '角 色 名 称',
				width : 18,
				align : 'center',
				sortable:true
			}, {
				field : 'description',
				title : '角 色 描 述',
				width : 40,
				align : 'center'
			},{
				field : "opt",
				title : '操          作',
				width : 50,
				align : 'center',
				formatter: function(value,row,index){
					//row.id
					var s = '<a href="#" iconCls="icon-search" style="margin-top:3px;margin-bottom:3px;" class="easyui-linkbutton" onclick=view(event,"'+row.id+'")>查看</a>&nbsp;';
				 	var u = '<a href="#" iconCls="icon-edit" class="easyui-linkbutton" onclick=update(event,"'+row.id+'")>修改</a>&nbsp;';
				 	var d = '<a href="#" iconCls="icon-remove" class="easyui-linkbutton" onclick=del(event,"'+row.id+'")>删除</a>&nbsp;';
				 	var p = '<a href="#" iconCls="icon-user-earth" class="easyui-linkbutton" onclick=per(event,"'+row.id+'","'+ row.name +'")>查看授权</a>';
				 	return s + u + d + p;
				 		
			}}] ],
			title:"角色信息列表",
			// 字体智能换行
			nowrap : true,
			striped : true,// 看文档
			// 是否可以伸缩
			collapsible : false,
			border : false,
			pageSize : 20,
			pageList : [ 20, 40],
			loadMsg : '数据加载中请稍后……',
			pagination : true,
			rownumbers : true,
			headerCls:"header",
			onLoadSuccess : function(data) {
				if(data.total == 0) {
					$.messager.show({
						msg : "没有相应的角色记录",
						title : '提示'
					});
				};
				$(".easyui-linkbutton").linkbutton();
			}
		});
		$("#btn_search").bind('click', function() {
			if($("#name").val().length == 0) {
				$("#name").val("");
			}
			if($("#description").val().length == 0) {
				$("#description").val("");
			}
			$("#action2").val("loadDataGrid_role");
			filterSQL($("#form2 input"));
			//序列化表单，datagrid自动发送异步请求
			datagrid.datagrid('load', serializeForm($("#form2")));
		});
		$("#btn_clear").bind('click',function() {
			$("#form2").form('clear');
			datagrid.datagrid('unselectAll');
		});
		//模糊查询回车事件
		$('#form2 input').keyup(function(event){
			if(event && event.which==13){
				$("#btn_search").click();
				$('#form2 input').blur();
			} 
			return false;
		}); 
		//作废
		function zuoFei(){
			var rows = datagrid.datagrid('getSelections');
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
					} else {
						datagrid.datagrid('unselectAll');
					}
				});
			} else {
				jq.messager.alert("警告", "请选择一条记录!", 'waring');
				datagrid.datagrid('unselectAll');
			}
		};
	});
	//查看角色
	var view = function(event,id) {
		var opt = "view:" + id;
		openDialog(opt);
		datagrid.datagrid('unselectAll');
		return false;
	};
	//更新角色
	var update = function(event,id) {
		event.stopPropagation(); 
		var opt = "update:" + id;
		openDialog(opt);
		datagrid.datagrid('unselectAll');
		return false;
	};
	//删除角色
	var del = function(event,id) {
		event.stopPropagation(); 
			jq.messager.confirm('确认框', '你确定要删除记录吗？', function(r) {
			if (r) {
				$.ajax({
					url : 'um/umRoleAction_delete.do',
					async : false,
					// 传到后台的参数
					data : {
						// 每一个元素用“，”隔开
						id : id,
						action : 'delete'
					},

					dataType : 'json',
					success : function(r) {
						if (r.success) {
							datagrid.datagrid('reload');
							datagrid.datagrid('unselectAll');
						} else {
							jq.messager
									.alert("提示", r.msg, 'waring');
							datagrid.datagrid('unselectAll');
						}
					}
				});
			} else {
				datagrid.datagrid('unselectAll');
			}
		});
		return false;
	};
	//保存角色
	var save = function() {
		var opt = "save";
		openDialog(opt);
	};
	
	//更新角色
	var per = function(event,id,name) {
		event.stopPropagation(); 
		var opt = "permission:" + id + ":" + name;
		openDialog(opt);
		datagrid.datagrid('unselectAll');
		return false;
	};
	
	var mngVisableMenu = function() {
		var opt = "mngVisableMenu";
		openDialog(opt);
		datagrid.datagrid('unselectAll');
		return false;
	};
