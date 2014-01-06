/**
 * 工具方法
 */
var jq = top.jQuery;
//全局ajax控制，用于session超时 无权限时 提示
$.ajaxSetup({
	cache: false, //close AJAX cache
	contentType:"application/x-www-form-urlencoded;charset=utf-8", 
	complete:function(XHR,textStatus){  
		console.info("status = " + textStatus);
		jq.hideLoading();
        var resText = XHR.responseText;
        //登录超时
        if(resText=='sessionTimeOut'){   
        	sessionTimeOut();
        }
        //没有权限
        else if(resText=='notPriority'){   
        	notPriority();
        };   
    }
});
jq.ajaxSetup({
	cache: false, //close AJAX cache
	contentType:"application/x-www-form-urlencoded;charset=utf-8", 
	complete:function(XHR,textStatus){   
        var resText = XHR.responseText;
        //登录超时
        if(resText=='sessionTimeOut'){   
        	sessionTimeOut();
        }
        //没有权限
        else if(resText=='notPriority'){   
        	notPriority();
        }        
    } 
});
function sessionTimeOut(){
	jq.hideLoading();
	jq.messager.alert('操作提示','用户登录会话已过期，请重新登录！','warning',function() {	
		window.parent.location='#';
		top.location="login.jsp";
	});
}

function notPriority(){
	jq.messager.alert('操作提示','无相应操作权限，请联系系统管理员！','warning');
}
function easyuiFormSubmitValidate(data) {
	//easyui的form提交不是真正的AJAX提交，使用的iframe框架实现的无刷新
	var d = $.trim(data);
//	console.info($.trim(data));//这个是空串
//	console.info(data.length);
//	console.info("" == undefined);//false
//	console.info("" == false);//true
	if(d.length == 0) {
		return -1;
	}
	return 1;
}
jQuery.ajaxSettings.traditional = true;
// 处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e) {
	var ev = e || window.event;
	// 获取event对象
	var obj = ev.target || ev.srcElement;
	// 获取事件源
	var t = obj.type || obj.getAttribute('type');
	// 获取事件源类型
	// 获取作为判断条件的事件类型
	var vReadOnly = obj.readOnly;
	var vDisabled = obj.disabled;
	// 处理undefined值情况
	vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
	vDisabled = (vDisabled == undefined) ? true : vDisabled;
	// 当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	// 并且readOnly属性为true或disabled属性为true的，则退格键失效
	var flag1 = ev.keyCode == 8
			&& (t == "password" || t == "text" || t == "textarea")
			&& (vReadOnly == true || vDisabled == true);
	// 当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	var flag2 = ev.keyCode == 8 && t != "password" && t != "text"
			&& t != "textarea";
	// 判断
	if (flag2 || flag1)
		return false;
}
// 禁止退格键 作用于Firefox、Opera
document.onkeypress = banBackSpace;
// 禁止退格键 作用于IE、Chrome
document.onkeydown = banBackSpace;
$(function() {
	$.extend(
					$.fn.validatebox.defaults.rules,
					{
						minLength : { // 判断最小长度
							validator : function(value, param) {
								return value.length >= param[0];
							},
							message : '最少输入 {0} 个字符。'
						},
						phone : {// 验证电话号码
							validator : function(value) {
								return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
										.test(value);
							},
							message : '格式不正确,请使用下面格式:020-88888888'
						},
						mobile : {// 验证手机号码
							validator : function(value) {
								return /^(13|15|18)\d{9}$/i.test(value);
							},
							message : '手机号码格式不正确(正确格式如：13450774432)'
						},
						idcard : {// 验证身份证
							validator : function(value) {
								return /^\d{15}(\d{2}[A-Za-z0-9])?$/i
										.test(value);
							},
							message : '身份证号码格式不正确'
						},
						qq : {// 验证QQ,从10000开始
							validator : function(value) {
								return /^[1-9]\d{4,9}$/i.test(value);
							},
							message : 'QQ号码格式不正确(正确如：453384319)'
						},
						integer : {// 验证整数
							validator : function(value) {
								return /^[+]?[1-9]+\d*$/i.test(value);
							},
							message : '请输入整数'
						},
						chinese : {// 验证中文
							validator : function(value) {
								return /^[\u0391-\uFFE5]+$/i.test(value);
							},
							message : '请输入中文'
						},
						english : {// 验证英语
							validator : function(value) {
								return /^[A-Za-z]+$/i.test(value);
							},
							message : '请输入英文'
						},
						unnormal : {// 验证是否包含空格和非法字符
							validator : function(value) {
								return /.+/i.test(value);
							},
							message : '输入值不能为空和包含其他非法字符'
						},
						zip : {// 验证邮政编码
							validator : function(value) {
								return /^[1-9]\d{5}$/i.test(value);
							},
							message : '邮政编码格式不正确'
						},
						email : {
							validator : function(value) {
								return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
										.test(value);
							},
							message : '请输入有效的电子邮件账号(例：abc@126.com)'
						},
						msn : {
							validator : function(value) {
								return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
										.test(value);
							},
							message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
						},
						department : {
							validator : function(value) {
								return /^[0-9]*$/.test(value);
							},
							message : '请输入部门排序号(例：1)'
						},
						same : {
							validator : function(value, param) {
								if ($("#" + param[0]).val() != ""
										&& value != "") {
									return $("#" + param[0]).val() == value;
								} else {
									return true;
								}
							},
							message : '两次输入的密码不一致！'
						}
					});
	//设置光标停留的位置:$("input").position(4);
	$.fn.extend({
		myPosition:function( value ){
			var elem = this[0];
				if (elem&&(elem.tagName=="TEXTAREA"||elem.type.toLowerCase()=="text")) {
				   if($.browser.msie){
						   var rng;
						   if(elem.tagName == "TEXTAREA"){ 
							    rng = event.srcElement.createTextRange();
							    rng.moveToPoint(event.x,event.y);
						   }else{ 
						    	rng = document.selection.createRange();
						   }
						   if( value === undefined ){
						   	 rng.moveStart("character",-event.srcElement.value.length);
						     return  rng.text.length;
						   }else if(typeof value === "number" ){
						   	 var index=this.position();
							 index>value?( rng.moveEnd("character",value-index)):(rng.moveStart("character",value-index))
							 rng.select();
						   }
					}else{
						if( value === undefined ){
						   	 return elem.selectionStart;
						   }else if(typeof value === "number" ){
						   	 elem.selectionEnd = value;
	       			         elem.selectionStart = value;
						   }
					}
				}else{
					if( value === undefined )
					   return undefined;
				}
		}
	});
	//选中文本内容
	$.fn.mySelectRange = function(start, end){
	    return this.each(function(){
	        if (this.setSelectionRange) {
	            this.focus();
	            this.setSelectionRange(start, end);
	        }
	        else 
	            if (this.createTextRange) {
	                var range = this.createTextRange();
	                range.collapse(true);
	                range.moveEnd('character', end);
	                range.moveStart('character', start);
	                range.select();
	            }
	    });
	};
	//移除验证和还原验证$('#id').validatebox('remove'); 
	$.extend($.fn.validatebox.methods, {  
		remove: function(jq, newposition){  
			return jq.each(function(){  
				$(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
			});  
		},
		reduce: function(jq, newposition){ 
			return jq.each(function(){  
			   var opt = $(this).data().validatebox.options;
			   $(this).addClass("validatebox-text").validatebox(opt);
			});  
		}	
	}); 
	$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				frame[0].contentWindow.document.write('');
				frame[0].contentWindow.close();
				frame.remove();
				if ($.browser.msie) {
					CollectGarbage();
				}
			} else {
				$(this).find('.combo-f').each(function() {
					var panel = $(this).data().combo.panel;
					panel.panel('destroy');
				});
			}
		} catch (e) {
		}
	};
});
function serializeForm(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
}
//简单过滤SQL关键字
function filterSQL(formInput) {
	$.each(formInput,function(index,value) {
		var val = $(value).val();
		val = val.replace(/'/g," ");
		val = val.replace(/[ ]/g,""); //去除中间空格
		$(value).val($.trim(val));
	});
}

var iconData = [{
		value : 'icon-add',
		text : 'icon-add'
	}, {
		value : 'icon-edit',
		text : 'icon-edit'
	}, {
		value : 'icon-remove',
		text : 'icon-remove'
	}, {
		value : 'icon-save',
		text : 'icon-save'
	}, {
		value : 'icon-cut',
		text : 'icon-cut'
	}, {
		value : 'icon-ok',
		text : 'icon-ok',
		selected : true
	}, {
		value : 'icon-no',
		text : 'icon-no'
	}, {
		value : 'icon-cancel',
		text : 'icon-cancel'
	}, {
		value : 'icon-reload',
		text : 'icon-reload'
	}, {
		value : 'icon-search',
		text : 'icon-search'
	}, {
		value : 'icon-print',
		text : 'icon-print'
	}, {
		value : 'icon-help',
		text : 'icon-help'
	}, {
		value : 'icon-undo',
		text : 'icon-undo'
	}, {
		value : 'icon-redo',
		text : 'icon-redo'
	}, {
		value : 'icon-back',
		text : 'icon-back'
	}, {
		value : 'icon-sum',
		text : 'icon-sum'
	}, {
		value : 'icon-tip',
		text : 'icon-tip'
	}, {
		value : 'icon-user_del',
		text : 'icon-user_del'
	}, {
		value : 'icon-user_zuoFei',
		text : 'icon-user_zuoFei'
	}, {
		value : 'icon-user_edit',
		text : 'icon-user_edit'
	}, {
		value : 'icon-user_add',
		text : 'icon-user_add'
	}, {
		value : 'icon-user_go',
		text : 'icon-user_go'
	}, {
		value : 'icon-jieYueZheng',
		text : 'icon-jieYueZheng'
	}, {
		value : 'icon-house',
		text : 'icon-house'
	}, {
		value : 'icon-import',
		text : 'icon-import'
	}, {
		value : 'icon-export',
		text : 'icon-export'
	}, {
		value : 'icon-user',
		text : 'icon-user'
	}, {
		value : 'icon-user-group',
		text : 'icon-user-group'
	}, {
		value : 'icon-user-earth',
		text : 'icon-user-earth'
	}];
