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
				//$( "#btn-next" ).hide();
				$( "#btn-next" ).attr( "disabled", true );
				return;
			}
			
			// HTML 생성한 후 UL에 append
			$.each( response.data, function(index, vo){
				//console.log( index + ":" + vo );
				$( "#gb-list" ).append( renderHtml( vo ) );	
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
	return html;	
}

$(function(){
	// ajax 방명록 메세지 등록
	$( "#form-insert" ).submit( function( event ) {
		 // submit 막음!
		event.preventDefault(); 
		
		// input & textarea 입력값 가져오기
		var name = $( "#name" ).val();
		var password = $( "#pass" ).val();
		var message = $( "#message" ).val();
		
		// 폼 리셋하기
		// reset()은 FORMHTMLElement 객체에 있는 함수! 
		this.reset();

		// AJAX 통신
		$.ajax({
			url:"${pageContext.request.contextPath }/guestbook", 
			type: "post",
			dataType: "json",
			data: "a=ajax-insert" +
				   "&name=" + name + 
				   "&pass=" + password +
				   "&content=" + message,
			success: function( response ){
				// console.log( response );
				$( "#gb-list" ).prepend( renderHtml( response.data ) );	
			},
			error: function( xhr/*XMLHttpRequest*/, status, error ) {
				console.error( status + ":" + error );
			}			
		});
	});
	
	// 바닥 근처까지 스크롤시 데이터 가져오기...
	$( window ).scroll( function(){
		// values for detecting bottom
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		var scrollTop = $(window).scrollTop();
		// logging
		console.log( documentHeight + ":" + windowHeight + ":" + scrollTop );	
		// measuring...
		if(  documentHeight - ( windowHeight + scrollTop ) < 50 ) {
			fetchList();
		}
	});

	// 다음 가져오기 버튼 클릭 이벤트 매핑
	$("#btn-next").on( "click", function(){
		fetchList();
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
							<td>이름</td><td><input type="text" name="name" id="name"></td>
							<td>비밀번호</td><td><input type="password" name="pass" id="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="message"></textarea></td>
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