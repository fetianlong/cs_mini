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


<script type="text/javascript">
function searchEntity(){
	$('#sform').submit();		
}
function editEntity(id){
	var url = '<%=path%>/place/branchDotGet.action?id='+id;
	window.location.href=url;
}

var map = true;
function changeShowType(){
	
}
function ShowMapDiv(){
	if(!map){
		$('#branchDotListShowDiv').hide();
		$('#branchDotMapShowDiv').show();
		$('#showMapButnDiv').attr('class','tit_map');
		$('#showListButnDiv').attr('class','tit_list');
		map = true;
	}
	
}
function ShowListDiv(){
	if(map){
		$('#branchDotListShowDiv').show();
		$('#branchDotMapShowDiv').hide();
		$('#showMapButnDiv').attr('class','tit_list');
		$('#showListButnDiv').attr('class','tit_map');
		map = false;
	}
	
}
var map;
$(document).ready(function(){
	var state = '<ww:property value="state" />';
	if(state != null && state != ''){
		if(state == 'map'){
			showMapDiv();
		}
		else if(state == 'list'){
			showListDiv();
		}
	}
	showMap();
});
function showMapDiv(){
	$('#sform').attr('action','<%=path%>/place/branchDotSearch.action?state=map');
	$('#branchDotListShowDiv').hide();
	$('#branchDotMapShowDiv').show();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-list-alt');
	$('#MapListButSpan').children().eq(1).text('列表');
	$('#MapListButSpan').attr('onclick','showListDiv()');
}
function showListDiv(){
	$('#sform').attr('action','<%=path%>/place/branchDotSearch.action?state=list');
	$('#branchDotListShowDiv').show();
	$('#branchDotMapShowDiv').hide();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-map');
	$('#MapListButSpan').children().eq(1).text('地图');
	$('#MapListButSpan').attr('onclick','showMapDiv()');
}
var resultDataInfo;
function showMap(){
	resultDataInfo = $('#resultDataInputInfo').val().split(',');
	map = new BMap.Map("branchDotMapShowDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
	map.centerAndZoom(new BMap.Point(116.507736, 39.811142), 11);
	map.enableScrollWheelZoom();
	if(resultDataInfo == null || resultDataInfo == ""){
		return;
	}
	showDot();
	var navigationControl = new BMap.NavigationControl({
	    // 靠左上角位置
	    anchor: BMAP_ANCHOR_TOP_LEFT,
	    // LARGE类型
	    type: BMAP_NAVIGATION_CONTROL_LARGE,
	    // 启用显示定位
	    enableGeolocation: true
	  });
	  map.addControl(navigationControl); 
	
}
function showDot(){
	 
	 $(resultDataInfo).each(function(index,entity){
			if(entity == null || entity == ""){
				return true;
			}
			var numb = entity.split(':')[5];
			var point = new BMap.Point(entity.split(':')[3],entity.split(':')[4]);
			var nickName = resultDataInfo[index].split(':')[0];
			var addr = resultDataInfo[index].split(':')[1];
			var id = resultDataInfo[index].split(':')[2];
			addMark(point,'operDot',index,numb,nickName,addr,id);
			
			
		});
}

function addMark(point,imgid,index,numb,nickName,addr,id){
	 var markImg = "";
	 var markImg2 = "";
	 var numbClass = "";
	 if(id != null && id != ""){
		 markImg = "mark_3.png";
		 markImg2 = "mark_4.png";
		 numbClass = "markico_mark3";
	 }
	 else{
		 markImg = "mark_1.png";
		 markImg2 = "mark_1.png";
		 numbClass = "markico_mark1";
	 }
		var html = '<div style="position: absolute; margin: 0pt; width: 32px; height: 32px; left:0px; top: -15px; ">'+
		'<img id="img_'+imgid+index+'" style="position:absolute;" src="'+'<%=path %>/common/css/images/'+markImg+'">'+
		'<span class="'+numbClass+'">'+numb+'</span>';
		var html = '<div style="position: absolute;margin-left:9px;margin-top:-6px; width: 30px; height: 32px;  ">'
	          +     '<img id="img_'+imgid+index+'" style="border:none;position:absolute;" src="<%=path %>/portal/common/images/booking/xianyouwangdian.png">'
	          + '<span style="background:url(\'<%=path %>/portal/common/images/booking/dian.png\')  no-repeat top center;position:absolute;font-size:10px; top:-5px;right:0;height:16px;text-align:center;color:#FFF;width:16px;">'+
	          numb
	          +'</span>'
		+ '</div>';
		var myRichMarker = new BMapLib.RichMarker(html, point ,{
           "anchor" : new BMap.Size(-20, -16),
			  "enableDragging" : false});
		
		
// 		myRichMarker.addEventListener("onmouseover", function(e) {
<%-- 			document.getElementById("img_"+imgid+index).src = "<%=path %>/common/css/images/"+markImg2; --%>
// 		});
// 		myRichMarker.addEventListener("onmouseout", function(e) {
<%-- 			document.getElementById("img_"+imgid+index).src = "<%=path %>/common/css/images/"+markImg; --%>
// 		});
		map.addOverlay(myRichMarker);
		var content = '<div class="qp_con">';
			   
			   if(id != null && id != ""){
				   content += '<p><b>网点名称：</b>'+nickName+'</p>'+
				   '<p><b>网点地址：</b>'+addr+'</p>'+
				   '<span class="xinxi" onclick="javascript:editEntity(\''+id+'\')">详细信息<img src="'+'<%=path %>/common/css/images/small-sanjiao.png" /></span>';
			   }
			   else{
				   content += '<p><b>网点名称：</b>'+nickName+'</p>'+
				   '<p><b>网点地址：</b>'+addr+'</p>';

			   }
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
}
function showDotInput(){
	if($('#showDotInputButId').attr('style') != null && $('#showDotInputButId').attr('style') != ""){
		$('#showDotInputButId').attr('style','');
		//map.clearOverlays();
		showDot();
	}
	else{
		$('#showDotInputButId').attr('style','color: rgb(173, 173, 173)');
		map.clearOverlays();
		if($('#showParkingInputButId').attr('style') == null || $('#showParkingInputButId').attr('style') == ""){
			showParking();
		}
		//showParking();
	}
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
			<form class="form-horizontal" name="sform" id="sform" method="post" action="">
			<input type="hidden" id="resultDataInputInfo" value="<ww:iterator value="page.results" id="data" status="rl"><ww:property value="name" />:<ww:property value="address" />:<ww:property value="id" />:<ww:property value="lng" />:<ww:property value="lat" />:<ww:property value="carCount" />,</ww:iterator>"/>
			
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">

<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="branchDot.name" class="col-xs-4 control-label">网点名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="branchDot.name" id="branchDot.name" type="text" value="<ww:property value="branchDot.name"/>">
					</div>
				</div>
				<div class="form-group">
					<label for="sfqy" class="col-xs-4 control-label">是否启用</label>
					<div class="col-xs-8">
						<select class="form-control"   name="branchDot.isActive" id="sfqy">
							<option value="">全部</option>
							<option value="1" <ww:if test="branchDot.isActive==1">selected=true</ww:if> >启用</option>	
							<option value="0" <ww:if test="branchDot.isActive==0">selected=true</ww:if>>未启用</option>	
						</select>				
					</div>
				</div>
			</div>
			
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="xzqh" class="col-xs-4 control-label">所属行政区划</label>
					<div class="col-xs-8">
						<select class="form-control"   name="branchDot.areaId" id="xzqh">
							<option value="">全部</option>
							<ww:iterator value="getAreas()" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="branchDot.areaId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="branchDot.address" class="col-xs-4 control-label">地址</label>
					<div class="col-xs-8">
						<input class="form-control" name="branchDot.address" id="branchDot.address" type="text" value="<ww:property value="branchDot.address"/>">
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<ww:if test="hasPrivilegeUrl('/place/branchDotSearch.action')">
				<div class="col-sm-2 col-xs-3 col-sm-offset-2">
					<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
				</div>
			</ww:if>
			<ww:if test="hasPrivilegeUrl('/place/branchDotAdd.action')">
				<div class="col-sm-2 col-xs-3">
					<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
				</div>
			</ww:if>
			<div class="col-sm-2 col-xs-3">
				<a class="btn btn-block Button4" id="MapListButSpan"  onclick="showListDiv();" target="_blank"><i class="fa fa-list-alt"></i><span>列表</span></a>
			</div>
		</div>
	</div>

<div id="branchDotListShowDiv" class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td>
								<input type="checkbox" name="checkdelcheckall"
													onclick="funCheck('','checkdel')">
							</td>
							
							<td >
								<a href="javascript:SetOrder('code')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								<a href="javascript:SetOrder('name')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								地址
							</td>
							<td >
								是否启用
							</td>
							<td >
								行政区划
							</td>
							<td >
							     经度
							</td>
							<td >
								纬度
							</td>
<!-- 							<td>还车网点</td> -->
							<td>车位数量</td>
							<td>
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align="center">
									<input type="checkbox" name="checkdel" 
										value="<ww:property value="id"/>">
								</td>
								
								<td align="left">
									<ww:property value="code" />
								</td>
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="address" />
								</td>
								<td align="center">
									<ww:if test="isActive==1">是</ww:if>
									<ww:if test="isActive==0">否</ww:if>
								</td>
								<td align="left">
									<ww:property value="getAreaName(areaId)" />
								</td>
								<td align="right">
									<ww:property value="lng" />
								</td>
								<td align="right">
									<ww:property value="lat" />
								</td>
<!-- 								<td align="left"> -->
<%-- 									<ww:if test="returnbackDotName == null || returnbackDotName == ''">本地</ww:if> --%>
<%-- 									<ww:else><ww:property value="returnbackDotName"/></ww:else> --%>
<!-- 								</td> -->
								<td align="right">
									<ww:property value="totalParkingPlace" />
								</td>
								<td align="center">
									<ww:if test="hasPrivilegeUrl('/place/branchDotGet.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
										<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td align="right" colspan="11">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
				<div id="branchDotMapShowDiv" style="width:100%;height: 600px"></div>
			</form>
</div>
</body>

</html>