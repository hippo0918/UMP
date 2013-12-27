(function($) {
	$.fn.resizeWindow = function(f) {
		version = '1.1';
		resizeWindow = {
			fired : false,
			width : 0
		};
		this.each(function() {
			var app = getApp();
			if (this == window) {
				$(this).resize(function(event) {
					var event = event || window.event;
					var flag = true;
					if (app.appname == "msie") {
						if (!resizeWindow.fired) {
							resizeWindow.fired = true;
						} else {
							var version = parseInt(app.version, 10);
							resizeWindow.fired = false;
							if (version < 7) {
								flag = false;
							} else if (version == 7) {
								var width = $(window).width();
								if (width != resizeWindow.width) {
									resizeWindow.width = width;
									flag = false;
								}
							}
						}
					}
					if (flag) {
						return f.apply(this, [event]);
					}
				});
			} else {
				$(this).resize(f);
			}
		});
		
		return this;
	};
	var getApp = function() {
		 var browser = {
            msie: false, firefox: false, opera: false, safari: false, 
            chrome: false, netscape: false, appname: 'unknown', version: 0
        },
        userAgent = window.navigator.userAgent.toLowerCase();
	    if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){
	        browser[RegExp.$1] = true;
	        browser.appname = RegExp.$1;
	        browser.version = RegExp.$2;
	    } else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari
	        browser.safari = true;
	        browser.appname = 'safari';
	        browser.version = RegExp.$2;
	    }
	    return browser;
	};
})(jQuery);