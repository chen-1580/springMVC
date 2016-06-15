<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<style type="text/css">
.shadow-frame {
	margin-bottom: 15px;
	background-color: #FFF;
	border: 10px solid #FFF;
	-webkit-box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	overflow: hidden;
	text-align: center;
}

input {
	width: 1000px;
	height: 24px;
	font-size: 18;
}

button {
	height: 30px;
	float: left;
}
form {
	text-align: center;
}
#searchDiv {
	width: 100%;
	height: auto;
	overflow:hidden;
}
#search_input{
	width: 1000px;
	height: 30px;
	float: left;
}
.sResult {
	width: 500px;
	height: auto;
}

.outer_center {
	text-align: center;
}

ul {
	list-style: none;
	margin: 0;
	padding-left: 0;
	width: 996px;
	text-align: left;
	border: 2px #CCC solid;
}

ul li {
	height: 24px;
	font-size: 16px;
}
</style>
</head>
<body>
	<div class="shadow-frame">
		<form action="">
			<div class="outer_center">
				<div id="searchDiv">
					<input type="text" name="searchText" id="search_input">
					<button>搜索</button>
				</div>
				<div class="sResult"></div>
			</div>
		</form>
		<embed src="http://www.blogclock.cn/swf/S1000928c51b9b1-8.swf"
			Width="200px" Height="200px" type="application/x-shockwave-flash"
			quality="high" wmode="transparent">
		</embed>
		<object type="application/x-shockwave-flash" style="outline: none;"
			data="http://7xjomi.com1.z0.glb.clouddn.com/hamster.swf" width="205"
			height="160">
			<param name="movie"
				value="http://7xjomi.com1.z0.glb.clouddn.com/hamster.swf">
			<param name="AllowScriptAccess" value="always">
			<param name="wmode" value="opaque">
		</object>
		<embed src="http://www.blogclock.cn/swf/S10014498e66b1f-f.swf"
			Width="176px" Height="109px" type="application/x-shockwave-flash"
			quality="high" wmode="transparent"></embed>
	</div>
</body>
<script type="text/javascript">
$(function(){
	getFocusEvent();
})
var getFocusEvent = function(){
	$("#search_input").keyup(function(e){
		var e = this;
		var data = $(e).val();
		var i = +data.length;
		if(i != 0){
			$.ajax({
				type : 'get',
				url : '<%=request.getContextPath()%>/search/' + data,
					dataType : "json",
					success : function(e) {
						drawTable(e);
					}
				});
			}
		});
	}
	var drawTable = function(e) {
		var obj = $(".sResult");
		obj.html("");
		var html = "<ul class='ulResult'>";
		$.each(e, function(index, ele) {
			if (ele != null) {
				html += "<li>" + ele.name + "</li>";
			}
		});
		html != "" ? obj.append(html) : "";

	}
</script>
</html>