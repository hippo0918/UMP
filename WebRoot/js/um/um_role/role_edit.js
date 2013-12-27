$(function() {
	if (jq("#role_edit", parent.document).window("options").title == '新增角色') {
		$("#update").css({
			display : "none"
		});
	}
	if (jq("#role_edit", parent.document).window("options").title == '更新角色') {
		$("#save").css({
			display : "none"
		});
	}
	$("#update").bind("click", function() {
		formSubmit();
		return false;
	});

	$("#save").bind("click", function() {
		formSubmit();
		return false;
	});

	$("#close").bind("click", function() {
		jq("#role_edit", parent.document).window("destroy");
	});
});

function formSubmit() {
	// var form = $("#txt_userno",);
	var form = $("#form");
	form.form('submit', {
		url : $("#formAction").val(),
		onSubmit : function() {
			var name = $.trim($("#txt_name").val());
			if (name.length != 0) {
				$("#txt_name").val(name);
			} else {
				jq.messager.alert("警告", "请输入角色名称", 'waring');
				$("#txt_name").val("");
				return false;
			}
			// 获取textarea的值
			var description = $.trim($("#txt_description").val());
			if (description.length != 0) {
				$("#txt_description").val(description);
			} else {
				jq.messager.alert("警告", "请输入角色描述", 'waring');
				$("#txt_description").val("");
				return false;
			}
			if (!$("#update").linkbutton("options").disabled) {
				$("#update").linkbutton('disable');
			}
			if (!$("#save").linkbutton("options").disabled) {
				$("#save").linkbutton('disable');
			}
			return true;
		},
		success : function(data) {
			var contined = easyuiFormSubmitValidate(data);
			if (contined < 0) {
				jq("#role_edit", parent.document).window("close");
				jq("#table_role", parent.document).datagrid('reload');
				return;
			}
			var r = $.parseJSON(data);
			// 添加一个角色之后，要重定向到主页，那样就看不到添加成功的信息了。。。
			if (r.success) {
				if (jq("#role_edit", parent.document).window("options").title == '新增角色') {
					jq("#table_role", parent.document).datagrid('load');
				}
				if (jq("#role_edit", parent.document).window("options").title == '更新角色') {
					jq("#table_role", parent.document).datagrid('reload');
				}
				// 表格加载完了再关闭窗口
				jq.messager.alert("提示", r.msg, 'waring', function() {
							jq("#role_edit", parent.document).window("close");
						});
			} else {
				jq.messager.alert("警告", r.msg, 'waring', function() {
					if ($("#update").linkbutton("options").disabled) {
						$("#update").linkbutton('enable');
					}
					if ($("#save").linkbutton("options").disabled) {
						$("#save").linkbutton('enable');
					}
				});
			}
		}
	});
}