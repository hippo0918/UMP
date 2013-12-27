$.extend($.fn.datagrid.defaults.editors, {   
	progressbar: {   
		init: function(container, options){   
			var bar = $('<div/>').appendTo(container);
			bar.progressbar(options); 				
			return bar;   
		},   
		getValue: function(target){   
			return $(target).progressbar('getValue');   
		},
		setValue: function(target, value){   
			$(target).progressbar('setValue',value);   
		},
		resize: function(target, width){    
			if ($.boxModel == true){   
				$(target).progressbar('resize',width - (input.outerWidth() - input.width()));
			} else {   
				$(target).progressbar('resize',width);
			}   
		}   
	},
	slider: {   
		init: function(container, options){   
			var slider = $('<div/>').appendTo(container);
			slider.slider(options); 				
			return slider;   
		},   
		getValue: function(target){   
			return $(target).slider('getValue');   
		},
		setValue: function(target, value){   
			$(target).slider('setValue',value);   
		},
		resize: function(target, width){    
			if ($.boxModel == true){   
				$(target).progressbar('slider',{width:width - (input.outerWidth() - input.width())});
			} else {   
				$(target).progressbar('slider',{width:width});
			}   
		}   
	}				
}); 

/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 *   在easyui.min.js之后导入本js
 *   代码案例:
 *		$("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *		$("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 */
$.extend($.fn.datagrid.methods, {
	tooltip : function (jq, fields) {
		return jq.each(function () {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object') {
				$.each(fields, function () {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] div.datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body div.datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function () {
				var content = $(this).text();
				$(this).tooltip({
					content : content,
					trackMouse : true,
					//deltaX:parseInt($(this).width)/2,
					onHide : function () {
						$(this).tooltip('destroy');
					}
				}).tooltip('show');
			});
		}
	}
});

 $.extend($.fn.datagrid.defaults.editors, {
     datetimebox: {//datetimebox������Ҫ�Զ���editor�����
         init: function(container, options){
             var input = $('<input class="easyuidatetimebox">').appendTo(container);
             return input.datetimebox({
                 formatter:function(date){
                     return new Date(date).format("yyyy-MM-dd hh:mm:ss");
                 }
             });
         },
         getValue: function(target){
             return $(target).parent().find('input.combo-value').val();
        },
         setValue: function(target, value){
             $(target).datetimebox("setValue",value);
         },
         resize: function(target, width){
            var input = $(target);
            if ($.boxModel == true){
                 input.width(width - (input.outerWidth() - input.width()));
            } else {
                 input.width(width);
             }
         }
     }
});
Date.prototype.format = function(format){
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    if(!format){
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
           "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
           "S": this.getMilliseconds()
            // millisecond
   };

   if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) { 
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" +o[k]).length));
       }
    }
    return format;
};
$.extend($.fn.datagrid.methods,{
	unCheckedAll:function(jq,target){
		return jq.each(function(){
			var dpanel = $(this).datagrid('getPanel');
			dpanel.find("div.datagrid-header-check :checkbox").attr({checked : false});
		});
	}
});

//datagrid隐藏和显示按钮
	//$('#tt').datagrid("addToolbarItem",[{"text":"xxx"},"-",{"text":"xxxsss","iconCls":"icon-ok"}])
	//$('#tt').datagrid("removeToolbarItem","GetChanges")//根据btn的text删除
	//$('#tt').datagrid("removeToolbarItem",0)//根据下标删除
	$.extend($.fn.datagrid.methods, {
		addToolbarItem : function (jq, items) {
			return jq.each(function () {
				var dpanel = $(this).datagrid('getPanel');
				var toolbar = dpanel.children("div.datagrid-toolbar");
				if (!toolbar.length) {
					toolbar = $("<div class=\"datagrid-toolbar\"><table cellspacing=\"0\" cellpadding=\"0\"><tr></tr></table></div>").prependTo(dpanel);
					$(this).datagrid('resize');
				}
				var tr = toolbar.find("tr");
				for (var i = 0; i < items.length; i++) {
					var btn = items[i];
					if (btn == "-") {
						$("<td><div class=\"datagrid-btn-separator\"></div></td>").appendTo(tr);
					} else {
						var td = $("<td></td>").appendTo(tr);
						var b = $("<a href=\"javascript:void(0)\"></a>").appendTo(td);
						b[0].onclick = eval(btn.handler || function () {});
						b.linkbutton($.extend({}, btn, {
								plain : true
							}));
					}
				}
			});
		},
		removeToolbarItem : function (jq, param) {
			return jq.each(function () {
				var dpanel = $(this).datagrid('getPanel');
				var toolbar = dpanel.children("div.datagrid-toolbar");
				var cbtn = null;
				if (typeof param == "number") {
					cbtn = toolbar.find("a").eq(param).find('span.l-btn-text');
					//去掉那条杠
					$(toolbar.find("a").eq(param).next()[0]).remove();
				} else if (typeof param == "string") {
					cbtn = toolbar.find("span.l-btn-text:contains('" + param + "')");
				}
				if (cbtn && cbtn.length > 0) {
					cbtn.closest('a').remove();
					cbtn = null;
				}
			});
		}
	});
	 