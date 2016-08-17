<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>个人中心</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<!--  <script type="text/javascript" src="common/js/popup_layer.js"></script>
-->
<script type="text/javascript">

/*
$(document).ready(function(){
	var t1 = new PopupLayer({
		trigger:"#ele1",
		popupBlk:"#blk1",
		closeBtn:"#close1",
		useOverlay:true,
		useFx:true,
		offsets:{
			x:120,
			y:-41
		}
	});
	t1.doEffects = function(way){
		if(way == "open"){
			this.popupLayer.css({opacity:0.3}).show(300,function(){
				this.popupLayer.animate({
					left:($(document).width() - this.popupLayer.width())/2,
					top:(document.documentElement.clientHeight - this.popupLayer.height())/2 + $(document).scrollTop(),
					opacity:0.8
				},300,function(){this.popupLayer.css("opacity",1)}.binding(this));
			}.binding(this));
		}
		else{
			this.popupLayer.animate({
				left:this.trigger.offset().left,
				top:this.trigger.offset().top,
				opacity:0.1
			},{duration:200,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
		}
	}


	$("a[name='grTitList']").click(function(){

		$("a[name='grTitList']").each(function(){
			$(this).css("color","#969595");
		});
	     $(this).css("color","#4bd3a5");
	     
	});
});

function alertMsg(msg,result){
	if("success"==result){
		$("#msgContent").html(msg);
	}else{
		$("#msgContent").html(msg);
	}
	$("#ele1").click();
 }
*/

function navigate(url) {
	$("#mainIframe").attr("src",url);
}
$(document).ready(function(){
	$("a[name='grTitList']").click(function(){

		$("a[name='grTitList']").each(function(){
			$(this).css("color","#969595");
		});
	     $(this).css("color","#4bd3a5");
	     
	});
	
});

function gotoPageByIndex(index){
	if(1==index){
		navigate('account/center.action');
		alertMsgClose();
	}
	
}
</script>
</head>
<body style="height:100%; position:relative;">
<ww:include page="/pages/subscriber/header.jsp"></ww:include>

<div class="gr_center">
   <!--左侧-->
     <div class="gr_tit_list fl">
       <ul>
         <li><a name="grTitList" style="color:#4bd3a5;" href="javascript:navigate('pages/subscriber/personalCenter/personal_main.jsp')"  class="click">个人信息</a></li>
         <li><a name="grTitList" id="grTitList_account" href="javascript:navigate('account/center.action')" >账户管理</a></li>
         <li><a name="grTitList" href="javascript:navigate('account/queryTradingRecord.action')">消费记录</a></li>
       </ul>  
         <a  href="javascript:void()" style="display:none;" id="ele9"></a>
     </div>
     <!--右侧-->
     <div class="gr_con">
        <iframe width="100%" height="1200" id="mainIframe"  style="border:none; " src="pages/subscriber/personalCenter/personal_main.jsp"></iframe>
         <div id="blk9" class="blk" style="display:none;">           
            <div class="main">                            
                <a href="javascript:void(0)" id="close9" class="closeBtn">X</a>        
                <img src="common/portal/images/zccg.png" style="margin:0 auto; margin-top:50px;" />
                <h2 id="h2msg">操作成功</h2>
            </div>      
        </div>
     </div>


</div>


 

</body>
</html>

