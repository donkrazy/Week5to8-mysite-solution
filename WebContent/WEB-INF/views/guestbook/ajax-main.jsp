<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" >
var page = 1;
var fetchList = function() {
	$.ajax({
		url: "${pageContext.request.contextPath }/guestbook?a=ajax-list&p=" + page,
		type: "get",
		dataType: "json",
		data: "",
		success: function( response ) {
			if( response.result != "success" ) {
				return;
			}
			
			if( response.data.length == 0 ) {
				console.log( "end----------" );
				//$( "#btn-next" ).hide();
				$( "#btn-next" ).attr( "disabled", true );
				return;
			}
			
			// HTML 생성한 후 UL에 append
			$.each( response.data, function(index, vo){
				//console.log( index + ":" + vo );
				renderHtml( vo );
			});
			page++;
		},
		error: function( xhr/*XMLHttpRequest*/, status, error ) {
			console.error( status + ":" + error );
		}
	});	
}

var renderHtml = function( vo ) {
	// 대신에 js template Library를 사용한다.  ex) EJS, underscore.js
	var html = 
		"<li><table><tr>" +
		"<td>" + vo.name + "</td>" +
		"<td>" + vo.regDate + "</td>" +
		"<td><a href='#'>삭제</a></td>" +
		"</tr><tr>" +
		"<td colspan='3'>" + vo.message.replace( /\r\n/g, "<br>") + "</td>" +
		"</tr></table></li>";
		
	$( "#gb-list" ).append( html );	
}



$(function(){
	$( "#form-insert" ).submit( function(event) {
		event.preventDefault();
		$.ajax({
			url:"...", 
			type: "post",
			data: "a=ajax-insert&name=&pass=",
			success: function( response ){
				/*
				response = {
					result: "success",
					data: {
						"no":10,
						"password":"",
						"name":"고길동",
						"regDate":"2016-04-18 AM 11:33:16",
						"message":"ㅎㅇ"
					}
				}
				*/
			}
		})
		
		
		return false;
	});
	
	$("#btn-next").on( "click", function(){
		fetchList();
	});
	
	$( window ).scroll( function(){
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		var scrollTop = $(window).scrollTop();
		console.log( documentHeight + ":" + windowHeight + ":" + scrollTop );	
	});
	
	// 최초 데이터 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form id="form-insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id="gb-list"></ul>
				<div style="margin-top:20px; text-align:center">
					<button id="btn-next">다음 가져오기</button>		
				</div>	
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>