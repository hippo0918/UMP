var iframe = $("<iframe width=\"100%\" height=\"100%\" name='iframe' id=\"iframe\" frameborder=\"0\"/>");
var window = $("#user_edit");
var datagrid = null;
//ajax传数组使用传统模式
jQuery.ajaxSettings.traditional = true;
function openDialog(operation) {
	var jsp = "";
	var title = "";
	var op = new Array();
	op = operation.split(":");
	if (op[0] == "save") {
		title = "新增用户";
		jsp = "um/umUserAction_doNotNeedSecurityEdit.do?action=" + op[0];
		$("#btn_edit_password").css({display:"none"});
	} else if (op[0] == "update") {
		title = "更新用户";
		$("#tr_passwordAgain").css({display:"none"});
		jsp = "um/umUserAction_doNotNeedSecurityEdit.do?action=" + op[0] + "&" + "id=" + op[1];
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
	//iframe加载完成
	if (iframe[0].attachEvent) {
		iframe[0].attachEvent("onload", function() {
			if (op[0] == "save") {
				iframe.contents().find("#btn_edit_password").css({display:"none"});
				iframe.contents().find("#txt_password").val("").prop({"readonly":false});
			} else if (op[0] == "update") {
				iframe.contents().find("#tr_passwordAgain").css({display:"none"});
				iframe.contents().find("#txt_password").val("******");
				iframe.contents().find("#btn_edit_password").on("click", function() {
					if($(this).html() == "修改") {
						$(this).html("取消");
						iframe.contents().find("#txt_password").val("");
						iframe.contents().find("#txt_password").val("").prop({"readonly":false});
						iframe.contents().find("#txt_passwordAgain").val("");
						//火狐
						iframe.contents().find("#tr_passwordAgain").css({display:"table-row"});
					} else {
						$(this).html("修改");
						iframe.contents().find("#txt_password").val("******");
						iframe.contents().find("#txt_password").val("").prop({"readonly":true});
						iframe.contents().find("#txt_passwordAgain").val("");
						iframe.contents().find("#tr_passwordAgain").css({display:"none"});
					}
				});
			} else {
				return;
			}
		});
	} else {
		iframe[0].onload = function() {
			if (op[0] == "save") {
				iframe.contents().find("#btn_edit_password").css({display:"none"});
				iframe.contents().find("#txt_password").val("").prop({"readonly":false});
			} else if (op[0] == "update") {
				iframe.contents().find("#tr_passwordAgain").css({display:"none"});
				iframe.contents().find("#txt_password").val("******");
				iframe.contents().find("#btn_edit_password").on("click", function() {
					if($(this).html() == "修改") {
						$(this).html("取消");
						iframe.contents().find("#txt_password").val("").prop({"readonly":false});
						iframe.contents().find("#txt_passwordAgain").val("");
						//火狐
						iframe.contents().find("#tr_passwordAgain").css({display:"table-row"});
					} else {
						$(this).html("修改");
						iframe.contents().find("#txt_password").val("").prop({"readonly":true});
						iframe.contents().find("#txt_password").val("******");
						iframe.contents().find("#txt_passwordAgain").val("");
						iframe.contents().find("#tr_passwordAgain").css({display:"none"});
					}
				});
			} else {
				return;
			}
		};
	}
	window = jq.window({
		title : title,
		height : 350,
		width : 480,
		minimizable : false,
		modal : true,
		collapsible : false,
		maximizable : true,
		collapsible : true,
		draggable : true,
		resizable : true,
		contents : iframe,
		winId : 'user_edit',
		onClose : function() {
			datagrid.datagrid('unselectAll');
		},
		onComplete : function() {
			jq(this).mask({
				maskMsg : "页面加载中...",
				opacity : 1.0,
				timeout : 1000
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
				var userno = $.trim(iframe.contents().find("#txt_userno").val());
				if(userno.length != 0) {
					iframe.contents().find("#txt_userno").val(userno);
				} else {
					jq.messager.alert("警告","请输入工号", 'waring');
					iframe.contents().find("#txt_userno").val("");
					return false;
				}
				var name = $.trim(iframe.contents().find("#txt_name").val());
				if(name.length != 0) {
					iframe.contents().find("#txt_name").val(name);				
				} else {
					jq.messager.alert("警告","请输入姓名", 'waring');
					iframe.contents().find("#txt_name").val("");
					return false;
				}
				
				if(iframe.contents().find("#tr_passwordAgain").css('display') == "table-row" || iframe.contents().find("#tr_passwordAgain").css('display') == "block") {
					if(iframe.contents().find("#txt_passwordAgain").val().length < 6 || iframe.contents().find("#txt_password").val().length < 6) {
						jq.messager.alert("警告", '密码至少要6位', 'waring');
						iframe.contents().find("#txt_password").val("");
						iframe.contents().find("#txt_passwordAgain").val("");
						iframe.contents().find("#txt_password").focus();
						return false;
					} else {
						if(iframe.contents().find("#txt_passwordAgain").val() != iframe.contents().find("#txt_password").val()) {
							iframe.contents().find("#txt_password").val("");
							iframe.contents().find("#txt_passwordAgain").val("");
							jq.messager.alert("警告", '两次密码不一致', 'waring');
							iframe.contents().find("#txt_password").focus();
							return false;
						}
					}
				}
			},
			success : function(data) {
				var contined = easyuiFormSubmitValidate(data);
				if(contined < 0) {
					jq("#user_edit", parent.document).window("close");
					datagrid.datagrid('reload');
					return;
				}
				var r = $.parseJSON(data);
				if(r.success) {
					jq.messager.alert("提示", r.msg, 'waring',function() {
						if(iframe.contents().find("#action").val() == "save") {
							datagrid.datagrid('load');
						} else {
							datagrid.datagrid('reload');
						}
						//表格加载完了再关闭窗口
						jq("#user_edit", parent.document).window("close");
					});
				} else {
					jq.messager.alert("警告", r.msg, 'waring');
				}
			}
	});
}
$(function() {
	 var rows = null;
	 datagrid = $('#table_user').datagrid({
			url : 'um/umUserAction_query.do',
			queryParams : {
				action : 'loadDataGrid_user'
			},
			fit : true,
			fitColumns : true,
			border:false,
			singleSelect : false,// 单选模式
			columns : [ [ {
				field : 'id',
				hidden : true
			}, {
				field : 'userno',
				title : '工    号',
				width : 15,
				align : 'center',
				sortable:true
			}, {
				field : 'password',
				title : '密    码',
				width : 15,
				align : 'center',
				formatter:function(value, row, index) {
					return "******";
				}
			}, {
				field : 'name',
				title : '姓    名',
				width : 15,
				align : 'center',
				sortable:true
			}, {
				field : 'gender',
				title : '性    别',
				width : 6,
				align : 'center'
			}, {
				field : 'createDate',
				title : '创建日期',
				width : 15,
				order : "desc",
				align : 'center',
				sortable:true
			}, {
				field : 'roles_name',
				title : '所   属  角  色',
				width : 40,
				fitColumns:true,
				align : 'center',
				formatter: function(value,row,index){
					//拼接此用户关联的角色名称
					var role_name = new Array();
					if(row.roleDtos.length > 0) {
						for(var i=0; i<row.roleDtos.length; i++) {
							role_name[i] = row.roleDtos[i].name;
						};
					} ;
					return role_name.join(",");
				}
			}] ],
			title:"员工信息列表",
			// 字体智能换行
			nowrap : true,
			striped : true,// 看文档
			// 是否可以伸缩
			collapsible : false,
			border : false,
			pageSize : 10,
			pageList : [ 10, 20, 30,40 ],
			loadMsg : '数据加载中请稍后……',
			pagination : true,
			//rownumbers : true,
			headerCls:"header",
			onLoadSuccess : function(data) {
				if(data.total == 0) {
					$.messager.show({
						msg : "没有相应的员工记录",
						title : '提示'
					});
				};
				datagrid.datagrid('tooltip',["roles_name"]);
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
					rows = datagrid.datagrid('getSelections');
					if (rows.length == 0 || rows.length > 1) {
						$.messager.show({
							msg : '请选择一条信息修改',
							title : '提示'
						});
						datagrid.datagrid('unselectAll');
					} else if (rows.length == 1) {
						openDialog("update:" + rows[0].id);
					}
				}
			}, '-', {
				text : "删    除",
				iconCls : "icon-remove",
				handler : function() {
					zuoFei();
					$("#form2").form('clear');
				}
			}, '-', {
				text : "取消选择",
				iconCls : "icon-redo",
				handler : function() {
					$("#form2").form('clear');
					datagrid.datagrid('unselectAll');
				}
			} ]
		});
		//用于搜索
		/*$("#select_role").combobox({
			panelHeight:150,
			editable : false,
			mode : 'remote',
			valueField : 'id',
			textField : 'name',
			multiple:true,
			url:'um/umUserAction_doNotNeedSecurityFind.do?action=find_role'
		});*/
		
		$("#btn_search").bind('click', function() {
			if($("#userno").val().length == 0) {
				$("#userno").val("");
			}
			if($("#name").val().length == 0) {
				$("#name").val("");
			}
			$("#action2").val("loadDataGrid_user");
			filterSQL($("#form2 input"));
			datagrid.datagrid('load', {
				"action":$("#action2").val(),
				"userno":$("#userno").val(),
				"name":$("#name").val()
				//"roles_id":jQuery("#select_role").combobox("getValues")
			});
			//$("#form2").form('clear');
		});
		$("#btn_clear").bind('click',function() {
			$("#form2").form('clear');
			datagrid.datagrid('unselectAll');
		});
		//模糊查询回车事件
		$('#form2 input').keyup(function(event){
			if(event.which==13){
				$("#btn_search").click();
				$('#form2 input').blur();
			} 
			return false;
		}); 
		//点击
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
						$.ajax({
							url:'um/umUserAction_delete.do',
							async:false,
							//传到后台的参数
							data:{
								//每一个元素用“，”隔开
								ids:ids.join(','),
								action:'delete'
							},
							
							dataType:'json',
							success:function(r){
								if(r.success) {
									$.messager.show({
										title:'提示窗口',
										msg:r.msg
									});
									//reload向后台发出page=...rows=,更新当前页面信息
									datagrid.datagrid('reload');
									datagrid.datagrid('unselectAll');
								} else {
									jq.messager.alert("提示", r.msg, 'waring');
									datagrid.datagrid('unselectAll');
								}
							}
						});
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
