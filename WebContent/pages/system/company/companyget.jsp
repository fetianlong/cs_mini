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

function saveEntity(id){
	
}

function r_savedata(data){

}
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
		$("#eform").attr('action','<%=path%>/company/companyAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/company/companyUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"company.name": {
				required: true,
				maxlength : 60,
				companyNameSc:true
			},
			"company.code": {
				required: true,
				maxlength : 60,
				companyCodeSc:true
			},
			"company.bizType": {
				required: true
			},
			"company.telephone" : {
				companyTelephone:true
			},
			"company.linkmanTelephone" : {
				companyLinkmanTelephone:true
			},
			"company.address":{
				maxlength : 120,
				companyAddrSc:true
			},
			"company.bizScope":{
				maxlength : 120
			},
			"company.chairman":{
				maxlength : 60,
				companyChairmanSc:true
			},
			"company.linkman":{
				maxlength : 60,
				companyLinkmanSc:true
			}
		},
		messages: {
			"company.name": {
				required: "请输入公司名称！",
				maxlength: "公司名称过长，最大为60个字符！"
			},
			"company.code": {
				required: "请输入公司编码！",
				maxlength: "公司编码过长，最大为60个字符！"
			},
			"company.bizType": {
				required: "请选择公司业务类型！"
			}
		}
	});
	val_check_Telephone("companyTelephone");
	val_check_Telephone("companyLinkmanTelephone");
	val_check_SpecialChar("companyNameSc,companyCodeSc,companyAddrSc,companyChairmanSc,companyLinkmanSc");
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
	window.location.href="<%=path%>/company/companySearch.action";
}
function sub(){
	var id = '<ww:property value="id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/company/companyAdd.action";
	}else{
		url="<%=path%>/company/companyUpdate.action";	
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
				window.location.href="<%=path%>/company/companySearch.action";
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
	var pars={
			"font":compname
		};
	$.post('<%=path %>/systemutil/pinyinUtil.action',pars,r_changeCode,'json').error(requestError);

}
function r_changeCode(data){
	if(data != null && data.result == 0){
		$('#company\\.code').val(data.info);
	}
}
</script>
</head>
<body>
  <div class="tc">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="company.id" id="company.id"value="<ww:property value="company.id" />">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			<tr>
                 <td class="zuo"><span class="xx red">*</span><span>公司名称</span>:</td>
                 <td class="you">
                   <input class="input_size fl" onkeyup="changeCode(this.value)" type="text" style="top: 0;left: 0px;position:relative;" maxlength="60" name="company.name" id="company.name"  
                   value="<ww:property value="company.name" />" />
                 </td>  
                 <td class="zuo1"><span class="xx red">*</span><span>公司编码</span>:</td>
                 <td class="you1">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="60" name="company.code" id="company.code"  
                   value="<ww:property value="company.code" />" />
                 </td>                
             </tr>
             
             <tr>
                 <td class="zuo"><span class="xx red">*</span><span>公司业务类型</span>:</td>
                 <td class="you">
                   	<div class="btt1 fl">
                      <select name="company.bizType" id="company.bizType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('companyBizType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="company.bizType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                 </td>  
                 <td class="zuo1"><span>公司地址</span>:</td>
                 <td class="you1">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="120" name="company.address" id="company.address"  
                   value="<ww:property value="company.address" />" />
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span>公司电话</span>:</td>
                 <td class="you">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="company.telephone" id="company.telephone"  
                   value="<ww:property value="company.telephone" />" />
                 </td> 
                 <td class="zuo1"><span>负责人</span>:</td>
                 <td class="you1">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="company.chairman" id="company.chairman"  
                   value="<ww:property value="company.chairman" />" />
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span>联系人姓名</span>:</td>
                 <td class="you">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="company.linkman" id="company.linkman"  
                   value="<ww:property value="company.linkman" />" />
                 </td>  
                 <td class="zuo1"><span>联系人电话</span>:</td>
                 <td class="you1">
                   <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="company.linkmanTelephone" id="company.linkmanTelephone"  
                   value="<ww:property value="company.linkmanTelephone" />" />
                 </td>                
             </tr>
             <tr>
                 <td class="zuo"><span>服务开始日期</span>:</td>
                 <td class="you">
                   <input name="company.serviceStart" id="serviceStart" readonly="readonly" value="<ww:property value="transDate10String(company.serviceStart)"/>"
			    			type="text" class="input_size fl Wdate" style="top: 0;left: 0px;position:relative;height:26px;"  id="company.serviceStart" 
			    			onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'company.serviceEnd\')||\'%y-%M-%d\'}'})"  /> 
                 </td>  
                 <td class="zuo1"><span>服务结束日期</span>:</td>
                 <td class="you1">
                   <input name="company.serviceEnd" id="serviceEnd" readonly="readonly" value="<ww:property value="transDate10String(company.serviceEnd)"/>"
			    			type="text" class="input_size fl Wdate" style="top: 0;left: 0px;position:relative;height:26px;"  id="company.serviceEnd" 
			    			onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'company.serviceStart\')}'})"  /> 
                 </td>                
             </tr>
             <tr>
                 
                 <td class="zuo"><span>经营范围</span>:</td>
                 <td class="you" colspan="3">
                 	<textarea name="company.bizScope" id="company.bizScope" style="width:91%"  class="textarea_size"><ww:property value="company.bizScope" /></textarea>
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