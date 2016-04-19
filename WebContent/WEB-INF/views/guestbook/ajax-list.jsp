<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% pageContext.setAttribute( "newLine", "\r\n"); %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript">
var page = 1;
var fetchList = function() {
	$.ajax({
		url : "${pageContext.request.contextPath}/guestbook?a=ajax-list&p=" + page,
		type: "get",
		dataType: "json",
		data: "",
		contentType: "application/json",
		success: function( response ) {
			if( response.data.length == 0 ) {
				return;
			}
			
			$.each(response.data, function(index, data){
				console.log( data );
				var html = 
					"<li><table><tr>" +
					"<td>" + data.name + "</td>" +
					"<td>" + data.regDate + "</td>" +
					"<td><a href='#' class='a-del'>삭제</a></td>" +
					"</tr><tr><td colspan='3'>" + data.message.replace( /\r\n/g, "<br>" ) + "</td></tr></table></li>";
					
				$( "#gb-list" ).append( html );
			});
			
			page++;
		},
		error: function( jqXHR, status, e ){
			console.error( status + " : " + e );
		}
	});
}

$(function(){
	dialog = $( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 200,
		width: 350,
		modal: true,
		buttons: {
	    	"삭제": function(){
	    		dialog.dialog( "close" );
	       		console.log( "삭제 합니다." );
	    	},
	    	"취소": function() {
	    		dialog.dialog( "close" );
	    	}
		},
		close: function() {
	        $( "#dialog-form form" ).get( 0 ).reset();
	        $( "#gb-password" ).removeClass( "ui-state-error" );
		}
	});
	
	$("#btn-next").click(function(){
		fetchList();
	});
	
	$( window ).scroll( function() {
		console.log( $(window).scrollTop() + ":" + $(window).height() + ":" + $(document).height()  );
	});
	
	$( document ).on( "click", ".a-del", function( event ){
		event.preventDefault();
		dialog.dialog( "open" );
	} );

});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath}/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
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
				<div style="margin:10px 0; text-align:center">
					<button id="btn-next">다음 가져오기</button>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
	
	<div id="dialog-form" title="비밀번호 입력">
	  <p class="validateTips">글 작성시 입력했던 비밀번호 입력을 입력해 주세요.</p>
	  <form style="margin-top:20px">
	      <label for="password">비밀번호</label>
	      <input type="hidden" name="no" value="">
	      <input type="password" name="password" id="gb-password" value="" class="text ui-widget-content ui-corner-all">
	      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
	  </form>
	</div>	
</body>
</html>