<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">

$().ready(function (){
	
	$('#sendMessageForm').validate({
		errorClass : 'text-danger',
		rules: {
			"phoneNo":{
				required: true,
			},
			"message":{
				required: true,
			}
		},
		messages: {
			"phoneNo":{
				required: "请输入手机号码！"	
			},
			"message":{
				required: "请输入短信内容！"	
			}
		}
	});


});



function getForm(){
	return $("#sendMessageForm");
}
function isValid(){
	return $("#sendMessageForm").valid();

}
</script>
<div class="table_con tanchuang" >
<form class="form-horizontal" name="sform" id="sendMessageForm" method="post"	action="<%=path%>/dialog/sendMessage.action">
      	 <table class="table table-striped table-bordered table-condensed" >
             <tr>
	                <td class="zuo"><span>接收号码</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"  maxlength="11" name="phoneNo" id="phoneNo"   value="<ww:property value="phoneNo" />" />
	                </td>  
             </tr>
              <tr>
	                <td class="zuo"><span>短信内容</span>:</td>
	                <td class="you">
	                	<textarea name="message" rows="5" cols="30"></textarea>
	                </td>  
             </tr>
             
   </table>
</form>
</div>
