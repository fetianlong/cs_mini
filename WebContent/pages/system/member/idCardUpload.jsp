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


<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main.js" ></script>




<script type="text/javascript">



</script>
</head>
<body>
用户注册---第二步 --上传用户资料
<form name="memberform" id="memberform" method="post" action="member/registerTwo.action">
<table>
	<tr>
		<td colspan="3" align="center"><input type="submit" value="注册"> </td>
		
	</tr>
</table>
</form>


<iframe style="width:100%; min-height:600px; border:1px solid #ccc;" id="maindoc" src="<%=path %>/test.jsp"></iframe>

 <div class="container" id="crop-avatar">

    <!-- Current avatar -->
    <div class="avatar-view" title="上传身份证件">
      <img src="common/js/cropper/images/idcard-license-pre_proc.jpg" alt="Avatar">
    </div>

    <!-- Cropping modal -->
    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form class="avatar-form" action="member/upload.action" enctype="multipart/form-data" method="post">
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">身份证上传</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload">
                  <input class="avatar-src" name="pic_src" type="hidden">
                  <input class="avatar-data" name="pic_data" type="hidden">
                <label for="avatarInput">选择图片:</label>
                  <input class="avatar-input" id="avatarInput" name="upload" type="file">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9" style="width: 95%;">
                    <div class="avatar-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div style="display:none;"class="avatar-preview preview-lg"></div>
                     <div style="display:none;" class="avatar-preview preview-md"></div>
                    <div style="display:none;" class="avatar-preview preview-sm"></div> 
                  </div>
                </div>

                <div class="row avatar-btns">
                  <div class="col-md-9" style="width:100%%;">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="左转">左转</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">左转15度</button>
                     &nbsp; <button  class="btn btn-primary" data-method="rotate" data-option="-30" type="button">左转30度</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">左转45度</button>
                    </div>
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="右转">右转</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
                      &nbsp;<button  class="btn btn-primary" data-method="rotate" data-option="30" type="button">右转30度</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
                    </div>
                    <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" type="submit">完成</button>
                  </div>
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