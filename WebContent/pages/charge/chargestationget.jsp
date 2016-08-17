<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />

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
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />


<script type="text/javascript">

$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/charge/addChargeStation.action');
	}else{
		$("#eform").attr('action','<%=path%>/charge/updateChargeStation.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"chargeStation.name": {
				required: true,
				maxlength : 30,
				chargeStationNameSc:true
			},
			"chargeStation.code": {
				required: true,
				maxlength : 30,
				chargeStationCodeSc:true
			},
			"chargeStation.address": {
				maxlength : 120,
				chargeStationAddressSc:true
			},
			"chargeStation.lat": {
				number: true
			},
			"chargeStation.lng": {
				number: true
			},
			"chargeStation.price":{
				number: true
			},
			"chargeStation.acNum":{
				number:true
			},
			"chargeStation.dcNum":{
				number:true
			},
			"chargeStation.acableNum":{
				number:true
			},
			"chargeStation.dcableNum":{
				number:true
			}
		},
		messages: {
			"chargeStation.name": {
				required: "请输入充电站名称！",
				maxlength: "充电站名称过长，最大为30个字符"
			},
			"chargeStation.code": {
				required: "请输入充电站编码！",
				maxlength: "充电站编码过长，最大为30个字符"
			},
			"chargeStation.address": {
				maxlength: "地址过长，最大为60个字符"
			},
			"chargeStation.lat": {
				number: "请输入数字"
			},
			"chargeStation.lng": {
				number: "请输入数字"
			},
			"chargeStation.price":{
				number: "请输入数字"
			},
			"chargeStation.acNum":{
				number: "请输入数字"
			},
			"chargeStation.dcNum":{
				number: "请输入数字"
			},
			"chargeStation.acableNum":{
				number: "请输入数字"
			},
			"chargeStation.dcableNum":{
				number: "请输入数字"
			}
		}
	});
	
	val_check_SpecialChar("chargeStationNameSc,chargeStationCodeSc,chargeStationAddressSc");
});

function isValid(){
	if ($("#eform").valid()){
		return true;
	}else{
		return false;
	}
}
function getForm(){
	return $("#eform");
}
var map;
var centerPointMark;
var centerLng;
var centerLat;

var addCenterFlag = false;
var drawingManager;
$(document).ready(function(){
	map = new BMap.Map("addPointMapDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	
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
	  var chargeStationlat = '<ww:property value="chargeStation.lat" />';
	  var chargeStationlng = '<ww:property value="chargeStation.lng" />';
	  if(chargeStationlat != null && chargeStationlng != null && chargeStationlat != "" && chargeStationlng != "" && chargeStationlat != "0.0"){
		  centerLng = chargeStationlng;
		  centerLat = chargeStationlat;
		  map.centerAndZoom(new BMap.Point(centerLng,centerLat),12);
		  var html = '<div style="position: absolute; margin: 0pt; width: 32px; height: 32px; left:0px; top: -15px; ">'+
			'<img style= "position:absolute;" src="<%=path %>/portal/common/images/booking/chongdianzhuang.png">';
		  var point = new BMap.Point(chargeStationlng,chargeStationlat);
		  map.centerAndZoom(point,12);
		  var myRichMarker = new BMapLib.RichMarker(html, point ,{
	            "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
		  map.addOverlay(myRichMarker);
	  }
	  else{
		  map.centerAndZoom('北京',12);
	  }
	  var overlaycomplete = function(e){
		  
			if(addCenterFlag){
				map.removeOverlay(e.overlay);
				map.removeOverlay(centerPointMark);
				var pointinfo = e.overlay.point;
				var point = new BMap.Point(pointinfo.lng,pointinfo.lat);
				var html = '<div style="position: absolute; margin: 0pt; padding: 0pt; width: 64px; height: 48px; left: -10px; top: -35px; overflow: hidden;">'
		            +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/portal/common/images/booking/chongdianzhuang.png">'
					+ '</div>';
				centerPointMark = new BMapLib.RichMarker(html, point ,{
		            "anchor" : new BMap.Size(-20, -16),
					  "enableDragging" : false});
				centerLng = e.overlay.point.lng;
				centerLat = e.overlay.point.lat;
				map.addOverlay(centerPointMark);
				
				<%--
				$.dialog.confirm('你确定要添加中心点吗？', function(){
					centerPoint[0] = selectPoint.lat;
					centerPoint[1] = selectPoint.lng;
				}, function(){
				});
				--%>
				addCenterFlag = false;
			}
			
			else{
					map.removeOverlay(e.overlay);
				}
	    };
	    var styleOptions = {
	    };
	    //实例化鼠标绘制工具
	    drawingManager = new BMapLib.DrawingManager(map, {
	        isOpen: false, //是否开启绘制模式
	        enableDrawingTool: true, //是否显示工具栏
	        drawingToolOptions: {
	            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
	            offset: new BMap.Size(5, 5), //偏离值
	            drawingModes : [
	                            BMAP_DRAWING_MARKER
	                         ]
	        }
	    });  
		 //添加鼠标绘制工具监听事件，用于获取绘制结果
	    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
	
   
});



function addCenterPoint(){
	addCenterFlag = true;
	$.dialog.tips('请用选点工具选择中心点！');
}
function clearAll(){
	map.removeOverlay(centerPointMark);
	
	centerPointMark = null;
	centerLng = null;
	centerLat = null;
	addCenterFlag = false;
}
function conmitAdd(){
	if(centerLng == null || centerLng == ""){
		return false;
	}
	return true;
}
function addOther(){
	$('#addPointMapDiv').toggle();
	$('#addOtherInfoDiv').toggle();
}
function backToParking(){
	window.location.href="<%=path%>/charge/searchChargeStation.action?state=map";
}
function sub(){
	var id = '<ww:property value="id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/charge/addChargeStation.action";
	}else{
		url="<%=path%>/charge/updateChargeStation.action";	
	}
	if(conmitAdd() == false){
		$.dialog.tips("请在地图上标注选择充电站地点");
		return;
	}
	var re=isValid();
	if(re){
		var formStr = $("#eform").serialize();
		formStr += '&chargeStation.lat='+centerLat+'&chargeStation.lng='+centerLng;
		$.post(url,formStr,r_saveCar,'json').error(requestError);
	}
}
function r_saveCar(data){
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
				window.location.href="<%=path%>/charge/searchChargeStation.action?state=map";
		    });
			break;
		case 1:
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
}
function cancel(){
	window.location.href="<%=path%>/charge/searchChargeStation.action?state=map";
}
</script>
</head>
<body>
  <div style="width:99%;height:99%;position: absolute;">
  <div id="buttonsDiv">
	<div class="pan_btn5" id="addCenterPointBtn" onclick="addCenterPoint();" style="margin:5px 15px 5px 0;width: 90px">标注充电站</div>
	<div class="pan_btn5" id="addOtherBtn" onclick="addOther();" style="margin:5px 15px 5px 0;width: 90px">切换信息</div>
	<div class="pan_btn5" id="cancelAddPointBtn" onclick="clearAll();" style="margin:5px 15px 5px 0;width: 110px">重新标注充电站</div>
	<div class="pan_btn5" id="addOtherBtn" onclick="backToParking();" style="margin:5px 5px 5px 0;">返&nbsp;&nbsp;回</div>
	</div>
	<div style="width:100%;height:90%;border:1px solid gray;margin:0px 0 0 0px;top:60px;left:0px;position: absolute;" id="addPointMapDiv"></div>
	
	<div style="width:100%;height:90%;margin:0px 0 0 0px;top:60px;left:0px;position: absolute; display: none" id="addOtherInfoDiv">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="chargeStation.id" id="chargeStation.id"value="<ww:property value="chargeStation.id" />">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
				 <td class="zuo1"><span class="xx red">*</span><span>充电站编码</span>:</td>
                 <td class="you1">
                 	<input class="input_size fl" type="text"  name="chargeStation.code" 
                 	 id="chargeStation.code"  value="<ww:property value="chargeStation.code" />" maxlength="30"/>
                 </td> 
                 <td class="zuo"><span class="xx red">*</span><span>充电站名称</span>:</td>
                 <td class="you">
                   <input class="input_size fl" type="text"   name="chargeStation.name" 
                     id="chargeStation.name"  value="<ww:property value="chargeStation.name" />" maxlength="30"/>
                 </td>  
              </tr>
             <tr>
                 <td class="zuo"><span>地址</span>:</td>
                 <td class="you">
                   <input class="input_size fl" type="text"  name="chargeStation.address" 
                     id="chargeStation.address" maxlength="60" value="<ww:property value="chargeStation.address" />" />
                 </td>
                 <td class="zuo"><span>收费标准(元/度)</span>:</td>
                 <td class="you">
                   <input class="input_size fl" type="text"  name="chargeStation.price" 
                     id="chargeStation.price" maxlength="10" value="<ww:property value="chargeStation.price" />" />
                 </td>
                   
              </tr>
                 <tr>
                    <td class="zuo"><span>电站类型</span>:</td>
                    <td class="you">
                    	<div class="btt1 fl">
	                      <select name="chargeStation.staType" id="chargeStation.staType" style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('chargestationType',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="chargeStation.staType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                    </td>  
                    <td class="zuo"><span>运营状态</span>:</td>
                    <td class="you">
                    	<div class="btt1 fl">
	                      <select name="chargeStation.staOpstate" id="chargeStation.staOpstate" style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('chargestaOpstate',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="chargeStation.staOpstate==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                    </td>  
                 </tr>
                 <tr>
	                 <td class="zuo"><span>直流充电桩数量</span>:</td>
	                 <td class="you">
	                   <input class="input_size fl" type="text"  name="chargeStation.acNum" 
	                     id="chargeStation.acNum" maxlength="8" value="<ww:property value="chargeStation.acNum" />" />
	                 </td>
	                 <td class="zuo"><span>交流充电桩数量</span>:</td>
	                 <td class="you">
	                   <input class="input_size fl" type="text"  name="chargeStation.dcNum" 
	                     id="chargeStation.dcNum" maxlength="8" value="<ww:property value="chargeStation.dcNum" />" />
	                 </td>
	             </tr>
	             <tr>
	                 <td class="zuo"><span>可用直流充电桩数量</span>:</td>
	                 <td class="you">
	                   <input class="input_size fl" type="text"  name="chargeStation.acableNum" 
	                     id="chargeStation.acableNum" maxlength="8" value="<ww:property value="chargeStation.acableNum" />" />
	                 </td>
	                 <td class="zuo"><span>可用交流充电桩数量</span>:</td>
	                 <td class="you">
	                   <input class="input_size fl" type="text"  name="chargeStation.dcableNum" 
	                     id="chargeStation.dcableNum" maxlength="8" value="<ww:property value="chargeStation.dcableNum" />" />
	                 </td>
	             </tr>
                 <tr>
                    <td class="zuo"><span>运营公司</span>:</td>
                    <td class="you">
                    	<div class="btt1 fl">
	                      <select name="chargeStation.manufacturer" id="chargeStation.manufacturer" style="top:12px;height:26px;">
								<option value="">请选择</option>
								<ww:iterator value="getCompanys('4')" id="data" status="rl">
									<option value="<ww:property value="id" />"  <ww:if test="chargeStation.manufacturer==id">selected=true</ww:if> ><ww:property value="name" /></option>	
								</ww:iterator>
							</select>
						</div>
                    </td>  
                 </tr>
                 <tr>
                 </tr>
                 <tr>
                        <td colspan="4">
                            <div class="btt">
                               <div class="sbtn fl" onclick="sub();">提&nbsp;&nbsp;交</div>
                               <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>
                            </div>
                        </td>
                   </tr>
                 
		  
		</table>
	</form>
	</div>
	</div> 
</body>
</html>