<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>附近充电站</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path %>/mobile/common/css/nearcharging.css">
<script src="<%=path %>/mobile/common/js/swiper/swiper.3.1.2.jquery.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>
<link rel=stylesheet href="<%=path %>/mobile/common/js/swiper/swiper.3.1.2.min.css">
<script src="<%=path %>/common/js/GpsTransform.js"></script>

<script>
$(function(){
	$(".MapBlock").height(window.innerHeight);
	$(".ListBlock").hide();
	
	$(".ChangeMapButton").click(function(e) {
        if ($(this).find("span").html() == "列表"){
			$(this).find("span").html("地图");
			$(".ListBlock").show();
			$(".MapBlock").hide();
		} else {
			$(this).find("span").html("列表");
			$(".ListBlock").hide();
			$(".MapBlock").show();
		};
    });
});
</script>
</head>

<body>
<button class="ChangeMapButton ListLabel"><i class="fa fa-exchange"></i><span>列表</span></button>
<div class="ListBlock">
    <ul class="ListUl" id="ListUl">
        <!--死循环li即可-->
    </ul>
</div>

<button class="hidden" id="getLocation" ></button>	
<div class="MapBlock" id="mapDiv"></div>
<div id="noneMapDiv" style="display: none"></div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
var ordersId = '<ww:property value="ordersId" />';

function sortStations(stations){
	var stationsInfo = [];
	$.each(stations,function(index,station){
		var pointA = carPoint;  
		var pointB = new BMap.Point(station.lng,station.lat);
		var distance = (map.getDistance(pointA,pointB)).toFixed(2);
		stationsInfo.push({'distance':distance,'station':station});
	});
	stationsInfo.sort(compare("distance"));
	return stationsInfo;
}
function compare(propertyName) { 
	return function (object1, object2) { 
		return Number(object1[propertyName])-Number(object2[propertyName]);
	};
}
var map;
var noneMap;
var carPoint;
$(function(){
	var carId = '<ww:property value="carId" />';
	map = new BMap.Map("mapDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	noneMap = new BMap.Map("noneMapDiv",{enableMapClick:false});
	map.centerAndZoom("北京", 15);
	map.enableScrollWheelZoom();
	$.ajax({
		type: "POST",
	    url: "<%=path %>/mobile/getCarRealTimeState.action",
	    dataType:'json',
	    data:{'carId':carId},
	    success: function(data2) {
	    	var convertor = new BMap.Convertor();
	    	var pointArr = [];
	        pointArr.push( new BMap.Point(data2.info.lng,data2.info.lat));
	        //转换车辆地址
	        convertor.translate(pointArr, 1, 5, function(gpsdata){
	        	carPoint = gpsdata.points[0];
	        	$.ajax({
	        		async: false,
	        		type: "POST",
	        		url: "<%=path %>/mobile/searchChargeStation.action",
	        		dataType: "json",
	        		success: function(data){
	        			if(data.result == 0 && data.info.length > 0){
	        				var stationsInfos = sortStations(data.info);
	         				$('#ListUl').empty();
//		         				var duration;
// 	         				var distance;
// 	         				var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	         				$.each(stationsInfos,function(index,stationInfo){
	         					var searchComplete = function (results){
	         						if (transit.getStatus() != BMAP_STATUS_SUCCESS){
	         							return ;
	         						}
	         						var plan = results.getPlan(0);
//		         						duration = plan.getDuration(true);                //获取时间
	         						distance = plan.getDistance(true);             //获取距离
	         						var html = '<li>'+
												'<table>'+
												'<tbody>'+
												'<tr>'+
												'<td>'+stationInfo.station.name+'</td>';
												
									if(stationInfo.station.acNum != 0){
										html += '<td><span class="label label-danger">快</span></td>';
									}
									
										html += '</tr>'+
												'<tr>'+
												'<td class="Dizhi">地址：'+stationInfo.station.address+'</td>'+
												'<td class="Kongxian">空闲：'+(stationInfo.station.acableNum + stationInfo.station.dcableNum)+'</td>'+
												'</tr>'+
												'<tr>'+
												'<td class="Quyu"> </td>'+
												'<td class="Juli">'+distance+'</td>'+
												'</tr>'+
												'</tbody>'+
												'</table>'+
												'</a>'+
												'</li>';
				     				$('#ListUl').append($(html));
	         					}
	         					var transit = new BMap.DrivingRoute(noneMap, {renderOptions: {map: noneMap},
	         						onSearchComplete: searchComplete});
	         					transit.search(new BMap.Point(carPoint.lng,carPoint.lat), new BMap.Point(stationInfo.station.lng, stationInfo.station.lat));
	         					var point = new BMap.Point(stationInfo.station.lng, stationInfo.station.lat);
	    						
								var html = '<div style="position: absolute; margin-left:5px;margin-top:-28px; width: 30px; height: 32px;  ">'
								          +     '<img imgtype="mark" class="SelectImg" src="<%=path %>/mobile/common/images/booking/chongdianzhuang.png">';
									+ '</div>';
								var myRichMarker = new BMapLib.RichMarker(html, point ,{
								          "anchor" : new BMap.Size(-20, -16),
									  "enableDragging" : false});
								map.addOverlay(myRichMarker);
								var content = '<div class="qp_con" style="padding: 0 0 0 0;border:0px;width:auto">'+
								   '<p class="ph font-size16">充电站名称：<span class="blue">'+stationInfo.station.name+'</span></p>'+
								   '<p style="font-size:14px">地址：'+stationInfo.station.address+'</p>'+
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
// 									driving.clearResults();
// 									driving.search(carPoint, point);
									map.openInfoWindow(infoWindow,point); //开启信息窗口
									
								});
							
								
	         					
	         				});
	         				
	         				
	         				
	        			}
	        			else{
	        				Alert("对不起，您附近没有充电站！");
	        			}
	        		},
					error: function(){
						Alert("暂时无法获取附近充电站信息，请稍后再试");
					},
					complete: function(){
						ShowLoading(false);
					}
	        		
	        	});
	        	
	        	
	        var carHtml = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 30px; height: 32px; z-index:9 ">'
			          +     '<img class="SelectImg" src="<%=path %>/mobile/common/images/booking/dibiao_03.png">'
				+ '</div>';
			var carMarker = new BMapLib.RichMarker(carHtml, carPoint ,{
			          "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
			map.addOverlay(carMarker);
			map.centerAndZoom(carPoint, 12);
	        });
	  	},
		error: function(){
			Alert("暂时无法获取停车网点信息，请稍后再试");	
		}
	   });
});

</script>

</html>
