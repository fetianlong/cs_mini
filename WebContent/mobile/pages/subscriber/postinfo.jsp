<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html >
<head>
<meta charset="utf-8">
<title>发票邮寄信息</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/userinfo.css">
<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form  name="subscriberform" id="subscriberform" method="post" >
		   <p class="Caption">发票信息</p>
		    <div class="form-group">
			<input type="text" class="form-control" name="subscriber.billTitle" maxlength="20" id="billTitle" value='<ww:property value="subscriber.billTitle"/>' placeholder="发票抬头">
		  </div>
		   <div class="form-group">
			<input type="text" class="form-control"  name="subscriber.billPostCode" id="billPostCode"  value='<ww:property value="subscriber.billPostCode"/>' maxlength="6" placeholder="邮编">
		  </div>
		  <div class="form-group">
			<input type="text" class="form-control" name="subscriber.postAddress" maxlength="400" id="postAddress" value='<ww:property value="subscriber.postAddress"/>'  placeholder="邮寄地址">
		  </div>
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit()"  class="btn btn-embossed btn-primary btn-block SubmitButton">确定</button>
			</div>
			<div id="myAlert" class="form-group col-xs-8 col-xs-offset-2 alert alert-warning hidden">
				   	<span id="msg"></span>
			</div>
		</form>
	</div>
  </div>
</div>

<!-- 模态框（Modal）alert -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="modelMsg"></div>
         <div class="modal-footer">
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div>
</div>
</div>
<!-- 模态框（Modal）end -->

<!-- 模态框（Modal） confirm-->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" 
   aria-labelledby="confirmModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="confirmModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="confirmMsg"></div>
         <div class="modal-footer">
             <button type="button" onclick="gotoAccountIndex()" class="btn btn-primary">确认</button>
         </div>
      </div>
</div>
</div>
<!-- 模态框（Modal）end -->
</body>

<script type="text/javascript">
function formSubmit(){
	if(formData==$('#subscriberform').serialize()){
		Alert("基本资料未修改，无需保存!");
		return;
	}
	
	if($("#subscriberform").valid()){
		$.ajax({
			type: "POST",
		    url: "<%=path %>/mobile/subscriber/updateBillInfo.action",
		    dataType:'json',
		    data:$('#subscriberform').serialize(),
		    success: function(data) {
				if(0==data.result){
					window.location.href="<%=path%>/mobile/chooseNeedBillOrders.action";
			    }else{
			    	Alert(data.msg);
			    }
		    },
			error: function(){
				Alert("保存失败，请稍后再试");	
			}
		});
	};
}


//查询网点
function queryDot(val){
	$("#dot").text("");
	if(val!=""){
		var data={"dot.areaId":val};
		$.post('<%=path %>/mobile/subscriber/ordersSearchDot.action',data,r_data,'json');
	}else{
		var str="<option value=''>请选择</option>";
		$("#dot").append(str);
	}
}

function r_data(data){
	$("#backSiteId").text("");
	if(data!=""){
		if(data.info!=""){
			var str="<option value=''>请选择</option>";
			for(var i=0;i<data.info.length;i++){
				str+="<option value="+data.info[i].id+">"+data.info[i].name+"</option>";
			}
			$("#dot").append(str);
			$("#backSiteId").append(str);
		}
	}
}

//查询城市
function queryCity(val){
	$("#area").empty();
	$("#dot").empty();
	$("#area").append("<option value=''>请选择</option>");
	$("#dot").append("<option value=''>请选择</option>");
	if(val!=""){
		var data={"dot.areaId":val};
		$.post('<%=path %>/mobile/subscriber/searchCity.action',data,area_data,'json');
	}else{
		//$("#dot").append(str);
	}
}

function area_data(data){
	if(data!=""){
		if(data.info!=""){
			var str="";
			for(var i=0;i<data.info.length;i++){
				str+="<option value="+data.info[i].id+">"+data.info[i].name+"</option>";
			}
			$("#area").append(str);

		}
	}
}
var formData;
$(document).ready(function () {
	formData=$('#subscriberform').serialize();

	 $('#subscriberform').validate({  
	        errorElement : 'span',  
	        errorClass : 'help-block',  
	        focusInvalid : false,  
	        rules: {
				   "subscriber.email": {
					  email: true,
		   			}
		  	  },
		      messages: {
		  	       "subscriber.email": {
		  	    	 email: "请输入正确格式的邮箱"
		  	         }
				 },
	        highlight : function(element) {  
	            $(element).closest('.form-group').addClass('has-error');  
	        },  
	        success : function(label) {  
	            label.closest('.form-group').removeClass('has-error');  
	            label.remove();  
	        },  
	        errorPlacement : function(error, element) {  
	            element.parent('div').append(error);  
	        } 
	    });

});

function wechatUnbinding(){
	$.ajax({
		type: "POST",
	    url: "<%=path %>/mobile/subscriber/wechatUnbinding.action",
	    dataType:'json',
	    data:$('#subscriberform').serialize(),
	    success: function(data) {
			if(0==data.result){
				confirmMsg("解绑成功!");
		    }else{
		    	Alert(data.msg);		
		    }
	    },
		error: function(){
			Alert("微信解绑失败，请稍后再试");	
		}
	});
}

function alertMsg(msg){
		$('#myModal').modal('show');
		$("#modelMsg").html(msg);
	};

function confirmMsg(msg){
		$('#confirmModal').modal('show');
		$("#confirmMsg").html(msg);
	};
	
function gotoAccountIndex(){
	  window.location.href="<%=path %>/mobile/subscriber/showBaseInfo.action";
	};

</script>
</html>
