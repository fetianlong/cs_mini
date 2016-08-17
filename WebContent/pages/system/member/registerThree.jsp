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
<link href="common/css/common.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="common/js/cropper/assets/js/jquery.min.js"></script>
<link href="common/js/cropper/dist/cropper.css" rel="stylesheet">
<script type="text/javascript" src="common/js/cropper/dist/cropper.js"></script>
<link href="common/js/cropper/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="common/js/cropper/examples/crop-avatar/css/main.css" rel="stylesheet">
<script type="text/javascript" src="common/js/cropper/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main_avatar.js" ></script>

<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript">
$().ready(function() {
	 $("#memberform").validate({
	      rules: {
			   "member.email": {
			                  email: true
			                 }
	  	  },
	      messages: {
	  		   "member.email": {
			          email: "请输入正确的email地址"
	  	               }
			 }
	    });
	});
		
</script>
</head>
<body>
用户注册--第三部 --完善客户资料
<form name="memberform" id="memberform" method="post" action="member/registerPerfect.action">
<table>
	<tr>
		<td>邮箱:</td>
		<td><input  type="text" name="member.email" id="email"   placeholder="请输入邮箱账号!"/></td>
		<td></td>
	</tr>
	<tr>
		<td>头像:</td>
		<td><input  type="hidden" name="member.avatar" id="memberAvatar"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td colspan="3" align="center"><input type="submit" value="注册完成"> </td>
		
	</tr>
</table>
</form>



  <div class="container" id="crop-avatar">
 <!-- Current avatar -->
    <div class="avatar-view" style="height:115px;width:115px;" title="选择头像">
      <img src="common/css/images/user_photo.png" alt="用户头像">
    </div>
    <!-- Cropping modal -->
    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form class="avatar-form" action="member/uploadAvatar.action" enctype="multipart/form-data" method="post">
          
          
          	<input type="hidden" name="imageData" id="imageData">
          
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">选择头像</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload">
                  <input class="avatar-src" name="pic_src" type="hidden">
                  <input class="avatar-data" name="pic_data" type="hidden">
                  <label for="avatarInput">&nbsp;</label>
                  <input class="avatar-input" id="avatarInput"  type="file">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9">
                    <div class="avatar-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div style="height:115px;width:115px;" class="avatar-preview preview-lg"></div>
                    <div style="display:none;" class="avatar-preview preview-md"></div>
                    <div style="display:none;" class="avatar-preview preview-sm"></div>
                  </div>
                </div>

                <div class="row avatar-btns">
                  <div class="col-md-9">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees">左转</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">-15deg</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-30" type="button">-30deg</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">-45deg</button>
                    </div>
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees">右转</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="30" type="button">30deg</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" type="submit">完成</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- <div class="modal-footer">
              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
            </div> -->
          </form>
        </div>
      </div>
    </div><!-- /.modal -->

    <!-- Loading state -->
    <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
  </div>









</body>
</html>