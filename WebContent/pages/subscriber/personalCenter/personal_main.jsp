<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>


<script type="text/javascript">
$(document).ready(function(){
        $(".tab li").click(function(){
        $(".tab li").eq($(this).index()).addClass("selected").siblings().removeClass('selected');
		$(".tab_view").removeClass("on").eq($(this).index()).addClass("on");
       //另一种方法: $("div").eq($(".tab li").index(this)).addClass("on").siblings().removeClass('on'); 

        });
    });

</script>
<script type="text/javascript"></script>

<style type="text/css">
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}

.tab_view{display:none; height:auto;}

.on{display:block;}

</style>
</head>

<body>
<div class="xyk_tit" style="margin-left:54px;">个人信息</div>
 <div class="tab_list">
            <ul class="tab">
              <li class="selected">基本资料</li>
              <li>修改密码</li>
              <li>修改手机号</li>          
            </ul>
            
         </div>
         
<!--基本资料 开始-->
         <div class="tab_view on">
             <iframe width="100%" height="1000"  style="border:none; margin:0px; padding:0px; clear:both;" src="subscriber/showBaseInfo.action"></iframe>  
           </div>      
<!--基本资料 结束-->
<!--修改密码 开始-->
         <div class="tab_view"> 
             <iframe width="100%" height="400"  style="border:none; margin:0px; padding:0px; clear:both;" src="pages/subscriber/personalCenter/personal_password.jsp"></iframe> 
         </div>
<!--修改密码 结束-->
<!--修改手机号 开始--> 
          <div class="tab_view">
           <iframe width="100%" height="400"  style="border:none; margin:0px; padding:0px; clear:both;" src="pages/subscriber/personalCenter/personal_phone_no.jsp"></iframe> 
          </div>
           
      
<!--修改手机号 结束-->
</body>
</html>
