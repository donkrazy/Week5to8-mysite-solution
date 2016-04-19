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

public class ModifyAction implements Action {
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
		
		String name = request.getParameter( "name" );
		String password = request.getParameter( "password" );
		String gender = request.getParameter( "gender" );
		
		UserVo userVo = new UserVo();
		userVo.setNo( authUser.getNo() );
		userVo.setName( name );
		userVo.setPassword(password);
		userVo.setGender(gender);

		// db 업데이트
		UserDao dao = new UserDao( new MySQLWebDBConnection() );
		dao.update(userVo);
		
		// 세션 저장객체 내용 변경
		authUser.setName( name );
		
		// 리다이렉트
		WebUtil.redirect( request, response, "/mysite/user?a=modifyform&result=success" );
	}
}
