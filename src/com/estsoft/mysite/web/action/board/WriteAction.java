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

public class WriteAction implements Action {

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
		
		// DAO
		BoardDao boardDao = new BoardDao( new MySQLWebDBConnection() ) ;

		// 데이터 받아오기
		String title = request.getParameter( "title" );
		String content = request.getParameter( "content" );
		String groupNo = request.getParameter( "group-no" );
		String orderNo = request.getParameter( "order-no" );
		String depth = request.getParameter( "depth" );
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContent(content);
		boardVo.setUserNo( authUser.getNo() );
		if( groupNo != null ) {
			boardVo.setGroupNo( Integer.parseInt( groupNo ) );
			boardVo.setOrderNo( Integer.parseInt( orderNo ) + 1 );
			boardVo.setDepth( Integer.parseInt( depth ) + 1 );
			
			boardDao.updateGroupOrder( boardVo );
		}
		
		boardDao.insert( boardVo );
		
		// 리다이렉트
		WebUtil.redirect( request, response, request.getContextPath() + "/board" );
	}

}
