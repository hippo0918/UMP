var combotreeData = undefined;
$(function() {
	var pidSelect = $("#pidSelect").combotree({
		editable:false,
		mode : "remote",
		url : "um/umMenuAction_doNotNeedSecurityGetMenusForSelect.do",
		method:"post",
		valueField:'id',    
		disabled:false,
		textField:'name',
		parentField:'pid',
		onHidePanel: function() {
			var t = $(this).combotree('tree');
			var node = t.tree('getSelected');
			if(node) {
				$("#pid").val(node.id);
			}
		},
		loadFilter : function(data, parentId) {
			combotreeData = data;
			var idFiled,textFiled,parentField;
			idFiled = 'id';
			textFiled = 'name';
			parentField = "pid";
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
					//修改设置父菜单
					if(pidSelectValue.length > 0 && data[i].id == pidSelectValue) {
						state = "open";
						$.extend(data[i], {
							"selected" : true
						});
						//设置隐藏域的pid值
						$("#pid").val(pidSelectValue);
					}
					if(pidSelectValue.length > 0 && pidSelectValue == "0") {
						state = "closed";
						$.extend(data[i], {
							"selected" : true
						});
						//设置隐藏域的pid值
						$("#pid").val(pidSelectValue);
					}
					state = "closed";
				}
				$.extend(data[i], {
					"state" : state
				});
				if(data[i][parentField] == "0") {
					data[i][parentField] == parseInt("0");
				}
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textFiled];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textFiled];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
	});
	if(pidSelectValue != null && pidSelectValue != undefined && pidSelectValue.length != 0) {
		pidSelect.combotree("setValue", pidSelectValue);
	}
	var iconSelect = $("#iconSelect").combobox({
		editable:false,
		mode : "local",
		valueField:'value',    
		disabled:false,
		editable:false,
		textField:'text',
		data:iconData,
		formatter:function(row) {
			return '<span class="'+row.value+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>&nbsp' + row.text;
		}
	});
	if(iconSelectValue && iconSelectValue.length != 0) {
		iconSelect.combobox("setValue", iconSelectValue);
	}
});