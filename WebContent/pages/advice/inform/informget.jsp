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

<script type="text/javascript">

$(function(){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/feedback/addInform.action');
	}else{
		$("#eform").attr('action','<%=path%>/feedback/updateInform.action');	
	}
	
	/*  
	 * 修改为单发时需要指定用户，显示用户输入框为必填项 否则隐藏 or readonly
	 */
	 $('#inform\\.informSendType').change(function(){ 
		var a= $(this).children('option:selected').val();
		debugger;

		if(a==2){//单发送，需要指定用户
			$('.grey').each(function(index,e,c){
				debugger;
				if(e.tagName =="SPAN"){
					if( e.className.indexOf("red")==-1)
						$(e).addClass("red");
					
				}else{
					$(e).attr('readonly',false); 
				}
			});
		}else{
			$('.grey').each(function(index,e,c){
				if(e.tagName =="SPAN"){
					if( e.className.indexOf("red")>-1)
						$(e).removeClass("red");
				}else{
					$(e).attr('readonly',true); 
				}
			});
		}
		 }) ;
	
		var isRequeired=function(){
			debugger;
			var sendType =$('#inform\\.informSendType').children('option:selected').val();
			return sendType ==2;
		}
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			
			"inform.informContent": {
				required: true,
				maxlength : 250
				
			},
			"inform.informType": {
				required: true
			},
			"inform.informSendType" : {
				required:true
			},
			"inform.subscriberName" : {
				required:isRequeired,
				checkName : isRequeired
			} 
		},
		messages: {
			"inform.informContent": {
				required: "请输入消息内容！",
				maxlength: "消息内容过长，最大为250个字符！"
			},
			"inform.informType": {
				required: "请选择消息类型！"
			},
			"inform.informSendType": {
				required: "请选择消息发送类型！"
			} ,
			"inform.subscriberName": {
				required: "请输入会员姓名！"
			} 
		}
	});
	
	jQuery.validator.addMethod("checkName", function(value, element,param) {  
		debugger;
		if(param ==false){ return true;}
		var flag = true;
		if(value){
			$.ajax({//校验用户是否存在
		         url :'<%=path%>/subscriber/isExistSubscriberName.action',
		         type:'post',
		         cache:false,
		         async:false,
		         data:{'subscriber.name':value},
		         dataType:'json',
		         success:function(data) {
		        	 if(data.result == 0){//会员存在 OK
		        		 flag = true;
		        	 }
		        	 else {
		        		 flag = false;
		        	 }
		         }
			 });
		}
		 return flag ;   //如果是单发送 需要会员ID 此项为必须填项
	}, "会员姓名不存在！");  
	
	
});


function isValid(){
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
	window.location.href="<%=path%>/feedback/queryInforms.action";
}
function sub(){
	
	var id = '<ww:property value="id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/feedback/addInform.action";
	}else{
		url="<%=path%>/feedback/updateInform.action";	
	}
	var re=isValid();
	if(re){
		$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
	}
}
function r_saveCar(data){
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
				window.location.href="<%=path%>/feedback/queryInforms.action";
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
function changeCode(compname){
	alert(compname)
	var pars={
			"font":compname
		};
	<%-- $.post('<%=path %>/systemutil/pinyinUtil.action',pars,r_changeCode,'json').error(requestError); --%>

}
function r_changeCode(data){
	if(data != null && data.result == 0){
		$('#inform\\.code').val(data.info);
	}
}



</script>
</head>
<body>
  <div class="tc">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="inform.id" id="inform.id"value="<ww:property value="inform.id" />">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
                 
                 <td class="zuo"><span class="xx red">*</span><span>消息类型</span>:</td>
                 <td class="you">
                   <select name="inform.informType" id="inform.informType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('informType',2)" id="data" status="rl">
								<option value="<ww:property value="code" />"  
								<ww:if test="(inform.informType+'')==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
                 </td>   
             </tr>
             
             <tr>
                 <td class="zuo"><span class="xx red">*</span><span>消息发送类型</span>:</td>
                 <td class="you">
                 		<select   name="inform.informSendType" id="inform.informSendType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('informSendType',2)" id="data" status="rl">
								<option value="<ww:property value="code" />"  
								<ww:if test="(inform.informSendType+'')==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
                 </td> 
                 <td class="zuo1 "><span class="xx  grey">*</span><span >会员姓名</span>:</td>
                 <td class="you1  ">
                   <input readonly  class="input_size fl grey" type="text" style="top: 0;left: 0px;position:relative;" maxlength="120" name="inform.subscriberName" id="inform.subscriberName"  
                   value="<ww:property value="inform.subscriberName" />" />
                 </td>                
             </tr>
             <tr>
                <td class="zuo1 "><span class="xx  red">*</span><span >消息内容</span>:</td>
                 <td class="you1" colspan="3">
                 	<textarea name="inform.informContent" id="inform.informContent" style="width:91%"  class="textarea_size"><ww:property value="inform.informContent" /></textarea>
                 </td>   
             </tr>
             <tr></tr>
             <tr>
                  <td colspan="4">
                      <div class="btt">
                       <ww:if test="inform==null "> 
                        <div class="sbtn fl" onclick="sub();">提&nbsp;&nbsp;交</div> 
                        <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消 </div>
                        </ww:if>
                        <ww:else>
                       	  <div class="qzbtn fl" onclick="cancel();"> 返&nbsp;&nbsp;回 </div>
                        </ww:else>
                       
                       
                      </div>
                  </td>
             </tr>
             
		</table>
	</form>
	</div> 
</body>
</html>