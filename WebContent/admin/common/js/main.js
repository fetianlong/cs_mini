// JavaScript Document

$(function(){
	$("body").css("height", $(window).height());
	$(window).resize(function(e) {
		$("body").css("height", $(window).height());
	});
	
	$(".ContentBlock #home").css("height", $(window).height() - $(".RightBlock .Header").outerHeight(true) - $(".RightBlock .NavBarBlock").outerHeight(true));
	
	/*左侧菜单控制*/
	$(".MainMenuUl>li>a").click(function(e) {
		$(".MainMenuUl .DirectionArrow").removeClass("fa-rotate-270");
		if (typeof($(this).attr("href")) == "undefined" || $(this).attr("href") == "#"){
			if ($(this).parent("li").hasClass("active")){
				if ($(this).parent("li").find("ul").css("display")=="none"){
					$(this).parent("li").find("ul").slideDown(200);
					$(this).find(".DirectionArrow").addClass("fa-rotate-270");
				} else {
					$(this).parent("li").find("ul").slideUp(200);
					$(".MainMenuUl .DirectionArrow").removeClass("fa-rotate-270");
				};
			} else {
				$(".MainMenuUl li").removeClass("active");
				$(this).parent("li").addClass("active");
				$(".MainMenuUl li ul").slideUp(200);
				$(this).find(".DirectionArrow").addClass("fa-rotate-270");
				$(this).parent("li").find("ul").slideDown(200);
			};
		} else {
			if ($(".RightBlock .NavBarBlock .NavBarTabs li[tags='tab_" + $(this).attr("id") + "'] a").length == 0){
				OpenTabs($(this));
			};
			$(".RightBlock .NavBarBlock .NavBarTabs li[tags='tab_" + $(this).attr("id") + "'] a").tab("show");
			$(".MainMenuUl li").removeClass("active");
			$(this).parent("li").addClass("active");
			$(".LeftBlock .MainMenuBlock .MainMenuUl .RightFa").hide();
			$(this).parents("li").find(".MenuLink .RightFa").show();
			return false;
		};
	});
	
	$(".MainMenuUl>li>ul>li").on('click', "a", function(e) {
		$(".MainMenuUl>li>ul>li").removeClass("active");
		$(this).parents("li").addClass("active");
		$(".MenuLink .RightFa").hide();
		$(this).parents("li").find(".MenuLink .RightFa").show();
		
		
		//if ($(".RightBlock .NavBarBlock .NavBarTabs li[tags='tab_" + $(this).attr("id") + "'] a").length == 0){
		//	OpenTabs($(this));
		//}
			
		OpenTabs($(this));
		
		$(".RightBlock .NavBarBlock .NavBarTabs li[tags='tab_" + $(this).attr("id") + "'] a").tab("show");
		return false;
	});
	
	function OpenTabs(AObject){
		
		if ($(".RightBlock .NavBarBlock .NavBarTabs li[tags='tab_" + AObject.attr("id") + "'] a").length == 0){
			$(".RightBlock .NavBarBlock .NavBarTabs").append('<li role="presentation" tags="tab_' + AObject.attr("id") + '"><a href="#tab_' + AObject.attr("id") + '" aria-controls="tab_' + AObject.attr("id") + '" role="tab" data-toggle="tab">' + AObject.find("span.LinkLabel").html() + '</a><i class="fa fa-times"></i></li>');/*缺已打开处理*/
		}
		
		$(".RightBlock .ContentBlock").append('<div role="tabpanel" id="tab_' + AObject.attr("id") + '" class="tab-pane fade"><iframe src="' + AObject.attr("href") + '"></iframe></div>');
		$(".RightBlock .ContentBlock #tab_" + AObject.attr("id") + ">iframe").css("height", $(window).height() - $(".RightBlock .Header").outerHeight(true) - $(".RightBlock .NavBarBlock").outerHeight(true));
	};
	
	/*首页时间选择*/
	$("#home .TimeSelect").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 0,
		autoclose: true,
		minuteStep: 5
	});
	
	/*控制页签删减*/
	$(".NavBarBlock").on("click", ".fa", function(e) {
		$(this).parent("li").remove();
		$(".ContentBlock #" + $(this).parent("li").find("a").attr("aria-controls")).remove();
		$(".RightBlock .NavBarBlock .NavBarTabs li:last a").tab("show");
		LeftMenuArrow($(".RightBlock .NavBarBlock .NavBarTabs li:last a"));
	});
	
	/*左侧菜单显隐*/
	$(".HideMenuButton").click(function(e) {
		$(".LeftBlock").toggle();
		if ($(".LeftBlock").css("display")=="none"){
			$(".RightBlock").css("width", "100%");
		} else {
			$(".RightBlock").css("width", "80%");
		};
	});
	
	$(".RightBlock .NavBarBlock .NavBarTabs").on("click", "li a", function(){
		LeftMenuArrow($(this));
	});

	/*切换Tab时控制左侧菜单展开效果*/
	function LeftMenuArrow(Link){
		ID = Link.parent("li").attr("tags").replace(/^tab_/, "");
		$(".LeftBlock .MainMenuBlock .MainMenuUl li").removeClass("active");
		$(".LeftBlock .MainMenuBlock .MainMenuUl .RightFa").hide();
		if (ID != "index"){
			$(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).parents("li").addClass("active");
			if ($(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).hasClass("MenuLink")){
				$(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).find(".RightFa").show();
			} else {
				$(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).closest("ul").closest("li").find("a .RightFa").show();
				if ($(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).closest("ul").css("display") == "none"){
					$(".MainMenuUl .DirectionArrow").removeClass("fa-rotate-270");
					$(".LeftBlock .MainMenuBlock .MainMenuUl>li>ul").slideUp(200);	
					$(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).closest("ul").slideDown(200);
					$(".LeftBlock .MainMenuBlock .MainMenuUl #" + ID).closest("ul").closest("li").find(".DirectionArrow").addClass("fa-rotate-270");
				};
			};
		} else {
			$(".MainMenuUl .DirectionArrow").removeClass("fa-rotate-270");
			$(".LeftBlock .MainMenuBlock .MainMenuUl>li>ul").slideUp(200);	
		};
	};
});


 ;



function showSubscriberDetailForDialog(id,path){
	
	var dialogsubscriberDetail = $.dialog({
	    id:'subscriberdailogid334', 
	    title:'会员详情',
	    content : "url:"+path+"/subscriber/showSubscriberDetailForDialog.action?subscriber.id="+id,
		resize:false,
		fixed:true,
		width:800,
		height:600,
 		max: false,
 		zIndex:100,
	    min: false,
	    lock: true,
	    ok: false,
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}



function showCarDetailForDialog(id,path){
	
	var dialoguser = $.dialog({
	    id:'cardailogid', 
	    title:'车辆详情',
	    content : "url:"+path+"/car/carGet.action?id="+id+"&state=dialog",
		resize:false,
		fixed:true,
		width:800,
		height:500,

 		max: false,
	    min: false,
	    lock: true,
	    ok: false,
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}


function showOrderDetailForDialog(id,path){
	
	var dialoguser = $.dialog({
	    id:'subscriberdailogid', 
	    title:'订单详情',
	    content : "url:"+path+"/orders/ordersDetailForDialog.action?id="+id,
		resize:false,
		fixed:true,
		width:800,
		height:500,
	    lock: true,
 		max: false,
	    min: false,

	    ok: false,
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}

function showOrderDetailByNoForDialog(no,path){
	
	var dialoguser = $.dialog({
	    id:'subscriberdailogid', 
	    title:'订单详情',
	    content : "url:"+path+"/orders/ordersDetailByNoForDialog.action?no="+no,
		resize:false,
		fixed:true,
		width:800,
		height:500,
	    lock: true,
 		max: false,
	    min: false,

	    ok: false,
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}



function showLogRecordForDialog(id,path,modelName){
	var urlContent = 'url:'+path+'/systemutil/getSysOperateLogByDataId.action?dataId='+id;
	if(modelName != null && modelName.trim() != ''){
		urlContent += '&modelName='+encodeURI(encodeURI(modelName));
	}
	var dialoguser = $.dialog({
	    id:'logRecordList', 
	    title:"操作记录",
		content :urlContent,
		fixed:true,
		width:600,
		height:650,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    ok: function(){},
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}

