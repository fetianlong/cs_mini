<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/portal/pages/common/include.jsp" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>华泰介绍 - <%=siteName %></title>

<%@ include file="/portal/pages/common/common_head.jsp"%>


<link rel="stylesheet" href="<%=path%>/portal/common/js/fullpage/jquery.fullPage.css">
<link rel="stylesheet" href="<%=path%>/portal/common/css/transform.css">

<link rel="stylesheet" href="<%=path%>/portal/common/css/about.css">

<script src="<%=path%>/portal/common/js/fullpage/jquery.fullPage.min.js"></script>

<script>
$(function(){
	$(".MainContent").fullpage({
		navigation: "true",
		continuousVertical: "true"	,
		onLeave: function(index, nextIndex, direction){
			$(".section" + nextIndex + " img").css("opacity", "0");
		},
		afterLoad: function(anchorLink, index){
			switch(index){
				case 2: 
					$(".section2 img:eq(0)").addClass("fadeInShortLeft");
					$(".section2 img:eq(1)").addClass("fadeInShortRight");
					$(".section2 img:eq(2)").addClass("fadeInShortLeft");
					break;
				case 3:
					$(".section3 img:eq(0)").addClass("fadeInShortBottom");
					$(".section3 img:eq(1)").addClass("fadeInTop");
					break;
				case 4:
					$(".section4 img").addClass("fadeInShortBottom");
					break;
				case 5:
					$(".section5 img").addClass("fadeInShortBottom");
					break;
				case 6:
					$(".section6 img:eq(0)").addClass("fadeInShortTop");
					$(".section6 img:eq(1)").addClass("fadeShow");
					break;
				case 7:
					$(".section7 img").addClass("fadeInShortBottom");
					break;
				case 8:
					$(".section8 img").addClass("fadeInShortBottom");
					break;
				case 9:
					$(".section9 img:eq(0)").addClass("fadeInShortTop");
					$(".section9 img:eq(1)").addClass("fadeShow");
					break;
				case 10:
					$(".section10 img").addClass("fadeInBottom");
					break;
			};
		}
	});
});
</script>
</head>

<body>
<ww:set name="mainNavIndex" value="'about'" />
<%@include file="/portal/pages/common/page_header.jsp" %>


<div class="MainContent">
    <div class="section section1">
		<img class="img-responsive center-block fadeShow" src="<%=path%>/portal/common/images/about/1_1.png">
	</div>
    <div class="section section2">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/2_1.png">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/2_2.png">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/2_3.png">
	</div>
    <div class="section section3">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/3_1.png">
		<img class="imgfloat3" src="<%=path%>/portal/common/images/about/3_2.png">
	</div>
    <div class="section section4">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/4_1.png">
	</div>
	<div class="section section5">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/5_1.png">
	</div>
	<div class="section section6">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/6_1.png">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/6_2.png">
	</div>
	<div class="section section7">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/7_1.png">
	</div>
	<div class="section section8">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/8_1.png">
	</div>
	<div class="section section9">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/9_1.png">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/9_2.png">
	</div>
	<div class="section section10">
		<img class="img-responsive center-block" src="<%=path%>/portal/common/images/about/10_1.png">
	</div>
</div>

<%@include file="/portal/pages/common/page_bottom.jsp" %>

</body>
</html>
