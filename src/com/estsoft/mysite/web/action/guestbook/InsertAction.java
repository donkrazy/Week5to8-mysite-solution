package com.estsoft.mysite.web.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;
import com.estsoft.web.action.Action;

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter( "name" );
		String password = request.getParameter( "pass" );
		String message = request.getParameter( "content" );
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
		dao.insert(vo);			
		response.sendRedirect( "/mysite/guestbook" );
	}

}
