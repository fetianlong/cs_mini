// JavaScript Document

$(function(){
	$(':checkbox').radiocheck();
	
	$(":checkbox").on('change.radiocheck', function() {
		if ($(this).context.checked){
			$(this).val(1);
		} else {
			$(this).val(0);
		};
	});
	
	$(':radio').radiocheck();
	
	$("body").on("click", ".AlertBlock .AlertArea .AlertWindow .AlertButton", function(){
		$(".AlertBlock").fadeOut(100);
	});
});

function ShowLoading(State){
	if (State){
		if ($(".Loading").length > 0){
			$(".Loading").fadeIn(100);
		} else {
			$("body").prepend('<div class="Loading"><div class="LoadingBlock"><i class="fa fa-spinner fa-lg fa-pulse"></i></div></div>');
		};
	} else {
		$(".Loading").fadeOut(100);
	};
};

function ShowLoading2(State){
	if (State){
		if ($(".Loading2").length > 0){
			$(".Loading2").fadeIn(100);
		} else {
			$("body").prepend('<div class="Loading2"><div class="LoadingBlock"><img class="LoadingImgBack" src="'+getRootPath()+'/mobile/common/images/main/Loading2.png"><div class="LoadingBackBlock"><img class="LoadingImgFront" src="'+getRootPath()+'/mobile/common/images/main/Loading1.png"><p>华泰租车</p></div></div></div>');
		};
	} else {
		$(".Loading2").fadeOut(100);
	};
};

function DateLast5Mins(){
	HasDisabled = 0;
	$(".datetimepicker").each(function(index, element) {
		if($(this).css("display")=="block"){
			$minute = $(this).find(".datetimepicker-minutes .table-condensed tbody .minute");
			$minute.each(function(index, element) {
				if ($(this).hasClass("disabled")){
					HasDisabled += 1;
				};
			});
			if (HasDisabled == $minute.length){
				$(this).find(".datetimepicker-minutes .table-condensed thead tr th.next span").click();
			};
		};
	});
};

function Alert(Content){
	if ($(".AlertBlock").length == 0){
		$("body").prepend('<div class="AlertBlock"><div class="AlertArea"><div class="AlertWindow"><p>' + Content + '</p><button class="AlertButton">确定</button></div></div></div>');
	} else {
		$(".AlertBlock .AlertArea .AlertWindow p").html(Content);
	};
	$(".AlertBlock").fadeIn(100);
};

function isAndroid(){
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	/*var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);*/ //ios终端
	return isAndroid;
};

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}