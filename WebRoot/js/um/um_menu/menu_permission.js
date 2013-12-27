var permissionTable = undefined;
$(function() {
	permissionTable = $("#permissionTable").datagrid({
		url : 'um/umMenuAction_viewPermission.do',
		fitColumns : true,
		fit : true,
		headerCls : "header",
		singleSelect : false,// 单选模式
		queryParams : {
			id: menuId,
			action : "findPermissionByMenuId"
		},
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
		} ] ],
		// 字体智能换行
		nowrap : true,
		border : false,
		pageSize : 25,
		// 是否可以伸缩
		collapsible : false,
		loadMsg : '数据加载中请稍后……',
		title : '权限信息列表',
		pagination : false,
		rownumbers : true,
		idField : "id",
		heigth:900,
		onLoadSuccess : function(data) {
			if(data.total == 0) {
				jq.messager.show({
					msg : "没有相应的权限",
					title : '提示'
				});
			};
			var rows = $(this).datagrid("getRows");
			for(var i in rows) {
				if(rows[i].checked) {
					$(this).datagrid("selectRow",i);						
				}
			}
		}
	});
});
function getCheckedRows() {
	var checkedRows = permissionTable.datagrid("getChecked");
	var permissionIds = new Array();
	for(var i in checkedRows) {
		permissionIds[i] = checkedRows[i].id;
	}
	return permissionIds;
}