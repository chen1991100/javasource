/*Show Notification*/
function Notify(message, position, timeout, theme, icon, closable) {
	toastr.options.positionClass = 'toast-' + position;
	toastr.options.extendedTimeOut = 0; //1000;
	toastr.options.timeOut = timeout;
	toastr.options.closeButton = closable;
	toastr.options.iconClass = icon + ' toast-' + theme;
	toastr['custom'](message);
}
var App = {

	_dataSetName: "ids", //　表格中多选提交时参数的name
	// wrapper function to scroll(focus) to an element
	scrollTo: function(el, offeset) {
		pos = (el && el.size() > 0) ? el.offset().top : 0;
		jQuery('html,body').animate({
			scrollTop: pos + (offeset ? offeset : 0)
		}, 'slow');
	},

	// function to scroll to the top
	scrollTop: function() {
		App.scrollTo();
	},

	// wrapper function to  block element(indicate loading)
	blockUI: function(el, centerY) {
		var el = jQuery(el);
		if (el.height() <= 400) {
			centerY = true;
		}
		el.block({
			message: '<img src="' + _GLOBAL.ctx + 'assets/img/ajax-loading.gif" align="">',
			centerY: centerY != undefined ? centerY : true,
			css: {
				top: '10%',
				border: 'none',
				padding: '2px',
				backgroundColor: 'none'
			},
			overlayCSS: { 
				backgroundColor: '#000',
				opacity: 0.05,
				cursor: 'wait'
			}
		});
	},

	// wrapper function to  un-block element(finish loading)
	unblockUI: function(el, clean) {
		jQuery(el).unblock({
			onUnblock: function() {
				jQuery(el).css('position', '');
				jQuery(el).css('zoom', '');
			}
		});
	}
};

jQuery(function($) {
	//sometimes we try to use 'tap' event instead of 'click' if jquery mobile plugin is available
	/**
	 * 时间对象的格式化
	 */
	Date.prototype.format = function(format) {
			/*
			 * format="yyyy-MM-dd hh:mm:ss";
			 */
			var o = {
				"M+": this.getMonth() + 1,
				"d+": this.getDate(),
				"h+": this.getHours(),
				"m+": this.getMinutes(),
				"s+": this.getSeconds(),
				"q+": Math.floor((this.getMonth() + 3) / 3),
				"S": this.getMilliseconds()
			}

			if (/(y+)/.test(format)) {
				format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			}

			for (var k in o) {
				if (new RegExp("(" + k + ")").test(format)) {
					format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
				}
			}
			return format;
		}
		/*Sets Themed Colors Based on Themes*/
	String.prototype.endWith = function(str) {
		if (str == null || str == "" || this.length == 0 || str.length > this.length)
			return false;
		if (this.substring(this.length - str.length) == str)
			return true;
		else
			return false;
		return true;
	}

	String.prototype.startWith = function(str) {
		if (str == null || str == "" || this.length == 0 || str.length > this.length)
			return false;
		if (this.substr(0, str.length) == str)
			return true;
		else
			return false;
		return true;
	}
	themeprimary = getThemeColorFromCss('themeprimary');
	themesecondary = getThemeColorFromCss('themesecondary');
	themethirdcolor = getThemeColorFromCss('themethirdcolor');
	themefourthcolor = getThemeColorFromCss('themefourthcolor');
	themefifthcolor = getThemeColorFromCss('themefifthcolor');

	//Gets Theme Colors From Selected Skin To Use For Drawing Charts
	function getThemeColorFromCss(style) {
		var $span = $("<span></span>").hide().appendTo("body");
		$span.addClass(style);
		var color = $span.css("color");
		$span.remove();
		return color;
	}



	function InitiateSideMenu() {

		//Sidebar Toggler
		$(".sidebar-toggler").on('click', function() {
			$("#sidebar").toggleClass("hide");
			$(".sidebar-toggler").toggleClass("active");
			return false;
		});
		//End Sidebar Toggler

		//Sidebar Collapse
		var b = $("#sidebar").hasClass("menu-compact");
		$("#sidebar-collapse").on('click', function() {
			if (!$('#sidebar').is(':visible'))
				$("#sidebar").toggleClass("hide");
			$("#sidebar").toggleClass("menu-compact");
			$(".sidebar-collapse").toggleClass("active");
			b = $("#sidebar").hasClass("menu-compact");

			if ($(".sidebar-menu").closest("div").hasClass("slimScrollDiv")) {
				$(".sidebar-menu").slimScroll({
					destroy: true
				});
				$(".sidebar-menu").attr('style', '');
			}
			if (b) {
				$(".open > .submenu")
					.removeClass("open");
			} else {
				if ($('.page-sidebar').hasClass('sidebar-fixed')) {
					var position = (readCookie("rtl-support") || location.pathname == "/index-rtl-fa.html" || location.pathname == "index-rtl-ar.html") ? 'right' : 'left';
					$('.sidebar-menu').slimscroll({
						height: 'auto',
						position: position,
						size: '3px',
						color: themeprimary
					});
				}
			}
			//Slim Scroll Handle



		});
		//End Sidebar Collapse


		//Sidebar Menu Handle
		$(".sidebar-menu").on('click', function(e) {
			var menuLink = $(e.target).closest("a");
			if (!menuLink || menuLink.length == 0)
				return;
			if (!menuLink.hasClass("menu-dropdown")) {
				if (b && menuLink.get(0).parentNode.parentNode == this) {
					var menuText = menuLink.find(".menu-text").get(0);
					if (e.target != menuText && !$.contains(menuText, e.target)) {
						return false;
					}
				}
				return;
			}
			var submenu = menuLink.next().get(0);
			if (!$(submenu).is(":visible")) {
				var c = $(submenu.parentNode).closest("ul");
				if (b && c.hasClass("sidebar-menu"))
					return;
				c.find("> .open > .submenu")
					.each(function() {
						if (this != submenu && !$(this.parentNode).hasClass("active"))
							$(this).slideUp(200).parent().removeClass("open");
					});
			}
			if (b && $(submenu.parentNode.parentNode).hasClass("sidebar-menu"))
				return false;
			$(submenu).slideToggle(200).parent().toggleClass("open");
			return false;
		});
		//End Sidebar Menu Handle
	}

	function InitiateWidgets() {
		$(document).delegate('.widget-buttons *[data-toggle="maximize"]', "click", function(event) {
			event.preventDefault();
			var widget = $(this).parents(".widget").eq(0);
			var button = $(this).find("i").eq(0);
			var compress = "fa-compress";
			var expand = "fa-expand";
			if (widget.hasClass("maximized")) {
				if (button) {
					button.addClass(expand).removeClass(compress);
				}
				widget.removeClass("maximized");
				widget.find(".widget-body").css("height", "auto");
			} else {
				if (button) {
					button.addClass(compress).removeClass(expand);
				}
				widget.addClass("maximized");
				maximize(widget);
			}
		});

		$(document).delegate('.widget-buttons *[data-toggle="collapse"]', "click", function(event) {
			event.preventDefault();
			var widget = $(this).parents(".widget").eq(0);
			var body = widget.find(".widget-body");
			var button = $(this).find("i");
			var down = "fa-plus";
			var up = "fa-minus";
			var slidedowninterval = 300;
			var slideupinterval = 200;
			if (widget.hasClass("collapsed")) {
				if (button) {
					button.addClass(up).removeClass(down);
				}
				widget.removeClass("collapsed");
				body.slideUp(0, function() {
					body.slideDown(slidedowninterval);
				});
			} else {
				if (button) {
					button.addClass(down)
						.removeClass(up);
				}
				body.slideUp(slideupinterval, function() {
					widget.addClass("collapsed");
				});
			}
		});

		$(document).delegate('.widget-buttons *[data-toggle="dispose"]', "click", function(event) {
			event.preventDefault();
			var toolbarLink = $(this);
			var widget = toolbarLink.parents(".widget").eq(0);
			var disposeinterval = 300;
			widget.hide(disposeinterval, function() {
				widget.remove();
			});
		});
	}

	// Fullscreen Widget
	function maximize(widgetbox) {
		if (widgetbox) {
			var windowHeight = $(window).height();
			var headerHeight = widgetbox.find(".widget-header").height();
			widgetbox.find(".widget-body").height(windowHeight - headerHeight);
		}
	}

	/* Scroll To */
	function scrollTo(el, offeset) {
		var pos = (el && el.size() > 0) ? el.offset().top : 0;
		jQuery('html,body').animate({
			scrollTop: pos + (offeset ? offeset : 0)
		}, 'slow');
	}



	/*#region handle Settings*/
	function InitiateSettings() {
		if (readCookie("navbar-fixed-top") != null) {
			if (readCookie("navbar-fixed-top") == "true") {
				$('#checkbox_fixednavbar').prop('checked', true);
				$('.navbar').addClass('navbar-fixed-top');
			}
		}

		if (readCookie("sidebar-fixed") != null) {
			if (readCookie("sidebar-fixed") == "true") {
				$('#checkbox_fixedsidebar').prop('checked', true);
				$('.page-sidebar').addClass('sidebar-fixed');

				//Slim Scrolling for Sidebar Menu in fix state
				if (!$(".page-sidebar").hasClass("menu-compact")) {
					var position = (readCookie("rtl-support") || location.pathname == "/index-rtl-fa.html" || location.pathname == "index-rtl-ar.html") ? 'right' : 'left';
					$('.sidebar-menu').slimscroll({
						height: 'auto',
						position: position,
						size: '3px',
						color: themeprimary
					});
				}
			}

		}
		if (readCookie("breadcrumbs-fixed") != null) {
			if (readCookie("breadcrumbs-fixed") == "true") {
				$('#checkbox_fixedbreadcrumbs').prop('checked', true);
				$('.page-breadcrumbs').addClass('breadcrumbs-fixed');
			}
		}
		if (readCookie("page-header-fixed") != null) {
			if (readCookie("page-header-fixed") == "true") {
				$('#checkbox_fixedheader').prop('checked', true);
				$('.page-header').addClass('page-header-fixed');
			}
		}


		$('#checkbox_fixednavbar')
			.change(function() {
				$('.navbar')
					.toggleClass('navbar-fixed-top');

				if (($('#checkbox_fixedsidebar')
						.is(":checked"))) {
					$('#checkbox_fixedsidebar')
						.prop('checked', false);
					$('.page-sidebar')
						.toggleClass('sidebar-fixed');
				}

				if (($('#checkbox_fixedbreadcrumbs')
						.is(":checked")) && !($(this)
						.is(":checked"))) {
					$('#checkbox_fixedbreadcrumbs')
						.prop('checked', false);
					$('.page-breadcrumbs')
						.toggleClass('breadcrumbs-fixed');
				}

				if (($('#checkbox_fixedheader')
						.is(":checked")) && !($(this)
						.is(":checked"))) {
					$('#checkbox_fixedheader')
						.prop('checked', false);
					$('.page-header')
						.toggleClass('page-header-fixed');
				}
				setCookiesForFixedSettings();
			});

		$('#checkbox_fixedsidebar')
			.change(function() {

				$('.page-sidebar')
					.toggleClass('sidebar-fixed');

				if (!($('#checkbox_fixednavbar')
						.is(":checked"))) {
					$('#checkbox_fixednavbar')
						.prop('checked', true);
					$('.navbar')
						.toggleClass('navbar-fixed-top');
				}
				if (($('#checkbox_fixedbreadcrumbs')
						.is(":checked")) && !($(this)
						.is(":checked"))) {
					$('#checkbox_fixedbreadcrumbs')
						.prop('checked', false);
					$('.page-breadcrumbs')
						.toggleClass('breadcrumbs-fixed');
				}

				if (($('#checkbox_fixedheader')
						.is(":checked")) && !($(this)
						.is(":checked"))) {
					$('#checkbox_fixedheader')
						.prop('checked', false);
					$('.page-header')
						.toggleClass('page-header-fixed');
				}
				setCookiesForFixedSettings();

			});
		$('#checkbox_fixedbreadcrumbs')
			.change(function() {

				$('.page-breadcrumbs')
					.toggleClass('breadcrumbs-fixed');


				if (!($('#checkbox_fixedsidebar')
						.is(":checked"))) {
					$('#checkbox_fixedsidebar')
						.prop('checked', true);
					$('.page-sidebar')
						.toggleClass('sidebar-fixed');
				}
				if (!($('#checkbox_fixednavbar')
						.is(":checked"))) {
					$('#checkbox_fixednavbar')
						.prop('checked', true);
					$('.navbar')
						.toggleClass('navbar-fixed-top');
				}
				if (($('#checkbox_fixedheader')
						.is(":checked")) && !($(this)
						.is(":checked"))) {
					$('#checkbox_fixedheader')
						.prop('checked', false);
					$('.page-header')
						.toggleClass('page-header-fixed');
				}
				setCookiesForFixedSettings();

			});

		$('#checkbox_fixedheader')
			.change(function() {

				$('.page-header')
					.toggleClass('page-header-fixed');


				if (!($('#checkbox_fixedbreadcrumbs')
						.is(":checked"))) {
					$('#checkbox_fixedbreadcrumbs')
						.prop('checked', true);
					$('.page-breadcrumbs')
						.toggleClass('breadcrumbs-fixed');
				}

				if (!($('#checkbox_fixedsidebar')
						.is(":checked"))) {
					$('#checkbox_fixedsidebar')
						.prop('checked', true);
					$('.page-sidebar')
						.toggleClass('sidebar-fixed');
				}
				if (!($('#checkbox_fixednavbar')
						.is(":checked"))) {
					$('#checkbox_fixednavbar')
						.prop('checked', true);
					$('.navbar')
						.toggleClass('navbar-fixed-top');
				}

				setCookiesForFixedSettings();
			});
	}

	function setCookiesForFixedSettings() {
		createCookie("navbar-fixed-top", $('#checkbox_fixednavbar').is(':checked'), 100);
		createCookie("sidebar-fixed", $('#checkbox_fixedsidebar').is(':checked'), 100);
		createCookie("breadcrumbs-fixed", $('#checkbox_fixedbreadcrumbs').is(':checked'), 100);
		createCookie("page-header-fixed", $('#checkbox_fixedheader').is(':checked'), 100);

		var position = (readCookie("rtl-support") || location.pathname == "/index-rtl-fa.html" || location.pathname == "index-rtl-ar.html") ? 'right' : 'left';
		if ($('#checkbox_fixedsidebar').is(':checked')) {
			if (!$('.page-sidebar').hasClass('menu-compact')) {
				//Slim Scrolling for Sidebar Menu in fix state
				$('.sidebar-menu').slimscroll({
					position: position,
					size: '3px',
					color: themeprimary,
					height: 'auto',
				});
			}
		} else {
			if ($(".sidebar-menu").closest("div").hasClass("slimScrollDiv")) {
				$(".sidebar-menu").slimScroll({
					destroy: true
				});
				$(".sidebar-menu").attr('style', '');
			}
		}
	}
	/*#endregion handle Settings*/



	//Switch Classes Function
	function switchClasses(firstClass, secondClass) {

		var firstclasses = document.getElementsByClassName(firstClass);

		for (i = firstclasses.length - 1; i >= 0; i--) {
			if (!hasClass(firstclasses[i], 'dropdown-menu')) {
				addClass(firstclasses[i], firstClass + '-temp');
				removeClass(firstclasses[i], firstClass);
			}
		}

		var secondclasses = document.getElementsByClassName(secondClass);

		for (i = secondclasses.length - 1; i >= 0; i--) {
			if (!hasClass(secondclasses[i], 'dropdown-menu')) {
				addClass(secondclasses[i], firstClass);
				removeClass(secondclasses[i], secondClass);
			}
		}

		tempClasses = document.getElementsByClassName(firstClass + '-temp');

		for (i = tempClasses.length - 1; i >= 0; i--) {
			if (!hasClass(tempClasses[i], 'dropdown-menu')) {
				addClass(tempClasses[i], secondClass);
				removeClass(tempClasses[i], firstClass + '-temp');
			}
		}
	}


	//Add Classes Function
	function addClass(elem, cls) {
		var oldCls = elem.className;
		if (oldCls) {
			oldCls += " ";
		}
		elem.className = oldCls + cls;
	}

	//Remove Classes Function
	function removeClass(elem, cls) {
		var str = " " + elem.className + " ";
		elem.className = str.replace(" " + cls, "").replace(/^\s+/g, "").replace(/\s+$/g, "");
	}

	//Has Classes Function
	function hasClass(elem, cls) {
		var str = " " + elem.className + " ";
		var testCls = " " + cls + " ";
		return (str.indexOf(testCls) != -1);
	}



	function initBeyond() {
		//Handle RTL SUpport for Changer CheckBox
		$("#skin-changer li a").click(function() {
			createCookie("current-skin", $(this).attr('rel'), 10);
			window.location.reload();
		});

		//Checks Not to Do rtl-support for Arabic and Persian Demo Pages

		var rtlchanger = document.getElementById('rtl-changer');

		if (location.pathname != "/index-rtl-fa.html" && location.pathname != "index-rtl-ar.html") {
			if (readCookie("rtl-support")) {
				switchClasses("pull-right", "pull-left");
				switchClasses("databox-right", "databox-left");
				switchClasses("item-right", "item-left");
				$('.navbar-brand small img').attr('src', _GLOBAL.ctx + '/assets/img/logo-rtl.png');
				if (rtlchanger != null)
					document.getElementById('rtl-changer').checked = true;
			} else {
				if (rtlchanger != null)
					rtlchanger.checked = false;
			}

			if (rtlchanger != null) {
				rtlchanger.onchange = function() {
					if (this.checked) {
						createCookie("rtl-support", "true", 10);
					} else {
						eraseCookie("rtl-support");
					}
					setTimeout(function() {
						window.location.reload();
					}, 600);

				};
			}
		}
		/*Loading*/
		//		$(window)
		//		    .load(function () {
		setTimeout(function() {
			$('.loading-container')
				.addClass('loading-inactive');
		}, 1000);
		//		    });


		/*Account Area --> Setting Button*/
		$('#btn-setting')
			.on('click', function(e) {
				$('.navbar-account')
					.toggleClass('setting-open');
			});

		/*Toggle FullScreen*/
		$('#fullscreen-toggler')
			.on('click', function(e) {
				var element = document.documentElement;
				if (!$('body')
					.hasClass("full-screen")) {

					$('body')
						.addClass("full-screen");
					$('#fullscreen-toggler')
						.addClass("active");
					if (element.requestFullscreen) {
						element.requestFullscreen();
					} else if (element.mozRequestFullScreen) {
						element.mozRequestFullScreen();
					} else if (element.webkitRequestFullscreen) {
						element.webkitRequestFullscreen();
					} else if (element.msRequestFullscreen) {
						element.msRequestFullscreen();
					}

				} else {

					$('body')
						.removeClass("full-screen");
					$('#fullscreen-toggler')
						.removeClass("active");

					if (document.exitFullscreen) {
						document.exitFullscreen();
					} else if (document.mozCancelFullScreen) {
						document.mozCancelFullScreen();
					} else if (document.webkitExitFullscreen) {
						document.webkitExitFullscreen();
					}

				}
			});


		InitiateSideMenu();
		InitiateSettings();

	}



	// 初始化菜单
	var handleAjaxPage = function(url, title) {
		var pageContent = $('#_main-page-content');
		var pageContentBody = $('#_main-page-body');
		// App.blockUI(pageContent, false);
		$('.loading-container').removeClass('loading-inactive');
		setTimeout(function() {
			$('.loading-container')
				.addClass('loading-inactive');
		}, 200);

		$.ajax({
			type: "GET",
			cache: false,
			url: url,
			dataType: "html",
			success: function(res) {
				App.unblockUI(pageContent);
				_GLOBAL.clearAllIntervals();
				pageContentBody.html(res);
				//            	App.fixContentHeight(); // fix content height
				App.initAjax(); // initialize core stuff

				//初始化上一点击的INLINE类型的BUTTON
				if (_GLOBAL.initInline) {
					_GLOBAL.initInline = !clickLastInlineBtn();;
				}
			},
			error: function(xhr, ajaxOptions, thrownError) {
				pageContentBody.html('<h4>无法加载请求页面.</h4><br/>请尝试<a href="javascript:location.reload();">刷新页面</a>以解决该问题.');
				App.unblockUI(pageContent);
			},
			async: true
		});
	}

	var handleMenuClickUrl = function(url, menu) {
		if (url.endWith('/list')) {
			url += 'Page';
		}
		if (url.indexOf('/list?') != -1) {
			url = url.replace('/list?', '/listPage?');
		}
		$(window).unbind('hashchange');
		window.location.hash = '#!' + url;

		// 将当前按钮保存到全局变量
		_GLOBAL.lastMenu = menu;
		_GLOBAL.setLastUrl(url);

		setTimeout(bindHashchange, 1);
		return url;
	}


	var bindHashchange = function() {
		$(window).bind('hashchange', function(eve) {
			handleSideBarInitState();
		});
	}
	var breadCount = 0;
	var findTitleBread = function(bread, tmp) {
		breadCount++;
		if (breadCount >= 10) {
			return;
		}
		var pul = tmp.parent();

		if (!pul || pul.attr('id') == '_main_page_sidebar_menu') {
			return;
		}
		if (pul.hasClass('submenu')) {
			tmp = pul.prev();
			bread.unshift('<li><a href="javascript:void(0)">' + tmp.text().trim() + '</a></li>');
		} else {
			tmp = pul;
		}
		findTitleBread(bread, tmp);
	}
	var handleMenuTitle = function($this) {
			if ($this) {
				var bread = [];
				breadCount = 0;
				findTitleBread(bread, $this);
				var title = $this.text().trim();

				//    		var pageContentTitle = $('#_main-page-title');
				//    		pageContentTitle.html(title);
				var pageContentBread = $('#_main-page-bread');
				var homeNode = '<i class="fa fa-home" style="font-size:20px;color:#777;"></i> ';

				pageContentBread.html(homeNode + bread.join('') + "<li>" + title + "</li>");

			}
		}
		// Handle sidebar menu
	var handleSidebarMenu = function() {
		jQuery('#_main_page_sidebar_menu').on('click', 'li > a', function(e) {

			var url = $(this).attr("href");
			// 如果url === "###" 则表示当前菜单不可用
			if (url == "###" || url == "javascript:;") {
				return;
			}
			if ($(this).next().hasClass('submenu') == true) {

				return;
			}

			e.preventDefault();
		});

		// handle ajax links
		jQuery('#_main_page_sidebar_menu').on('click', ' li > a.ajaxify', function(e) {
			e.preventDefault();


			var url = $(this).attr("href");
			// 如果url === "###" 则表示当前菜单不可用
			if (url == "###" || url == "javascript:;") {
				return;
			}


			handleMenuTitle($(this));
			if ($(this).parents('li').hasClass('active')) {
				if (_GLOBAL.lastMenu) {
					_GLOBAL.lastMenu.parents('li').removeClass('active');
				}
			} else {

				var menuContainer = jQuery('#_main_page_sidebar_menu');
				var lastActive = menuContainer.find('li.active');
				if (lastActive) {
					lastActive.removeClass('active');
					lastActive.removeClass('open');
					var lastActiveUl = lastActive.find('ul.submenu');
					lastActiveUl.removeClass('open');
					lastActiveUl.slideUp(250);
				}
			}
			$(this).parents('li').addClass('active');
			//            $(this).parents('li').addClass('open');

			// 如果为list 则 转换为listPage ,为了适应单独ajax请求的列表页面
			url = handleMenuClickUrl(url, $(this));
			handleAjaxPage(url, $(this).text());

		});
	}



	// Handle logout
	var handleLogout = function() {
		// mozfullscreenerror event handler

		// toggle full screen
		function logout() {

			bootbox.confirm("确认要退出系统吗?", function(result) {
				if (result) {
					window.location.href = _GLOBAL.ctx + "admin_user/log_out";
				}
			});
		}

		$('#trigger_logout').click(function() {
			logout();
		});
	}



	/**
	 *  按钮触发事件响应数据的处理类型(data-handler)
	 *  "INLINE":用返回的html内容替换当前的portlet
	 *  "MESSAGE":返回简单的结果信息{content:''}
	 *  "CONFIRM" 确认
	 *  "MODAL":将返回的html以modal 的形式展示
	 *  
	 */
	var handleDataHandler = function() {
		handleMenu4INLINE(" [data-handler='INLINE']");
		handleMenu4MESSAGE(" [data-handler='MESSAGE']");
		handleMenu4CONFIRM(" [data-handler='CONFIRM']");
		handleMenu4CONFIRM2(" [data-handler='CONFIRM2']");
		handleMenu4MODAL(" [data-handler='MODAL']");
		handleMenu4PAGE(" [data-handler='PAGE']");
	}


	// 获取列表中选中的行的值集合
	var _getDataSetValues = function(dataSet) {
		var data = {};
		if (dataSet) {
			var values = [];
			$(dataSet).each(function() {
				if ($(this).attr("checked") || $(this).prop("checked")) {
					values.push($(this).val());
				}
			});
			if (values.length <= 0) {
				bootbox.alert("请选择数据进行该操作！");
				return false;
			}
			var valuesStr = values.toString();
			data[App._dataSetName] = valuesStr;
		}
		return data;
	}

	var handleMenu4INLINE = function(selector) {

		$(selector).unbind("click");
		$(selector).on('click', function(e) {

			e.preventDefault();
			var $portA = $(this).parents('.data-div');
			var $this = $(this);
			var $portAParent = $portA.parent();
			var url = $this.attr('href');
			var title = $this.attr('data-title');
			var form = $this.attr('data-form');
			var dataSet = $this.attr('data-set');
			var picon = $this.attr('portlet-icon');
			picon = picon ? picon : 'fa fa-edit';
			var data = _getDataSetValues(dataSet);
			if (!data) {
				return;
			}
			// 利用hash来记录上次点击的菜单URL
			_GLOBAL.setLastInlineUrl(url);


			App.blockUI($portAParent, false);
			//加载下一个portlet
			$portA.animate({
				right: '+200%',
				opacity: 'toggle'
			}, 'nomal', 'swing', function() {
				//		   			 App.blockUI($portAParent, false);
				$.ajax({
					type: "post",
					cache: false,
					url: url,
					data: data,
					dataType: "html",
					success: function(res) {
						App.unblockUI($portAParent);
						_GLOBAL.clearAllIntervals();
						var tmpl = res;
						if (title) {
							tmpl = [
								'<div class="widget ">',
								'<div class="widget-header bordered-bottom ">',
								'<span class="widget-caption">' + title + '</span>',
								'<div class="widget-buttons">',
								' <button href="javascript:;" class="btn  btn-xs remove" data-action="close" >返回</button>',
								'</div>',
								'</div>',
								'<div class="widget-body ' + (form ? 'form' : '') + '">',
								//		   									'<div class="widget-main">',
								res,
								//		   									'</div>',
								'</div>',
								'</div>'

							].join('');
						}

						$portA.after(tmpl);
						//		   					 App.fixContentHeight(); // fix content height
						App.initAjax(); // initialize core stuff

						// 管理返回按钮 
						var $portB = $portA.next();
						var $backMenu = $portB.find('.widget-header > .widget-buttons > .remove');
						var fnback = function(e) {
							e.preventDefault();

							$portB.hide('fast', 'swing', function() {
								$portB.empty();
								$portB.remove();
								$portA.animate({
									right: '+0%',
									opacity: 'toggle'
								}, 'fast', 'swing', function() {
									// 刷新 portlet
									//		   								 window.location.hash = window.location.hash.split("#!!")[0];
									//		   								 $portA.find('  .widget-buttons > .reload').click();
									$('#refresh-toggler').click();
								});
							});
						}
						$backMenu.unbind();
						$backMenu.on('click', fnback);
						var $backMenu2 = $portB.find('button.remove');

						if ($backMenu2) {
							$backMenu2.on('click', fnback);
						}

					},
					error: function(xhr, ajaxOptions, thrownError) {
						App.unblockUI($portAParent);
						$portAParent.html('<h4>无法加载请求页面.</h4><br/>请尝试<a href="javascript:location.reload();">刷新页面</a>以解决该问题.');
						//		   					 App.unblockUI($portAParent);
					},
					async: true
				});
			});

		})
	}
	var handleMenu4PAGE = function(selector) {
		$(selector).unbind("click");
		$(selector).on('click', function(e) {
			e.preventDefault();
			//	    		_GLOBAL.lastHandler=$(this);
			//	    		var $portA = $(this).parents('.portlet');
			var url = $(this).attr('href');
			//		   		 var dataSet =  $(this).attr('data-set');
			//	    		handleAjaxPage(url,$(this).attr('title'));
			//	    		var menu = jQuery('#_main_page_sidebar_menu').find( 'a.ajaxify[href="'+url+'"]');
			//	    		console.log(menu);
			//	    		menu.click();
			openSidebarMenu(url);
		})
	}
	var handleMenu4MODAL = function(selector) {
		$(selector).unbind("click");
		$(selector).on('click', function(e) {
			e.preventDefault();
			_GLOBAL.lastHandler = $(this);
			var $portA = $(this).parents('.wedget-box');
			var url = $(this).attr('href');
			var dataSet = $(this).attr('data-set');

			var modal = openModal({
				url: url,
				simple: $(this).attr('modal-simple'),
				title: $(this).attr('data-title'),
				size: $(this).attr('modal-size'),
				dataSet: dataSet
			});

		})
	}
	var handleMenu4MESSAGE = function(selector) {
		$(selector).unbind("click");
		$(selector).on('click', function(e) {
			e.preventDefault();


			var size = $(this).attr('modal-size') ? $(this).attr('modal-size') : '400';
			var title = $(this).attr('data-title') ? $(this).attr('data-title') : 'By System.';
			var content = $(this).attr('data-content') ? $(this).attr('data-content') : '';
			var icon = $(this).attr('modal-icon') ? $(this).attr('modal-icon') : 'fa-smile-o';
			var color = $(this).attr('modal-color') ? $(this).attr('modal-color') : 'blue';
			var tmpl = [
				'<div class="modal fade " tabindex="-1" data-width="' + size + '" data-replace="false">',
				'<div class="dashboard-stat ' + color + '">',
				'<div class="visual">',
				'<i class="fa ' + icon + '"></i>',
				'</div>',
				'<div class="details">',
				'<div class="number">' + content + '</div>',
				'<div class="desc">' + title + '</div>',
				'</div>',
				'</div>',
				'</div>'
			].join('');
			$(tmpl).modal().on('hidden.bs.modal', function(e) {
				// do something...
				$(this).empty();
			})
		})
	}

	var openModal = function(opt) {

		var data = _getDataSetValues(opt.dataSet);
		if (!data) {
			return;
		}
		//    	 $('body').modalmanager('loading');
		$.ajax({
			type: "post",
			cache: false,
			url: opt.url,
			dataType: "html",
			data: data,
			success: function(res) {
				// general settings
				var size = opt.size ? opt.size : '75%';
				var title = opt.title ? opt.title : '';
				var tmpl;
				if (opt.simple && opt.simple == 'true') {
					tmpl = [
						// data-replace 为true时 表示替换已经打开的modal，为false时表示新打开一个modal
						'<div class="modal fade " tabindex="-1" data-width="' + size + '" data-replace="false">',
						res,
						'</div>'

					].join('');
				} else {
					tmpl = [
						'<div class="modal  modal-info   fade " tabindex="-1" data-replace="false" data-width="' + size + '" >',
							'<div class="modal-dialog" style="width:' + size + '">',
								'<div class="modal-content ">',
									'<div class="modal-header ">',
											'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
												' <h4 class="modal-title" >',
													title ? title : '&nbsp;',
												'</h4>',
									'</div>',
									'<div class="modal-body">',
										res,
									'</div>',
								'</div>',
							'</div>',
						'</div>'

					].join('');
				}
				setTimeout(function() {
					var modal = $(tmpl).modal();
					modal.on('hidden.bs.modal', function(e) {

						// do something...
						//					    	 if ($('.modal:visible').size() <= 0) {
						//				            	 $('body').removeClass('modal-open-noscroll');
						//				             }
						$(this).empty();
					});
					//					    	 if ($('.modal:visible').size() == 0) {
					//					    		 $('body').addClass('modal-open-noscroll');
					//					    	 } 
					_GLOBAL.lastModal = modal;

					App.initAjax();
				}, 150)
			},
			error: function(xhr, ajaxOptions, thrownError) {
				bootbox.alert("操作失败，请尝试刷新页面以解决该问题。");
			},
			async: true
		});

	}
	var handleMenu4CONFIRM = function(selector) {
		$(selector).unbind("click");
		$(selector).on('click', function(e) {
			e.preventDefault();
			var $portA = $(this).parents('.widget-box');
			var url = $(this).attr('href');
			var dataSet = $(this).attr('data-set');
			var dataTitle = $(this).attr('data-title');
			var callback = $(this).attr('data-callback');
			var data = _getDataSetValues(dataSet);
			if (!data) {
				return;
			}
			//加载下一个portlet
			bootbox.confirm(dataTitle ? dataTitle : '确定要执行此操作吗？', function(result) {

				if (result) {
					App.blockUI('body', true);
					$.ajax({
						type: "post",
						cache: false,
						url: url,
						data: data,
						dataType: "json",
						success: function(res) {
							//		   					bootbox.alert(res.message);
							//    						console.log(res);
							App.unblockUI('body', true);

//							toastr[res.type ? res.type : 'info'](res.message, '系统消息');
							Notify(res.message, 'top-right', res.type=='success'?'2000':'7000', res.type?res.type:'info', res.type=='success'?'fa-check':'fa-info', true);
							if (callback) {
								try {
									eval(callback);
								} catch (e) {}
							} else {
								$('#refresh-toggler').click();
							}
						},
						error: function(xhr, ajaxOptions, thrownError) {
							bootbox.alert("操作失败，请尝试刷新页面以解决该问题。");
						},
						async: true
					});
				}
			})
		})
	}

	
	var handleMenu4CONFIRM2 = function(selector) {
		$(selector).unbind("click");
		$(selector).on('click', function(e) {
			e.preventDefault();
			var $portA = $(this).parents('.widget-box');
			var url = $(this).attr('href');
			var dataSet = $(this).attr('data-set');
			var dataTitle = $(this).attr('data-title');
			var callback = $(this).attr('data-callback');
			var data = _getDataSetValues(dataSet);
			if (!data) {
				return;
			}
			//加载下一个portlet
			bootbox.confirm(dataTitle ? dataTitle : '确定要执行此操作吗？', function(result) {

				if (result) {
					bootbox.confirm(dataTitle ? dataTitle : '确定要执行此操作吗？', function(result1) {
						if(result1){
							App.blockUI('body', true);
							$.ajax({
								type: "post",
								cache: false,
								url: url,
								data: data,
								dataType: "json",
								success: function(res) {
									//		   					bootbox.alert(res.message);
									//    						console.log(res);
									App.unblockUI('body', true);

//									toastr[res.type ? res.type : 'info'](res.message, '系统消息');
									Notify(res.message, 'top-right', res.type=='success'?'2000':'7000', res.type?res.type:'info', res.type=='success'?'fa-check':'fa-info', true);
									if (callback) {
										try {
											eval(callback);
										} catch (e) {}
									} else {
										$('#refresh-toggler').click();
									}
								},
								error: function(xhr, ajaxOptions, thrownError) {
									bootbox.alert("操作失败，请尝试刷新页面以解决该问题。");
								},
								async: true
							});
						}
					})
 
				}
			})
		})
	}

	var handleSideBarInitState = function() {
		// 显示页面
		//    	setTimeout('$("html").removeClass("hideall")',200);
		// 打开上次的页面
		var oldHash = window.location.hash;
		var oldHashs = oldHash.split("#!!");
		var urlHistory = oldHashs[0].split("#!");
		if (urlHistory && urlHistory.length > 1) {

			if (urlHistory[1] != _GLOBAL.lastUrl || oldHashs.length == 1) {
				if (oldHashs.length > 1) {
					_GLOBAL.initInline = true;
				}
				if (urlHistory[1] != '/commons/homePage') {

					openSidebarMenu(urlHistory[1]);
					if (oldHashs.length > 1) {
						window.location.hash = oldHash;
					}
				} else {

					_GLOBAL.initInline = false;
					// 打开首页
					$("#_main_page_sidebar_menu li:eq(0) a:eq(0)").click();
					//alert($("#_main_page_sidebar_menu li:eq(0) ul:eq(0) li:eq(0) a:eq(0)"));
				}
			}
		} else {
			_GLOBAL.initInline = false;
			// 打开首页
			$("#_main_page_sidebar_menu li:eq(0) a:eq(0)").click();

		}


	}


	/**
	 * 根据菜单的url 打开菜单
	 */
	var openSidebarMenu = function(menu_url) {
		$('#_main_page_sidebar_menu  li > a').each(function(i, t) {
			var url = $(t).attr('href');
			if (url) {
				//    			console.log(url);
				if (url.endWith('/list')) {
					url += 'Page';
				}
				if (url.indexOf('/list?') != -1) {
					url = url.replace('/list?', '/listPage?');
				}
				if (url == menu_url) {

					$(t).click();
					$(t).parent().parents('li').addClass('open');
					return false;
				}
			}
		})
	}

	var clickLastInlineBtn = function() {
		var url = _GLOBAL.getLastInlineUrl();
		if (url) {
			var lastBtn = $('#_global_main_page-content-body').find('[href="' + url + '"][data-handler="INLINE"]');
			//    		console.log(lastBtn);
			if (lastBtn && lastBtn.length > 0) {
				lastBtn.click();
				return true;
			}
		}
		return false;
	}



	App.clickLastInlineBtn = clickLastInlineBtn;


	// 初始化
	App.init = function() {
		if (!_GLOBAL.initedMenu) {
			_GLOBAL.initedMenu = true;
			$.ajax({
				url: _GLOBAL.ctx + "admin_user/my_nav",
				success: function(data) {

					$("#_main_page_sidebar_menu").append(data);
					initBeyond();

				},
				dataType: "text",
				async: false
			});
		}

		handleSidebarMenu(); // 菜单点击事件
		handleLogout();
		InitiateWidgets();
		App.initAjax();
		// 打开首页  显示整个页面  展开第一个菜单目录 
		handleSideBarInitState();
	}

	App.initAjax = function() {
		handleDataHandler();

		/*Handles Popovers*/
		var popovers = $('[data-toggle=popover]');
		$.each(popovers, function() {
			$(this)
				.popover({
					html: true,
					template: '<div class="popover ' + $(this)
						.data("class") +
						'"><div class="arrow"></div><h3 class="popover-title ' +
						$(this)
						.data("titleclass") + '">Popover right</h3><div class="popover-content"></div></div>'
				});
		});

		var hoverpopovers = $('[data-toggle=popover-hover]');
		$.each(hoverpopovers, function() {
			$(this)
				.popover({
					html: true,
					template: '<div class="popover ' + $(this)
						.data("class") +
						'"><div class="arrow"></div><h3 class="popover-title ' +
						$(this)
						.data("titleclass") + '">Popover right</h3><div class="popover-content"></div></div>',
					trigger: "hover"
				});
		});


		/*Handles ToolTips*/
		$("[data-toggle=tooltip]")
			.tooltip({
				html: true
			});

	}

})