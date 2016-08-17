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
<title>车型管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
var deletePhotoArr = [4];
	$(function(){
		
		for(var imgIndex = 0;imgIndex <= 3;imgIndex++){
			$("#up"+imgIndex).uploadPreview({ Img: "ImgPr"+imgIndex, Width: 120, Height: 120 });
			deletePhotoArr.push('1');
		}
		
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/carVehicleModel/carVehicleModelAdd.action";
		}else{
			url="<%=path%>/carVehicleModel/carVehicleModelUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"carVehicleModel.name": {
					required: true,
					carVehicleModelNameSc:true
				},
				"carVehicleModel.brand":{
					required: true
				},
				"carVehicleModel.level":{
					required: true
				},
				"carVehicleModel.engineType":{
					required: true
				},
				"carVehicleModel.standardMileage":{
					digits:true
				},
				"carVehicleModel.casesNum":{
					required: true,
					digits:true
				},
				"carVehicleModel.luggageNum":{
					required: true,
					number:true
				},
				"carVehicleModel.seatingNum":{
					required: true,
					digits:true
				},
				"carVehicleModel.gearboxType":{
					required: true
				},
				"jszStrategyBaseId":{
					required: true
				}
				
			},
			messages: {
				"carVehicleModel.name": {
					required: "请输入车辆型号"
				},
				"carVehicleModel.brand":{
					required: "请选择车辆品牌"
				},
				"carVehicleModel.level":{
					required: "请选择车辆等级"
				},
				"carVehicleModel.engineType":{
					required: "请选择动力类型"
				},
				"carVehicleModel.standardMileage":{
					digits:"续航里程必须为整数"
				},
				"carVehicleModel.casesNum":{
					required:"请选择车辆厢数",
					digits:"车辆厢数必须为数字"
				},
				"carVehicleModel.luggageNum":{
					required:"请输入行李数",
					number:"行李箱容积必须为数字"
				},
				"carVehicleModel.seatingNum":{
					required:"请输入座位数",
					digits:"座位数必须为整数"
				},
				"carVehicleModel.gearboxType":{
					required: "请选择挡类别"
				},
				"jszStrategyBaseId":{
					required: "请选择时租策略"
				}
			}
			
		});
		val_check_SpecialChar("carVehicleModelNameSc");
	});
	
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	
	function cancel(){
		window.location.href="<%=path%>/carVehicleModel/carVehicleModelSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
    function IsNum(e) {
        var k = window.event ? e.keyCode : e.which;
        if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
        } else {
            if (window.event) {
                window.event.returnValue = false;
            }
            else {
                e.preventDefault(); //for firefox 
            }
        }
    } 
    
	function sub(){
		if(checkPhotoType() == false){
			return;
		}
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/carVehicleModel/carVehicleModelAdd.action";
		}else{
			url="<%=path%>/carVehicleModel/carVehicleModelUpdate.action";	
		}
		$("#carVehicleModel\\.name").removeAttr("disabled"); 
		$("#carVehicleModel\\.brand").removeAttr("disabled"); 
		var re=isValid();
		if(re){
			//$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
// 			var formData = $("#eform").serialize();
// 			var data = new FormData();
// 		    data.append('webModelPhoto', $(':file')[0].files[0]);
// 		    data.append('androidModelPhoto', $(':file')[1].files[0]);
// 		    data.append('IOSModelPhoto', $(':file')[2].files[0]);
// 		    data.append('microWebModelPhoto', $(':file')[3].files[0]);
// 		    data.append('formData',formData);
// 		    $.ajax({
// 		        url: url,
// 		        type: 'POST',
// 		        data: data,
// 		        processData: false,
// 		        contentType: false,
// 		        dataType: 'json',
// 		        success:function(data){
// 		        	r_saveCar(data);
// 		        }

// 		    });
		    
		    var formData = $("#eform").serialize();
			var data = new FormData();
			data.append('webModelPhoto', $(':file')[0].files[0]);
		    data.append('androidModelPhoto', $(':file')[1].files[0]);
		    data.append('IOSModelPhoto', $(':file')[2].files[0]);
		    data.append('microWebModelPhoto', $(':file')[3].files[0]);
			 data.append('deletePhotoArr',deletePhotoArr);
			var formDatas = formData.split('&');
			$.each(formDatas,function(index,fd){
				data.append(fd.split('=')[0],fd.split('=')[1]);
			});
			 $.ajax({
				 url: url,
			        type: 'POST',
			        data: data,
			        processData: false,
			        contentType: false,
			        dataType: 'json',
			        success:function(data){
			        	r_saveCar(data);
			        }

			    });
		}
	}
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/carVehicleModel/carVehicleModelSearch.action";
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
	function checkPhotoType(){
		if($('#up0').val() != null && $('#up0').val() != ""){
			if(!isimg($('#up0').val())){
				$.dialog.tips("web图片上传错误！");
				return false;
			}
		}
		if($('#up1').val() != null && $('#up1').val() != ""){
			if(!isimg($('#up1').val())){
				$.dialog.tips("Android图片上传错误！");
				return false;
			}
		}
		if($('#up2').val() != null && $('#up2').val() != ""){
			if(!isimg($('#up2').val())){
				$.dialog.tips("ios图片上传错误！");
				return false;
			}
		}
		if($('#up3').val() != null && $('#up3').val() != ""){
			if(!isimg($('#up3').val())){
				$.dialog.tips("微网站图片上传错误！");
				return false;
			}
		}
		return true;
	}
	function isimg(ths)
	{
		if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths)) {  
		    return false;  
		} 
		else{
			return true;
		}
	}
	function deletePhoto(imgIndex){
	    $('#ImgPr'+imgIndex).val('');
	    $('#ImgPr'+imgIndex).attr('src','');
	    $('#up'+imgIndex).show();
	    $('#imgShow'+imgIndex).val('');
	    $('#imgShow'+imgIndex).attr('src','');
	    $('#imgShow'+imgIndex).attr('width','0');
	    $('#imgShow'+imgIndex).attr('height','0');
	    deletePhotoArr[imgIndex]='0';
	}
	
var api = frameElement.api, W = api.opener;
function selectStrategyBase(targetId,targetName){
	$.dialog({
		id:'searchStrategyBaseDial',
	    title:'基础计费策略查询',
		content : 'url:<%=path%>/feestrategy/strategyBase/searchStrategyBase.action?state=page&targetId='+targetId+'&targetName='+targetName,
		fixed:true,
		width:740,
		height:450,
		resize:false,
		max: false,
	    min: false,
	    lock: true,
	    parent:api,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
</script>
</head>
<body  style="overflow-y: auto;" class="bxglPage">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="" enctype="multipart/form-data"  >
			<input type="hidden" name="carVehicleModel.id" id="carVehicleModel.id"
					value="<ww:property value="carVehicleModel.id" />">
			
			 <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
			 	<tr>
                    <td class="zuo"><span class="xx red">*</span><span>车辆型号</span>:</td>
                    <td class="you">
                      <input class="input_size fl"  type="text"  maxlength="30"
                       name="carVehicleModel.name" id="carVehicleModel.name"   value="<ww:property value="carVehicleModel.name" />" 
                       <ww:if test="carVehicleModel.id != null">disabled="disabled"</ww:if>
                       />
                    </td>  
                    <td class="zuo1"><span class="xx red">*</span><span>车辆品牌</span>:</td>
                      <td class="you1">
                      <div class="btt1 fl">
	                      <select name="carVehicleModel.brand" id="carVehicleModel.brand" 
	                       <ww:if test="carVehicleModel.id != null">disabled="disabled"</ww:if>
	                       style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('10',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="carVehicleModel.brand==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                      </td>   
                 </tr>
                 
                 <tr>
                    <td class="zuo"><span class="xx red">*</span><span>车辆等级</span>:</td>
                    <td class="you">
                    	<div class="btt1 fl">
	                      <select name="carVehicleModel.level" id="carVehicleModel.level" style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('1',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="carVehicleModel.level==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                    </td>  
                    <td class="zuo1"><span class="xx red">*</span><span>动力类型</span>:</td>
                      <td class="you1">
                      	<div class="btt1 fl">
	                        <select name="carVehicleModel.engineType" id="carVehicleModel.engineType" style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('2',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="carVehicleModel.engineType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                      </td>   
                 </tr>
                 <tr>
                    <td class="zuo"><span class="xx red">*</span><span>座位数</span>:</td>
                    <td class="you">
                      <input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      name="carVehicleModel.seatingNum" value="<ww:property value="carVehicleModel.seatingNum" />" />
                    </td>  
                    <td class="zuo1"><span class="xx red">*</span><span>行李箱容积（L）</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text"  maxlength="8" id="carVehicleModel.luggageNum"  
                        name="carVehicleModel.luggageNum" value="<ww:property value="carVehicleModel.luggageNum" />" />

                      </td>   
                 </tr>
				 
				 <tr>
                    <td class="zuo"><span class="xx red">*</span><span>车辆厢数</span>:</td>
                    <td class="you">
                    	<div class="btt1 fl">
	                        <select name="carVehicleModel.casesNum" id="carVehicleModel.casesNum" style="top:12px;height:26px;">
	                        <option value="3" <ww:if test="carVehicleModel.casesNum==3">selected="selected"</ww:if>>3</option>
							<option value="2" <ww:if test="carVehicleModel.casesNum==2">selected="selected"</ww:if>>2</option>
							</select>
						</div>
                    </td>  
                    <td class="zuo1"><span class="xx red">*</span><span>档类别</span>:</td>
                      <td class="you1">
                      	<div class="btt1 fl">
	                        <select name="carVehicleModel.gearboxType" id="carVehicleModel.gearboxType" style="top:12px;height:26px;">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('3',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  
									<ww:if test="carVehicleModel.gearboxType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
						</div>
                      </td>   
                 </tr>
                 
                 <tr>
                 	<td class="zuo"><span>导航仪</span>:</td>
                      <td class="you">
                      	<div class="btt1 fl">
	                        <select name="carVehicleModel.hasgps" style="top:12px;height:26px;">
	                        	<option value="0" <ww:if test="carVehicleModel.hasgps==0">selected="selected"</ww:if>>没有</option>
								<option value="1" <ww:if test="carVehicleModel.hasgps==1">selected="selected"</ww:if>>有</option>
							</select>
						</div>
                      </td>   
                 <%--
                    <td class="zuo"><span>标准续航里程</span>:</td>
                    <td class="you">
                      <input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      name="carVehicleModel.standardMileage" value="<ww:property value="carVehicleModel.standardMileage" />" />
                    </td>  
                 --%>
                    <td class="zuo1"><span>娱乐系统</span>:</td>
                      <td class="you1">
                      	<div class="btt1 fl">
	                        <select name="carVehicleModel.entertainmentSystem" style="top:12px;height:26px;">
	                        	<option value="0" <ww:if test="carVehicleModel.entertainmentSystem==0">selected="selected"</ww:if>>没有</option>
								<option value="1" <ww:if test="carVehicleModel.entertainmentSystem==1">selected="selected"</ww:if>>有</option>
							</select>
						</div>
                      </td>   
                 </tr>
                 
                 <tr>
                 	<td class="zuo"><span class="xx red">*</span><span>即时租策略</span>:</td>
	                <td class="you">
	                  	<input type="hidden" name="carVehicleModel.shizuStrategy" id="jszStrategyBaseId" value="<ww:property value="carVehicleModel.shizuStrategy" />"/>
	                    <input class="InlineInput" name="jszStrategyBaseName" id="jszStrategyBaseName" type="text" readonly 
	                  	    class="input_size fl"
	                  		value="<ww:property value="carVehicleModel.shizuStrategyName" />" />
						<input onclick="selectStrategyBase('jszStrategyBaseId','jszStrategyBaseName');" type="button" value="选择" class="searchinputbut" />
	                </td>
	                <td class="zuo"><span>日租策略</span>:</td>
	                <td class="you">
	                  	<input type="hidden" name="carVehicleModel.rizuStrategy" id="rzStrategyBaseId" value="<ww:property value="carVehicleModel.rizuStrategy" />"/>
	                    <input  class="InlineInput" name="rzStrategyBaseName" id="rzStrategyBaseName" type="text" readonly 
	                  	    class="input_size fl"
	                  		value="<ww:property value="carVehicleModel.rizuStrategyName" />" />
						<input onclick="selectStrategyBase('rzStrategyBaseId','rzStrategyBaseName');" type="button" value="选择" class="searchinputbut" />
	                </td>
                 </tr>
                 <tr>
                    <td class="zuo"><span>标准续航里程</span>:</td>
                    <td class="you">
                      <input class="input_size fl" type="text" maxlength="8" id="carVehicleModel.standardMileage"
                      name="carVehicleModel.standardMileage" value="<ww:property value="carVehicleModel.standardMileage" />" />
                    </td>  
                 </tr>
                 
                 <tr>
                    <td class="zuo"><span>剩余电量/可行驶里程</span>：</td>
                    <td class="you"></td>  
                    <td class="zuo"><span>剩余电量/可行驶里程</span>：</td>
                    <td class="you"></td>  
                 </tr>
                 <tr>
                    <td class="zuo"><span>20%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                     	    name="carVehicleModel.mileage20" value="<ww:property value="carVehicleModel.mileage20" />" />
                    </td>  
                    <td class="zuo1"><span>30%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      		name="carVehicleModel.mileage30" value="<ww:property value="carVehicleModel.mileage30" />" />
                    </td>  
                 </tr>
                 <tr>
                    <td class="zuo"><span>40%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                     	    name="carVehicleModel.mileage40" value="<ww:property value="carVehicleModel.mileage40" />" />
                    </td>  
                    <td class="zuo1"><span>50%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      		name="carVehicleModel.mileage50" value="<ww:property value="carVehicleModel.mileage50" />" />
                    </td>  
                 </tr>
                 <tr>
                    <td class="zuo"><span>60%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                     	    name="carVehicleModel.mileage60" value="<ww:property value="carVehicleModel.mileage60" />" />
                    </td>  
                    <td class="zuo1"><span>70%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      		name="carVehicleModel.mileage70" value="<ww:property value="carVehicleModel.mileage70" />" />
                    </td>  
                 </tr>
                 <tr>
                    <td class="zuo"><span>80%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                     	    name="carVehicleModel.mileage80" value="<ww:property value="carVehicleModel.mileage80" />" />
                    </td>  
                    <td class="zuo1"><span>90%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                      		name="carVehicleModel.mileage90" value="<ww:property value="carVehicleModel.mileage90" />" />
                    </td>  
                 </tr>
                 <tr>
                    <td class="zuo"><span>100%</span>:</td>
                    <td class="you">
                    	<input class="input_size fl" type="text" onkeypress="return IsNum(event);" maxlength="8"  
                     	    name="carVehicleModel.mileage100" value="<ww:property value="carVehicleModel.mileage100" />" />
                    </td>  
                 </tr>
                 <tr>
                 
                    <td class="zuo"><span>车型照片(Web)</span>:</td>
                    <td class="you">
                    	<div id="ImgDiv0"> 
               				<div>
               					<ww:if test="carVehicleModel.webModelPhoto != null && carVehicleModel.webModelPhoto != ''">
               						<img id="imgShow0" width="120" height="120" src="<ww:property value="carVehicleModel.webModelPhoto" />"/>
               					</ww:if>
               					<img id="ImgPr0" width="120" height="120" />
               				</div> 
               				<input type="file" class="inputfile" id="up0" <ww:if test="carVehicleModel.webModelPhoto != null && carVehicleModel.webModelPhoto != ''">style="display:none"</ww:if> />
               				<input type="button" id="deleteInp0" value="删除图片" onclick="deletePhoto(0)"/> 
               			</div>
               			
                    </td>  
                    <td class="zuo1"><span>车型照片(Android)</span>:</td>
                    <td class="you1">
                    	<div id="ImgDiv1"> 
               				<div>
               				    <ww:if test="carVehicleModel.androidModelPhoto != null && carVehicleModel.androidModelPhoto != ''">
               						<img id="imgShow1" width="120" height="120" src="<ww:property value="carVehicleModel.androidModelPhoto" />"/>
               					</ww:if>
               					<img id="ImgPr1" width="120" height="120" />
               				</div> 
               				<input type="file" class="inputfile" id="up1" <ww:if test="carVehicleModel.androidModelPhoto != null && carVehicleModel.androidModelPhoto != ''">style="display:none"</ww:if> />
               				<input type="button" id="deleteInp1" value="删除图片" onclick="deletePhoto(1)"/> 
               			</div> 
                   </td>   
                 </tr>
                 <tr>
                    <td class="zuo"><span>车型照片(IOS)</span>:</td>
                    <td class="you">
                    	<div id="ImgDiv2"> 
               				<div>
               					<ww:if test="carVehicleModel.IOSModelPhoto != null && carVehicleModel.IOSModelPhoto != ''">
               						<img id="imgShow2" width="120" height="120" src="<ww:property value="carVehicleModel.IOSModelPhoto" />"/>
               					</ww:if>
               					<img id="ImgPr2" width="120" height="120" />
               				</div> 
               				<input type="file" class="inputfile" id="up2" <ww:if test="carVehicleModel.IOSModelPhoto != null && carVehicleModel.IOSModelPhoto != ''">style="display:none"</ww:if> />
               				<input type="button" id="deleteInp2" value="删除图片" onclick="deletePhoto(2)"/> 
               			</div>
                    </td>  
                    <td class="zuo1"><span>车型照片(微网站)</span>:</td>
                    <td class="you1">
                    	<div id="ImgDiv3"> 
               				<div>
               					<ww:if test="carVehicleModel.microWebModelPhoto != null && carVehicleModel.microWebModelPhoto != ''">
               						<img id="imgShow3" width="120" height="120" src="<ww:property value="carVehicleModel.microWebModelPhoto" />"/>
               					</ww:if>
               					<img id="ImgPr3" width="120" height="120" />
               				</div> 
               				<input type="file" class="inputfile" id="up3" <ww:if test="carVehicleModel.microWebModelPhoto != null && carVehicleModel.microWebModelPhoto != ''">style="display:none"</ww:if> />
               				<input type="button" id="deleteInp3" value="删除图片" onclick="deletePhoto(3)"/> 
               			</div>
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