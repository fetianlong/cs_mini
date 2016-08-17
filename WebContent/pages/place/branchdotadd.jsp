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
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
<script type="text/javascript">
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/place/branchDotAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/place/branchDotUpdate.action');	
		$('#addPointMapDiv').show();
		$('#addOtherInfoDiv').hide();
		//$('#buttonsDiv').hide();
		$('#branchDot.code').attr('disabled','disabled');
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"branchDot.code": {
				required: true,
				maxlength:30,
				branchDotCodeSc:true
			},
			"branchDot.areaId": {
				required: true
			},
			"branchDot.name": {
				maxlength:30,
				branchDotNameSc:true
			},
			"branchDot.address": {
				maxlength:120,
				branchDotAddrSc:true
			},
			"branchDot.lng": {
				required: true
			},
			"branchDot.lat": {
				required: true
			}
		},
		messages: {
			"branchDot.code": {
				required: "请输入网点编码！"
			},
			"branchDot.areaId": {
				required: "请选择行政区划！"
			},
			"branchDot.lng": {
				required:"请添加中心点！"
			},
			"branchDot.lat": {
				required:"请添加中心点！"
			}
		}
	});
	
	val_check_SpecialChar("branchDotCodeSc,branchDotNameSc,branchDotAddrSc");
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
	  var branchDotlat = '<ww:property value="branchDot.lat" />';
	  var branchDotlng = '<ww:property value="branchDot.lng" />';
	  if(branchDotlat != null && branchDotlng != null && branchDotlat != "" && branchDotlng != ""){
		  centerLng = branchDotlng;
		  centerLat = branchDotlat;
		  map.centerAndZoom(new BMap.Point(centerLng,centerLat),12);
		  var html = '<div style="position: absolute; margin: 0pt; width: 32px; height: 32px; left:0px; top: -15px; ">'+
			'<img style= "position:absolute;" src="<%=path %>/portal/common/images/booking/xianyouwangdian.png">';
		  var point = new BMap.Point(branchDotlng,branchDotlat);
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
		            +     '<img style="border:none;left:16px; top:16px; position:absolute;" src="'+'<%=path %>/portal/common/images/booking/xianyouwangdian.png">'
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
function r_add(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
				window.location.href="<%=path%>/place/branchDotSearch.action?state=map";
		    });
			break;
		case 1:
			restoreInfo();
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
}
function addOther(){
	$('#addPointMapDiv').toggle();
	$('#addOtherInfoDiv').toggle();
}
function backToParking(){
	window.location.href="<%=path%>/place/branchDotSearch.action?state=map";
}

//取消按钮
function cancel(){
	window.location.href="<%=path%>/place/branchDotSearch.action?state=map";
}
function sub(){
	var id = '<ww:property value="id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/place/branchDotAdd.action";
	}else{
		url="<%=path%>/place/branchDotUpdate.action";	
	}
	if(conmitAdd() == false)
	{
		$.dialog.tips("请在地图上标注选择网点地点！");
		return;
	}
	var re=isValid();
	if(re){
		showLoading();
		var formSer = $("#eform").serialize();
		formSer += '&branchDot.lat='+centerLat+'&branchDot.lng='+centerLng;
		$.post(url,formSer,r_add,'json').error(requestError);
	}
}
function selectReturnbackDot(){
	$.dialog({
		id:'selectReturnbackDotDial',
	    title:'网点查询',
		content : 'url:<%=path%>/place/branchDotSearch.action?state=returnbackdot&branchDot.id='+'<ww:property value="id" />',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
</script>
</head>
<body class="wdglPage">
<div style="width:99%;height:99%;position: absolute;">
	<div id="buttonsDiv">
	<div class="pan_btn5" id="addCenterPointBtn" onclick="addCenterPoint();" style="margin:5px 15px 5px 0;width: 90px">标注网点</div>
	<div class="pan_btn5" id="addOtherBtn" onclick="addOther();" style="margin:5px 15px 5px 0;width: 90px">切换信息</div>
	<div class="pan_btn5" id="cancelAddPointBtn" onclick="clearAll();" style="margin:5px 15px 5px 0;width: 110px">重新标注网点</div>
	<div class="pan_btn5" id="addOtherBtn" onclick="backToParking();" style="margin:5px 5px 5px 0;">返&nbsp;&nbsp;回</div>
	</div>
	
	<div style="width:100%;height:90%;border:1px solid gray;margin:0px 0 0 0px;top:60px;left:0px;position: absolute;" id="addPointMapDiv"></div>
	
	<div style="width:100%;height:90%;margin:0px 0 0 0px;top:60px;left:0px;position: absolute; display: none" id="addOtherInfoDiv">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="branchDot.id" id="branchDot.id" value="<ww:property value="branchDot.id" />">
				
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
	           <td class="zuo"><span class="xx red">*</span><span>编码</span>:</td>
	            <td class="you">
	              <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="branchDot.code" id="branchDot.code"  
	              value="<ww:property value="branchDot.code" />" />
	            </td>  
	            <td class="zuo1"><span>名称</span>:</td>
	              <td class="you1">
	                <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="branchDot.name" 
	                id="branchDot.name" value="<ww:property value="branchDot.name" />" />
	              </td>   
	         </tr>
	         <tr>
	         	<td class="zuo"><span class="xx red">*</span><span>所属行政区划</span>:</td>
	            <td class="you">
	              <div class="btt1 fl">
                      <select name="branchDot.areaId" id="branchDot.areaId" style="top:12px;height:26px;">
							<option value="">请选择</option>
							<ww:iterator value="getAreas()" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="branchDot.areaId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
							</ww:iterator>
						</select>
					</div>
	            </td>  
	           <td class="zuo1"><span>地址</span>:</td>
	            <td class="you1">
	              <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="120" name="branchDot.address" 
	              id="branchDot.address" value="<ww:property value="branchDot.address" />" />
	            </td>  
	            
	         </tr>
         	 
	         <tr>
	           <td class="zuo"><span>是否启用</span>:</td>
	           <td class="you">
		           	<div class="btt1 fl">
		              <select name="branchDot.isActive" style="top:12px;height:26px;">
				          <option value="1" <ww:if test="branchDot.isActive==1">selected="selected"</ww:if>>是</option>
				          <option value="0"  <ww:if test="branchDot.isActive==0">selected="selected"</ww:if>>否</option>
				       </select>
					</div>
			    </td>
			    <td class="zuo1"><span class="xx red">*</span><span>车位数量</span>:</td>
	            <td class="you1">
	              <input class="input_size fl" type="text" maxlength="8" name="branchDot.totalParkingPlace" id="branchDot.totalParkingPlace"  
	              value="<ww:property value="branchDot.totalParkingPlace" />" />
	            </td>  
	            
	            <%--<td class="zuo1"><span>是否A借B还</span>:</td>
	              <td class="you1">
	                 <div class="btt1 fl">
		              <select name="branchDot.isAB" style="top:12px;height:26px;">
				          <option value="1" <ww:if test="branchDot.isAB==1">selected="selected"</ww:if>>是</option>
				          <option value="0"  <ww:if test="branchDot.isAB==0">selected="selected"</ww:if>>否</option>
				       </select>
					</div>
	              </td>   
	               --%>
	         </tr>
	         <tr>
			    <td class="zuo1"><span>还车网点</span>:</td>
	            <td class="you1" colspan="3">
	              <input class="input_size fl wdglhcwd" type="text" readonly name="branchDot.returnbackDotName" 
	              id="branchDot.returnbackDotName" value="<ww:property value="branchDot.returnbackDotName" />" />
				  <input onclick="selectReturnbackDot();" type="button" value="选择" class="searchinputbut" />
	              <input type="hidden" name="branchDot.returnbackDot" 
	              id="branchDot.returnbackDot" value="<ww:property value="branchDot.returnbackDot" />" />
	            </td>
	         </tr>
         	<tr></tr>
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