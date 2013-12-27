/* 
 *  Document   : mask 1.1
 *  Created on : 2011-12-11, 14:37:38
 *  Author     : GodSon
 *  Email      : wmails@126.com
 *  Link       : www.btboys.com 
 *  
 */
//��ʱ��

(function($){
    function init(target,options){
        var wrap = $(target);
		if($("div.mask",wrap).length) wrap.mask("hide");
        wrap.attr("position",wrap.css("position"));
		wrap.attr("overflow",wrap.css("overflow"));
        wrap.css("position", "relative");
		wrap.css("overflow", "hidden");
        
		var width,height,left,top;
		if(target == 'body'){
			width = Math.max(document.documentElement.clientWidth, document.body.clientWidth);
			height = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
		}else{
			width = wrap.outerWidth() || "100%";
			height = wrap.outerHeight() || "100%";
		}
		
        $('<div id="mask-id" class="mask"></div>').addClass("datagrid-mask").css($.extend({},{
            display : 'block',
            width : width,
            height : height,
            opacity:options.opacity,
            filter:"alpha(opacity="+options.opacity*90+")",
            zIndex:options.zIndex
        })).appendTo(wrap);

		var maskMsg = $('<div id="mask-msg-id" class="mask-msg"></div>').html(options.maskMsg).appendTo(wrap).addClass("datagrid-mask-msg");
		if(target == 'body'){
			left = (Math.max(document.documentElement.clientWidth,document.body.clientWidth) - $('div.mask-msg', wrap).outerWidth())/ 2;
			if(document.documentElement.clientHeight > document.body.clientHeight){
				top = (Math.max(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.mask-msg', wrap).outerHeight())/ 2;
			}else{
				top = (Math.min(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.mask-msg', wrap).outerHeight())/ 2;
			}
			
		}else{
			left = (wrap.width() - $('div.mask-msg', wrap).outerWidth())/ 2;
			top = (wrap.height() - $('div.mask-msg', wrap).outerHeight())/ 2;
		}
        maskMsg.css({
        	fontSize:12,
        	textAlign : "center",
            display : 'block',
            zIndex:options.zIndex+1,
            left : left,
            top :  top,
            opacity:0.7,
            filter:"alpha(opacity=70)"
        });
        
        t = setTimeout(function(){
            wrap.mask("hide");
        }, options.timeout);
            
        return wrap;
    }
       
    $.fn.mask = function(options){   
        if (typeof options == 'string'){
            return $.fn.mask.methods[options](this);
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init(this,options);
    };
    $.mask = function(options){  
        if (typeof options == 'string'){
            return $.fn.mask.methods[options]("body");
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init("body",options);
    };
	
	$.mask.hide = function(){
		$("body").mask("hide");
	};
    $.fn.mask.methods = {
        hide : function(jq) {
            return jq.each(function() {
                var wrap = $(this);
                $("div.mask",wrap).fadeOut(500,function(){
                    $(this).remove();
                });
                $("div.mask-msg",wrap).fadeOut(500,function(){
                    $(this).remove();
                    wrap.css("position",  wrap.attr("position"));
					wrap.css("overflow", wrap.attr("overflow"));
                });
            });
        }
    };
    $.fn.mask.defaults = {
        maskMsg:'页面正在加载中...',
        zIndex:100000,
        timeout:30000,
        opacity:0.6
    };
})(jQuery);