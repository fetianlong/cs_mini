<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>


<script type="text/javascript">

var map;
$(document).ready(function(){
	showMap();
	/*时间选择*/
	$("#sform .TimeSelect").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
		minuteStep: 5,
		format: "yyyy-mm-dd"
	});
});


function searchEntity(){
	$("#sform").submit();
}

var josnlist = <ww:property value='jsonhtlist'/>;

function showMap(){
	 
	map = new BMap.Map("branchDotMapShowDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 8);
	map.setCurrentCity("北京");
	map.enableScrollWheelZoom();
	showDot();
	
	var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl); 
}
function showDot(){
	
	 $(josnlist).each(function(index,entity){
			if(entity == null || entity == ""){
				return true;
			}
			var numb ;
			var point = new BMap.Point(entity.lat,entity.lng);
			var nickName = entity.subname;
			var ts = entity.ts;
			var id = entity.id;
			addMark(point,'operDot',index,numb,nickName,ts,id);
			
			
		});
}

function addMark(point,imgid,index,numb,nickName,ts,id){
	
	 	var markImg = "mark_1.png";
		var  markImg2 = "mark_1.png";
	 	var numbClass = "markico_mark1";
	 	
		var html = '<div style="position: absolute; margin: 0pt; width: 32px; height: 32px; left:0px; top: -15px; ">'+
		'<img id="img_'+imgid+index+'" style="position:absolute;" src="'+'<%=path %>/common/css/images/'+markImg+'">';
		var html = '<div style="position: absolute;margin-left:9px;margin-top:-6px; width: 30px; height: 32px;  ">'
	       //   +     '<img id="img_'+imgid+index+'" style="border:none;position:absolute;" src="<%=path %>/portal/common/images/booking/xianyouwangdian.png">'
	          + '<span style="background:url(\'<%=path %>/portal/common/images/booking/hongdian.png\')  no-repeat top center;position:absolute;font-size:10px; top:-5px;right:0;height:16px;text-align:center;color:#FFF;width:16px;"></span></div>';
		var myRichMarker = new BMapLib.RichMarker(html, point ,{
           "anchor" : new BMap.Size(-20, -16),
			  "enableDragging" : false});
		
		

		map.addOverlay(myRichMarker);
		var content = '<div class="qp_con">';
		content += '<p><b>提交人：</b>'+nickName+'</p>'+
				   '<p><b>提交时间：</b>'+ts+'</p>';

		content += '</div><img class="aa" src="'+'<%=path %>/common/css/images/ditu_sanjiao.png" />';
		myRichMarker.addEventListener("click",function(e){
			var opts = {
					width : 225,     // 信息窗口宽度
					height: 126,     // 信息窗口高度
					enableMessage:false //设置允许信息窗发送短息
				};
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		});
}
function showDotInput(){
	if($('#showDotInputButId').attr('style') != null && $('#showDotInputButId').attr('style') != ""){
		$('#showDotInputButId').attr('style','');
		//map.clearOverlays();
		showDot();
	}
	else{
		$('#showDotInputButId').attr('style','color: rgb(173, 173, 173)');
		map.clearOverlays();
		if($('#showParkingInputButId').attr('style') == null || $('#showParkingInputButId').attr('style') == ""){
			showParking();
		}
		//showParking();
	}
}
</script>
<style type="text/css">
.SubPage .SubmitButtonBlock .Button4 {
	background: rgb(145, 137, 53);
}
</style>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">

<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="branchDot.name" class="col-xs-4 control-label">开始时间段</label>
					<div class="col-xs-8">
						<input type="text" class="form-control TimeSelect" name="beginDate" id="beginDate" value='<ww:property value="beginDate" />'>

					</div>
				</div>

			</div>
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="branchDot.name" class="col-xs-4 control-label">结束时间段</label>
					<div class="col-xs-8">
						<input type="text" class="form-control TimeSelect" name="endDate" id="endDate" value="<ww:property value="endDate" />">
					</div>
				</div>

			</div>

		</div>
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-5 col-xs-6 col-xs-offset-3">
						<a class="btn btn-block Button1" onclick="searchEntity();"
							target="_blank"><i class="fa fa-search"></i>查询</a>
					</div>
				</div>
	</div>


				<div id="branchDotMapShowDiv" style="width:100%;height: 600px"></div>
			</form>
</div>
</body>

</html>