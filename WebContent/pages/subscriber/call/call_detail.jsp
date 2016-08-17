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

<link  type="text/css"  href="<%=path%>/common/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">

<script type="text/javascript" charset="utf-8" src="<%=path%>/common/js/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/common/js/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/umeditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
function saveEntity(id){
	
}

function r_savedata(data){

}
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"subscriberCall.state": {
				required: true
			},
			"subscriberCall.resultDesc": {
				required: true
				
			}
		},
		messages: {
			"subscriberCall.state": {
				required: "请选择状态！"
				
			},
			"subscriberCall.resultDesc": {
				required: "请输入处理内容！"
				
			}
		}
	});

});

function isValid(){

	var v=UM.getEditor('resultContainer').getContent();

	$("#subscriberCallContent").val(v);
	
	if ($("#eform").valid()){
		return true;
	}else{
		return false;
	}
}
function getForm(){
	return $("#eform");
}
function cancel(){
	window.location.href="<%=path%>/subscriber/showCallList.action";
}
function sub(){
	
	var re=isValid();
	if(re){
		$.post($("#eform").attr("action"),$("#eform").serialize(),r_saveCar,'json').error(requestError);
	}
}
function r_saveCar(data){
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
				window.location.href="<%=path%>/subscriber/showCallList.action";
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

</script>
</head>
<body style="overflow-y: auto;">
  <div class="tc">
	<form name="eform" id="eform" method="post" action="<%=path %>/subscriber/callHandlingUpdate.action">
			<input type="hidden" name="subscriberCall.id"   value="<ww:property value="subscriberCall.id" />" >
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
                 <td class="zuo"><span class="xx red"></span><span>来电号码</span>:</td>
                 <td class="you">
                   <input class="input_size fl" style="top: 0;left: 0px;position:relative;"  type="text" disabled="disabled"     value="<ww:property value="subscriberCall.callPhoneNo" />"  maxlength="13"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 	/>
                 </td>  
                 <td class="zuo1"><span class="xx red"></span><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;</span>:</td>
                 <td class="you1">
                   <input type="text" disabled="disabled"  class="input_size fl" style="top: 0;left: 0px"   value="<ww:property value="subscriberCall.callName" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 	/>
                 </td>                
             </tr>
             
             <tr>
             	 <td class="zuo"><span>紧急程度</span>:</td>
                 <td class="you">
                   <select class="input_size" disabled="disabled"  style="width:150px;top: 0;left: 0px;" >
		      	 			<option value="">请选择</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NOT_URGENT"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NOT_URGENT==subscriberCall.level">selected</ww:if> style="color:#038f5d;">稍缓</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL==subscriberCall.level">selected</ww:if> style="color:#f99338;">正常</option>	
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT==subscriberCall.level">selected</ww:if>  style="color:red;">紧急</option>
					</select>
                 </td> 
                  
                 <td class="zuo1"><span>来电类型</span>:</td>
                 <td class="you1">
                   	<select class="input_size" style="width:150px;top: 0;left: 0px" disabled="disabled"  >
					    	<option value="">请选择</option>
					    	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('17')" id="data" status="rl">
									<option value="<ww:property value="code" />"   <ww:if test="@java.lang.Integer@parseInt(code)==subscriberCall.type">selected</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
					</select>
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span class="xx red"></span><span>来电时间</span>:</td>
                 <td class="you">
                   	<input class="input_size f1"  disabled="disabled" value="<ww:property value="transDateString(subscriberCall.callTime)" />" style="top: 0;left: 0px" >
                 </td> 
                  <td class="zuo" ><span class="xx red"></span><span>状&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;&nbsp;</span>:
			    </td>
			     <td class="you">
				     <select class="input_size"  disabled id="subscriberCall.state" name="subscriberCall.state" style="width:150px;top: 0;left: 0px;">
				     		<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING==subscriberCall.state">selected</ww:if> >处理中</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING==subscriberCall.state">selected</ww:if> >待处理</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED==subscriberCall.state">selected</ww:if> >已处理</option>	
					</select>
			    </td>  
                              
             </tr>
             <tr>
             	 <td class="zuo"><span>受&nbsp;理&nbsp;人&nbsp;</span>:</td>
                 <td class="you">
                  <input class="input_size f1" type="text" disabled="disabled" value="<ww:property value="subscriberCall.acceptOperatorName" />"  style="top: 0;left: 0px" >
                 </td>   
                  <td class="zuo"><span>处&nbsp;理&nbsp;人&nbsp;</span>:</td>
                 <td class="you">
                  <input class="input_size f1" type="text" disabled="disabled" value="<ww:property value="subscriberCall.operatorName" />"  style="top: 0;left: 0px" >
                 </td>   
             </tr>
             <tr>
                 <td class="zuo"><span>主&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;</span>:</td>
                 <td class="you" colspan="3">
                   <input class="input_size f1" type="text" disabled="disabled"   value="<ww:property value="subscriberCall.title" />"  style="top: 0;left: 0px;width:91%;" >
                 </td>  
          
             </tr>
             <tr>
                 <td class="zuo"><span>通话内容</span>:</td>
                 <td  colspan="3" style="padding-left: 10px;padding-top: 10px;">
                    <script type="text/plain" id="container" style="width:808px;height:200px;"><ww:property value="subscriberCall.content"/></script>	
                 </td>  
                          
             </tr>
             
              <tr>
                 <td class="zuo"><span class="xx red"></span><span>处理内容</span>:</td>
                 <td  colspan="3" style="padding-left: 10px;padding-top: 10px;">
                    <script type="text/plain" id="resultContainer" style="width:808px;height:200px;"><ww:property value="subscriberCall.resultDesc"/></script>	
                 </td>  
                          
             </tr>
           
              
            
             <tr></tr>
             <tr>
                  <td colspan="4">
                      <div class="btt">
                         
                         <div class="qzbtn fl" onclick="cancel();">返&nbsp;&nbsp;回</div>
                      </div>
                  </td>
             </tr>
             
		</table>
	</form>
	</div> 
	
	
	<script type="text/javascript">
    //实例化编辑器
     var um = UM.getEditor('container',{
    	 toolbar:[
    	            
    	        ]
         });
     um.setDisabled();

     var um2 = UM.getEditor('resultContainer',{
    	 toolbar:[
    	           
    	        ]
         });
     um2.setDisabled();
     
     
    </script>
    
</body>
</html>