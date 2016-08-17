<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>选择车辆</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel="stylesheet" href="<%=path %>/mobile/common/css/carlist.css">

<script type="text/javascript">
$(function(){
	var pars = {'dotId':'<ww:property value="dotId" />'};
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
			$.each(data.info,function(index,carInfo){
					var htmlstr = '<li>'+
						'<a onclick="bookCar(\''+carInfo.car.id+'\');return false;">'+
					'<div class="col-xs-3 CarImg"><img class="img-responsive center-block" src="<%=path%>/vehiclemodelimgs/web/'+carInfo.carImg+'">'+
						'<p>'+carInfo.car.vehiclePlateId+'</p>'+
					'</div>'+
					'<div class="col-xs-9 CarInfo">'+
						'<div class="row">' + 
							'<div class="col-xs-12"><p class="CarName">'+carInfo.brandModel+' / <span class="CarType">' + carInfo.carType + '</span></p></div>' +
						'</div>'+
						'<div class="row">' + 
							'<div class="col-xs-8">'+
							'<p class="TypeTip">';
						if(carInfo.strategyCarShowInfos != null && carInfo.strategyCarShowInfos.length > 0){
							$.each(carInfo.strategyCarShowInfos,function(index,info){
								htmlstr += '<span style="background:#'+info.color+'">'+info.strategyType.remark+'</span>';
							});
						}
			 htmlstr += '<p>续航：'+carInfo.km+'km</p>'+
						'<div class="row DianliangBlock">'+
							'<div class="col-xs-2 DianliangLabel">电量：</div>'+
							'<div class="col-xs-7">';
// 								'<!--当电量大于70%时用Progress3类，30%-70%用Progress2类，30%以下用Progress1类--
								if(Number(carInfo.SOC) >= 70){
									htmlstr += '<div class="progress Progress3">';
								}
								else if(Number(carInfo.SOC) >= 30){
									htmlstr += '<div class="progress Progress2">';
								}
								else{
									htmlstr += '<div class="progress Progress1">';
								}
								
// 								<!--进度条，每10%一个progress-bar，最多10个
								for(var ii = 10;ii <= Number(carInfo.SOC); ii+=10){
									htmlstr += '<div class="progress-bar"></div>';
								}
					htmlstr += '</div>'+
							'</div>'+
							'<div class="col-xs-2 Dianliang">'+carInfo.SOC+'%</div>'+
						'</div>'+
					'</div>'+
					'<div class="col-xs-4 CarPrice">';

					if(carInfo.strategyCarShowInfos != null && carInfo.strategyCarShowInfos.length > 0){
						$.each(carInfo.strategyCarShowInfos,function(index,info){
							htmlstr += '<p>'+info.price+'<span>'+info.unitStr+'</span></p>';
						});
					}
		 htmlstr += '</div></div></div>' +
					'<div class="clearfix"></div>'+
				'</a>'+
			'</li>';
				$('#CarListDiv').append($(htmlstr));
			});
			
		},
		error: function(){
			Alert("暂时无法获取车辆列表，请稍后再试");	
		},
		complete: function(){
			
		}
	});
	
});
function bookCar(carid){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/mobile/toBookingInfoCheck.action",
	    dataType:'json',
	    data:{'carId':carid,'dotId':$("input[name='dotId']").val()},
	    success: function(data) {
	    	if(data.result == 0){
				window.location.href="<%=path%>/mobile/toBookingInfo.action?carId="+carid+"&dotId="+$('#dotId').val();
			}
			else{
				Alert(data.msg);
			}
	    },
		error: function(){
			Alert("暂时无法预订，请稍后再试");	
		}
	});
}
</script>


</head>

<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="row ListBlock">
				<input type="hidden" name="dotId" id="dotId" value="<ww:property value="dotId" />">
				<ul class="ListUl" id="CarListDiv">
					
				</ul>
			</div>
		</div>
	</div>
</div>
</body>
</html>
