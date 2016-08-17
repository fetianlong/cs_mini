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

<title>车辆事故</title>

<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript" src="<%=path%>/common/js/FileUploadShow.js"></script>

<script type="text/javascript">
var deletePhotoArr = [10];
	$(function(){
		for(var imgIndex = 0;imgIndex <= 9;imgIndex++){
			$("#up"+imgIndex).uploadPreview({ Img: "ImgPr"+imgIndex, Width: 120, Height: 120 });
			deletePhotoArr.push('1');
		}
	
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carAccidentAdd.action";
		}else{
			url="<%=path%>/carservice/carAccidentUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"orderCode":{
					required: true
				},
				"memberName":{
					required: true
				},
				"vehiclePlateId":{
					required: true
				},
				"carAccident.accidentType":{
					required: true
				}
			},
			messages: {
				"orderCode":{
					required: "请选择事故订单!"
				},
				"memberName":{
					required: "请选择事故会员！"
				},
				"vehiclePlateId":{
					required: "请选择事故车辆！"
				},
				"carAccident.accidentType":{
					required: "请选择事故类型！"
				}
			}
			
		});
		
		val_check_SpecialChar("accidentCodeSc");
		
		var noticeFlag = '<ww:property value="carAccident.noticeCs" />';
		if(noticeFlag == '0' || noticeFlag == 0){
			$('#noticeTimeTrId').hide();
		}
		else if(noticeFlag == '1' || noticeFlag == 1){
			$('#noticeTimeTrId').show();
		}
		
		$('.timeselect').datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 2,
			autoclose: true,
			format:'yyyy-mm-dd',
			endDate:new Date()
		});
		$('.timeselect').attr('readonly','readonly');
	});
	
	
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	//取消按钮
	function cancel(){
		window.location.href="<%=path%>/carservice/carAccidentSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carAccidentAdd.action";
		}else{
			url="<%=path%>/carservice/carAccidentUpdate.action";	
		}
		
		var re=isValid();
		if(re){
// 			$.post(url,$("#eform").serialize(),r_saveAccident,'json').error(requestError);
			var formData = $("#eform").serialize();
			var data = new FormData();
			var ImgDivs = $('#addAccidentPhotoDiv').children();
			if(ImgDivs.length > 0){
				$.each(ImgDivs,function(index,imgDiv){
// 					var ff = $(imgDiv).find('input[type="file"]')[0];
					var ff = $(':file')[index].files[0];
					data.append('accidentImage'+index,ff);
				});
			}
		    data.append('formData',formData);
		    data.append('deletePhotoArr',deletePhotoArr);
		    $.ajax({
		        url: url,
		        type: 'POST',
		        data: data,
		        processData: false,
		        contentType: false,
		        dataType: 'json',
		        success:function(data){
		        	r_saveAccident(data);
		        }

		    });
		}
	}
	function r_saveAccident(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/carservice/carAccidentSearch.action";
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
function selectOrder(){
	$.dialog({
		id:'searchOrderDia',
	    title:'订单查询',
		content : 'url:<%=path%>/orders/ordersSearch.action?state=page&query=accident',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
function selectMember(){
	$.dialog({
		id:'searchMemberDia',
	    title:'会员查询',
		content : 'url:<%=path%>/subscriber/showSubscriberList.action?state=page',
		fixed:true,
		width:840,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
function selectCar(){
	$.dialog({
		id:'searchCarDia',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page&query=accident',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
function showNoticeTime(noticeFlag){
	if(noticeFlag == '0'){
		$('#noticeTimeTrId').hide();
	}
	else if(noticeFlag == '1'){
		$('#noticeTimeTrId').show();
	}
}
// var imgIndex = 0;
// function addPhotoDivInp(){
// 	var ImgDivs = $('#addAccidentPhotoDiv').children();
// 	if(ImgDivs.length >= 10){
// 		$.dialog.tips("最多上传10张照片！");
// 		return;
// 	}
// 	var photoDiv = '<div id="ImgDiv'+imgIndex+'"> <div><img id="ImgPr'+imgIndex+'" width="120" height="120" /></div> <input type="file" id="up'+imgIndex+'" /><input id="deleteInp'+imgIndex+'" type="button" value="删除图片" onclick="deletePhoto('+imgIndex+')"/> </div>';
// 	$('#addAccidentPhotoDiv').append($(photoDiv));
// 	$("#up"+imgIndex).uploadPreview({ Img: "ImgPr"+imgIndex, Width: 120, Height: 120 });
// 	imgIndex = imgIndex + 1;
// }
function deletePhoto(imgIndex){
    $('#ImgPr'+imgIndex).val('');
    $('#ImgPr'+imgIndex).attr('src','');
    $('#up'+imgIndex).show();
    $('#imgShow'+imgIndex).val('');
    $('#imgShow'+imgIndex).attr('src','');
    $('#imgShow'+imgIndex).attr('width','0');
    $('#imgShow'+imgIndex).attr('height','0');
    deletePhotoArr[imgIndex]='0';
// 	if(imgIndex < 9){
// 		imgIndex = imgIndex + 1;
// 		for(;imgIndex <= 9; imgIndex ++){
// 			$('#ImgDiv'+imgIndex).attr('id','ImgDiv'+(imgIndex - 1));
// 			$('#ImgPr'+imgIndex).attr('id','ImgPr'+(imgIndex - 1));
// 			$('#up'+imgIndex).attr('id','up'+(imgIndex - 1));
// 			$('#deleteInp'+imgIndex).attr('id','deleteInp'+(imgIndex - 1));
// 			$('#deleteInp'+imgIndex).attr('onclick','deletePhoto('+(imgIndex - 1)+')');
// 		}
// 	}
	
}
</script>

</head>
<body style="overflow-y:auto;" class="sgglPage">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="" enctype="multipart/form-data"  >
			<input type="hidden" name="carAccident.id" id="carAccident.id" value="<ww:property value="carAccident.id" />">
			<input type="hidden" name="carAccident.isDiscard" value="<ww:property value="carAccident.isDiscard" />">
			<input type="hidden" name="carAccident.code" value="<ww:property value="carAccident.code" />">
			
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>事故订单</span>:</td>
                <td class="you">
                  <input type="hidden" name="carAccident.orderId" id="orderId" value="<ww:property value="carAccident.orderId" />"/>
                  <input name="orderCode" id="orderCode" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carAccident.orderCode" />" />
						<input onclick="selectOrder();" type="button" value="选择" class="searchinputbut" />
                </td>  
                <td class="zuo1"><span>事故会员</span>:</td>
                  <td class="you1">
                      <input type="hidden" name="carAccident.memberId" id="memberId" value="<ww:property value="carAccident.memberId" />"/>
<%-- 	                  <input id="memberIdSearchInp" onclick="selectMember();" type="button" value="" class="searchinputbut" /> --%>
	                  <input name="memberName" id="memberName" type="text" readonly 
	                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
	                  		value="<ww:property value="carAccident.memberName" />" />
                  </td>   
             </tr>
		  	 <tr>
                <td class="zuo"><span>事故车辆</span>:</td>
                <td class="you">
                  <input type="hidden" name="carAccident.carId" id="carId" value="<ww:property value="carAccident.carId" />"/>
<%--                   <input id="carIdSearchInp" onclick="selectCar();" type="button" value="" class="searchinputbut" /> --%>
                  <input name="vehiclePlateId"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carAccident.plateNumber" />" />
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>事故类型</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carAccident.accidentType" id="carAccident.accidentType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('accidentType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carAccident.accidentType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>   
             </tr>
              <tr>
                <td class="zuo"><span>是否通知客服</span>:</td>
                <td class="you">
                  <div class="btt1 fl">
                      <select name="carAccident.noticeCs" onchange="showNoticeTime(this.value);" style="top:12px;height:26px;">
                        <option value="0" <ww:if test="carAccident.noticeCs==0">selected="selected"</ww:if>>否</option>
                       	<option value="1" <ww:if test="carAccident.noticeCs==1">selected="selected"</ww:if>>是</option>
					  </select>
				  </div>
                </td>  
                <td class="zuo1"><span>客户态度</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carAccident.acceptFlag" id="carAccident.acceptFlag" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('acceptFlag',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carAccident.acceptFlag==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>   
             </tr>
             <tr id="noticeTimeTrId" style="display: none">
             		
                  <td class="zuo1"><span>通知客服时间</span>:</td>
                  <td class="you1">
                    <input name="carAccident.noticeTime" value="<ww:property value="transDate10String(carAccident.noticeTime)"/>"
		    			type="text" class="input_size fl timeselect" id="onlineDate"  />
                  </td>   
                   
              </tr>
             <tr>
             	<td class="zuo1"><span>发生时间</span>:</td>
                 <td class="you1">
                   <input name="carAccident.happenTime" value="<ww:property value="transDate10String(carAccident.happenTime)"/>"
	    			type="text" class="input_size fl timeselect"  id="onlineDate" />
                 </td>  
                <td class="zuo1"><span>处理状态</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carAccident.handleStatus" id="carAccident.handleStatus" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('handleStatus',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carAccident.handleStatus==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>   
             </tr>
              <tr style="height:170px">
              	<td class="zuo1"><span>事故报案编号</span>:</td>
                <td class="you1">
                	<input class="input_size fl" type="text"  maxlength="30" name="carAccident.callCode" id="carAccident.callCode"  
                		value="<ww:property value="carAccident.callCode" />" />
                </td> 
              </tr>
              
              <tr style="height:170px">
              	 <td class="zuo1"><span>事故经过</span>:</td>
                 <td class="you1" colspan="3">
                  	<textarea name="carAccident.carLose" style="width: 91%" class="textarea_size"><ww:property value="carAccident.carLose"/></textarea>
                 </td> 
              </tr>
              <tr style="height:170px">
              	<td class="zuo1"><span>车辆受损情况</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carAccident.accidentDesc" style="width: 91%" class="textarea_size"><ww:property value="carAccident.accidentDesc"/></textarea>
                  </td>  
                 
              </tr>
             
               <tr style="height:170px">
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carAccident.remark" style="width: 91%" class="textarea_size"><ww:property value="carAccident.remark"/></textarea>
                  </td>  
                 
              </tr>
              <tr style="height:10px"><td colspan="4" style=" border-top:2px solid #A0AFAE;"></td></tr>
              <tr>
              	<td class="zuo1" valign="top"><span>事故现场照片</span>:</td>
                  <td class="you1" colspan="3">
                  <div id="addAccidentPhotoDiv">
<%--                   		<ww:if test="carAccident.photo0 != null && carAccident.photo0 != ''"> --%>
                  			<div id="ImgDiv0"> 
                  				<div>
                  					<ww:if test="carAccident.photo0 != null && carAccident.photo0 != ''">
                  						<img id="imgShow0" width="120" height="120" src="<ww:property value="carAccident.photo0" />"/>
                  					</ww:if>
                  					<ww:else>
	                  					<img id="ImgPr0" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up0" <ww:if test="carAccident.photo0 != null && carAccident.photo0 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp0" value="删除图片" onclick="deletePhoto(0)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo1 != null && carAccident.photo1 != ''"> --%>
                  			<div id="ImgDiv1"> 
                  				<div>
                  				    <ww:if test="carAccident.photo1 != null && carAccident.photo1 != ''">
                  						<img id="imgShow1" width="120" height="120" src="<ww:property value="carAccident.photo1" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr1" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up1" <ww:if test="carAccident.photo1 != null && carAccident.photo1 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp1" value="删除图片" onclick="deletePhoto(1)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo2 != null && carAccident.photo2 != ''"> --%>
                  			<div id="ImgDiv2"> 
                  				<div>
                  					<ww:if test="carAccident.photo2 != null && carAccident.photo2 != ''">
                  						<img id="imgShow2" width="120" height="120" src="<ww:property value="carAccident.photo2" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr2" width="120" height="120" />
                  					</ww:else>
                  					
                  				</div> 
                  				<input type="file" class="inputfile" id="up2" <ww:if test="carAccident.photo2 != null && carAccident.photo2 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp2" value="删除图片" onclick="deletePhoto(2)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo3 != null && carAccident.photo3 != ''"> --%>
                  			<div id="ImgDiv3"> 
                  				<div>
                  					<ww:if test="carAccident.photo3 != null && carAccident.photo3 != ''">
                  						<img id="imgShow3" width="120" height="120" src="<ww:property value="carAccident.photo3" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr3" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up3" <ww:if test="carAccident.photo3 != null && carAccident.photo3 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp3" value="删除图片" onclick="deletePhoto(3)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo4 != null && carAccident.photo4 != ''"> --%>
                  			<div id="ImgDiv4"> 
                  				<div>
                  					<ww:if test="carAccident.photo4 != null && carAccident.photo4 != ''">
                  						<img id="imgShow4" width="120" height="120" src="<ww:property value="carAccident.photo4" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr4" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up4" <ww:if test="carAccident.photo4 != null && carAccident.photo4 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp4" value="删除图片" onclick="deletePhoto(4)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo5 != null && carAccident.photo5 != ''"> --%>
                  			<div id="ImgDiv5"> 
                  				<div>
                  					<ww:if test="carAccident.photo5 != null && carAccident.photo5 != ''">
                  						<img id="imgShow5" width="120" height="120" src="<ww:property value="carAccident.photo5" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr5" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up5" <ww:if test="carAccident.photo5 != null && carAccident.photo5 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp5" value="删除图片" onclick="deletePhoto(5)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo6 != null && carAccident.photo6 != ''"> --%>
                  			<div id="ImgDiv6"> 
                  				<div>
                  					<ww:if test="carAccident.photo6 != null && carAccident.photo6 != ''">
                  						<img id="imgShow6" width="120" height="120" src="<ww:property value="carAccident.photo6" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr6" width="120" height="120" />
                  					</ww:else>
								</div> 
                  				<input type="file" class="inputfile" id="up6" <ww:if test="carAccident.photo6 != null && carAccident.photo6 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp6" value="删除图片" onclick="deletePhoto(6)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo7 != null && carAccident.photo7 != ''"> --%>
                  			<div id="ImgDiv7"> 
                  				<div>
                  					<ww:if test="carAccident.photo7 != null && carAccident.photo7 != ''">
                  						<img id="imgShow7" width="120" height="120" src="<ww:property value="carAccident.photo7" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr7" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up7" <ww:if test="carAccident.photo7 != null && carAccident.photo7 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp7" value="删除图片" onclick="deletePhoto(7)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo8 != null && carAccident.photo8 != ''"> --%>
                  			<div id="ImgDiv8"> 
                  				<div>
                  					<ww:if test="carAccident.photo8 != null && carAccident.photo8 != ''">
                  						<img id="imgShow8" width="120" height="120" src="<ww:property value="carAccident.photo8" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr8" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up8" <ww:if test="carAccident.photo8 != null && carAccident.photo8 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp8" value="删除图片" onclick="deletePhoto(8)"/> 
                  			</div>
<%--                   		</ww:if> --%>
<%--                   		<ww:if test="carAccident.photo9 != null && carAccident.photo9 != ''"> --%>
                  			<div id="ImgDiv9"> 
                  				<div>
                  					<ww:if test="carAccident.photo9 != null && carAccident.photo9 != ''">
                  						<img id="imgShow9" width="120" height="120" src="<ww:property value="carAccident.photo9" />"/>
                  					</ww:if>
                  					<ww:else>
                  					<img id="ImgPr9" width="120" height="120" />
                  					</ww:else>
                  				</div> 
                  				<input type="file" class="inputfile" id="up9" <ww:if test="carAccident.photo9 != null && carAccident.photo9 != ''">style="display:none"</ww:if> />
                  				<input type="button" id="deleteInp9" value="删除图片" onclick="deletePhoto(9)"/> 
                  			</div>
<%--                   		</ww:if> --%> 
                  		
                  </div>
<!--                   <input type="button" value="增加图片" onclick="addPhotoDivInp()" /> -->
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