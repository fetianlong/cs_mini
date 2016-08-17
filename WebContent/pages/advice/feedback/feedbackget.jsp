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

$().ready(function (){
	
		$("#serviceStart").datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 4,
			autoclose: true,
			minuteStep: 5,
			format: "yyyy-mm-dd"
		});
		
		$("#serviceEnd").datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 4,
			autoclose: true,
			minuteStep: 5,
			format: "yyyy-mm-dd"
		});
	

	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/feedback/addFeedback.action');
	}else{
		$("#eform").attr('action','<%=path%>/feedback/updateFeedbackState.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"feedback.comment": {
				required: true,
				maxlength : 65535,
				feedbackCommentSc:true
			}
		},
		messages: {
			"feedback.comment": {
				required: "请输入处理意见！",
				maxlength: "公司名称过长，最大为65535个字符！"
			}
		}
	});
	val_check_SpecialChar("feedbackCommentSc");
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
	window.location.href="<%=path%>/feedback/queryFeedbacks.action";
}
function sub(){
	var id = '<ww:property value="id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/feedback/addFeedback.action";
	}else{
		url="<%=path%>/feedback/updateFeedbackState.action";	
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
				window.location.href="<%=path%>/feedback/queryFeedbacks.action";
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
<body>
  <div class="tc">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="feedback.id" id="feedback.id"value="<ww:property value="feedback.id" />">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
                 <td class="zuo"><span class="xx gray">*</span><span>会员ID</span>:</td>
                 <td class="you">
                   <input readonly class="input_size fl"  type="text" style="top: 0;left: 0px;position:relative;" maxlength="60"  id="feedback.subscriberId"  
                   value="<ww:property value="feedback.subscriberId" />" />
                 </td>  
                 <td class="zuo1"><span class="xx gray">*</span><span>联系方式</span>:</td>
                 <td class="you1">
                   <input readonly  class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="60"  id="feedback.contactType"  
                   value="<ww:property value="feedback.contactType" />" />
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span class="xx gray">*</span><span>投诉处理状态</span>:</td>
                 <td class="you">
                   	<div class="btt1 fl">
                      <select name="feedback.state" id="feedback.state" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('dealState',1)" id="data" status="rl">
								<option value="<ww:property value="code" />"  
								<ww:if test="feedback.state==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                 </td>  
                 <td class="zuo1"><span class="xx gray">*</span><span>投诉时间</span>:</td>
                 <td class="you1">
                   <input readonly class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="120"  id="feedback.ts"  
                   value="<ww:property value="transDateString(feedback.ts)" />" />
                 </td>                
             </tr>
              <tr>
                 
                 <td class="zuo"><span class="xx gray">*</span><span>反馈意见</span>:</td>
                 <td class="you" colspan="3">
                 	<textarea readonly   id="feedback.feedbackDesc" style="width:91%"  class="textarea_size"><ww:property value="feedback.feedbackDesc" /></textarea>
                 </td>   
             </tr>
             
             <tr>
                 
                 <td class="zuo"><span class="xx red">*</span><span>处理意见</span>:</td>
                 <td class="you" colspan="3">
                 	<textarea  name="feedback.comment" id="feedback.comment" style="width:91%"  class="textarea_size"><ww:property value="feedback.comment" /></textarea>
                 </td>   
             </tr>
             <tr></tr>
             
              <tr>
		   	<td class="zuo"><span>照片</span>：</td>
		  	<td class="you">
		  	<ww:if test="imgurl != null && imgurl  != ''">
		  		<ww:iterator value="imgurl" status="loop"> 
			  		<%-- <a target="_blank" href='<ww:property value="imgurl[#loop.index]"/>' > --%>
			  			<img  id='img<ww:property value="#loop.index"/>' width="120" height="120" src="<ww:property value="imgurl[#loop.index]"/>"/>
			  		<!-- </a> -->
		  		</ww:iterator>
		  	</ww:if>
		  	<ww:else>
		  			<img id="ImgPr0" width="120" height="120" />
		  	</ww:else>
		  	</td>
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
	</form>
	</div> 
</body>
</html>