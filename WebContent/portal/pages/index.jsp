<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/portal/pages/common/include.jsp" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=siteName %></title>

<%@ include file="/portal/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path %>/portal/common/css/index.css">

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path %>/common/js/richMarker_min.js"></script>


<script type="text/javascript">
var map;
$(document).ready(function(){
	var pars = {};
	$.post('<%=path %>/portal/place/dotInfoSearch.action',pars,showMap,'json');
});
var wdmarks=[];
var wdcontents = [];


var cdzmarks=[];
var cdzcontents = [];

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
function showMap(data){
	map = new BMap.Map("branchDotMapShowDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.centerAndZoom("北京", 11);
	map.enableScrollWheelZoom();
	map.setMinZoom(10);
	var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl); 
	  
	  if(data == null || data.info == null){
		  return;
	  }
	  $('#wdsl').text(data.info.dots.length);
	  $('#cdzsl').text(data.info.chargeStations.length);
	  
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
				var content = '<div class="qp_con" style="padding: 0 0 0 0;border:0px;width:auto">'+
				   '<p class="ph font-size16">网点名称：<span class="blue">'+dot.name+'</span></p>'+
				   '<p style="font-size:14px">地址：'+dot.address+'</p>'+
				   '<span class="xinxi" onclick="javascript:orderCar(\''+dot.id+'\')">约车<img src="'+'<%=path %>/common/css/images/small-sanjiao.png" /></span>'+
				   '</div><img class="aa" src="'+'/carsharing/common/css/images/ditu_sanjiao.png" />';
				wdcontents.push(content);
				 addClickHandler(content,myRichMarker,map);
			
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
				var content = '<div class="qp_con" style="padding: 0 0 0 0;border:0px;width:auto">'+
				   '<p class="ph font-size16">充电桩名称：<span class="blue">'+chargestation.name+'</span></p>'+
				   '<p style="font-size:14px">地址：'+chargestation.address+'</p>'+
				   '</div><img class="aa" src="'+'<%=path %>/common/css/images/ditu_sanjiao.png" />';
				cdzcontents.push(content);
				 addClickHandler(content,myRichMarker,map);
			
		  });
	  }
	  
}

function addClickHandler(content,marker,map){
	marker.addEventListener("click",function(e){
		openInfo(content,e,map);
		}
	);
}
function openInfo(content,e,map){
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
}

function orderCar(dotId){
	window.location.href='<%=path%>/portal/orders/toBookCar.action?dotId='+dotId;
}
function tiyan(){
	if($("#areaSelectId").val() == 0){
		window.location.href='<%=path%>/portal/orders/toBookCar.action';
	}
	else{
		alert("该城市暂未开通服务，请选择其他城市！");
	}
}
</script>
</head>

<body>
<%-- <jsp:include page="pageheader.jsp" flush="true"/>  --%>
<ww:set name="mainNavIndex" value="'index'" />
<%@include file="/portal/pages/common/page_header.jsp" %>
<div class="MainContent">
	<!--封面图片-->
	<section class="Section1">
		<div class="CarRun">
			<img class="CarImg hidden-xs" src="<%=path %>/portal/common/images/index/CarBG.png">
			<img class="CarLun CarLun1 hidden-xs" src="<%=path %>/portal/common/images/index/lun1.png">
			<img class="CarLun CarLun2 hidden-xs" src="<%=path %>/portal/common/images/index/lun2.png">
		</div>
    	<div class="container TopBlock">
			<img class="HandImg hidden-xs" src="<%=path %>/portal/common/images/index/hand.png">
			<div class="Topic">
				<h1>APP自助用车</h1>
				<p>全程无需人工与门店，仅需在线认证之后即可通过手机<br>APP实现自助的车辆使用（订车·开门与还车功能）。</p>
			</div>
			<div class="col-sm-3 col-xs-6 TiyanBlock">
				<form action="">
					<select class="form-control" id="areaSelectId">
						<option value=0>北京</option>
						<option value=1>天津</option>
						<option value=2>青岛</option>
						<option value=3>上海</option>
						<option value=4>重庆</option>
					</select>
					<button class="btn btn-warning btn-block" onclick="tiyan();return false;">立即体验<i class="fa fa-angle-double-right"></i></button>
				</form>
			</div>
			
        </div>
    </section>
	
    <!--注册流程-->
    <section class="Section2">
    	<div class="container">
            <img class="ArrowImg" src="<%=path %>/portal/common/images/index/Arrow1.png">
            <h1 class="text-center">注册流程</h1>
            <h4 class="text-center">注册操作流程 全程自助无需前往门店</h4>
            <div class="row LiuchengBlock">
            	<div class="col-md-3 col-sm-6 col-xs-6">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/zhuce.png">
                    <h3 class="text-center"><span>1.</span>注册认证</h3>
                    <p>在华泰租车网站或手机客户端进行<a href="#">注册</a>并提交驾照等个人证件信息</p>
                    <i class="fa fa-angle-double-right fa-2x visible-lg-block"></i>
                </div>
                
                <div class="col-md-3 col-sm-6 col-xs-6">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/chongzhi.png">
                    <h3 class="text-center"><span>2.</span>充值订车</h3>
                    <p>通过网站或者手机客户端查找附近车辆进行预定（最长订车时长20分钟），充值或绑定信用卡后预定车辆成功即可收到订单短信通知。</p>
                    <i class="fa fa-angle-double-right fa-2x visible-lg-block"></i>
                </div>
                
                <div class="col-md-3 col-sm-6 col-xs-6">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/quche.png">
                    <h3 class="text-center"><span>3.</span>开门取车</h3>
                    <p>到达预定车辆旁，点击华泰手机客户端中开门按键，车辆自动解锁，车钥匙放在车内，发动车辆出发</p>
                    <i class="fa fa-angle-double-right fa-2x visible-lg-block"></i>
                </div>
                
                <div class="col-md-3 col-sm-6 col-xs-6">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/shanglu.png">
                    <h3 class="text-center"><span>4.</span>驾驶上路</h3>
                    <p>车辆已发动，赶快出发吧！用车完毕后，将汽车停回原处并充电，手机锁门即完成还车手续，还等什么赶快预定。</p>
                </div>
            </div>
            <div class="row">
            	<div class="col-md-2 col-md-offset-5">
                	<a class="btn LijiZhuceButton btn-block btn-lg" href="<%=path %>/portal/subscriber/register.action" role="button">立即注册</a>
                </div>
            </div>
        </div>
    </section>
    
    <!--价格声明-->
    <section class="Section3">
    	<div class="container">
        	<img class="ArrowImg" src="<%=path %>/portal/common/images/index/Arrow2.png">
            <h1 class="text-center">车辆的价格说明</h1>
            <h4 class="text-center">分时租赁不同时间不同价格</h4>
            <div class="row JiageBlock">
            	<div class="col-md-4 col-sm-4 col-xs-4 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/taiyang.png">
                    <h3 class="text-center">白天档</h3>
                    <h4>(08:00~18:00)</h4>
                    <p><span>30.0</span>元/小时</p>
                </div>
                
                <div class="col-md-4 col-sm-4 col-xs-4 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/yejian.png">
                    <h3 class="text-center">晚上包段</h3>
                    <h4>(18:00~08:00)</h4>
                    <p><span>70.0</span>元/14小时</p>
                </div>
                
                <div class="col-md-4 col-sm-4 col-xs-4 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/biao.png">
                    <h3 class="text-center">全天</h3>
                    <h4>(全天24小时)</h4>
                    <p><span>100.0</span>元/24小时</p>
                </div>
            </div>
            
            <div class="row">
            	<div class="col-md-2 col-md-offset-5">
                	<a class="btn LiaojieButton btn-block btn-lg" href="<%=path %>/portal/pages/about.jsp" role="button">了解我们</a>
                </div>
            </div>
        </div>
    </section>
    
    <!--地图-->
    <section class="Section4">
    	<div class="container">
        	<img class="ArrowImg" src="<%=path %>/portal/common/images/index/Arrow3.png">
            <h1 class="text-center">车辆分布图</h1>
            <div id="branchDotMapShowDiv" class="MapBlock">
            	
            </div>
            <div class="MapControl" id="tools">
            	<ul class="list-group">
                	<li class="list-group-item" onclick="togWd();">
                    	<span class="badge label-success" id="wdsl">1</span>
                        <img src="<%=path %>/portal/common/images/booking/xianyouwangdian.png">网点
                    </li>
                    <li class="list-group-item" onclick="togCdz();">
                    	<span class="badge" id="cdzsl">2</span>
                        <img src="<%=path %>/portal/common/images/booking/chongdianzhuang.png">充电站
                    </li>
                </ul>
            </div>
            
            <!--<div class="clyd_wd_yc_por"  id="tools">
		         <ul>
		           <li class="allli wd" onclick="togWd();"><div id="wdsl" class="alla wd_a"></div>网点</li>
		           <li class="allli cdz" onclick="togCdz();"><div id="cdzsl" class="alla cdz_a"></div>充电桩</li>
		           <li class="allli tcc" onclick="togTcc();"><div id="tccsl" class="alla tcc_a"></div>网点</li>
		         </ul>
      		</div>-->
        </div>
        
        </div>
    </section>
    
    <!--订单需要满足条件-->
    <section class="Section5">
    	<div class="container">
        	<img class="ArrowImg" src="<%=path %>/portal/common/images/index/Arrow2.png">
            <h1 class="text-center">订单需要满足条件</h1>
            
            <div class="row TiaojianBlock">
            	<div class="col-md-3 col-sm-3 col-xs-6 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/duigou.png">
                    <h3>注册华泰账户</h3>
                </div>
                
                <div class="col-md-3 col-sm-3 col-xs-6 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/duigou.png">
                    <h3>上传驾驶证、身份证</h3>
                </div>
                
                <div class="col-md-3 col-sm-3 col-xs-6 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/duigou.png">
                    <h3>驾驶证、身份证验证</h3>
                </div>
                
                <div class="col-md-3 col-sm-3 col-xs-6 text-center">
                	<img class="img-responsive center-block" src="<%=path %>/portal/common/images/index/duigou.png">
                    <h3>充值或信用卡绑定</h3>
                </div>
            </div>
        </div>
    </section>
    
    <!--车辆图片-->
    <section class="Section6">
    	<div class="container">
        	<img class="ArrowImg" src="<%=path %>/portal/common/images/index/Arrow3.png">
            <img class="img-responsive center-block CarImg" src="<%=path %>/portal/common/images/index/car.png">
        </div>
    </section>
    
</div>
 <%@include file="/portal/pages/common/page_bottom.jsp" %>
<%-- <jsp:include page="pagebottom.jsp" flush="true"/>  --%>
</body>
</html>
