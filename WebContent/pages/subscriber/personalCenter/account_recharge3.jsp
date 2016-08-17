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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>充值方式2</title>
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}
</style>
</head>
<body>
 <!--充值 开始-->
        <div class="zhgl">
             <div class="xyk_tit">充值</div>
             <div class="tuikuan2">
                 <div class="chz">
                 <li class="chongzhi-j">充值金额：<span>100.00</span>元</li>
                 </div>                   
                 <ul class="zhifu_ta">
                   <li class="zf_selected">支付宝</li>
                   <li>网上银行</li>                  
                 </ul>
                 <span class="xz fr">选择您的网银，输入网银登录帐号和支付密码完成付款。</span>
                 <div class="zhifu_con_xz">
                  <div class="zhifu_con2">
                           <table width="" border="0" style="margin:0 auto; margin-top:20px;">
                              <tr>
                                 <td width="173" height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                            </table>
                     </div>
                     <div class="tuk_sub" style="margin-top:15px;">下一步</div>
                 </div>
                 
             </div>
            
                    
        </div>
        <!--退款 开始-->
</body>
</html>
