package com.estsoft.mysite.web.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;
import com.estsoft.web.action.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong( request.getParameter( "no" ) );
		String password = request.getParameter( "password" );

		GuestbookVo vo = new GuestbookVo();
		vo.setNo( no );
		vo.setPassword(password);
		
		GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
		dao.delete( vo );
		
		response.sendRedirect( "/mysite/guestbook" );
	}

}
