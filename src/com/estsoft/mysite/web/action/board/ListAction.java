package com.estsoft.mysite.web.action.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class ListAction implements Action {
	private static final int SIZE_LIST = 5;     // 리스팅되는 게시물의 수
	private static final int SIZE_PAGE = 3;   // 페이지 리스트에서 표시되는 페이지 수
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 검색어
		String keyword = request.getParameter( "kwd" );

		// DAO 생성
		BoardDao boardDao = new BoardDao( new MySQLWebDBConnection() ) ;
	
		// 페이징 정보
		String page = request.getParameter( "p" );
		long currentPage = 1; 
		if( page != null && WebUtil.isNumeric( page ) ) {
			currentPage = Long.parseLong( page );
		}
		long totalCount = boardDao.getTotalCount( keyword );
		long totalPage = (long)Math.ceil( (double)totalCount / SIZE_LIST );
		if( currentPage < 1 || currentPage > totalPage ) {
			currentPage = 1;
		}
		long firstPage = ( (long)Math.ceil( (double)currentPage / SIZE_PAGE  ) - 1 ) * SIZE_PAGE + 1;
		long lastPage = firstPage + SIZE_PAGE - 1;
		if( lastPage > totalPage ) {
			lastPage = totalPage;
		}
		long prevPage = 0;
		if( firstPage > SIZE_PAGE ) {
			prevPage = firstPage - 1;
		}
		long nextPage = 0;
		if( lastPage < totalPage ) {
			nextPage = lastPage + 1;
		}
		
		System.out.println( prevPage +  ":" + firstPage + ":" + lastPage + ":" + nextPage + ":" + currentPage );

		// 리스트 가져오기
		List<BoardVo> list = boardDao.getList( keyword, currentPage, SIZE_LIST  );

		// 포워딩
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "list", list );
		map.put( "totalCount",  totalCount);
		map.put( "sizeList",  SIZE_LIST );
		map.put( "keyword", keyword );
		map.put( "firstPage", firstPage );
		map.put( "lastPage", lastPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "currentPage", currentPage );
		
		request.setAttribute( "map", map );
		WebUtil.forward( request, response, "/WEB-INF/views/board/list.jsp" );
	}
}
