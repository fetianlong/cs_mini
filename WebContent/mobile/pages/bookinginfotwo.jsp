<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>车辆预定</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<script src="<%=path %>/portal/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

<link rel=stylesheet href="<%=path %>/mobile/common/css/bookingcar.css">
<script>
$(function(){
	$(".switch input[type='checkbox']").bootstrapSwitch();
	$('#NeedFapiao').on('switchChange.bootstrapSwitch', function (event, state) {
		if (state){
			$('#NeedFapiao').attr('value',1);
			$(".FapiaoInfo").slideDown(500);
		} else {
			$('#NeedFapiao').attr('value',0);
			$(".FapiaoInfo").slideUp(500);
		};
	});
	$(".FukuanButton button").click(function(e) {
		$("#fukuan").val($(this).attr("value"));
		$(".FukuanButton button").removeClass("active");
		$(".FukuanButton button").removeClass("btn-info");
		$(this).addClass("active");
		$(this).addClass("btn-info");
	});
});
function sub(){
	if($("#NeedFapiao").val() == 1){
		if(!$('#bookTimeForm').valid()){return;}
	}
	$.post('<%=path %>/mobile/bookCar.action',$('#bookTimeForm').serialize()+'&payStyle='+$('#fukuan').val()+'&orders.isBill='+Number($('#NeedFapiao').val()),function(data){
		if(data.result == 0){
			var url = '<%=path%>/mobile/pages/bookingfinish.jsp?img='+'<ww:property value="carInfo.carImg" />'
			+'&vehiclePlateId='+'<ww:property value="carInfo.car.vehiclePlateId" />'
			+'&nickname='+'<ww:property value="carInfo.car.nickName" />'
			+'&model='+'<ww:property value="carInfo.brandModel" />'
			+'&address='+'<ww:property value="carInfo.address" />';
			window.location.href=encodeURI(encodeURI(url));
		}
		else{
			Alert(data.msg);
		}
	},'json');
}
</script>

</head>

<body class="Yuding2">
<div class="CarImgBlock">
	<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="carInfo.carImg" />">
	<h4 class="text-center Chepai"><ww:property value="carInfo.car.vehiclePlateId" /></h4>
</div>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<p class="AddressShow"><i class="fa fa-map-marker"></i>取车网点<span class="pull-right"><ww:property value="choseCarBranchDot.name" /></span></p>
		<p class="AddressShow"><i class="fa fa-map-pin"></i>还车网点<span class="pull-right"><ww:property value="ordersBackSite.name" /></span></p>
		
		<form class="form-horizontal" id="bookTimeForm">
			<input type="hidden" name="orders.carId" value="<ww:property value="carId" />" />
			<input type="hidden" name="getCarTime" value="<ww:property value="getCarTime" />" />
			<input type="hidden" name="backCarTime" value="<ww:property value="backCarTime" />" />
			<input type="hidden" name="orders.ordersBackSiteId" value="<ww:property value="ordersBackSite.id" />" />
			<div class="form-group">
				<label for="fukuan" class="col-sm-3 control-label">支付方式</label>
				<div class="col-sm-6 switch">
					<div class="btn-group FukuanButton" role="group" aria-label="...">
					  <button type="button" class="btn btn-default btn-info active" value="1">账户</button>
					  <button type="button" class="btn btn-default" value="2">信用卡</button>
					</div>
					<input id="fukuan" type="hidden" name="orders.payStyle" value="1" />
				</div>
			</div>
			<p class="FapiaoShow">我需要开发票<span class="switch pull-right">
					<input id="NeedFapiao" type="checkbox" name="orders.isBill"  data-size="mini" 
					data-on-text="是" data-off-text="否" data-on-color="success" data-off-color="warning" value=0 />
				</span></p>
			<div class="FapiaoInfo">
				<div class="form-group FapiaoType">
					<div class="col-xs-12">
						<label class="radio">
							<input type="radio" name="ordersBill.type" data-toggle="radio" value="1"> 单位
						</label>
						<label class="radio">
							<input type="radio" name="ordersBill.type" data-toggle="radio" value="0"> 个人
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<input type="text" class="form-control"  name="ordersBill.title" placeholder="发票抬头">
					</div>
  				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<input type="text" class="form-control" name="ordersBill.recipients" placeholder="收件人">
					</div>
					<div class="col-xs-6">
						<input type="text" class="form-control" name="ordersBill.postcode" placeholder="邮编">
					</div>
  				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<input type="text" class="form-control" name="ordersBill.telphone" placeholder="联系方式">
					</div>
  				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<input type="text" class="form-control" name="ordersBill.address" placeholder="邮寄地址">
					</div>
  				</div>
			</div>
			<div class="row SubmitButton">
				<div class="col-xs-8 col-xs-offset-2">
					<button onclick="sub();" type="button" class="btn btn-embossed btn-primary btn-block">提交订单</button>
				</div>
			</div>
		</form>
	</div>
  </div>
</div>

</body>
<script type="text/javascript">
         		
         	
            var MyValidator = function() {  
                var handleSubmit = function() {  
                    $('.form-horizontal').validate({  
                        errorElement : 'span',  
                        errorClass : 'help-block',  
                        focusInvalid : false,  
                        rules: {
             			   "ordersBill.type": {
                               required: true
             	   			},
             			   "ordersBill.title": {
                               required: true
             	   			},
             			   "ordersBill.address": {
                               required: true
             	   			},
             			   "ordersBill.postcode": {
                               required: true,
                               number:true,
                               rangelength:[6,6]
             	   			},
             			   "ordersBill.telphone": {
                               required: true,
                               isConnection:true
             	   			},
             			   "ordersBill.recipients": {
                               required: true
             	   			}
             			   
             	  	  },
             	      messages: {
	           			   "ordersBill.type": {
	                             required: "请选择取车发票类型！"
	           	   			},
	           			   "ordersBill.title": {
	                             required: "请填写发票抬头！"
	           	   			},
	           			   "ordersBill.address": {
	                             required: "请填写邮寄地址！"
	           	   			},
	           			   "ordersBill.postcode": {
	                             required: "请填写邮编！",
	                             number:"请填写正确的邮编！",
	                             rangelength:"请填写正确的邮编！",
	           	   			},
	           			   "ordersBill.telphone": {
	                             required: "请填写联系方式！"
	           	   			},
		           	   		"ordersBill.recipients": {
	                            required: "请填写联系人！"
	          	   			}
             			 },
              
                        highlight : function(element) {  
                            $(element).closest('.form-group').addClass('has-error');  
                        },  
              
                        success : function(label) {  
                            label.closest('.form-group').removeClass('has-error');  
                            label.remove();  
                        },  
              
                        errorPlacement : function(error, element) {  
                            
                            if ( element.is(":radio") )
                                error.appendTo( element.parent().parent('div').append(error) );
                            else 
                            	element.parent('div').append(error);  
                        }
              
                    });
                    
                    jQuery.validator.addMethod("isConnection", function(value, element) {   
                 	   var tel = /^\d{3,4}-?\d{7,8}$/;
                 	   return this.optional(element) || (tel.test(value));
                 	}, "请输入正确的联系方式");
                   
                };
                return {  
                    init : function() {  
                        handleSubmit();  
                    }  
                };  
              
            }();  
         	
            
            MyValidator.init();
         	
         	</script>
</html>
