<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/portal/pages/common/include.jsp" %>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
 

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>我的订单 - <%=siteName %></title>

<%@ include file="/portal/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path%>/portal/common/js/datetimepicker/datetimepicker.min.css">

<link rel="stylesheet" href="<%=path%>/portal/common/css/center.css">
<link rel="stylesheet" href="<%=path%>/portal/common/css/myorder.css">
<link rel="stylesheet" href="<%=path%>/portal/common/css/page.css">
<!--<link rel="stylesheet" href="<%=path%>/portal/common/js/flaviusmatis/simplePagination.css">-->


<script src="<%=path%>/portal/common/js/datetimepicker/datetimepicker.min.js"></script>
<script src="<%=path%>/portal/common/js/datetimepicker/datetimepicker.zh-CN.js"></script>
<script src="<%=path%>/portal/common/js/page.js"></script>
<script src="<%=path%>/portal/common/js/flaviusmatis/jquery.simplePagination.js"></script>


<script>
$(function(){
	$('.timeselect').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
	});
	
	$(".OrderList .nav li").click(function(e) {
		$(".OrderList .nav li").removeClass("active");
		$(this).addClass("active");
		$('#orderState').val($(this).attr('orderState'));
		$("#sform").submit();
	});
	
	$(".ChangePage").pagination({
		items: '<ww:property value="page.totalRows"/>',
		itemsOnPage: '<ww:property value="page.pageSize"/>',
		currentPage:'<ww:property value="page.currentPage"/>',
		cssStyle: 'light-theme',
		onPageClick: function(PageNumber, event){
			$("#pageCurrentPage").val($(".ChangePage").pagination('getCurrentPage'));
			searchEntity();
		},
		prevText: "<i class='fa fa-angle-left'></i>",
		nextText: "<i class='fa fa-angle-right'></i>"
	});
	
	$(".ShowDetailButton").click(function(e) {
		Fee = $(this).closest(".OneList").find(".TotalFee").html();
		State = $(this).closest(".OneList").find(".State").html();
		CarID = $(this).attr("carid");
		$(".OrderOverview .Feiyong").html(Fee);
		$(".OrderOverview .Zhuangtai").html(State);
		$(".OrderOverview .Bianhao").html(CarID);
		updateDetailInfo(CarID);
		$("#listPage").hide();
		$("#detailPage").show();
	});
	
});

function searchEntity(){
	
	var pars = $("#sform").serialize();
	$.ajax({
	  url: '<%=path %>/portal/orders/showOrdersListMini.action',
    data: pars,
    type: 'POST',
	 
	  dataType: 'html',
	  success: function(data){
		 
	 		$("#showDiv").html(data);
	  }
	});
}

function sub(){
	if ($('#endtime').val() != null && $('#endtime').val() != '' && 
    		$('#begintime').val() != null && $('#begintime').val() != '' &&
    		$('#endtime').val() < $('#begintime').val()){
    	alert("起始时间不能大于终止时间");
    	return;
    }
	$('#searchTypeInp').val('rangetime');
	$("#sform").submit();
    	
}
function searchRangeTime(){
	$('#searchTypeInp').val('lasttime');
	$('#begintime').val('');
	$('#endtime').val('');
	$('#sform').submit();
}

/*function showOrderDetail(orderno){
 	updateDetailInfo(orderno);
 	document.getElementById("listPage").style.display="none";
 	document.getElementById("detailPage").style.display="block";
}*/

function goBack(){
	$("#listPage").show();
	$("#detailPage").hide();
 	$('#ordersDetailList').empty();
}



Date.prototype.Format = function (fmt) { //author: meizz 
	 var o = {         
			    "M+" : this.getMonth()+1, //月份         
			    "d+" : this.getDate(), //日         
			    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
			    "H+" : this.getHours(), //小时         
			    "m+" : this.getMinutes(), //分         
			    "s+" : this.getSeconds(), //秒         
			    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
			    "S" : this.getMilliseconds() //毫秒         
			    };         
			    var week = {         
			    "0" : "/u65e5",         
			    "1" : "/u4e00",         
			    "2" : "/u4e8c",         
			    "3" : "/u4e09",         
			    "4" : "/u56db",         
			    "5" : "/u4e94",         
			    "6" : "/u516d"        
			    };         
			    if(/(y+)/.test(fmt)){         
			        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
			    }         
			    if(/(E+)/.test(fmt)){         
			        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
			    }         
			    for(var k in o){         
			        if(new RegExp("("+ k +")").test(fmt)){         
			            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
			        }         
			    }         
			    return fmt;         
};




function updateDetailInfo(orderno){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/portal/orders/portalOrdersDetailSearch.action",
	    dataType:'json',
	    data:{"orderno":orderno},
	    success: function(data) {
	    	var htmlstr = '';
	    	$.each(data.info,function(index,ordDetailInfo){
	    		 htmlstr += '<div class="col-md-12 SubOrder">'+
					'<div class="Border">'+
				'<div class="row">'+
					'<div class="col-md-12"><h4 class="Caption"><span class="label label-success">'+(index+1)+'</span>  '+ordDetailInfo.typeName;   
				if(ordDetailInfo.isOver=='1'){
					htmlstr +='<span class="label label-success Zhuangtai pull-right">已结束</span>';
				}	
				if(ordDetailInfo.isRunning=='1'){
					htmlstr +='<span class="label label-warning Zhuangtai pull-right">进行中</span>';
				}	
				if(ordDetailInfo.isTimeout=='1'){
					htmlstr +='<span class="label label-danger Zhuangtai pull-right">已超时</span>';
				}	
				if(ordDetailInfo.isException=='1'){
					htmlstr +='<span class="label label-danger Zhuangtai pull-right">未支付成功</span>';
				}
				htmlstr +=	'</h4></div>'+
				'</div>'+
				'<div class="row">'+
					'<div class="col-sm-6">'+
						'<p>开始时间：<span>'+ordDetailInfo.beginTimeStr+'</span></p>'+
					'</div>'+
					'<div class="col-sm-6">'+
						'<p>结束时间：<span>'+ordDetailInfo.endTimeStr+'</span></p>'+
					'</div>'+
					'<div class="col-sm-6">'+
						'<p>租赁类型：<span>'+ordDetailInfo.typeName+'</span></p>'+
					'</div>'+
					'<div class="col-sm-6">'+
						'<p>费用：';
						
				if(ordDetailInfo.isRunning=='1'&&ordDetailInfo.isPrePay=='0'){
					htmlstr +='<span>计费中...</span>';
				}else{
					htmlstr +='<span>￥'+ordDetailInfo.totalFee+'</span>(元)';
				} 
					
				htmlstr +='</p></div>';
				if(ordDetailInfo.ticketsFee!=0){
					htmlstr +='<div class="col-sm-6">'+
						'<p>超时罚金：<span>￥'+ordDetailInfo.ticketsFee+'</span>(元)</p>'+
					'</div>';
				}		
				htmlstr += '</div>'+
			'</div>'+
		'</div>';});
	    	$('#ordersDetailList').append($(htmlstr));	
	    }
	});
}
</script>
</head>

<body>
<%@include file="/portal/pages/common/page_header.jsp" %>

<div class="MainContent">
	<div  class="container" >
    	<img class="img-responsive center-block TitleImg" src="<%=path%>/portal/common/images/myorder/title.png">
        <!--个人中心公共控制-->
        
        <ww:set name="pageindexpage" value="'myorder'" />
         <%@include file="/portal/pages/common/center_left.jsp" %>
        <!--独立内容-->
        <div id="listPage" class="col-sm-9 ContentBlock">
        <form id="sform" class="form-horizontal" action="<%=path %>/portal/orders/portalOrdersSearch.action" method="post">
        	<div class="row">
				<div class="col-md-12 CenterInfo">
					<div class="Border">
							<input type="hidden" name="page.currentPage" id="pageCurrentPage" value="<ww:property value="page.currentPage"/>">
							<input type="hidden" id="searchTypeInp" name="searchType" value="<ww:property value="searchType"/>" />
							<input type="hidden" id="orderState" name="orderState" value="<ww:property value="orderState"/>" />
							<div class="col-sm-8">
								<div class="form-group">
									<label for="begintime" class="col-sm-2 control-label">预计时间</label>
									<div class="col-sm-4">
										<input type="text" class="form-control timeselect" id="begintime" name="begintime" value='<ww:property value="begintime"/>' 
											<ww:if test="begintime == null || begintime.equals('')">placeholder="起始时间" </ww:if><ww:else>placeholder="<ww:property value="begintime"/>" </ww:else>>
									</div>
									<div class="col-sm-1">
										<p class="form-control-static">至</p>
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control timeselect" id="endtime" name="endtime" value='<ww:property value="endtime"/>'
											<ww:if test="endtime == null || endtime.equals('')">placeholder="终止时间" </ww:if><ww:else>placeholder="<ww:property value="endtime"/>" </ww:else>>
									</div>
								</div>
								<div class="form-group">
									<label for="xiangxi" class="col-sm-2 control-label">详细时间</label>
									<div class="col-sm-4">
										<select class="form-control" id="xiangxi" name="lasttimetype" onchange="searchRangeTime();">
											<option <ww:if test="lasttimetype.equals(\"0\")">selected=selected</ww:if> value="0"></option>
											<option <ww:if test="lasttimetype.equals(\"1\")">selected=selected</ww:if> value="1">最近一个月</option>
											<option <ww:if test="lasttimetype.equals(\"2\")">selected=selected</ww:if> value="2">最近两个月</option>
											<option <ww:if test="lasttimetype.equals(\"3\")">selected=selected</ww:if> value="3">最近三个月</option>
											<option <ww:if test="lasttimetype.equals(\"4\")">selected=selected</ww:if> value="4">最近半年</option>
											<option <ww:if test="lasttimetype.equals(\"5\")">selected=selected</ww:if> value="5">最近一年</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-sm-4">
								<button onclick="sub()" class="btn SubmitButton">查询</button>
							</div>
						
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			
			<!--当前订单-->
			<div class="row">
				<div class="col-md-12 OrderInfo">
					<div class="Border" id="showDiv">
						<h4 class="Caption">历史订单</h4>
						<!--无订单时显示-->
						<div class="EmptyOrder hidden">
							<p class="text-center">当前没有订单<a href="#">立即预订<i class="fa fa-angle-right"></i></a></p>
						</div>
						
						<!--有订单时显示-->
						<div class="table-responsive OrderList" >
							<!--zhour: 我做成假页签形式了，其实就是个筛选功能，你们直接ajax调相应数据显示出来即可-->
							<ul class="nav nav-tabs" role="tablist">
								<li <ww:if test="orderState.equals(\"0\")">class="active" </ww:if> orderState="0"><a>全部订单</a></li>
								<li <ww:if test="orderState.equals(\"1\")">class="active" </ww:if> orderState="1"><a>等待中</a></li>
								<li <ww:if test="orderState.equals(\"2\")">class="active" </ww:if> orderState="2"><a>服务中</a></li>
								<li <ww:if test="orderState.equals(\"3\")">class="active" </ww:if> orderState="3"><a>订单结束</a></li>
								<li <ww:if test="orderState.equals(\"4\")">class="active" </ww:if> orderState="4"><a>订单取消</a></li>
								<li <ww:if test="orderState.equals(\"5\")">class="active" </ww:if> orderState="5"><a>订单超时</a></li>
							</ul>
							
							<table class="table table-hover">
								<thead>
									<tr>
										<th>订单号</th>
										<th>车牌号</th>
										<th>品牌/型号</th>
										<th>取车时间</th>
										<th>还车时间</th>
										<th>金额</th>
										<th>订单状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<ww:iterator value="page.results" id="data" status="rl">
									<tr class="OneList">
										<td><ww:property value="ordersNo" /></td>
										<td><ww:property value="plateNumber" /></td>
										<td><ww:property value="vehicleModelName" /></td>
										<td><ww:property value="transDate12String(beginTime)" /></td>
										<td><ww:property value="transDate12String(endTime)" /></td>
										<td class="TotalFee"><ww:property value="totalFee" /></td>
										<td class="State"><ww:property value="#dictUtil.getCnNameByCode('14',state)" /></td>
										<td><a class="ShowDetailButton" carid="<ww:property value='ordersNo' />">详情</a> 
											<ww:if test="isAppraise == 0 && state.equals(\"4\")"> / <a href="<%=path%>/portal/orders/portalGoOrdersComment.action?ordersno=<ww:property value="ordersNo" />">评价</a> </ww:if>
										</td>
									</tr>
									</ww:iterator>
								</tbody>
							</table>
						</div>
						<!--翻页-->
						<div class="pull-right ChangePage">
						</div>
						
					</div>
				</div>
			</div>
			</form>
        </div>
        
        <div  style="display:none" class="container" id="detailPage">
        <div class="col-sm-9 ContentBlock OrderInfoPage">
        
			<div class="row OrderOverview">
				<div class="col-md-12">
					<div class="Border">
						<div class="row">
							<div class="col-sm-4">
								<p>订单编号：<span class="Bianhao"></span></p>
							</div>
							<div class="col-sm-4">
								<p>订单状态：<span class="Zhuangtai"></span></p>
							</div>
							<div class="col-sm-4">
								<p>总费用：￥<span class="Feiyong"></span>(元)</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row OrderInfo" id="ordersDetailList">
				
			</div>
			
			<div class="row">
				<div class="col-md-2 col-md-offset-5">
					<a class="btn btn-block SubmitButton"  onclick="goBack();">返回</a> 
				</div>
			</div>
			
		</div>
    </div>
    </div>
</div>

 <%@include file="/portal/pages/common/page_bottom.jsp" %>

</body>
</html>
