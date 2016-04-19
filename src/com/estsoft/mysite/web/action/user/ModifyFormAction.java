package com.estsoft.mysite.web.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect( request, response, "/mysite/main" );
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			WebUtil.redirect( request, response, "/mysite/main" );
			return;
		}
		
		UserDao dao = new UserDao( new MySQLWebDBConnection() );
		UserVo userVo = dao.get( authUser.getNo() );
		
		request.setAttribute( "userVo", userVo );
		WebUtil.forward( request, response, "/WEB-INF/views/user/modifyform.jsp" );
	}

}
