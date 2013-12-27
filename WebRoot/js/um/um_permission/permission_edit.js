$(function() {
	var iconClsSelect = $("#iconClsSelect").combobox({
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
		iconClsSelect.combobox("setValue", iconSelectValue);
	}
});