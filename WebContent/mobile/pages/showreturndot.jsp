<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>还车网点</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>
<link rel=stylesheet href="<%=path %>/mobile/common/css/booking.css">
<script src="<%=path %>/common/js/GpsTransform.js"></script>
<script>

var map;
$(function(){
	$(".MapBlock").height($(window).height());
	
	var pars = {'dotId':'<ww:property value="dotId" />'};
	$.post('<%=path %>/mobile/getReturnDot.action',pars,function(data){
		map = new BMap.Map("container",{enableMapClick:false});//构造底图时，关闭底图可点功能
		map.centerAndZoom("北京", 11);
		map.enableScrollWheelZoom();
		
		
		if(data.info != null && data.info.length > 0){
			$.each(data.info,function(index,dot){
			    var point = new BMap.Point(dot.lng, dot.lat);
			
				var html = '<div style="position: absolute; margin-left:-10px;margin-top:10px; width: 30px; height: 32px;  ">'
				          +     '<img imgtype="mark" class="SelectImg" src="<%=path %>/mobile/common/images/booking/defaultselect3.png">'
				          + '<span class="CarNumSpan">'+dot.remainderParkingPlace+'</span>'
					+ '</div>';
				var myRichMarker = new BMapLib.RichMarker(html, point ,{
				          "anchor" : new BMap.Size(-20, -16),
					  "enableDragging" : false});
				map.addOverlay(myRichMarker);
				var content = '<div class="qp_con" style="padding: 0 0 0 0;border:0px;width:auto">'+
				   '<p class="ph font-size16">网点名称：<span class="blue">'+dot.name+'</span></p>'+
				   '<p style="font-size:14px">地址：'+dot.address+'</p><p>剩余车位：'+dot.remainderParkingPlace+'</p>'+
				   '</div>';
				   
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
				});
			});
		}
		
	},'json');
	
});



</script>
</head>

<body>
<div class="container-fluid">
	<div class="row">
		<div id="container" class="MapBlock"> </div>
	</div>
	<button style="display: none" id="getLocation" ></button>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
wx.config({
debug: false,    
appId: '<ww:property value="appId"/>',    
timestamp: '<ww:property value="timestamp"/>',    
nonceStr: '<ww:property value="nonceStr"/>',    
signature: '<ww:property value="signature"/>',    
jsApiList: [    
            'getLocation',
			'checkJsApi'
]    
});  
var userPoint;
var userMark;
wx.ready(function () {
	  // 获取当前地理位置
	  document.querySelector('#getLocation').onclick = function () {
		  
		    wx.getLocation({
		      success: function (res) {
		        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				var convertor = new BMap.Convertor();
		        var pointArr = [];
		        pointArr.push(new BMap.Point(longitude,latitude));
		        convertor.translate(pointArr, 1, 5, function(data){
		        	var point = data.points[0];
		 	        userPoint = point;
		 			var html = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 30px; height: 32px; z-index:9 ">'
					          +     '<img class="SelectImg" src="<%=path %>/mobile/common/images/booking/dibiao_03.png">'
		 				+ '</div>';
		 			var myRichMarker = new BMapLib.RichMarker(html, point ,{
		 			          "anchor" : new BMap.Size(-20, -16),
		 				  "enableDragging" : false});
		 			userMark = myRichMarker;
		 			map.addOverlay(myRichMarker);
		        });
		      },
		      cancel: function (res) {
		        Alert('用户拒绝授权获取地理位置');
		      },
			  fail: function(){
				Alert("暂时无法获取您的位置，请您关闭并重新打开当前页面");
			  },
			  complete: function(){
				  ShowLoading2(false);
			  }
		    });
		  };
		  
		  wx.checkJsApi({
			jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
			success: function(res) {
					ShowLoading2(true);
					$('#getLocation').click();
				}
			});
		  
});
</script>
</html>
