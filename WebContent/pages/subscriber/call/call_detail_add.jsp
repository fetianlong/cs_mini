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
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"subscriberCall.callPhoneNo": {
				required: true
				//isPhone:true 
			},
			"subscriberCall.callName":{
				required: true
			},
			"subscriberCall.type":{
				required: true
			},
			"subscriberCall.level":{
				required:true
			},
			"subscriberCall.title":{
				required:true
			},
			"subscriberCall.content":{
				required:true
			},
			"subscriberCall.state":{
				required:true
			}
			
		},
		messages: {
			"subscriberCall.callPhoneNo": {
				required: "请填写手机号"
			},
			"subscriberCall.callName":{
				required: "请填写姓名"
			},
			"subscriberCall.type":{
				required:"请选择来电类型"
			},
			"subscriberCall.level":{
				required:"请选择紧急程度"
			},
			"subscriberCall.title":{
				required:"请填写主题"
			},
			"subscriberCall.content":{
				required:"请填写通话内容"
			},
			"subscriberCall.state":{
				required:"请选择处理状态"
			}
		},
		errorPlacement: function(error, element) {
			
			
		    if ( $(element).attr("id")=="subscriberCallContent" ){
		    	error.appendTo( $("#UEsubscriberCallContent")); 
		    }
		    else
		        error.appendTo( element.parent() );
		}
		
	});

	$("#subscriberCallLevel").change(function(){ 
		if($(this).val()=="0"){
			$(this).css("color","#337ab7");
		}else if($(this).val()=="1"){
			$(this).css("color","#f0ad4e");
		}else if($(this).val()=="2"){
			$(this).css("color","#d9534f");
		}else{
			$(this).css("color","black");
		}
		
	});


	
});




function getForm(){
	return $("#eform");
}
function isValid(){
	var v=UM.getEditor('container').getContent();

	$("#subscriberCallContent").val(v);
	
	return $("#eform").valid();
}

jQuery.validator.addMethod("isPhone", function(value, element) {   
	  
	   var isPhone = /^1\d{10}$/;
	   var isMob= /^([0-9]{3,4}-)?[0-9]{7,8}$/;
		   
	   return this.optional(element) || isPhone.test(value) ||isMob.test(value);
	}, "请输入正确的来电号码");



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

//查询会员
function selectCallPhoneSearch(){

	var value=$("#callPhoneNo").val();
	 var isPhone = /^1\d{10}$/;
	if(value==null || value == "" || !isPhone.test(value)){
		$("#callName").val("");
		$("#callOrderNo").val("");
		$("#subscriberId").val("");
		return;
	}
	
	$.ajax({
		type: "POST",
	    url:"<%=path%>/subscriber/callPhoneSearch.action?subscriber.phoneNo="+value,
	    dataType:'json',
	    data:$('#refundForm').serialize(),
	    success: function(data) {
			if(0==data.result){
				$("#callName").val(data.subscriberName);
				$("#callOrderNo").val(data.orderNo);
				$("#subscriberId").val(data.subscriberId);
		    }else{
		    	$("#callName").val("");
				$("#callOrderNo").val("无订单来电");
				$("#subscriberId").val("");
		    }
	    }
	});
}


function selectOrderDetail(){
	var ordersNo=$("#callOrderNo").val();
	var isNum = /\d$/;
	if(ordersNo==null || ordersNo == "" || !isNum.test(ordersNo)){
		return;
	}
	
	showOrderDetailByNoForDialog(ordersNo,'<%=path%>');
	
	
}


function selectsubscriberSearch(){
	var suscriberId=$("#subscriberId").val();
	
	if(suscriberId==null || suscriberId ==""){
		return;
	}
	
	
	showSubscriberDetailForDialog(suscriberId,'<%=path%>');
	
}
</script>
</head>
<body style="overflow-y: auto;" class="hyldglPage">
  <div class="tc">
	<form name="eform" id="eform" method="post" action="<%=path %>/subscriber/callAdd.action">
				<input type="hidden" name="subscriberCall.subscriberId" id="subscriberId" value="<ww:property value="subscriberCall.subscriberId"/>">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
                 <td class="zuo"><span class="xx red">*</span><span>来电号码</span>:</td>
                 <td class="you">
                  
	                  	
	                    <input name="subscriberCall.callPhoneNo" id="callPhoneNo" type="text"  maxlength="13"
	                  		class="input_size fl form-control"
	                  		value="<ww:property value="subscriberCall.callPhoneNo" />" />
	                  	<input type="button" class="searchinputbut form-control" value="查询" onclick="selectCallPhoneSearch();">
		                
                 </td>  
                 <td class="zuo1"><span class="xx red">*</span><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;</span>:</td>
                 <td class="you1">
                   <input type="text" name="subscriberCall.callName"  class="input_size fl form-control" style="top: 0;left: 0px"  id="callName" value="<ww:property value="subscriberCall.callName" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 	/>
                   <input type="button" class="searchinputbut fl form-control" value="详情" onclick="selectsubscriberSearch();">
                  
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span class="xx red"></span><span>最近订单</span>:</td>
                 <td class="you">
                   		<input id="orderId" type="hidden" value=""/>
                   		
		                <input  id="callOrderNo" type="text" 
		                  		class="input_size fl form-control"
		                  		value="" />
		               <input onclick="selectOrderDetail();" type="button"  class="searchinputbut form-control" value="详情"   />
		               
                 </td> 
                 <td class="zuo1"><span class="xx red"></span><span>来电时间</span>:</td>
                 <td class="you1">
                   	<ww:if test="'add'== #request.type">
		     			<input class="input_size f1" disabled="disabled"  value="<ww:property value="transDateString(#request.addTime)" />" style="top: 0;left: 0px" >
		     			<input type="hidden" name="callTimeStr" value="<ww:property value="transDateString(#request.addTime)" />"  >
			     	</ww:if>
			     	<ww:else>
			     		<input class="input_size f1"  disabled="disabled" value="<ww:property value="transDateString(subscriberCall.callTime)" />" style="top: 0;left: 0px" >
			     	</ww:else>
                 </td> 
                
                               
             </tr>
             <tr>
             	 <td class="zuo"><span class="xx red">*</span><span>紧急程度</span>:</td>
                 <td class="you">
                  <select class="input_size" id="subscriberCallLevel" name="subscriberCall.level" style="width:150px;top: 0;left: 0px;" >
		      	 			<option value="">请选择</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL==subscriberCall.level">selected</ww:if> style="color:#f0ad4e">正常</option>	
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT==subscriberCall.level">selected</ww:if>  style="color:#d9534f">紧急</option>
					</select>
                 </td> 
                  
                 <td class="zuo1"><span class="xx red">*</span><span>来电类型</span>:</td>
                 <td class="you1">
                   	<select class="input_size" style="width:150px;top: 0;left: 0px" id="subscriberCall.type" name="subscriberCall.type" >
					    	<option value="">请选择</option>
					    	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('17')" id="data" status="rl">
									<option value="<ww:property value="code" />"   <ww:if test="@java.lang.Integer@parseInt(code)==subscriberCall.type">selected</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
					</select>
                 </td>                
             </tr>
             
             <tr>
                 <td class="zuo"><span class="xx red">*</span><span>主&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;</span>:</td>
                 <td class="you" colspan="3">
                  <input class="input_size f1" type="text" id="subscriberCall.title" name="subscriberCall.title" value="<ww:property value="subscriberCall.title" />"  style="top: 0;left: 0px;width:91%;" >
                 </td>  
          
             </tr>
             <tr >
                 <td class="zuo"><span class="xx red">*</span><span>通话内容</span>:</td>
                 <td  colspan="3" id="UEsubscriberCallContent">
                   	<script type="text/plain" id="container" style="width:808px;height:200px;"><ww:property value="subscriberCall.content"/></script>
                   
              </td>  
                          
             </tr>
            
             
              <tr>
                 <td class="zuo" ><span class="xx red">*</span><span>状&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;&nbsp;</span>:
			    </td>
			     <td class="you">
				     <select class="input_size" id="subscriberCall.state" name="subscriberCall.state" style="width:150px;top: 0;left: 0px;">
				     		<option value="">请选择</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING==subscriberCall.state">selected</ww:if> >待处理</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING==subscriberCall.state">selected</ww:if> >处理中</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED==subscriberCall.state">selected</ww:if> >已处理</option>	
					</select>
			    </td>   
                 <td class="zuo1"><span></span></td>
                 <td class="you1"></td>         
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
		
		
        <input type="text"  style="top: 0;left: 0px;visibility:hidden;height:0px;" id="subscriberCallContent"  name="subscriberCall.content" value="<ww:property value="subscriberCall.content" />"/>
		
             
	</form>
	</div> 
	
	
	<script type="text/javascript">
    //实例化编辑器
    

     var um = UM.getEditor('container',{
    	 toolbar:[
    	            ' undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
    	            'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
    	            '| justifyleft justifycenter justifyright justifyjustify |',
    	            
    	            ' fullscreen'
    	        ]
         });

     if('<ww:property value="#request.type"/>'=='show'){
    	  um.setDisabled();
 		}
     
    </script>
    
</body>
</html>