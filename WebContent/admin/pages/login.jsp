<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="webwork" %>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台登录 - 华泰租车后台管理系统</title>

<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="<%=path%>/admin/common/js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/admin/common/js/fontawesome/css/font-awesome.min.css">
<%--<link rel="stylesheet" href="<%=path%>/admin/common/js/flatui/dist/css/flat-ui.min.css">--%>
<link rel="stylesheet" href="<%=path%>/admin/common/css/loginlt.css">
<script src="<%=path%>/admin/common/js/jquery.min.js"></script>
<script src="<%=path%>/admin/common/js/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/admin/common/js/flatui/dist/js/flat-ui.js"></script>
<%-- <script src="<%=path%>/admin/common/js/cookie.js"></script>
<script src="<%=path%>/admin/common/js/sha_1.js"></script> --%>

<script type="text/javascript" src="<%=path%>/common/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>

<script src="<%=path %>/admin/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>
<script>
$(function(){
	winheight=$(window).height();
	Mainheig=$(".MainContent").outerHeight();
	 couheig=(winheight-Mainheig)/2-10;
		 if(couheig<winheight){
			$(".MainContent").css("margin-top",couheig); 
			 }
});
</script>

<script>
var isCookie=false;
$(function(){
	$(".RememberPW").radiocheck();
	$(".RememberPW").on('change.radiocheck', function() {
		if ($(this).context.checked){
			$(this).val(1);
		} else {
			$(this).val(0);
		};
	});
	
	$("#name").keyup(function(e){
		isCookie=false;
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 $("#password").focus();
		 }
		});
	

	$("#password").keyup(function(e){
		isCookie=false;
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			$("#validateCode").focus();
		 }
		});

	$("#validateCode").keyup(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			$("#submitId").trigger("click");
		 }
		});
	
});
</script>
<script type="text/javascript">
var isRightClient = true;

$().ready(function () {
	if ($("#name").attr("value") == ""){
		$("#name").focus();
	}else if ($("#password").attr("value") == ""){
		$("#password").focus();
	}else{
		$("#loginBtn").focus();	//？
	}
	$("#loginform").validate({
		 errorElement : 'span',  
         errorClass : 'help-block',  
         focusInvalid : false,  
		rules: {
			name: {
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 6
			},
			validateCode: {
				required: true
			}
		},
		messages: {
			name: {
				required: "*用户名不能为空！",
				minlength: "*用户名至少2个字符！"
			},
			password: {
				required: "*密码不能为空！",
				minlength: "*密码至少6个字符！"
			},
			validateCode: {
				required: "*请输入验证码！"
			}
		},
		 highlight : function(element) {  
             //$(element).closest('.form-group').addClass('has-error');  
         },  
         success : function(label) {  
             label.closest('.form-group').removeClass('has-error');  
             label.remove();  
         },  
         errorPlacement : function(error, element) {  
        	 element.closest('.form-group .Yanzheng').append(error); 
         } 
	});
	//checkClient();
	
	//var usename = getCookie('SESSION_LOGIN_USERNAME_DEARHO');
	//var pwd = getCookie('SESSION_LOGIN_PASSWORD_DEARHO');
	//if(usename != null && $.trim(usename) != '' && pwd != null && $.trim(pwd) != ''){
		//document.getElementById("name").value = usename;
		//document.getElementById("password").value = "000000";
		//document.getElementById("isCookie").value = "true";
	//}
 });

function login(){
		if($("#loginform").valid()){
			/* if (isRightClient == false){
				showErrorClientInfo();
				return;
			}
			var name;
			var password;
			var isCookie = document.getElementById('isCookie').value;
			if(isCookie != null && isCookie == "true"){
				name = getCookie('SESSION_LOGIN_USERNAME_DEARHO');
				password = getCookie('SESSION_LOGIN_PASSWORD_DEARHO');
			}else{
				name = $('#name').val();
				password = $('#password').val();
			} */
			
	
			var pars={
				"name":$('#name').val(),
				"password":$('#password').val(),
				"validateCode":$('#validateCode').val(),
				"isCookie":isCookie,
				"autoLogin":$("#savaPwdChk").val(),
				tSessionId:new Date().valueOf(),
				postAjax:1
			};
			
			backupInfo();
			document.getElementById("submitId").style.display = "none";
			$("#submitMsgDiv").html("<div class='loading'>正在登录，请稍候...</div>");
			$.post('<%=path%>/common/doLogin.action',pars,r_login,'json').error(requestError);
		}else{
			alertinfo("请先输入必填项。");
		}
}

function r_login(data){
	switch(data.result){
		case 0:
			$("#submitMsgDiv").html("<div class='loading'>登录成功！</div>");
			document.location='<%=path%>/common/index.action';
			break;
		case 1:
			restoreInfo();
			alerterror(data.msg);
			document.getElementById("submitId").style.display = "inline";
	//		document.getElementById("yzmimgDiv").click();
			$("#submitMsgDiv").html("<div class='loading'></div>");
			break;
	}
}

function checkClient(){
	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0"){ 
		isRightClient = false;
		showErrorClientInfo();
	} 
} 

function showErrorClientInfo(){
	alertinfo("您的IE浏览器版本太低，请升级到IE8.0及以上，否则系统将无法使用！");	
}
</script>	
</head>

<body>


<div class="MainContent">
   <div class="container">
	<div class="row" style=" clear:both;">
	 <div class="col-md-6">
     <img src="<%=path %>/admin/common/images/login/img_letu/left_pic.png" class="img-responsive sign_left"/>
     </div>
     <div class="col-md-4 sign_box">
      <div class="top_tex"><img src="<%=path %>/admin/common/images/login/img_letu/login_text.png" class="img-responsive"/></div>
      <div class="LoginBlock">


				
				
					<form class="form-horizontal" name="loginform" id="loginform" method="post" action="<%=path %>/login.action?type=RTWGGHPWF">
						<input type="hidden" name="token" value="<ww:property value='token'/>" />
						<input type="hidden" name="security" value="<ww:property value='security'/>" />
						<input type="hidden" name="url" value="<ww:property value='url'/>" />
						<input type="hidden" name="netIndex" value="<ww:property value='netIndex'/>" />
						<input type="hidden" id="isCookie" name="isCookie" value="" />
						
					
						<div class="form-group has-feedback UsernameCheck has-error">
							<div class="col-sm-13 Yanzheng">
							<div class="input-group">
									
										
											<div class="input-group-addon"><img src="<%=path %>/admin/common/images/login/img_letu/uesr.jpg"></div>
												<ww:if test="@com.dearho.cs.util.CookieTool@getCookieValueByName(request,'cs_admin_authmark')!=NULL">
													<input type="text" class="form-control input-sm" id="name" name="name" placeholder="请输入用户名" value="<ww:property value="@com.dearho.cs.util.CookieTool@getCookieValueByName(request,'CS_ADMIN_PHONENO')"/>" >
												</ww:if>
												<ww:else>
													<input type="text" class="form-control input-sm" id="name" name="name"  placeholder="请输入用户名">
												</ww:else>
												
												
							</div>		
							</div>
						</div>
						
						<div class="form-group has-feedback PasswordCheck has-error">
						<div class="col-sm-13 Yanzheng">
							<div class="input-group">
								
										<div class="input-group-addon"><img src="<%=path %>/admin/common/images/login/img_letu/poww.jpg"></div>
											<ww:if test="@com.dearho.cs.util.CookieTool@getCookieValueByName(request,'cs_admin_authmark') !=NULL">
												<input type="password" class="form-control" id="password"  name="password" maxlength="20"  placeholder="请输入密码" value="" >
											</ww:if>
											<ww:else>
												<input type="password" class="form-control" id="password" name="password" maxlength="20"  placeholder="请输入密码">
											</ww:else>
							</div>
						</div>
						</div>
						<div class="form-group has-feedback YanzhengCheck has-error">
							<div class="row">
								<div class="col-xs-7 YanzhengInput">
								<div class="input-group" style="padding-top:4px;">
								       <div class="input-group-addon"><img src="<%=path %>/admin/common/images/login/img_letu/yan.jpg"></div>
<!-- 									<input type="text" class="form-control Yanzheng" id="username" placeholder="验证码"> -->
									 <input type="text" class="form-control" maxlength="5" value="不用填"   name="validateCode" id="validateCode">							
								</div>
								</div>
								<div class="col-xs-5" style="padding-top:2px;" >
<%-- 								<img src="<%=path%>/yzmimg"> --%>
									<div style="cursor:pointer" id="yzmimgDiv" onclick="javascript:document.getElementById('randImage').src = '<%=path%>/yzmimg?'+Math.random(); ">
										<img id="randImage" src="<%=path %>/yzmimg" alt="" />
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="padding:0;">
						<button type="button" id="submitId" onclick="javascript:login();" class="btn btn-primary SubmitButton btn-block active">登录</button>
						<div class="wenz" id="submitMsgDiv"></div>
						</div>
						<div class="checkbox SavePassword"  style="margin-top:5px;">
							<label style="padding-left:0;">							
								<input type="checkbox" class="RememberPW" id="savaPwdChk" value="1" checked="checked">记住密码
							</label>
						</div>
					</form>
	 </div>
     </div>	
	</div>
  </div>
</div>			

<ww:if test="@com.dearho.cs.util.CookieTool@getCookieValueByName(request,'cs_admin_authmark') !=NULL"> 
 <script type="text/javascript"> 
 	isCookie=true;
 </script>  

 </ww:if> 
</body>

</html>
