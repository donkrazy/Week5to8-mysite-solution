package com.estsoft.mysite.web.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증 유무 체크
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect(request, response, request.getContextPath() + "/board");
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			WebUtil.redirect(request, response, request.getContextPath() + "/board");
			return;
		}

		// 데이터 받아오기
		String no = request.getParameter( "no" );
		if( no == null || WebUtil.isNumeric( no ) == false ) {
			WebUtil.redirect( request, response, request.getContextPath() + "/board" );
			return;
		}
		
		Long boardNo = Long.parseLong( no );
		
		// DAO 
		BoardDao boardDao = new BoardDao( new MySQLWebDBConnection() ) ;
		BoardVo boardVo = new BoardVo();
		boardVo.setNo( boardNo );
		boardVo.setUserNo( authUser.getNo() );
		boardDao.delete( boardVo );
		
		// 리다이렉트
		WebUtil.redirect( request, response, request.getContextPath() + "/board" );
	}

}
