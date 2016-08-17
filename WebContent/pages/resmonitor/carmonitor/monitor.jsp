<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<%@ page import="com.dearho.cs.util.DateUtil" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<title>车辆监控系统</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>

<script type="text/javascript">
var map;
var carListData;
$().ready(function(){
	searchCars();		
// 	setInterval("searchCars()",10000);
	$('#selectTypeSel').change(function(){
		$('#selectTypeValueInput').val($(this).children('option:selected').attr("data"));
	});
	$('#trajStartTime').datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 0,
		autoclose: true,
		minuteStep: 5,
		format:'yyyy-mm-dd hh:ii',
		endDate:new Date()
	});
	$('#trajEndTime').datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 0,
		autoclose: true,
		minuteStep: 5,
		format:'yyyy-mm-dd hh:ii',
		endDate:new Date()
	});
});

function searchCars(){
	var selectd = $('#selectTypeSel').find("option:selected");
	$(selectd).attr("data",$('#selectTypeValueInput').val());
	
	
	var pars={
			"selectType":$('#selectTypeSel').val(),
			"selectValue":$('#selectTypeValueInput').val()
		};
	$.post('<%=path %>/carmonitor/carRealtimeState.action',pars,r_showCars,'json').error(requestError);

}
function clearSelectValue(){
	$('#selectTypeValueInput').val("");
	
	$("#selectTypeSel option").each(function () {
		$(this).attr("data","");
    });
	
}
function r_showCars(data){
	switch(data.result){
		case 0:
			showCarsInfo(data);
			if(wdFlag){
				wdFlag = false;
				togWd();
			}
			if(cdzFlag){
				cdzFlag = false;
				togCdz();
			}
			break;
		case 1:
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
}



var searchWdCdzFlag = false;
var wdmarks=[];
var wdcontents = [];

var cdzmarks=[];
var cdzcontents = [];

var clmarks=[];
var clcontents = [];

var wdFlag = true;
function addMark2Map(index,wdmark,arrys,contentarr){
	var point = new BMap.Point( wdmark._position.lng,wdmark._position.lat );
	var myRichMarker = new BMapLib.RichMarker(wdmark._content,point,{
        "anchor" : new BMap.Size(-20, -16),
		  "enableDragging" : false});
	map.addOverlay(myRichMarker);
	
	arrys[index] = myRichMarker;
	addClickHandler(contentarr[index],myRichMarker,map);
}
function togWd(){
	if(wdFlag){
		$.each(wdmarks,function(index,wdmark){
			map.removeOverlay(wdmark);
		});
		wdFlag = false;
	}
	else{
		$.each(wdmarks,function(index,wdmark){
			addMark2Map(index,wdmark,wdmarks,wdcontents);
		});
		wdFlag = true;
	}
	
}
var clFlag = true;
function togCl(){
	if(clFlag){
		$.each(clmarks,function(index,clmark){
			map.removeOverlay(clmark);
		});
		clFlag = false;
	}
	else{
		$.each(clmarks,function(index,clmark){
			addMark2Map(index,clmark,clmarks,clcontents);
		});
		clFlag = true;
	}
	
}
var cdzFlag = true;
function togCdz(){
	if(cdzFlag){
		$.each(cdzmarks,function(index,cdzmark){
			map.removeOverlay(cdzmark);
		});
		cdzFlag = false;
	}
	else{
		$.each(cdzmarks,function(index,cdzmark){
			addMark2Map(index,cdzmark,cdzmarks,cdzcontents);
		});
		cdzFlag = true;
	}
	
}

function showCarsInfo(data){
	map = new BMap.Map("container",{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 12);
	map.enableScrollWheelZoom();
	var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl); 
	  /**
	var geolocationControl = new BMap.GeolocationControl();
	 map.addControl(geolocationControl);
	 **/
	carListData = data.info;
	var wzjclNum = 0;
	var zjclNum = 0;
	$(carListData).each(function(index,entity){
		 if(entity.bizStatus != '租借中'){
			 return true;
		 }
		var point = new BMap.Point(entity.lng, entity.lat);
		/**
		translateCallback = function(p){
			point = p;
		}
		
		BMap.Convertor.translate(point,0,translateCallback);  //转换真实坐标
		**/
		//等转换完成之后再标注位置，延迟500ms
		/**setTimeout(function(){**/
			var html = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
				+ '<div id="cpdiv_'+entity.id+'" style="position: absolute; width: 64px; height: 18px;background-color:#31b0d5;font-size:12px;line-height:18px;color:#fff; display:none;text-align:center;border-radius:2px;z-index:1000;">'+entity.plateNumber+'</div>'
	            +     '<img id="img_'+entity.id+'" style="border:none;left:16px; top:16px; position:absolute;" src="<%=path %>/portal/common/images/booking/';
	        if(entity.bizStatus == '租借中'){
	        	html += 'caruse.png';
	        	zjclNum++;
	        }
// 	        else{
// 	        	wzjclNum++;
// 	        	html += 'car.png';
// 	        }
	            html += '">'
				+ '</div>';
			var myRichMarker = new BMapLib.RichMarker(html, point ,{
	            "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
			map.addOverlay(myRichMarker);
			
			myRichMarker.addEventListener("onmouseover", function(e) {
<%-- 				document.getElementById("img_"+entity.id).src = "<%=path %>/common/css/images/mark_4.png"; --%>
				document.getElementById("cpdiv_"+entity.id).style.display = "";
			});
			myRichMarker.addEventListener("onmouseout", function(e) {
<%-- 				document.getElementById("img_"+entity.id).src = "<%=path %>/common/css/images/mark_3.png"; --%>
				document.getElementById("cpdiv_"+entity.id).style.display = "none";
			});
			
			//var param = encodeURI(encodeURI(entity.plateNumber));
			
// 			var content="<table align='center' border='0' cellspacing='0' cellpadding='0' class='map-status-info'>"
// 			+"<tbody><tr><td width='30%' >	<strong>车牌号码</strong></td><td width='30%' >	<strong>是否在充电</strong></td>"
// 			+"<td rowspan=3><button  onclick='showCarDetailAll(\""+entity.id+"\");' >详细信息</button></td></tr>"
// 			+"<tr><td>"+entity.plateNumber+"</td><td >"+entity.status+"</td></tr>"
// 			+"<tr><td><button>开车门</button></td><td><button>锁车门</button></td></tr></tbody></table>";
			var socimg;
			var fontColor = 'green';
			if(entity.SOC > 80){
				socimg = 'dl100.png';
			}else if(entity.SOC > 60){
				socimg = 'dl80.png';
			}else if(entity.SOC > 40){
				socimg = 'dl60.png';
			}else if(entity.SOC > 20){
				socimg = 'dl40.png';
			}else{
				socimg = 'dl20.png';
				fontColor = 'red';
			}
			/*var content = '<table class="table table-condensed InfoTable"><tr><th scope="row">牌号</th><td>' + entity.plateNumber + '</td></tr><tr><th scope="row">车身状态</th><td class="' + fontColor + '">' + entity.status + '</td></tr><tr><th scope="row">运营状态</th><td>' + entity.bizStatus + '</td></tr><tr><th scope="row">当前电量</th><td><span class="' + fontColor + '">' + entity.SOC + '%<img class="dc_img" src="<%=path %>/common/css/images/'+socimg+'" /></td></tr></table><button class="btn btn-info btn-xs pull-right" onclick="showCarDetailAll(\''+entity.id+'\');">详细信息</button></div>';*/
			
			var ordersMileage = '0.0';
			
				var content = '<div class="MonitorDetail">' +
			 	'<button class="btn btn-default pull-right GuijiButton " onclick="showCarDetailAll(\''+entity.id+'\');showCarTrajectory(\''+entity.id+'\')">历史轨迹</button>' +
			 	'<p><b>车牌号码：</b>' + entity.plateNumber + '&nbsp;' + entity.bizStatus + '</p>' +
				'<p><b>车型信息：</b>' + entity.brandModel + '/' + entity.engineTypeName + '/' + entity.gearboxType + '</p>' +
				'<p><b>接收时间：</b>' + entity.obdTime + '&nbsp;&nbsp;&nbsp;车速：' + entity.speed + '&nbsp;转速：' + entity.motorSpeed + '</p>' +
				'<table class="table table-bordered table-condensed">' +
				'<tbody>' + 
				'<tr>' +
				'<th>车辆里程</th><th>订单里程</th><th>续航里程</th>' +
				'</tr>' +
				'<tr>' +
				'<td>' + entity.currentMileage + '</td><td id="tdom_'+entity.id+'">'+ordersMileage+'</td><td>' + entity.lifeMileage + '</td>' +
				'<tr>' +
				'<tr>' +
				'<th>蓄电池电量</th><th>动力电池电量</th><th>是否在充电</th>' +
				'</tr>' +
				'<tr>' +
				'<td>' + entity.batteryPower + '</td><td>' + entity.SOC + '</td><td>' + entity.status + '</td>' +
				'<tr>' +
'</tbody></table>';
				if(entity.ordersCode != null && entity.ordersCode != ''){
					content += '<p><b>当前订单：</b><a href="#">' + entity.ordersCode + '</a>&nbsp;[' + entity.ordersUserName + ']</p>' +
					'<p><b>订单时间：</b>' + entity.ordersStartTime + ' 至 ' + entity.ordersEndTime + '</p>';
				} else {
					content += 	'<p><b>当前订单：</b>无</p>';
				}
				
				content += '</div>';
			 clmarks.push(myRichMarker);
			 clcontents.push(content);
			 addClickHandler(content,myRichMarker,map,450,310);
			 
// 				var distance = (map.getDistance(startPoint,endPoint)).toFixed(2);
// 				if(Number(distance) >= 10000){
// 					ordersMileage = (Number(distance)/1000).toFixed(2) + "公里";
// 		        }
// 		        else{
// 		        	ordersMileage = Number(distance) + "米";
// 		        }
			 
		/**}, 500);**/
		
	});
// 	$('#wzjclsl').text(wzjclNum);
	$('#zjclsl').text(zjclNum);
	if(!searchWdCdzFlag){
		var pars = {};
		$.post('<%=path %>/place/dotInfoSearch.action',pars,function(data){
			$('#wdsl').text(data.info.dots.length);
			$('#cdzsl').text(data.info.chargestations.length);
			
			if(data.info.dots.length > 0){
				  $.each(data.info.dots,function(index,dot){
					    var point = new BMap.Point(dot.lng, dot.lat);
						
						var html = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 30px; height: 32px;  overflow: hidden;">'
				            +     '<img id="img_'+dot.id+'" style="border:none;position:absolute;" src="'+'<%=path %>/portal/common/images/booking/xianyouwangdian.png">'
							+ '</div>';
						var myRichMarker = new BMapLib.RichMarker(html, point ,{
				            "anchor" : new BMap.Size(-20, -16),
							  "enableDragging" : false});
						wdmarks.push(myRichMarker);
						map.addOverlay(myRichMarker);
						var content = '<div class="qp_con">'+
						   '<p><b>网点名称：</b>'+dot.name+'</p>'+
						   '<p><b>网点地址：</b>'+dot.address+'</p>'+
						   '</div><img class="aa" src="'+'/carsharing/common/css/images/ditu_sanjiao.png" />';
						wdcontents.push(content);
						 addClickHandler(content,myRichMarker,map,100,80);
					
				  });
			  }
			  
			  if(data.info.chargestations.length > 0){
				  $.each(data.info.chargestations,function(index,chargestation){
					    var point = new BMap.Point(chargestation.lng, chargestation.lat);
						
						var html = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 24px; height: 32px;  overflow: hidden;">'
				            +     '<img id="img_'+chargestation.id+'" style="border:none; position:absolute;" src="'+'<%=path %>/portal/common/images/booking/chongdianzhuang.png">'
							+ '</div>';
						var myRichMarker = new BMapLib.RichMarker(html, point ,{
				            "anchor" : new BMap.Size(-20, -16),
							  "enableDragging" : false});
						cdzmarks.push(myRichMarker);
						map.addOverlay(myRichMarker);
						var content = '<div class="qp_con CDZDetail">'+
						   '<p><b>充电站名称：</b>'+chargestation.name+'</p>'+
						   '<p><b>充电站地址：</b>'+chargestation.address+'</p>'+
						   '<p><b>充电站信息：</b></p>';
						   '</div><img class="aa" src="'+'<%=path %>/common/css/images/ditu_sanjiao.png" />';
						cdzcontents.push(content);
						 addClickHandler(content,myRichMarker,map,550,250);
					
				  });
			  }
		},'json');
		
		
		searchWdCdzFlag = true;
	}
}

function addClickHandler(content,marker,map,width,height){
	marker.addEventListener("click",function(e){
		openInfo(content,e,map,width,height);
		}
	);
}
function openInfo(content,e,map,width,height){
	var opts = {
			width : width,     // 信息窗口宽度
			height: height,     // 信息窗口高度
			enableMessage:false //设置允许信息窗发送短息
		};
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	//map.centerAndZoom(point,15);
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}

/**
//获取点击点的地理位置
var geoc = new BMap.Geocoder();    
map.addEventListener("click", function(e){        
	var pt = e.point;
	console.log(pt);
	geoc.getLocation(pt, function(rs){
		var addComp = rs.addressComponents;
		console.log(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
	});        
});
**/

function showCarDetailAll(id){
	var currentCar;
	for(var i = 0; i<carListData.length; i++){
		if(carListData[i].id == id){
			currentCar = carListData[i];
			break;
		}
	}
	
	document.getElementById("carsMapDiv").style.display = "none";
	
	
	
	
	$("#showGpsListInfoButton").attr("onclick","showGpsListInfo('"+id+"');");
	$("#showTrajectionButton").attr("onclick","showCarTrajectory('"+id+"');");
	$("#showCarDetailBaseInfoButton").click();
	
}

function showGpsListInfo(id){
	document.getElementById("carDetailBaseInfoDiv").style.display = "none";
	document.getElementById("carGpsListInfoDiv").style.display = "";
	
	$("#showCarDetailBaseInfoButton").attr("class","pan_btn5");
	$("#showCarDetailBaseInfoButton").attr("onclick","showCarDetialBaseInfo(\""+id+"\");");
	
	$("#showGpsListInfoButton").attr("class","pan_btn7");
	
	$("#GPS_data_iframe").attr("src","<%=path %>/carmonitor/getCarGpsDetailInfo.action?id="+id);
	$("#sform").attr("action","<%=path %>/carmonitor/getCarGpsDetailInfo.action?id="+id);
}
function showCarDetialBaseInfo(id){
	document.getElementById("carDetailBaseInfoDiv").style.display = "";
	document.getElementById("carGpsListInfoDiv").style.display = "none";
	
	$("#showCarDetailBaseInfoButton").attr("class","pan_btn7");
	$("#showCarDetailBaseInfoButton").attr("onclick","");
	
	$("#showGpsListInfoButton").attr("class","pan_btn5");
}
/**
function showCarDetailInfo(data){
	var carDetailInfo = data.info;
	document.getElementById("lb_vehiclePlateName").innerHTML="<strong>"+carDetailInfo.vehiclePlateName+"</strong>";
	document.getElementById("lb_pricePerHour").innerHTML="<strong>"+carDetailInfo.pricePerHour+"</strong>";
	document.getElementById("lb_vinNumber").innerHTML="<strong>"+carDetailInfo.vinNumber+"</strong>";
	
	


	
		document.frames('GPS_data_iframe').location.reload();
	window.open(document.all.ifrmname.src,'GPS_data_iframe','');
	
	var pars={
			"plateNumber":carDetailInfo.plateNumber
		};
	$.post(appname+'/carmonitor/getCarGpsDetailInfo.action',pars,showCarGpsDetailInfo,'json').error(requestError);


}
**/
function tabclick(obj){  
    if(obj.className=='cur')return;  
    var idx;  
    for(var n=0; n<obj.parentNode.childNodes.length; n++){  
        obj.parentNode.childNodes[n].className="";  
        if(obj==obj.parentNode.childNodes[n])idx=n;  
    }  
    obj.className="cur";  
    var pc = obj.parentNode.nextSibling;  
    while(pc.nodeType==3)
    	pc=pc.nextSibling;  
    for(var n=0; n<pc.childNodes.length; n++){  
        pc.childNodes[n].className="hdn";  
    }  
    pc.childNodes[idx].className="";  
}  

function backToMap(){
	document.getElementById("carTrajectoryMapDiv").style.display = "none";
	document.getElementById("carsMapDiv").style.display = "";
}
function showCarTrajectory(id){

	document.getElementById("carTrajectoryMapDiv").style.display = "";
	
	var pars={
			"id":id
		};
	$.post('<%=path %>/carmonitor/carTrajectory.action',pars,showTrajectory,'json').error(requestError);
	$('#carIdHiddenInp').val(id);
}
var lushu;
var lushuindex = 0;
var lushuinfo;
var markArrLength = 0;
var markArr;
var markArrIndex = 0;
function showTrajectory(data){
	lushuinfo = data.info;
	markArrLength = 0;
	markArr = [];
	markArrIndex = 0;
	changeLushuBut();
	showLushuInfo();
}
function showLushuInfo(){
	if(lushuindex >= lushuinfo.length){
		lushuindex = 0;
	}
	if(lushuinfo.length == 0){
		var map = new BMap.Map('carTrajectoryContainer',{enableMapClick:false});//构造底图时，关闭底图可点功能
		map.enableScrollWheelZoom();
		map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 10);
		lushuindex = -1;
	}
	document.getElementById("lushuIndexCountDiv").innerHTML=(lushuindex+1)+"/"+lushuinfo.length;
	
	$(lushuinfo).each(function(index,entity){
		
		if(index == lushuindex){
			/**
			var arrPois = [entity.length];
			markArrLength = entity.length;
			markArr = [markArrLength];
			$(entity).each(function(index,ent){
				arrPois[index] = new BMap.Point(ent.lng, ent.lat);
			});
			console.log("send:"+arrPois.length);
			setTimeout(function(){
				BMap.ConvertorMore.transMore(arrPois,0,callback);        //一秒之后开始进行坐标转换。参数2，表示是从GCJ-02坐标到百度坐标。参数0，表示是从GPS到百度坐标
			}, 500);
			**/
			var points = [entity.carLocations.length];
			var map = new BMap.Map('carTrajectoryContainer',{enableMapClick:false});//构造底图时，关闭底图可点功能
			map.enableScrollWheelZoom();
			map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 10);
			var navigationControl = new BMap.NavigationControl();
			  map.addControl(navigationControl); 
			
			var sumPathRoute = 0;
			var driving = new BMap.DrivingRoute(map);    //创建驾车实例
			var tsStartTime;
			var tsEndTime;
			var maxSpeed = 0;
			var valSpeed = 0;
			var lastTime;
			var isTooNear = true;
			tsStartTime = entity.orders.ordersTimeStr;
			tsEndTime = entity.orders.ordersBackTimeStr;
			$(entity.carLocations).each(function(index,ent){
				points[index] = new BMap.Point(ent.lng, ent.lat);
				if(index == "0"){
					maxSpeed = ent.speed;
// 					lastTime = ent.tsDate;
					return true;
				}
				if(ent.speed > maxSpeed){
					maxSpeed = ent.speed;
				}
				var distance = map.getDistance(points[index - 1], points[index]);
				if(distance < 100){
					points[index] = points[index - 1];
					return true;
				}
				isTooNear = false;
// 				var lastTimes = lastTime.substring(0,10).split('-');  
// 				var thisTimes = ent.tsDate.substring(0,10).split('-');
// 				var lastTimeStr = lastTimes[1]+'/'+lastTimes[2]+'/'+lastTimes[0]+' '+lastTime.substring(10,16)+":00";
// 				var thisTimeStr = thisTimes[1]+'/'+thisTimes[2]+'/'+thisTimes[0]+' '+ent.tsDate.substring(10,16)+":00";
// 				var valtime =(Date.parse(thisTimeStr)-Date.parse(lastTimeStr))/1000;  
// 				if(valtime > 0){
// 					valSpeed = distance*3.6/valtime;
// 					if(valSpeed > maxSpeed){
// 						maxSpeed = valSpeed.toFixed(2);
// 					}
// 				}
				
				driving.search(points[index - 1], points[index]);    
// 				lastTime = ent.tsDate;
			});
			if(isTooNear){
				points[0] = new BMap.Point(points[0].lng+0.0001,points[0].lat);
				$('#ordersInfoTable').empty();
// 				document.getElementById("TrajInfoSpan").innerHTML = "<strong>总行程:</strong>0米";
			}
			else{
// 				var beginTimes=tsStartTime.substring(0,10).split('-');  
// 				 var endTimes=tsEndTime.substring(0,10).split('-');  
// 				 var beginTime=beginTimes[1]+'/'+beginTimes[2]+'/'+beginTimes[0]+' '+tsStartTime.substring(10,16)+":00";  
// 				 var endTime=endTimes[1]+'/'+endTimes[2]+'/'+endTimes[0]+' '+tsEndTime.substring(10,16)+":00";  
// 				 var trajTime =(Date.parse(endTime)-Date.parse(beginTime))/1000;  
				 
				driving.setSearchCompleteCallback(function(){
			        var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
			        sumPathRoute +=  driving.getResults().getPlan(0).getDistance(false);
			        var polyline = new BMap.Polyline(pts);     
			        map.addOverlay(polyline);
			        var sumPathStr;
			        if(sumPathRoute >= 10000){
			        	sumPathStr = (sumPathRoute/1000).toFixed(2) + "公里";
			        }
			        else{
			        	sumPathStr = sumPathRoute + "米";
			        }
			        $('#ordersInfoTable').empty();
			        var ordersTbInfo = '<thead><tr class="active">'+
									'<th>订单起始时间</th>'+
									'<th>订单终止时间</th>'+
									'<th>订单号</th>'+
									'<th>承租人</th>'+
									'<th>总行程</th>'+
									'<th>取车网点</th>'+
									'<th>还车网点</th>'+
									'<th>时长</th>'+
									'<th>费用</th>'+
								'</tr></thead><tbody><tr>'+
									'<td>'+entity.orders.ordersTimeStr+'</td>'+
									'<td>'+entity.orders.endTimeStr+'</td>'+
									'<td><a style="color:#e86d4d" href="javascript:showOrderDetailForDialog(\''+entity.orders.id+'\',\'<%=path%>\')">'+entity.orders.ordersNo+'</a></td>'+
									'<td><a style="color:#e86d4d" href="javascript:showSubscriberDetailForDialog(\''+entity.orders.memberId+'\',\'<%=path%>\')">'+entity.orders.memberName+'</a></td>'+
									'<td>'+sumPathStr+'</td>'+
									'<td>'+entity.orders.beginSiteId+'</td>'+
									'<td>'+entity.orders.endSiteId+'</td>'+
									'<td>'+entity.orders.ordersDuration+'分钟</td>'+
									'<td>'+entity.orders.totalFee+'元</td>'+
								'</tr>'+
							'</tbody>';
			        $('#ordersInfoTable').append($(ordersTbInfo));
// 			        document.getElementById("TrajInfoSpan").innerHTML = "<strong>订单起始时间:</strong>"+tsStartTime+"，<strong>订单终止时间:</strong>"+tsEndTime+"，" +
// 			        		"<strong>总行程:</strong>"+sumPathStr+"，<strong>订单号:</strong>"+entity.orders.ordersNo+"，<strong>租车人:</strong>"+entity.orders.memberName;
					
				});
			}
			 
			
		    var myP1 = points[0];    //起点
			var myP2 = points[points.length - 1];    //终点
			var html1 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 48px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
				/** +'<div id="startPointTime" style="position: absolute; width: 100px; height: 16px;background-color:#000;font-size:12px;color:#fff; display:none">'+tsStartTime+'</div>' **/
		        +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/common/css/images/lushustart.png">'
				+ '</div>';
			var myRichMarker1 = new BMapLib.RichMarker(html1, myP1 ,{
		        "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
			myRichMarker1.addEventListener("onmouseover", function(e) { 
				document.getElementById("startPointTime").style.display = "";
			});
			myRichMarker1.addEventListener("onmouseout", function(e) { 
				document.getElementById("startPointTime").style.display = "none";
			});
			map.addOverlay(myRichMarker1);
			
			var html2 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 48px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
				/**+ '<div id="endPointTime" style="position: absolute; width: 100px; height: 16px;background-color:#000;font-size:12px;color:#fff; display:none">'+tsEndTime+'</div>'**/
				+ '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/common/css/images/lushuend.png">'
				+ '</div>';
			var myRichMarker2 = new BMapLib.RichMarker(html2, myP2 ,{
		        "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
			myRichMarker2.addEventListener("onmouseover", function(e) { 
				document.getElementById("endPointTime").style.display = "";
			});
			myRichMarker2.addEventListener("onmouseout", function(e) { 
				document.getElementById("endPointTime").style.display = "none";
			});
			map.addOverlay(myRichMarker2);
			
			/**
			// 定义一个控件类,即function
			function showLushuTime(){
				// 默认停靠位置和偏移量
				 this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
				 this.defaultOffset = new BMap.Size(52, 0);
			}
			// 通过JavaScript的prototype属性继承于BMap.Control
			showLushuTime.prototype = new BMap.Control();
			
			// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
			// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
			showLushuTime.prototype.initialize = function(map){
			  // 创建一个DOM元素
			  var div = document.createElement("div");
			  // 添加文字说明
			  div.appendChild(document.createTextNode("行程起始时间:"+tsStartTime));
			  div.appendChild(document.createElement("br"));
			  div.appendChild(document.createTextNode("行程终止时间:"+tsEndTime));
			  // 设置样式
			  div.style.cursor = "default";
			  div.style.border = "1px solid gray";
			  div.style.backgroundColor = "white";
			  
			  // 添加DOM元素到地图中
			  map.getContainer().appendChild(div);
			  // 将DOM元素返回
			  return div;
			}
			// 创建控件
			var showLushuTimeCtrl = new showLushuTime();
			// 添加到地图当中
			map.addControl(showLushuTimeCtrl);
			**/
			
			map.setViewport(points);          //调整到最佳视野
		}
		
	});
	
}
function callback(xyResults){
	$(xyResults).each(function(index,entity){
		markArr[markArrIndex + index] = entity;
	});
	markArrIndex += xyResults.length;
	if(markArrIndex == markArrLength){
		callback2(markArr);
	}
}
function callback2(xyResults){
	
	
	var points = [xyResults.length];
	var map = new BMap.Map('carTrajectoryContainer',{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.enableScrollWheelZoom();
	map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 10);
	var navigationControl = new BMap.NavigationControl();
	  map.addControl(navigationControl); 
	var driving = new BMap.DrivingRoute(map);    //创建驾车实例
	$(xyResults).each(function(index,xyResult){
		if(xyResult.error != 0){return true;}//出错就直接返回;
		var point = new BMap.Point(xyResult.x, xyResult.y);
		points[index] = point;
		if(index == "0"){
			return true;
		}
		var distance = map.getDistance(points[index - 1], point);
		if(distance < 10){
			points[index] = points[index - 1];
			return true;
		}
		driving.search(points[index - 1], point);    
		
		/**
		var m1 = new BMap.Marker(point);         //创建marker
		var lab1 = new BMap.Label("途经点"+index,{position:point});        //创建label
		map.addOverlay(lab1);
		map.addOverlay(m1); 
		**/
	});
    driving.setSearchCompleteCallback(function(){
        var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组

        var polyline = new BMap.Polyline(pts);     
        map.addOverlay(polyline);
	});
	
    var myP1 = points[0];    //起点
	var myP2 = points[points.length - 1];    //终点
	var html1 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
        +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/common/css/images/lushustart.png">'
		+ '</div>';
	var myRichMarker1 = new BMapLib.RichMarker(html1, myP1 ,{
        "anchor" : new BMap.Size(-20, -16),
		  "enableDragging" : false});
	
	map.addOverlay(myRichMarker1);
	
	var html2 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
        +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/common/css/images/lushuend.png">'
		+ '</div>';
	var myRichMarker2 = new BMapLib.RichMarker(html2, myP2 ,{
        "anchor" : new BMap.Size(-20, -16),
		  "enableDragging" : false});
	map.addOverlay(myRichMarker2);
	map.setViewport(points);          //调整到最佳视野
	/**
	map.addEventListener("click",function(e){
		console.log(e.point.lng + "," + e.point.lat);
	});
	
	
	var getRandomColor = function(){   
		  
		  return  '#' +   
		    (function(color){   
		    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])   
		      && (color.length == 6) ?  color : arguments.callee(color);   
		  })('');   
	} 
	map.addOverlay(new BMap.Polyline(points, {strokeColor: getRandomColor}));
	map.setViewport(points);
	var myP1 = points[0];    //起点
	var myP2 = points[points.length - 1];    //终点
	
	var html1 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
         +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="<%=path%>/common/css/images/lushustart.png">'
		+ '</div>';
	var myRichMarker1 = new BMapLib.RichMarker(html1, myP1 ,{
         "anchor" : new BMap.Size(-20, -16),
		  "enableDragging" : false});
	
	map.addOverlay(myRichMarker1);
	
	var html2 = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
         +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="<%=path%>/common/css/images/lushuend.png">'
		+ '</div>';
	var myRichMarker2 = new BMapLib.RichMarker(html2, myP2 ,{
         "anchor" : new BMap.Size(-20, -16),
		  "enableDragging" : false});
	map.addOverlay(myRichMarker2);
	
	
	lushu = new BMapLib.LuShu(map,points,{
		defaultContent:"",//
		autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
		icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
		speed: 2500,
		enableRotation:true //是否设置marker随着道路的走向进行旋转
	});        
	**/
		
	
}
function leastLushu(){
	if(lushuindex > 0){
		lushuindex -= 1;
		resetSearch();
	}
	
}
function nextLushu(){
	if(lushuindex < lushuinfo.length - 1){
		lushuindex += 1;
		resetSearch();
	}
	
}
function firstLushu(){
	if(lushuindex > 0){
		lushuindex = 0;
		resetSearch();
	}
}
function lastLushu(){
	if(lushuindex < lushuinfo.length - 1){
		lushuindex = lushuinfo.length - 1;
		resetSearch();
	}
}
function goLushu(){
	var thisIndex = $('#lushuIndexInp').val();
	if(thisIndex > 0 && thisIndex <= lushuinfo.length){
		lushuindex = thisIndex - 1;
		resetSearch();
	}
}
function resetSearch(){
	markArr = [0];
	markArrLength = 0;
	markArrIndex = 0;
	showLushuInfo();
	changeLushuBut();
}
function changeLushuBut(){
	if(lushuinfo.length == 1 || lushuinfo.length == 0){
		$('#up').attr("class","pan_btn7");
		$('#down').attr("class","pan_btn7");
		$('#first').attr("class","pan_btn7");
		$('#last').attr("class","pan_btn7");
		
	}
	else if(lushuindex == 0){
		$('#up').attr("class","pan_btn7");
		$('#down').attr("class","pan_btn5");
		$('#first').attr("class","pan_btn7");
		$('#last').attr("class","pan_btn5");
	}
	else if(lushuindex == lushuinfo.length - 1){
		$('#down').attr("class","pan_btn7");
		$('#up').attr("class","pan_btn5");
		$('#first').attr("class","pan_btn5");
		$('#last').attr("class","pan_btn7");
	}
	else{
		$('#up').attr("class","pan_btn5");
		$('#down').attr("class","pan_btn5");
		$('#first').attr("class","pan_btn5");
		$('#last').attr("class","pan_btn5");
	}
}

function backToCarGpsDetail(){
	document.getElementById("carTrajectoryMapDiv").style.display = "none";
}
/**
function lushuAction(type){
	if(type == 1){
		lushu.start();
	}
	else if(type == 2){
		lushu.stop();
	}
	else if(type == 3){
		lushu.pause();
	}
	
}
*/
function searchLushu(){
	lushuindex = 0;
	var pars={
			"id":$('#carIdHiddenInp').val(),
			"startTime":$('#trajStartTime').val(),
			"endTime":$('#trajEndTime').val()
		};
	$.post('<%=path %>/carmonitor/carTrajectory.action',pars,showTrajectory,'json').error(requestError);
	
}

</script>

</head>
<body class="ddqcjkptPage">
<%-- 
<div style="width:200px;height:500px;border:0px solid gray;margin:10px 0 0 10px;top:1px;left:1px;position: absolute;">
<ul>
	<li id="li_dtjk" class="selectLiCss">地图监控</li>
	<li>车辆远程控制</li>
	<li>车辆状态
		<ul>
			<li id="li_clzt_dqzt"><a href="carstatus.jsp">当前状态</a></li>
			<li>历史状态</li>
		</ul>
	</li>
</ul>
</div>
 --%>

<div id="carsMapDiv" style="width:100%;height:100%;margin:0px 0 0 0px;top:0px;left:0px;position: absolute;overflow:hidden; ">


<div class="panel" style="padding:0px 0px;">
	<div class="panel-heading"></div>
</div>
	<div style="width:100%;height:10%;border:0px solid gray;margin:0px 0 0 0px;top:10px;left:10px;position: absolute;" >
		<table>
			<tr>
			<td width="150"><span style="margin-right: 3px;">按</span><select class="form-control" style="display: inline-block; width:85%;" id="selectTypeSel"><option id="selectTypeOpt_cphm" value="plateNumber" data="">车牌号码</option>
			<option id="selectTypeOpt_cjh" value="deviceNo" data="">车机号</option></select></td>
			<td>：</td><td><div class="nr"><input type="text" class="kd form-control" id="selectTypeValueInput" /></div></td>
			<td><div class="btn btn-danger" onclick="searchCars();" style="margin: 0 10px;padding: 5px 10px;">查询</div></td>
			<td><div class="btn btn-info" onclick="clearSelectValue()" style="padding: 5px 10px;">清空</div></td>
			</tr>
		</table>
	</div>
	<div style="width:99%;height:85%;border:1px solid gray;margin:0px 0 0 0px;top:60px;left:0px;position: absolute;" id="container"></div>
	<div id="tools">
		<ul class="list-group">
<!-- 			<li class="list-group-item" onclick="togCl();"> -->
<!-- 				<span class="badge" id="wzjclsl"></span> -->
<%-- 		   		<img src="<%=path %>/portal/common/images/booking/car.png">车辆 (空闲) --%>
<!-- 		    </li> -->
			<li class="list-group-item" onclick="">
				<span class="badge" id="zjclsl"></span>
		   		<img src="<%=path %>/portal/common/images/booking/caruse.png">车辆 (租借中)
		    </li>
		   	<li class="list-group-item" onclick="togWd();">
				<span class="badge label-success" id="wdsl"></span>
		   		<img src="<%=path %>/portal/common/images/booking/xianyouwangdian.png">自有网点
			</li>
			<li class="list-group-item" onclick="togCdz();">
				<span class="badge" id="cdzsl"></span>
		   		<img src="<%=path %>/portal/common/images/booking/chongdianzhuang.png">公共充电站
			</li>
		</ul>
	</div>

</div>
	
<div id="carTrajectoryMapDiv" style="width:99%;height:99%;border:1px solid gray;margin:0px 0 0 0px;top:0px;left:0px;position: absolute;overflow-x:hidden;display:none;">
	
	<input type="hidden" id="carIdHiddenInp"/>
	<span style="margin-right: 10px;margin-left: 10px;">
	从：<input type="text" value="<%=DateUtil.getChar10DateString() + " 00:00"%>"   id="trajStartTime" class="form-control" readonly/>
	到：<input type="text" value="<%=DateUtil.formatDate(null,"yyyy-MM-dd HH:mm")%>"  id="trajEndTime" class="form-control" readonly/>
	</span>
		<div class="pan_btn5" style="padding:6px 5px;margin-right: 15px"  onclick="searchLushu();">查询</div> 
		<div id="first" style="display: none" class="pan_btn5" onclick="firstLushu();">首个订单</div> 
		<div id="up" class="pan_btn5" style="padding:6px 5px;margin-right: 15px" onclick="leastLushu();">上个订单行程</div> 
		<div id="down" class="pan_btn5" style="padding:6px 5px;margin-right: 15px" onclick="nextLushu();">下个订单行程</div> 
		<div id="last" style="display: none" class="pan_btn5" onclick="lastLushu();">最后订单</div> 
		<span>查看第<input type="text" id="lushuIndexInp" class="form-control" style="width:30px;" />个订单</span>
		<div class="pan_btn5" style="padding:6px 5px;margin-right: 15px" onclick="goLushu();">GO</div>
		<span id="lushuIndexCountDiv"></span>
		
		<div class="pan_btn5" onclick="backToMap();" style="margin-left: 20px;padding:6px 5px;margin-right: 15px">返回</div> 
		<div class="table-responsive XingchengInfo">
			<table id="ordersInfoTable" class="table table-condensed text-center">
				
			</table>
		</div>
	<div style="width:100%; height:20px; float:left;font-family:'微软雅黑';  line-height:20px;
	            margin:-2px 0px 0px 5px;">
<!-- 	    <span id="TrajInfoSpan" style="font-size: 13px"></span> -->
	</div>
	<div style="width:100%;height:87.8%;border:0px solid gray;margin:0px 0 0 0px;top:120px;left:0px;position: absolute;" id="carTrajectoryContainer"></div>

</div>
<div style="display: none" id="maptest"></div>

</body>



</html>
