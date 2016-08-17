<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>还车</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>
<script src="<%=path %>/common/js/GpsTransform.js"></script>

<link rel=stylesheet href="<%=path %>/mobile/common/css/returncar.css">
<script>
var startReturnTime = new Date();
var map;
var dotsMap = [];
var returnBackDotId;
$(function(){
	
	$(".MapBlock").height($(window).height());
	var carId = '<ww:property value="carId" />';
	var pars = {'dotId':'<ww:property value="dotId" />'};
	$.post('<%=path %>/mobile/getReturnDot.action',pars,function(data){
		map = new BMap.Map("container",{enableMapClick:false});//构造底图时，关闭底图可点功能
		map.centerAndZoom("北京", 11);
		map.enableScrollWheelZoom();
		
		if(data.info != null && data.info.length > 0){
			
			$.ajax({
				type: "POST",
			    url: "<%=path %>/mobile/getCarRealTimeState.action",
			    dataType:'json',
			    data:{'carId':carId},
			    success: function(data2) {
			    	var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
			    	var convertor = new BMap.Convertor();
			    	var pointArr = [];
			        pointArr.push( new BMap.Point(data2.info.lng,data2.info.lat));
			        //转换车辆地址
			        convertor.translate(pointArr, 1, 5, function(gpsdata){
			        	var carPoint = gpsdata.points[0];
				    	var carhtml = '<div style="position: absolute; margin-left:3px;margin-top:-29px; width: 30px; height: 32px;  ">'
						          +     '<img imgtype="mark" class="SelectImg" src="<%=path %>/mobile/common/images/booking/dibiao_03.png">';
							+ '</div>';
						var carMarker = new BMapLib.RichMarker(carhtml, carPoint ,{
						          "anchor" : new BMap.Size(-20, -16),
							  "enableDragging" : false});
						map.addOverlay(carMarker);
						$.each(data.info,function(index,dot){
						    var point = new BMap.Point(dot.lng, dot.lat);
						
							var html = '<div style="position: absolute; margin-left:5px;margin-top:-28px; width: 30px; height: 32px;  ">'
							          +     '<img imgtype="mark" class="SelectImg" src="<%=path %>/mobile/common/images/booking/defaultselect3.png">';
								+ '</div>';
							var myRichMarker = new BMapLib.RichMarker(html, point ,{
							          "anchor" : new BMap.Size(-20, -16),
								  "enableDragging" : false});
							map.addOverlay(myRichMarker);
							var content = '<div class="qp_con" style="padding: 0 0 0 0;border:0px;width:auto">'+
							   '<p class="ph font-size16">网点名称：<span class="blue">'+dot.name+'</span></p>'+
							   '<p style="font-size:14px">地址：'+dot.address+'</p>'+
							   '</div><img class="aa" src="'+'<%=path %>/common/css/images/ditu_sanjiao.png" />';
							   
							myRichMarker.addEventListener("click",function(e){
								var opts = {
										width : 200,     // 信息窗口宽度
										height: 100,     // 信息窗口高度
										enableMessage:false //设置允许信息窗发送短息
									};
								var p = e.target;
								var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
								var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
								//map.centerAndZoom(point,15);
								map.openInfoWindow(infoWindow,point); //开启信息窗口
								var p = myRichMarker.getPosition();       //获取marker的位置
								driving.clearResults();
								driving.search(carPoint, p);
							});
						
							var dotInfo = {'dotId':dot.id,'dot':dot};
							dotsMap.push(dotInfo);
						});
						var dist = null;
				    	var nedot = null;
				    	$.each(dotsMap,function(index,dotInfo){
				    		var pointB = new BMap.Point(dotInfo.dot.lng,dotInfo.dot.lat); 
				    		var distance = (map.getDistance(carPoint,pointB)).toFixed(2);
				    		if(dist == null || dist > Number(distance)){
				    			dist = Number(distance);
				    			nedot = {'distance':distance,'dot':dotInfo.dot};
				    		}
				    	});
				    	if(Number(nedot.distance) <= 500){
				    		$('#container').hide();
				    		$('#tishi').show();
				    		$('#dotName').text(nedot.dot.name);
				    		returnBackDotId = nedot.dot.id;
				    	}
				    	else{
				    		$('#container').show();
				    		$('#tishi').hide();
				    		Alert('您还没有驶入还车网点，现在指引您到最近的还车网点');
				    		
				    		var p = new BMap.Point(nedot.dot.lng,nedot.dot.lat);       //获取marker的位置
				    		driving.clearResults();
				    		driving.search(carPoint, p);
				    	}
			        });
			  	},
				error: function(){
					Alert("暂时无法获取停车网点信息，请稍后再试");	
				}
			   });
		}
		
		
	},'json');
	
});



function returnBackCar(){
	if(new Date() - startReturnTime > 1000*60*2){
		window.loaction.reload();
	}
	if(confirm('确定要还车吗?'))
    {
		checkReturn();
    }
	else{
		
	}
}
var outTimeConfFlag = false;
var hasNextConfFlag = false;
function checkReturn(){
	$.ajax({
		async: false,
		data: {'ordersId':$('#ordersId').val(),'returnBackDotId':returnBackDotId,'outTimeConfFlag':outTimeConfFlag,'hasNextConfFlag':hasNextConfFlag},
		type: "POST",
		url: "<%=path %>/mobile/checkReturnBackCar.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				toReturnBackCar();
			}
			else if(data.result == 2){
				if(confirm(data.msg)){
					outTimeConfFlag = true;
					checkReturn();
				}
			}
			else if(data.result == 3){
				if(confirm(data.msg)){
					hasNextConfFlag = true;
					checkReturn();
				}
			}
			else if(data.result == 5){
				window.location.href='<%=path %>/mobile/toPayOrderFinishFee.action';
			}
			else if(data.result == 6){
				window.location.href='<%=path %>/mobile/login.action';
			}
			else {
				alert(data.msg);
			}
		},
		error: function(){
			Alert("还车失败，请稍后再试");	
		}
		
	});
}
function toReturnBackCar(){
	$.ajax({
		async: false,
		data: {'ordersId':$('#ordersId').val(),
			'returnBackDotId':returnBackDotId,
			'ordersDetailId':$('#ordersDetailId').val()	
		},
		type: "POST",
		url: "<%=path%>/mobile/returnBackCar.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				window.location.href='<%=path%>/mobile/toReturnComplete.action';
			}
			else if(data.result == 1){
				alert(data.msg);
				window.location.href='<%=path%>/mobile/pages/login.jsp';
			}
			else if(data.result == 2){
				alert(data.msg);
				window.location.href='<%=path%>/mobile/toBookCar.action';
			}
			else if(data.result == 3){
				alert(data.msg);
				window.location.href='<%=path%>/mobile/toCurrentOrder.action';
			}
			
			
		}
	});
}



</script>
</head>

<body>
<div class="container">
	<div class="row">
		<div id="container" style="display: none;" class="MapBlock"> </div>
	</div>
	<div class="row" id="tishi" style="display: none;">
			<div class="Icon">
				<img class="img-respond center-block" src="<%=path%>/mobile/common/images/returncar/icon.png">
			</div>
		<div class="col-md-8 col-md-offset-2">
			<p class="Tips">您已驶入"<span id="dotName" ></span>"还车网点!</p>
		</div>
		
		<div class="BottomButton">
			<div class="col-xs-12">
				<p class="text-center">请您确认门窗已关闭，并已插上充电枪</p>
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2">
						<button class="btn btn-primary btn-block" onclick="returnBackCar()">立即还车</button>
					</div>
					<div class="col-xs-2">
						<a class="btn btn-block ReturnButton" href="javascript:history.go(-1)"><i class="fa fa-angle-left"></i>返回</a>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<button style="display: none" id="getLocation" ></button>
	<input id="ordersId" type="hidden" value="<ww:property value="ordersId" />" />
	<input id="ordersDetailId" type="hidden" value="<ww:property value="ordersDetailId" />" />
</div>
</body>
</html>
