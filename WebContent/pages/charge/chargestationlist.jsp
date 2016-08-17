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
function editEntity(id){
	window.location.href="<%=path%>/charge/toAddOrUpdateChargeStation.action?id="+id;
}
function searchEntity(){
	$('#sform').submit();		
}
function deleteEntity(){
	var ob = document.getElementsByName("checkdel");
	var check = false;
	for (var i = 0; i < ob.length; i++) {
		if (ob[i].checked) {
			check = true;
			break;
		}
	}
	if (!check) {
		alertinfo("请选择要删除的数据！");
		return false;
	}
	alertconfirm("确认删除选中的数据吗？",function (){
		showLoading();
		$.post('deleteChareStation.action',$('#sform').serialize(),r_delete,'json').error(requestError);
	});	
}
function r_delete(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("删除成功！", function(){
		    	$('#sform').submit();		
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
function deleteOneEntity(id){
	var pars={
			"id":id
		};
	alertconfirm("确认删除选中的数据吗？",function (){
		showLoading();
		$.post('deleteChareStation.action',pars,r_delete,'json').error(requestError);
	});	
}

function showMapDiv(){
	$('#sform').attr('action','<%=path%>/chargestation/searchChargeStation.action?state=map');
	$('#chargestationListShowDiv').hide();
	$('#chargestationMapShowDiv').show();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-list-alt');
	$('#MapListButSpan').children().eq(1).text('列表');
	$('#MapListButSpan').attr('onclick','showListDiv()');
}
function showListDiv(){
	$('#sform').attr('action','<%=path%>/chargestation/searchChargeStation.action?state=list');
	$('#chargestationListShowDiv').show();
	$('#chargestationMapShowDiv').hide();
	$('#MapListButSpan').children().eq(0).attr('class','fa fa-map');
	$('#MapListButSpan').children().eq(1).text('地图');
	$('#MapListButSpan').attr('onclick','showMapDiv()');
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

var resultDataInfo;
var resultData;
function showMap(){
	resultDataInfo = $('#resultDataInputInfo').val().split(',');
	resultData = $('#resultDataInput').val().split(',');
	map = new BMap.Map("chargestationMapShowDiv",{enableMapClick:false});//构造底图时，关闭底图可点功能
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
			var id = resultDataInfo[index].split(':')[2];
			
// 			myRichMarker.addEventListener("onmouseover", function(e) {
<%-- 				document.getElementById("img_"+index).src = "<%=path %>/common/css/images/mark_3.png"; --%>
// 			});
// 			myRichMarker.addEventListener("onmouseout", function(e) {
<%-- 				document.getElementById("img_"+index).src = "<%=path %>/common/css/images/mark_4.png"; --%>
// 			});
			map.addOverlay(myRichMarker);
			var content = '<div class="qp_con">';
				   
		   if(id != null && id != ""){
			   content += '<p><b>充电站名称：</b>'+pileName+'</p>'+
			   '<p><b>充电站地址：</b>'+addr+'</p>'+
			   '<span class="xinxi" onclick="javascript:editEntity(\''+id+'\')">详细信息<img src="'+'<%=path %>/common/css/images/small-sanjiao.png" /></span>';
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
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/charge/searchChargeStation.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				
			<input type="hidden" id="resultDataInput" value="<ww:iterator value="page.results" id="data" status="rl"><ww:property value="lng" />:<ww:property value="lat" />,</ww:iterator>"/>
			<input type="hidden" id="resultDataInputInfo" value="<ww:iterator value="page.results" id="data" status="rl"><ww:property value="name" />:<ww:property value="address" />:<ww:property value="id" />,</ww:iterator>"/>
			
			<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-4">
				<div class="form-group">
					<label for="chargeStation.name" class="col-xs-4 control-label">充电站名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="chargeStation.name" id="chargeStation.name" type="text" value="<ww:property value="chargeStation.name"/>">
					</div>
				</div>
				<div class="form-group">
					<label for="chargeStation.address" class="col-xs-4 control-label">充电站地址</label>
					<div class="col-xs-8">
						<input class="form-control" name="chargeStation.address" id="chargeStation.address" type="text" value="<ww:property value="chargeStation.address"/>">				
					</div>
				</div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-5 control-label">电站类型</label>
					<div class="col-xs-7">
						<select class="form-control"   name="chargeStation.staType" >
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('chargestationType',1)" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="chargeStation.staType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">运营状态</label>
					<div class="col-xs-7">
						<select class="form-control"   name="chargeStation.staOpstate" >
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('chargeStaOpstate',1)" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="chargeStation.staOpstate==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-4 control-label">运营公司</label>
					<div class="col-xs-8">
						<select class="form-control"   name="chargeStation.manufacturer" >
							<option value="">全部</option>
							<ww:iterator value="getCompanys('5')" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="chargeStation.manufacturer==id">selected=true</ww:if> ><ww:property value="name" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-2 col-xs-3">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			<ww:if test="hasPrivilegeUrl('/charge/addChargeStation.action')">
				<div class="col-sm-2 col-xs-3">
					<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
				</div>
			</ww:if>
			<ww:if test="hasPrivilegeUrl('/charge/deleteChargeStation.action')">
				<div class="col-sm-2 col-xs-3">
					<a class="btn btn-block Button3"  onclick="deleteEntity();" target="_blank"><i class="fa fa-floppy-o"></i>删除</a>
				</div>
			</ww:if>
			<div class="col-sm-2 col-xs-3">
				<a class="btn btn-block Button4" id="MapListButSpan" onclick="showListDiv();" target="_blank"><i class="fa fa-list-alt"></i>列表</a>
			</div>
		</div>
	</div>

<div class="row TableBlock" id="chargestationListShowDiv" style="display: none">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td  width="68" height="50">
								<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
							</td>
							<td >
								<a href="javascript:SetOrder('code')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								<a href="javascript:SetOrder('name')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td width="150" >
								地址
							</td>
							<td >
								运营公司
							</td>
							<td >
								电站类型
							</td>
							<td >
								运营状态
							</td>
							<td>
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align="center">
									<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
								</td>
								<td align=left>
									<ww:property value="code" />
								</td>
								<td align="center">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="address" />
								</td>
								<td align="left">
									<ww:property value="getCompNameById(manufacturer)" />
								</td>
								<td align="left">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('chargestationType',staType)" />
								</td>
								<td align="left">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('chargestaOpstate',staOpstate)" />
								</td>
								
								<td align="center">
									<ww:if test="hasPrivilegeUrl('/charge/toAddOrUpdateChargeStation.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/charge/deleteChargeStation.action')">
										<div class="pan_btn4"  onclick="javascript:deleteOneEntity('<ww:property value="id"/>');">删除</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
										<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td align="center" colspan="13">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
				
				<div id="chargestationMapShowDiv" style="width:99%;height:80%;border:1px solid gray;margin:0px 0 0 0px;left:0px;position: absolute;">
				
				</div>
			</form>
</div>
</body>
</html>