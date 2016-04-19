package com.estsoft.mysite.web.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for( Cookie cookie : cookies ) {
			System.out.println( cookie.getName() + ":" + cookie.getValue() );
		}
		
		Cookie cookie = new Cookie( "myCookie", "Hello World" );
		cookie.setMaxAge( 60 * 60 * 24 * 365 );
		cookie.setPath( "/mysite/" );
		response.addCookie( cookie );
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

}
