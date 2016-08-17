<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>Insert title here</title>
<base href="<%=basePath%>">
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"subscriber.idCard": {
				required: true,
				rangelength:[15,18]
			},
			"subscriber.drivingLicense":{
				required: true,
				rangelength:[15,20]
			}
		},
		messages: {
			"subscriber.idCard": {
				required: "请输入身份证号称！",
				rangelength: "请输入正确的身份证号"
			},
			"subscriber.drivingLicense":{
				required: "请输入驾驶证号！",
				rangelength: "请输入正确的驾驶证号"
			}
		}
	});
});
function getForm(){
	return $("#eform");
}
function isValid(){
	
	return true;
	
	

}



</script>
</head>

<body style="height:160%">
	
	<div class="tc">
	<form name="eform" id="eform" method="post" action="subscriber/subscriberUpdate.action">
				<input type="hidden" name="subscriber.id" id="subscriber.id"value="<ww:property value="subscriber.id" />">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>用户手机号</span>:</td>
		    <td class="you"><input type="text" name="subscriber.phoneNo" disabled="disabled" id="subscriber.phoneNo" maxlength="60"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 
		    value="<ww:property value="subscriber.phoneNo" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red"></span><span>用户姓名</span>:
		    </td>
		    <td class="you"><input type="text" name="subscriber.name" id="subscriber.name"  maxlength="60" class="input_size fl" style="top: 0;left: 0px;position:relative;"
		    value="<ww:property value="subscriber.name" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span>驾驶证号</span>:
		    </td>
		    <td class="you"><input type="text" name="subscriber.drivingLicenseNo" id="subscriber.drivingLicenseNo"  maxlength="60" class="input_size fl" style="top: 0;left: 0px;position:relative;"
		    value="<ww:property value="subscriber.drivingLicenseNo" />"/>
		    </td>   
		  </tr> 
		  
		   <tr>
		    <td class="zuo"><span>状&nbsp;&nbsp;态&nbsp;&nbsp;</span>:
		    </td>
		    <td class="you">&nbsp;
		    	  <select  class="input_size"  style="width:100px;"  class="kd" id="subscriber.state" disabled="disabled">
				   		<option></option>
						<option value="1" <ww:if test="1==subscriber.state">selected</ww:if> >未审核</option>
						<option value="2" <ww:if test="2==subscriber.state">selected</ww:if> >审核中</option>
						<option value="3" <ww:if test="3==subscriber.state">selected</ww:if> >正常</option>
						<option value="4" <ww:if test="4==subscriber.state">selected</ww:if> >审核未通过</option>
				</select>
		   
		    </td>   
		  </tr> 
		  
		    <tr>
		    <td class="zuo"><span>锁定状态</span>:
		    </td>
		    <td class="you">&nbsp;
		    	 <select  class="input_size"  style="width:100px;"  class="kd" name="subscriber.eventState" id="subscriber.eventState">
				   		<option>无</option>
						<option value="4"  <ww:if test="4==subscriber.eventState">selected=true</ww:if> >半锁</option>
						<option value="5"  <ww:if test="5==subscriber.eventState">selected=true</ww:if> >全锁</option>
				</select>
		   
		    </td>   
		  </tr> 
		  
		  <tr>
	        <td class="zuo">账户金额：</td>
	        <td class="you"><input class="input_size" type="text" name="subscriber.alipayAccount" maxlength="30"value='<ww:property value="subscriber.alipayAccount"/>' id="alipayAccount" style=" width:320px; height:25px;"></td>
	      </tr>
		  
		  <tr>
	        <td class="zuo">支付宝帐号：</td>
	        <td class="you"><input class="input_size" type="text" name="subscriber.alipayAccount" maxlength="30"value='<ww:property value="subscriber.alipayAccount"/>' id="alipayAccount" style=" width:320px; height:25px;"></td>
	      </tr>
		  <tr>
	        <td class="zuo">微信帐号：</td>
	        <td class="you"><input class="input_size" type="text" name="subscriber.webchatNo" maxlength="30" value='<ww:property value="subscriber.webchatNo"/>' id="webchatNo" style=" width:320px; height:25px;"></td>
	      </tr>
      
       <tr>
        <td class="zuo">常用城市：</td>
        <td class="you">
           <select class="input_size" style="width:100px;" name="subscriber.city"  onchange="queryCity(this.value)">
		                <option value="">无</option>
						<ww:iterator value="citys" >
							<option value='<ww:property value="id"/>' <ww:if test="id.equals(subscriber.city)">selected</ww:if> >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	        </select>
        </td>
      </tr>
      
      
       <tr>
      	<td class="zuo">默认网点：</td>
      	<td class="you">
      		 	<select class="input_size" style="width:100px;" onchange="queryDot(this.value);" id="area" name="subscriber.defaultArea">
		                <option value="">无</option>
						<ww:iterator value="areas" >
							<option value='<ww:property value="id"/>' <ww:if test="id.equals(subscriber.defaultArea)">selected</ww:if> >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	                </select>
	                <select class="input_size" style="width:120px;" id="dot" name="subscriber.defaultSite">
	                	<option value="">无</option>
						<ww:iterator value="dots">
							<option value='<ww:property value="id"/>'  <ww:if test="id.equals(subscriber.defaultSite)">selected</ww:if>   >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	                </select>
      	</td>
      </tr>
      
      
      
      
       <tr>
        <td  class="zuo">邮寄地址：</td>
        <td class="you"><input class="input_size" type="text" name="subscriber.postAddress" maxlength="400" id="postAddress" value='<ww:property value="subscriber.postAddress"/>' style=" width:320px; height:25px;"></td>
      </tr>
      <tr>
        <td  class="zuo">邮&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;&nbsp;：</td>
        <td class="you"><input class="input_size" type="text" name="subscriber.email" id="email" maxlength="30" onBlur="emailOnBlur();" maxlength="30" value='<ww:property value="subscriber.email"/>' style=" width:320px; height:25px;"></td>
      </tr>
      <tr>
        <td  class="zuo">紧急联系人：</td>
        <td class="you"><input class="input_size" type="text" name="subscriber.emergencyContact" maxlength="20" id="emergencyContact" value='<ww:property value="subscriber.emergencyContact"/>' style=" width:320px; height:25px;"></td>
      </tr>
      <tr>
        <td class="zuo">紧急联系人号码：</td>
        <td class="you"><input class="input_size" type="text" name="subscriber.emergencyContactPhone" maxlength="20" id="emergencyContactPhone" value='<ww:property value="subscriber.emergencyContactPhone"/>' style=" width:320px; height:25px;"></td>
      </tr>
      <tr>
        <td class="zuo">紧急联系人地址：</td>
        <td class="you"><input class="input_size" type="text" name="subscriber.emergencyContactAddress"  maxlength="400" id="emergencyContactAddress" value='<ww:property value="subscriber.emergencyContactAddress"/>' style=" width:320px; height:25px;"></td>
      </tr>
      
      
      
		</table>
	</form>
	</div> 
	
</body>


</html>