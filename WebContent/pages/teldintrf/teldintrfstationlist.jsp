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


<script type="text/javascript">
function searchEntity(){
	$('#sform').submit();		
}

function showMapDiv(){
	$('#sform').attr('action','<%=path%>/teldIntrf/teldIntrfStationSearch.action?state=map');
	$('#teldintrfstationListShowDiv').hide();
	$('#teldintrfstationMapShowDiv').show();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-list-alt');
	$('#stateName').text('列表');
	$('#MapListButSpan').attr('onclick','showListDiv();searchEntity();');
}
function showListDiv(){
	$('#sform').attr('action','<%=path%>/teldIntrf/teldIntrfStationSearch.action?state=list');
	$('#teldintrfstationListShowDiv').show();
	$('#teldintrfstationMapShowDiv').hide();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-map');
	$('#stateName').text('地图');
	$('#MapListButSpan').attr('onclick','showMapDiv();searchEntity();');
}
var map;
$(document).ready(function(){
	var state = '<ww:property value="state" />';
	if(state != null && state != ''){
		if(state == 'map'){
			showMapDiv();
			showMap();
		}
		else if(state == 'list'){
			showListDiv();
		}
	}
	
});

var resultDataInfo;
var resultData;
function showMap(){
	resultDataInfo = $('#resultDataInputInfo').val().split(',');
	resultData = $('#resultDataInput').val().split(',');
	map = new BMap.Map("teldintrfstationMapShowDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 11);
	map.enableScrollWheelZoom();
	if(resultData == null || resultData == ""){
		return;
	}
	var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl); 
	
	  $(resultData).each(function(index,entity){
			if(entity == null || entity == ""){
				return true;
			}
			var point = new BMap.Point(entity.split(':')[0],entity.split(':')[1]);
			var html = '<div style="position: absolute; margin: 0pt; width: 32px; height: 32px; left:0px; top: -15px; ">'+
			'<img id="img_'+index+'" style="position:absolute;" src="<%=path %>/portal/common/images/booking/chongdianzhuang.png">';
			var myRichMarker = new BMapLib.RichMarker(html, point ,{
	           "anchor" : new BMap.Size(-20, -16),
				  "enableDragging" : false});
			
			var pileName = resultDataInfo[index].split(':')[0];
			var addr = resultDataInfo[index].split(':')[1];
			
// 			myRichMarker.addEventListener("onmouseover", function(e) {
<%-- 				document.getElementById("img_"+index).src = "<%=path %>/common/css/images/mark_3.png"; --%>
// 			});
// 			myRichMarker.addEventListener("onmouseout", function(e) {
<%-- 				document.getElementById("img_"+index).src = "<%=path %>/common/css/images/mark_4.png"; --%>
// 			});
			map.addOverlay(myRichMarker);
			var content = '<div class="qp_con"><p><b>充电站名称：</b>'+pileName+'</p>'+
		   '<p><b>充电站地址：</b>'+addr+'</p>';
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
			
		});
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
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/teldIntrf/teldIntrfStationSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				
			<input type="hidden" id="resultDataInput" value="<ww:iterator value="page.results" id="data" status="rl"><ww:property value="lng" />:<ww:property value="lat" />,</ww:iterator>"/>
			<input type="hidden" id="resultDataInputInfo" value="<ww:iterator value="page.results" id="data" status="rl"><ww:property value="staName" />:<ww:property value="staAddress" />:<ww:property value="id" />,</ww:iterator>"/>
			
			<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-4">
				<div class="form-group">
					<label for="teldIntrfStation.name" class="col-xs-4 control-label">充电站名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="teldIntrfStation.staName" id="teldIntrfStation.staName" type="text" value="<ww:property value="teldIntrfStation.staName"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="teldIntrfStation.address" class="col-xs-4 control-label">充电站地址</label>
					<div class="col-xs-8">
						<input class="form-control" name="teldIntrfStation.staAddress" id="teldIntrfStation.staAddress" type="text" value="<ww:property value="teldIntrfStation.staAddress"/>">				
					</div>
				</div>
			</div>
			
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-2 col-xs-3">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			<div class="col-sm-2 col-xs-3">
				<a class="btn btn-block Button4" id="MapListButSpan" onclick="showListDiv();" target="_blank"><i class="fa fa-list-alt"></i><span id="stateName">列表</span></a>
			</div>
		</div>
	</div>

<div class="row TableBlock" id="teldintrfstationListShowDiv">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td >
								<a href="javascript:SetOrder('staCode')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								<a href="javascript:SetOrder('staName')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>运营城市</td>
							<td>区/县</td>
							<td>状态</td>
							<td>类型</td>
							<td>直流数量</td>
							<td>交流数量</td>
							<td>地址</td>
						</tr>
					<ww:if test="state.equals(\"list\")">
						<ww:iterator value="page.results" id="data" status="rl">
							<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align=left><ww:property value="staCode" /></td>
								<td align="center"><ww:property value="staName" /></td>
								<td align="center"><ww:property value="province" /></td>
								<td align="center"><ww:property value="region" /></td>
								<td align="center"><ww:property value="staOpstate" /></td>
								<td align="center"><ww:property value="staType" /></td>
								<td align="right"><ww:property value="acNum" /></td>
								<td align="right"><ww:property value="dcNum" /></td>
								<td align="left"><ww:property value="staAddress" /></td>
							</tr>
						</ww:iterator>
					</ww:if>
						<tr>
							<td align="center" colspan="9">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
				
				<div id="teldintrfstationMapShowDiv" style="width:99%;height:80%;border:1px solid gray;margin:0px 0 0 0px;left:0px;position: absolute;">
				
				</div>
			</form>
</div>
</body>
</html>