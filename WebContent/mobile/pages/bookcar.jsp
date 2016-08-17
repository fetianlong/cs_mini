<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>车辆预定</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>
<link rel=stylesheet href="<%=path %>/mobile/common/css/booking.css">
<script src="<%=path %>/common/js/GpsTransform.js"></script>

<link rel="stylesheet" href="<%=path %>/mobile/common/css/sitelist.css">

<script>

var map;
var chosedDotId;
var dotsMap=[];
$(function(){
	
	$("#listDiv").hide();
	
	$(".ChangeMapButton").click(function(e) {
        if ($(this).find("span").html() == "列表"){
			$(this).find("span").html("地图");
			$("#listDiv").show();
			$("#mapDiv").hide();
		} else {
			$(this).find("span").html("列表");
			$("#listDiv").hide();
			$("#mapDiv").show();
		};
    });
	
	var showLoading ='<ww:property value="showLoading" />';
	if(showLoading == null || showLoading == '' || showLoading != '0'){
		ShowLoading(true);
	};
	
	$(".MapBlock").height($(window).height());
	$(".TopMenu ul>li:eq(0)").width($(window).width() * 0.2);
	$(".TopMenu ul>li:eq(1)").width($(window).width() * 0.6);
	$(".TopMenu ul>li:eq(2)").width($(window).width() * 0.2);
	
	$(window).resize(function(){
		$(".MapBlock").height($(window).height());
		$(".TopMenu ul>li:eq(0)").width($(window).width() * 0.2);
		$(".TopMenu ul>li:eq(1)").width($(window).width() * 0.6);
		$(".TopMenu ul>li:eq(2)").width($(window).width() * 0.2);
	});

	$(".SelectCarBlock").hide();
		
	$(".SelectCarBlock .CloseSelectCar").click(function(e) {
		$(".SelectCarBlock").slideUp(200);
		$(".CarPositionBlock").fadeOut(200);
	});
	
	$("#useCarBut").on("click", function(e) {
		if(userPoint == null || userPoint.lng == null){
			$('#getLocation').click();
			return;
		}
		var dotInfoCars;
		var type;
		if(chosedDotId == null){
			var dotDistanceInfo = choseNeerDot();
			if(dotDistanceInfo == null || dotDistanceInfo.length <= 0){
				window.location.href='<%=path %>/mobile/pages/createdot.jsp?lat='+userPoint.lat+'&lng='+userPoint.lng;
				return;
			}
			$.each(dotDistanceInfo,function(index,dotDisInfo){
				if(dotDisInfo.dot.carCount > 0){
					chosedDotId = dotDisInfo.dot.id;
					type = 'tj';
					return false;
				}
			});
			if(chosedDotId == null){
				Alert('您附近的网点没有车辆可以预约，请您手动选择其他网点');
				return;
			}
		}
		$.each(dotsMap,function(index,dotMap){
			if(chosedDotId == dotMap.id){
				dotInfoCars = dotMap.dot;
			}
		});
		showCarList(dotInfoCars,type);
		/*做判断，如果选择了网点就弹出车辆选择框*/
		$(".SelectCarBlock").slideDown(200);
		$(".CarPositionBlock").fadeIn(200);
		var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
		var p = new BMap.Point(dotInfoCars.lng,dotInfoCars.lat);       //获取marker的位置
		driving.clearResults();
		driving.search(userPoint, p);
		changeImg(chosedDotId);
	});
	$('#searchDotButton').click(function(e){
		map.clearOverlays();
		var pars = {'searchDotName':$('#search').val()};
		$.post('<%=path %>/mobile/dotInfoSearch.action',pars,showMap,'json');
		$('#getLocation').click();
	});
	var pars = {};
	$.post('<%=path %>/mobile/dotInfoSearch.action',pars,showMap,'json');


	
	function showMap(data){
		
		map = new BMap.Map("container",{enableMapClick:false});//构造底图时，关闭底图可点功能
		map.centerAndZoom("北京", 15);
		map.enableScrollWheelZoom();
		var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
		
		$('#xywdsl').text(data.info.xydots.length);
		
		if(data.info.xydots.length > 0){
			$('#ListUl').empty();
			
			$.each(data.info.xydots,function(index,dot){
			    var point = new BMap.Point(dot.lng, dot.lat);
			
				var html = '<div style="position: absolute; margin-left:-10px;margin-top:10px; width: 30px; height: 32px;  ">'
				          +     '<img imgtype="mark" class="SelectImg" id="img_'+dot.id+'" src="<%=path %>/mobile/common/images/booking/defaultselect2.png">'
				          + '<span class="CarNumSpan">'+
				          dot.carCount
				          +'</span>'
					+ '</div>';
				var myRichMarker = new BMapLib.RichMarker(html, point ,{
				          "anchor" : new BMap.Size(-20, -16),
					  "enableDragging" : false});
	// 			xywdmarks.push(myRichMarker);
				map.addOverlay(myRichMarker);
				
				myRichMarker.addEventListener("click",function(e){
					var p = myRichMarker.getPosition();       //获取marker的位置
					driving.clearResults();
					driving.search(userPoint, p);
					changeImg(dot.id);
					if(dot.carCount > 0){
						window.location.href='<%=path%>/mobile/toShowDotCarList.action?dotId='+dot.id;
					}
// 					$('.OpenCarSelect').click();
				});
				
				dotsMap.push({'id':dot.id,'dot':dot});
				
				var listhtml = '<li>'+
										'<a href="<%=path%>/mobile/toShowDotCarList.action?dotId='+dot.id+'">'+
									'<h4>'+dot.name+'<span class="KMBlock"><span>54</span>KM</span></h4>'+
					                '<ul class="TypeUl">'+
					                	'<li><span class="Icon PositionIcon"><img src="<%=path%>/mobile/common/images/sitelist/positionicon.png"></span>'+dot.address+'</li>'+
					                    '<li><span class="Icon CarIcon"><img src="<%=path%>/mobile/common/images/sitelist/caricon.png"></span>可用车辆：'+dot.carCount+'<button class="DaohangButton"><img src="<%=path%>/mobile/common/images/sitelist/daohangicon.png"></button></li>'+
					                '</ul>'+
								'</a>'+
							'</li>';
				$('#ListUl').append($(listhtml));
				
			});
		}
		map.addEventListener("click",function(e){
			$("img[imgtype='mark']").attr("src", "<%=path %>/mobile/common/images/booking/defaultselect2.png");
			chosedDotId = null;
	// 		alert(e.point.lng + "," + e.point.lat);
		});
		
	}
	function changeImg(dotId){
		chosedDotId = dotId;
		$("img[imgtype='mark']").attr("src", "<%=path %>/mobile/common/images/booking/defaultselect2.png");
		$('#img_'+dotId).attr('src','<%=path %>/mobile/common/images/booking/selected2.png');
	}
	
	
	function showCarList(dot,type){
		if(dot != null){
			$('.CarPositionBlock').text(dot.name);
		}
		else {
			$('.CarPositionBlock').text('');
		}
		if(dot == null || dot.carCount <= 0){
			$('#CarListDiv').html("<div class='swiper-slide'><p class='text-center NoCar'>当前网点无车辆可用，请选择其它网点</p></div>");
			$(".JianImg").hide();
			$(".CarButton").hide();
			return;
		} else {
			$(".CarButton").show();
			$(".JianImg").show();
		};
	// 	$('.AddressBlock').children().eq(0).text(dot.name);
		$('.CarPositionBlock').text(dot.address);
	// 	$('.AddressBlock').children().eq(2).children().eq(1).text(dot.parkings[0].address);
		
		var pars = {'dotId':dot.id};
		$.ajax({
			async: false,
			data: pars,
			type: "POST",
			url: "<%=path %>/mobile/getDotDetailInfo.action",
			dataType: "json",
			success: function(data){
				$('#CarListDiv').empty();
				if(data.result != 0 || data.info == null ){
					return;
				};
				//有推荐车辆
				if(type != null && type == 'tj'){
					var tjcar =  data.info[0];
					$("#confirmUseCarBut").attr('carId',tjcar.car.id);
					$('#moreDotCarBut').attr('dotId',dot.id);
					addCarInfoList(dot,tjcar);
				}
				//没有推荐车辆，其他网点车辆
// 				else{
// 					$.each(data.info,function(index,carInfo){
// 						addCarInfoList(dot,carInfo);
// 					});
// 				}
				
			}
		});
		
	}
	
	$(".SelectCarBlock").on("click", ".CarButton .UseCarButton", function(){
		<%--由于删除了滑动选车，故木有传车辆id--%>
		bookCar($(this).attr('carId'));
	});
	$(".SelectCarBlock").on("click", ".CarButton .GengduoButton", function(){
		<!--查看更多车辆-->
		window.location.href='<%=path%>/mobile/toShowDotCarList.action?dotId='+$(this).attr('dotId');
	});
	
});

function addCarInfoList(dot,carInfo){
	var htmlstr = '<div class="swiper-slide" id="' + carInfo.car.id + '">'+
					'<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/web/'+carInfo.carImg+'">'+
				'<p class="text-center CarCaption">'+carInfo.car.nickName+'('+carInfo.brandModel+')<span>'+carInfo.car.vehiclePlateId+'</span></p>'+
				'<div class="row CarInfo">'+
					'<div class="col-xs-6 CarProperty">'+
						'<img src="<%=path %>/mobile/common/images/booking/ECO.png">'+carInfo.carType+
					'</div>'+
					'<div class="col-xs-6 CarScore">'+
						'好评度<div class="CarStar">';
						for(var ii = 1;ii <= Number(carInfo.commentScore);ii++){
							htmlstr += '<i class="fa fa-star"></i>';
						}
						htmlstr += '</div>'+carInfo.commentScore+'分'+
					'</div>'+
					'<div class="col-xs-12 CarPosition">'+
						'<i class="fa fa-map-marker"></i>'+dot.address+
					'</div>'+
				'</div>'+
				'<p class="text-center Continued">预计续航<span>'+carInfo.km+'</span>km</p>'+
				'<div class="row CarProgress">'+
				'	<!--电量，大于75%用fa-battery-4，50%-75%用fa-battery-3，25%-50%用fa-battery-2，0%-25%用fa-battery-1，0%用fa-battery-0-->'+
					'<div class="col-xs-2 BatteryIcon"><i class="fa fa-battery-4"></i></div>'+
					'<div class="col-xs-8">'+
						'<div class="progress">';
						for(var ii = 10;ii <= Number(carInfo.SOC); ii+=10){
							htmlstr += '<div class="progress-bar"></div>';
						}
				htmlstr += '</div>'+
					'</div>'+
					'<div class="col-xs-2 BatteryNum"><span>'+carInfo.SOC+'</span>%</div>'+
				'</div>'+
				'</div>';
	$('#CarListDiv').append($(htmlstr));
}

function toShowFee(carId){
	window.location.href="<%=path%>/mobile/toShowCarFee.action?carId="+carId;
}
function bookCar(carid){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/mobile/toBookingInfoCheck.action",
	    dataType:'json',
	    data:{'carId':carid},
	    success: function(data) {
	    	if(data.result == 0){
				window.location.href="<%=path%>/mobile/toBookingInfo.action?carId="+carid;
			}
			else{
				Alert(data.msg);
			}
	    }
	});
}

function choseNeerDot(){
	var dotDistanceInfo = [];
	$.each(dotsMap,function(index,dotMap){
		var pointA = userPoint;  
		var pointB = new BMap.Point(dotMap.dot.lng,dotMap.dot.lat); 
		var distance = (map.getDistance(pointA,pointB)).toFixed(2);
// 		if(Number(distance) <= 1000){
			dotDistanceInfo.push({'distance':distance,'dot':dotMap.dot});
// 		}
	});
	dotDistanceInfo.sort(compare("distance")); 
	return dotDistanceInfo;
}

function compare(propertyName) { 
	return function (object1, object2) { 
		return Number(object1[propertyName])-Number(object2[propertyName]);
// 		var value1 = object1[propertyName]; 
// 		var value2 = object2[propertyName]; 
// 		if (value2 < value1) { 
// 			return -1; 
// 		} 
// 		else if (value2 > value1) { 
// 			return 1; 
// 		} 
// 		else { 
// 			return 0; 
// 		} 
	};
}

</script>
</head>

<body>
<button class="ChangeMapButton ListLabel"><i class="fa fa-exchange"></i><span>列表</span></button>
<div class="container-fluid" id="listDiv">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="row ListBlock">
				<ul class="ListUl" id="ListUl">
				</ul>
			</div>
		</div>
	</div>
</div>
	
	
<div class="container-fluid" id="mapDiv">
	<div class="TopMenu">
		<ul>
			<li><a href="<%=path%>/mobile/toOrderList.action"><i class="fa fa-list-alt"></i></a></li>
			<li>
				<form>
					<div class="input-group">
						<input type="text" class="form-control" placeholder="搜索网点" id="search">
						<span class="input-group-btn">
						<button type="button" id="searchDotButton" class="btn"><span class="fui-search"></span></button>
						</span> </div>
				</form>
			</li>
			<li><a href="<%=path%>/mobile/account/index.action"><i class="fa fa-user"></i></a></li>
			<div class="clearfix"></div>
		</ul>
	</div>
	<div class="row">
		<div id="container" class="MapBlock"> </div>
	</div>
	<div class="CarPositionBlock">
		<%-- 网点具体位置地址 --%>
	</div>
	<div class="SelectCarBlock">
		<div class="col-md-8 col-md-offset-2">
			<div class="SelectCarBorder">
				<i class="fa fa-angle-down fa-2x CloseSelectCar"></i>
				<img class="JianImg" src="<%=path%>/mobile/common/images/booking/jian.png">
				<div id="CarListDiv" class="CarShow">
					
				</div>
				<div class="row CarButton">
					<div class="col-xs-7">
						<button class="btn btn-primary btn-block UseCarButton" id="confirmUseCarBut" carId="">确认用车</button>
					</div>
					<div class="col-xs-5"> <a class="btn btn-primary btn-block GengduoButton" id="moreDotCarBut" dotId="">更多车辆</a> </div>
				</div>
			</div>
		</div>
	</div>
	<div class="BottomButton">
		<div class="col-xs-8 col-xs-offset-2"> 
			<!--当已有订单的时候显示当前订单并隐藏掉立即用车-->
			<ww:if test="hasCurrentOrder.equals(\"true\")">
				<a class="btn btn-embossed btn-primary btn-block OpenCarSelect" href="<%=path%>/mobile/toCurrentOrder.action">当前订单</a>
			</ww:if>
			<ww:else>
				<button type="submit" class="btn btn-embossed btn-primary btn-block OpenCarSelect" id="useCarBut">立即用车</button>
			</ww:else>
			<button style="display: none" id="getLocation" ></button>
		</div>
	</div>
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
			'checkJsApi',
            'getLocation'  
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
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
// 		        var p = tx2bd(latitude,longitude);
// 		        var p = transPoint(latitude,longitude);
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
		 			map.centerAndZoom(point, 15);
		        });
	 	        
		      },
		      cancel: function (res) {
		        Alert('请您授权公众号获取您的地理位置，否则系统将无法定位！');
		      },
			  fail: function(){
				
			  },
			  complete: function(){
				  CloseLoading();
			  }
		    });
		  };

	wx.checkJsApi({
    jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    success: function(res) {
			$('#getLocation').click();
		}
	});
		  
		  
});

function CloseLoading(){
	ShowLoading(false);
};
</script>
</html>
