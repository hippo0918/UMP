/* {
        "key": "j1",
        "pid": 9,
        "name": "Java"
    },
*/
$.fn.tree.defaults.loadFilter = function (data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled,textFiled,parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		
		var i,l,treeData = [],tmpMap = [];
		
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = "&nbsp;" + data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = "&nbsp;" + data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	
	return data;
};

$.extend($.fn.tree.methods,{
	unSelectAll:function(jq,target){
		return jq.each(function(){
			$(jq).find("li div.tree-node-selected").removeClass("tree-node-selected");
		});
	}
});
//��дtree��loader
$.extend($.fn.tree.defaults, {
    loader : function (param, success, error) {
        var opts = $(this).tree("options");
        if (!opts.url) {
            return false;
        }
        if(opts.queryParams){
            param = $.extend({},opts.queryParams,param);
        }
        $.ajax({
            type : opts.method,
            url : opts.url,
            data : param,
            dataType : "json",
            success : function (data) {
                success(data);
            },
            error : function () {
                error.apply(this, arguments);
            }
        });
    },
    queryParams:{}
});
//���ò���
$.extend($.fn.tree.methods, {
    setQueryParams : function (jq, params) {
        return jq.each(function () {
            $(this).tree("options").queryParams = params;
        });
    }
});
// �ڻ�ȡtree������chekced��ʱ��,������Ҫ�Ѹ��ڵ���checked״̬�����������checked���ӽڵ��޳�
$.extend($.fn.tree.methods, {
	getImperfectCheck : function (jq) {
		var checked = jq.find("span.tree-checkbox1");
		var nodes = [];
		for (var i = 0; i < checked.length; i++) {
			var node = $(checked[i]).parent();
			nodes.push(node.data('treeNode'));
			var children = node.next('ul');
			if (children.length > 0) {
				i += children.find("span.tree-checkbox1").length;
			}
		}
		return nodes;
	}
});
//�÷���
//var node = $().tree("getSelected");
//var lv =  $().tree("getLevel",node.target);
$.extend($.fn.tree.methods, {
	getLevel:function(jq,target){
		var l = $(target).parentsUntil("ul.tree","ul");
		return l.length+1;
	}
});