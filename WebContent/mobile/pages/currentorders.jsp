<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>当前订单</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>
<script src="<%=path %>/common/js/GpsTransform.js"></script>


<link rel=stylesheet href="<%=path%>/mobile/common/css/ordernow2.css">
<script type="text/javascript">
$(function(){
	$('#getLocation').click();
	if($("#type").val()=="jishizu"){
		setFee();
	}else{
		$("#feiyong").html($("#totalFee").val());
	};
	
	/*水波纹如果是Android机不显示Canvas，用背景图片显示*/
	if (isAndroid()){
		$(".StateBlock .StateImgBlock .StateCircleBlock .StateFlowingBlock .CanvasBlock").remove();
		$(".StateBlock .StateImgBlock .StateCircleBlock .StateFlowingBlock").addClass("Flowing");
		$(".StateBlock .StateImgBlock .StateCircleBlock .StateFlowingBlock").attr("style", "background-position: center " + (100-<ww:property value="socimg" />) + "px");
	};
});

var carlat = '<ww:property value="carRealtimeState.lat"/>';
var carlng = '<ww:property value="carRealtimeState.lng"/>';
function checkLocation(){
	if(userPoint == undefined || userPoint == null){
		Alert('请稍后，位置获取中...');
	}
	if(userPoint != null && carlng != null && carlng != ''){
		var carPoint = new BMap.Point(carlng,carlat);
		var map1 = new BMap.Map("maptest",{enableMapClick:false});//构造底图时，关闭底图可点功能
		var distance = (map1.getDistance(carPoint,userPoint)).toFixed(2);
		if(distance < 50){
			return true;
		}
	}
	return false;
}

function toChargeList(ordersId){
	if($('#ordersState').val() == "3"){
		window.location.href = '<%=path%>/mobile/toChargingStationListPage.action?ordersId='+ordersId;
	}
	else{
		Alert("补电之前，请您先开启订单，即使用车辆！");
	}
}

function returnbackCar(ordersDetailId){
 		window.location.href='<%=path%>/mobile/toBackCarInfo.action?ordersDetailId='+ordersDetailId;
}

function openDoor(){
	ShowLoading(true);
	if($('#ordersState').val() == "1"){
		$.ajax({
			async: true,
			data: {'ordersId':$('#ordersId').val()},
			type: "POST",
			url: "<%=path %>/mobile/startUseCar.action",
			dataType: "json",
			success: function(data){
				if(data.result == 0){
					$('#ordersState').val("3");
				}
				else {
					Alert(data.msg);
				}
			},
			error: function(){
				Alert("操作失败，请您稍后再次尝试");
			}
			
		});
	}
	
	$.ajax({
		async: true,
		data: {'ordersId':$('#ordersId').val()},
		type: "POST",
		url: "<%=path %>/mobile/openDoor.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				if($('#ordersState').val() == "1"){
					Alert("车已开门，请尽快取车");
				}
				Alert("操作成功");
			}
			else{
				Alert("操作失败，请您稍后再次尝试");
			};
		},
		error: function(){
			Alert("操作失败，请您稍后再次尝试");
		},
		complete: function(){
			ShowLoading(false);
		}
		
	});
}

function ordersDetail(id){
	  window.location.href="<%=path%>/mobile/toOrdersDetail.action?orderId="+id; 
}

function changeType(){
	$.ajax({
		async: true,
		type: "POST",
		url: "<%=path %>/mobile/checkAreadyRent.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				window.location.href="<%=path%>/mobile/changeRentType.action"; 
			}
			else{
				Alert(data.msg);
			};
		}
	});	
}


function closeDoor(){
	ShowLoading(true);
	var lf = checkLocation();
	/* if(!lf){
		Alert("请您在车边50范围内进行操作");
		return;
	} */
	
	$.ajax({
		async: true,
		data: {'ordersId':$('#ordersId').val()},
		type: "POST",
		url: "<%=path %>/mobile/closeDoor.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				Alert("操作成功");
			}
			else{
				Alert("操作失败，请您稍后再次尝试");
			};
		},
		error: function(){
			Alert("操作失败，请您稍后再次尝试");
		},
		complete: function(){
			ShowLoading(false);
		}
		
	});
}


var currentFee;
function setFee(){
	if($("#type").val()=="jishizu"){
		if(currentFee==null){
			$.ajax({
				async: false,
				type: "POST",
				url: "<%=path %>/mobile/countCurrentFee.action",
				dataType: "json",
				success: function(data){
					if(data.result == 0){
						currentFee = data.info;
					}
				}
			});
		}
		if(currentFee!=null){
			var totalFee = (parseFloat($("#totalFee").val())+parseFloat(currentFee)).toFixed(2);
			$("#feiyong").html(totalFee);
			$("#shizuFee").html(parseFloat(currentFee).toFixed(2));
		}
	}
}
var TimeShow = setInterval("setFee()",1000 * 60);

</script>
</head>

<body>
<ww:if test="orders == null">
<div class="container">
	<div class="row">
		<div class="col-xs-8 col-xs-offset-2 NoOrderBlock">
			<p class="text-center">您当前没有订单！</p>
			<a class="btn btn-embossed btn-primary btn-block YudingButton" href="<%=path%>/mobile/toBookCar.action">去预定</a>
		</div>
	</div>
</div>
</ww:if>
<ww:else>
<input type="hidden" id="ordersId" value="<ww:property value="orders.id" />" />
<input type="hidden" id="ordersState" value="<ww:property value="orders.state" />" />
<input type="hidden" id="totalFee" value="<ww:if test="orders.totalFee != null"><ww:property value="orders.totalFee" /></ww:if><ww:if test="orders.totalFee == null"><ww:property value="0" /></ww:if>" />
<input type="hidden" id="basePrice" value="<ww:property value="currentStrategyBase.basePrice" />" />
<input type="hidden" id="minConsumption" value="<ww:property value="currentStrategyBase.minConsumption" />" />
<input type="hidden" id="type" value="<ww:property value="currentType.code" />" />

<div class="container">
	<div class="row">
    	<div class="col-md-4 col-md-offset-4 StateBlock">
        	<div class="StateImgBlock" style="background-image: url(<%=path%>/mobile/common/images/times2/<ww:property value="socimg" />.png)"><!--外圈刻度，每5一张图，0到100-->
            	<div class="StateCircleBlock">
                	<div class="StateFlowingBlock">
                    	<canvas class="CanvasBlock"></canvas>
                    	<div class="StateNumBlock">
                        	<ww:property value="soc" /><span>%</span>
                        </div>
                    </div>
                </div>
            </div>
            <p class="XuhangBlock">预计续航里程：<span class="Number"><ww:property value="km" /><span class="Danwei">KM</span></span></p>
    	</div>
    </div>
    
    <div class="row">
    	<div class="col-md-4 col-md-offset-4 FeiyongBlock">
        	<p>当前费用：<i class="fa fa-jpy"></i><span class="Number" id="feiyong"></span></p>
        </div>
    </div>
    
    <div class="row">
    	<div class="col-md-4 col-md-offset-4 LinkBlock">
        	<ul>
            	<li>
                	<a class="btn" onclick="openDoor()" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/OpenDoor.png">
                        <p>开车门</p>
                        <small>OPEN</small>
                    </a>
                </li>
                <li>
                	<a class="btn" onclick="closeDoor()" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/LockDoor.png">
                        <p>锁车门</p>
                        <small>LOCK</small>
                    </a>
                </li>
                <li>
                	<a class="btn" onclick="ordersDetail('<ww:property value="orders.id" />')" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/Dingdan.png">
                        <p>订单详情</p>
                        <small>DETAIL</small>
                    </a>
                </li>
                <li>
                	<a class="btn" href="tel:010-57862214" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/Service.png">
                        <p>呼叫客服</p>
                        <small>HELP</small>
                    </a>
                </li>
                <li>
                	<a class="btn" onclick="toChargeList('<ww:property value="orders.id" />')" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/Charge.png">
                        <p>补电</p>
                        <small>CHARGING</small>
                    </a>
                </li>
                <li>
                	<a class="btn" onclick="returnbackCar('<ww:property value="currentOrdersDetail.id" />')" ontouchstart="">
                        <img class="img-responsive center-block" src="<%=path%>/mobile/common/images/ordernow/BackCar.png">
                        <p>还车</p>
                        <small>RETURN</small>
                    </a>
                </li>
                <div class="clearfix"></div>
            </ul>
        </div>
    </div>
    
    <div class="row">
    	<div class="col-md-4 col-md-offset-4 OrderBlock">
        	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <ww:iterator value="orders.ordersDetail" id="data" status="rl">
              <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="Order<ww:property value="#rl.index+1" />">
                  <div class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#OrderDetail<ww:property value="#rl.index+1" />" aria-expanded="true" aria-controls="OrderDetail<ww:property value="#rl.index+1" />">
                      <div class="row">
                      	<div class="col-xs-4 OrderState">
                        	<p> <ww:property value="typeName" />
                            <ww:if test='isTimeout!="1"&&isOver=="1"'><span class="label label-default">已结束</span></ww:if>
                            <ww:if test='isTimeout=="1"&&isOver=="1"'><span class="label label-danger">已超时</span></ww:if>
                            <ww:if test='isRunning=="1"'><span class="label label-warning">进行中</span></ww:if>
                            </p>
                        </div>
                        
                        <div class="col-xs-5 OrderTime">
                             <ww:if test='endTime!=null'>
                             	<p><span class="label label-default">开始</span><ww:property value="transDate12String(beginTime)" /></p>
                             	<p><span class="label label-default">结束</span><ww:property value="transDate12String(endTime)" /></p>
                             </ww:if> 	
                             <ww:else><p class="OneP"><span class="label label-default">开始</span><ww:property value="transDate12String(beginTime)" /></p></ww:else>
                        </div>
                        <div class="col-xs-3 OrderPrice">
                        	<p>
                                <ww:if test='isPrePay=="0" && isRunning=="1"'>计费中...</ww:if>
                                <ww:else><i class="fa fa-jpy"></i><ww:property value="totalFee" /></ww:else>
                            </p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>
                <div id="OrderDetail<ww:property value="#rl.index+1" />" class="panel-collapse collapse <ww:if test='isRunning=="1"'>in</ww:if>" role="tabpanel" aria-labelledby="Order<ww:property value="#rl.index+1" />">
                  <div class="panel-body">
                    <table>
                    	<tbody>
                            <tr>
                            	<th>开始时间：</th>
                                <td><ww:property value="transDate12String(beginTime)" /></td>
                            </tr>
                            
                            <ww:if test='endTime!=null'>
                            <tr>
                                <th>结束时间：</th>
                                <td><ww:property value="transDate12String(endTime)" /></td>
                            </tr>
                            </ww:if>
                            <ww:if test='isRunning=="1" && isPrePay=="1"'>
                                <tr>
                                    <th>剩余时长：</th>
                                    <td><ww:property value="restTimeStr" /></td>
                                </tr>
                            </ww:if>
                            
                            <ww:if test='isPrePay=="0"'> 
                            	<tr>
                            		<th>已用时长：</th>
                                	<td><ww:property value="useTimeStr" />(<i><ww:property value="timeFee" /></i> 元)</td>
                            	</tr>
                            	
                            	<ww:if test='mileage!=null && mileage!=0'>
                            		<tr>
                            			<th>已开里程：</th>
                                		<td><ww:property value="mileage" />公里(<i><ww:property value="mileFee" /></i> 元)</td>
                            		</tr>
                            	</ww:if>
                            	
                             </ww:if>
                             
                              <ww:if test='ticketsFee!=null && ticketsFee!=0'>
                                <tr>
                                    <th>超时罚金：</th>
                                    <td><i><ww:property value="ticketsFee" /></i> 元</td>
                                </tr>
                            </ww:if>
                            
                            <tr>
                                <th>总费用：</th>
                                <td>
                                    <ww:if test='isPrePay=="0" && isRunning=="1"'><i id="shizuFee"><ww:property value="totalFee" /></i> 元</ww:if>
                                    <ww:else><i><ww:property value="totalFee" /></i> 元</ww:else>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                  </div>
                </div>
              </div>
              </ww:iterator>
            </div>
        </div>
    </div>
	
</div>
</ww:else>
<button style="display: none" id="getLocation" ></button>	
<div style="display: none" id="maptest"></div>
</body>
<script>
(function(){for(var a=0,b=['ms','moz','webkit','o'],c=0;b.length>c&&!window.requestAnimationFrame;++c)window.requestAnimationFrame=window[b[c]+'RequestAnimationFrame'],window.cancelAnimationFrame=window[b[c]+'CancelAnimationFrame']||window[b[c]+'CancelRequestAnimationFrame'];window.requestAnimationFrame||(window.requestAnimationFrame=function(b){var d=(new Date).getTime(),e=Math.max(0,16-(d-a)),f=window.setTimeout(function(){b(d+e)},e);return a=d+e,f}),window.cancelAnimationFrame||(window.cancelAnimationFrame=function(a){clearTimeout(a)})})();



(function(){
    var canvas = document.querySelector('canvas');
    var ctx = canvas.getContext('2d');

    var stageWidth = 0;
    var stageHeight = 0;
    var stageWidth2 = 0;
    var stageHeight2 = 0;
    var totalLength2 = 0;

    var distanceX = 0;

    var config = {
        height: 30,
        waveLength: 200,
        curveFactor: 3,
        speed: 4
    }

    var DELTA_WIDTH = 1;

    function init(){
        window.onresize = onResize;
        onResize();
        render();
    }

    function onResize(){
        stageWidth = canvas.width = window.innerWidth;
        stageHeight = canvas.height = window.innerHeight;
        stageWidth2 = stageWidth / 2;
        stageHeight2 = stageHeight / 2  + 180 - 3.6 * <ww:property value="socimg" />;
        totalLength2 = Math.ceil(stageWidth2 / DELTA_WIDTH) * DELTA_WIDTH;
        redraw();
    }

    function render(){
        redraw();
        requestAnimationFrame(render);
    }

    function _getHeight(distanceX, x) {
        var offsetX = ((distanceX + x) /totalLength2 - 1) * (totalLength2 / config.waveLength);
        var waveFactor = Math.cos((x / totalLength2 - 1) * Math.PI / 2);
        return Math.cos(offsetX * Math.PI) * Math.pow(waveFactor, config.curveFactor) * config.height;
    }

    function redraw(){
        var x = stageWidth2 - totalLength2;
        var toX = x + totalLength2 * 2;
        var centerY = stageHeight2;
        ctx.clearRect(0, 0, stageWidth, stageHeight);
        ctx.beginPath();
        ctx.moveTo(x, stageHeight);
        for(; x < toX; x+=DELTA_WIDTH){
            ctx.lineTo(x, centerY - _getHeight(distanceX, x));
        }
        ctx.lineTo(x-DELTA_WIDTH, stageHeight);
        ctx.lineWidth = 10;
        ctx.strokeStyle = '#d0dde3';
        ctx.fillStyle = '#d6e3e9';
        ctx.fill();
        ctx.stroke();
        distanceX += config.speed;
    }
    init();

}())
</script>
<script>
wx.config({
	debug: false,    
	appId: '<ww:property value="appId"/>',    
	timestamp: '<ww:property value="timestamp"/>',    
	nonceStr: '<ww:property value="nonceStr"/>',    
	signature: '<ww:property value="signature"/>',    
	jsApiList: [    
	            'getLocation'  
	]    
});  
var userPoint;
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
		        });
	 	        
		      },
		      cancel: function (res) {
		        Alert('用户拒绝授权获取地理位置');
		      }
		    });
		  };
		  $('#getLocation').click();
		  
});
</script>
</html>
