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
<title>会员详情</title>
<%@ include file="/pages/common/common_head.jsp"%>


</head>
<body  class="hyglbjPage" style="overflow-y:auto;">
      <div class="tc" >
		<form name="eform" id="eform" method="post" action="<%=path%>/subscriber/subscriberUpdate.action">
				<input type="hidden" name="subscriber.id" id="subscriber.id"value="<ww:property value="subscriber.id" />">
        	 <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
                 <tr>
                    <td class="zuo"><span>&nbsp;手&nbsp;机&nbsp;号&nbsp;</span>:</td>
                    <td class="you">
                      	<input type="text" name="subscriber.phoneNo" disabled="disabled" id="subscriber.phoneNo" maxlength="60"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 
		    				value="<ww:property value="subscriber.phoneNo" />"/>
                    </td>  
                    <td class="zuo1"><span class="xx red"></span><span>姓&nbsp;&nbsp;&nbsp;名 &nbsp;&nbsp;&nbsp;</span>:</td>
                    <td class="you1">
                     	<input type="text" name="subscriber.name" id="subscriber.name" disabled="disabled"  maxlength="10" class="input_size fl" style="top: 0;left: 0px;position:relative;"
		    				value="<ww:property value="subscriber.name" />"/>
                    </td>   
                 </tr>
                 <tr>
                      <td class="zuo"><span class="xx red"></span><span>驾驶证号</span>:</td>
                      <td class="you">
                        	<input type="text" name="subscriber.drivingLicenseNo" id="subscriber.drivingLicenseNo" disabled="disabled" maxlength="60" class="input_size fl" style="top: 0;left: 0px;position:relative;"
		    					value="<ww:property value="subscriber.drivingLicenseNo" />"/>
                      </td>  

                     <td class="zuo">微信帐号：</td>
	        		 <td class="you"><input disabled="disabled" class="input_size fl" type="text" value='<ww:property value="wechatUserInfo.nickname"/>'  ></td>
	     
	     
                   </tr>
                    <tr>
                      <td class="zuo"><span class="xx red"></span><span>身份证</span>:</td>
                      <td class="you">
                      
                    		 <ww:if test="subscriber.idCardImg == null">
					  				
					  		 </ww:if>
					  		 <ww:else>
								      <a href="<%=path %>/subscriberConfirm/showOriginalImage.action?imageType=idCard&subscriberConfirm.subscriber.idCardImg=<ww:property value='subscriber.idCardImg'/>" target="_blank">
								         <img class="img-responsive IDImg img-thumbnail" alt="身份证缩略图"  src="<ww:property value="subscriber.idCardImg"/>">
								      </a>
					  		 </ww:else>
                        	
                      </td>  
                    
                      
                     <td class="zuo">驾驶证：</td>
	        		 <td class="you">
	        		 		 <ww:if test="subscriber.drivingLicenseImg == null">
					  				
					  		 </ww:if>
					  		 <ww:else>
					  		 	  
								      <a href="<%=path %>/subscriberConfirm/showOriginalImage.action?imageType=drivingCard&subscriberConfirm.subscriber.drivingLicenseImg=<ww:property value='subscriber.drivingLicenseImg'/>" target="_blank">
								         <img class="img-responsive IDImg img-thumbnail" alt="驾驶证缩略图"  src="<ww:property value="subscriber.drivingLicenseImg"/>">
								      </a>
								  
					  		 </ww:else>
	        		 </td>
	     
	     
                   </tr>
            
		             <tr>
				     
			      	 <td class="zuo">押金：</td>
				        <td class="you">
				        	<input class="input_size fl" type="text" name="account.amount" disabled="disabled" maxlength="30"value='<ww:property value="formatAmount(subscriber.account.frozenAmount)"/>' id="account.amount" >
				        </td>
				         <td class="zuo">可用余额：</td>
				        <td class="you">
				        	<input class="input_size fl" type="text" name="account.amount" disabled="disabled" maxlength="30"value='<ww:property value="formatAmount(subscriber.account.usableAmount)"/>' id="account.amount" >
				        </td>
			      	</tr>
            		
            		 <tr>
					    <td class="zuo"><span>状&nbsp;&nbsp;&nbsp;态&nbsp;&nbsp;&nbsp;</span>:
					    </td>
					    <td class="you">
					    	
					    	<input class="input_size fl" disabled="disabled"  type="text" value='<ww:property  value="@com.dearho.cs.subscriber.pojo.Subscriber@getState(subscriber.state)"/>'>
					    </td>   
					    <td class="zuo1"><span>锁定状态</span>:
					    </td>
					    <td class="you1">
					    	 <select disabled="disabled" class="input_size fl"  style="width:100px;"  class="kd" name="subscriber.eventState" id="subscriber.eventState">
							   		<option>无</option>
									<option value="4"  <ww:if test="4==subscriber.eventState">selected=true</ww:if> >半锁</option>
									<option value="5"  <ww:if test="5==subscriber.eventState">selected=true</ww:if> >全锁</option>
							</select>
					    </td>   
		  		</tr> 
		  
		  	<tr>
		        <td  class="zuo">邮&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;&nbsp;：</td>
		        <td class="you"><input class="input_size fl" type="text" disabled="disabled"  name="subscriber.email" id="email" maxlength="30"  maxlength="30" value='<ww:property value="subscriber.email"/>' ></td>
		        <td  class="zuo1">性&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;：</td>
		        <td class="you1">
		        	 <select  class="input_size fl"  disabled="disabled"  class="kd" name="subscriber.sex" id="subscriber.sex">
							   		<option>无</option>
							   		
									<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN.equals(subscriber.sex)">selected=true</ww:if> >男</option>
									<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN"/>' <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN.equals(subscriber.sex)">selected=true</ww:if> >女</option>
					</select>
		        	
		        </td>
     		
     		 </tr>
		  

	     
      
        <tr>
        <td class="zuo">省份：</td>
        <td class="you">
	        <select class="input_size fl" id="s_province" name="subscriber.province"></select>
        </td>
     
      	<td class="zuo">地级市：</td>
      	<td class="you">
      		 	<select  class="input_size fl" id="s_city" name="subscriber.city" ></select>
	            
      </tr>
      
       <tr>
        <td class="zuo">县级市：</td>
        <td class="you">
	        <select  class="input_size fl" id="s_county" name="subscriber.county"></select>
      	</td>
       
      	<td class="zuo">家庭地址：</td>
      	<td class="you">
	             <textarea  class="textarea_size form-control" name="subscriber.address"  id="address"  ><ww:property value="subscriber.address"/></textarea>

      	</td>
      </tr>
      
       <tr>
        <td class="zuo">发票抬头：</td>
        <td class="you"><input class="input_size fl" type="text" name="subscriber.billTitle"   value='<ww:property value="subscriber.billTitle"/>' ></td>
	        
      	
       
      	<td class="zuo">邮编：</td>
      	 <td class="you"><input class="input_size fl" type="text" name="subscriber.billPostCode"   value='<ww:property value="subscriber.billPostCode"/>' ></td>
	   
      </tr>
      
      
      
       <tr>
        <td  class="zuo">邮寄地址：</td>
        <td class="you">
        		<textarea disabled="disabled"  class="textarea_size form-control" name="subscriber.postAddress"  id="postAddress"    ><ww:property value="subscriber.postAddress"/></textarea>
        </td>
        
         <td  class="zuo1">注册时间：</td>
        <td class="you1"><input disabled="disabled" class="input_size fl" disabled="disabled" type="text" name="subscriber.createDate" maxlength="400" id="createDate" value='<ww:property value="transDateString(subscriber.createDate)"/>' ></td>
      
      </tr>
     
      <tr>
        <td  class="zuo">紧急联系人：</td>
        <td class="you"><input disabled="disabled" class="input_size fl" type="text" name="subscriber.emergencyContact" maxlength="20" id="emergencyContact" value='<ww:property value="subscriber.emergencyContact"/>' ></td>
      
        <td class="zuo1">紧急联系人号码：</td>
        <td class="you1"><input disabled="disabled" class="input_size fl" type="text" name="subscriber.emergencyContactPhone" maxlength="20" id="emergencyContactPhone" value='<ww:property value="subscriber.emergencyContactPhone"/>' ></td>
      </tr>
      <tr>
        <td class="zuo">紧急联系人地址：</td>
        <td class="you">
        	<textarea disabled="disabled"  class="input_size" name="subscriber.emergencyContactAddress" id="emergencyContactAddress" maxlength="400" id="postAddress"   style="height:40px;resize: none;" rows="2" ><ww:property value="subscriber.emergencyContactAddress"/></textarea>
        </td>
         <td class="zuo"></td>
        <td class="you"></td>
      
      </tr>
         <tr>
      	<td class="zuo">备注：</td>
      	<td class="you" colspan="3">
      		 <textarea  class="textarea_size form-control" name="subscriber.remark" maxlength="1024" id="address"   rows="2" ><ww:property value="subscriber.remark"/></textarea>
      		
      	</td>
      </tr>
      		

             </table>
            
     			</form>
     	</div>
     	
     	
	
  <div class="ta">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" id="myTab">
   <li><a href="#orders"   		  data-toggle="tab" refHref="orders/ordersSearchForDialog.action" 	refId="orders">订单记录</a></li>
   <li><a href="#recharge" 		  data-toggle="tab" refHref="dialog/queryRechargeList.action" refId="recharge">充值记录</a></li>
   <li><a href="#refund" 		  data-toggle="tab" refHref="dialog/queryRefundList.action" refId="refund">退款记录</a></li>
   <li><a href="#consume" 		  data-toggle="tab" refHref="dialog/queryAccountTrading.action" refId="consume">消费记录</a></li>
   <li><a href="#vehicleConverse" data-toggle="tab" refHref="carservice/carViolationSearchForDialog.action" refId="vehicleConverse">违章记录</a></li>
   <li><a href="#accident"        data-toggle="tab" refHref="carservice/carAccidentSearchForDialog.action" refId="accident">事故记录</a></li>
</ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane fade"  isload="N" id="orders"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="recharge"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="refund"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="consume"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="vehicleConverse"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="accident"></div>
  </div>

</div>
</body>
<script type="text/javascript">
 var paraIds={"accountTradeRecord.subscriberId":'<ww:property value="subscriber.id" />',"carViolation.memberId":'<ww:property value="subscriber.id" />',"orders.memberId":'<ww:property value="subscriber.id" />',"carAccident.memberId":'<ww:property value="subscriber.id" />'};
$(function () {
	$('#myTab a').click(function (e) {
		  e.preventDefault()

		  var refId=$(this).attr("refId");
		  var refHref=$(this).attr("refHref");
		  var isLoad=$("#"+refId).attr("isload");
		  
		  
		  if(isLoad=="Y"){
			  $(this).tab('show')
		  }else{
			  $.ajax({
				  url: '<%=path%>/'+refHref,
				  data:paraIds,
			      type: 'POST',
				  dataType: 'html',
				  success: function(data){
				 		$("#"+refId).html(data);
				 		$(this).tab('show')
				 		$("#"+refId).attr("isload","Y");	
				  }
				});
		  }
		});
	
	$('#myTab a:first').click();
});

var paraPage;
var p;
function skipToPage(page, func){
	p=page;
	paraPage={'page.currentPage':page};
	if (typeof func == 'function'){
		func();
	}else{
		searchTrading();
	};
}

function searchTrading(){
	var paras = $.extend({}, paraPage,paraIds);
	var paraForm = $(".tab-content div.active form").serialize();
	var pars=$.extend({}, paras,paraForm);
	var parsStr=JSON.stringify(paraForm);
	

	 var refId=$("ul li.active a").attr("refId");
	 var refHref=$("ul li.active a").attr("refHref");
	
	$.ajax({
	 url: '<%=path%>/'+refHref+"?page.currentPage="+p,
	 data:$(".tab-content div.active form").serialize(),

     type: 'POST',
     dataType: 'html',
	  success: function(data){
	 		$("#"+refId).html(data);
	  },
	  error: function(a,b,c){
		  alert(b);
	  }
	});
}
</script>

<script class="resources library" src="<%=path %>/mobile/common/js/area.js" type="text/javascript"></script>
    
<script type="text/javascript">_init_area();</script>
<script type="text/javascript">



document.getElementById("s_province").value="<ww:property value="subscriber.province"/>";
$("#s_province").trigger("change");  

document.getElementById("s_city").value="<ww:property value="subscriber.city"/>";
$("#s_city").trigger("change");  

document.getElementById("s_county").value="<ww:property value="subscriber.county"/>";
$("#s_county").trigger("change");
</script>
</html>