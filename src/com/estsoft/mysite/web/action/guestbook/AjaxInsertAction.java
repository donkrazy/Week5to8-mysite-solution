package com.estsoft.mysite.web.action.guestbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;
import com.estsoft.web.action.Action;

import net.sf.json.JSONObject;

public class AjaxInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *  parameter 값을 가져오기
		 */
		
		GuestbookVo vo = new GuestbookVo();
		/*
		 *   vo 값 세팅
		 *   name, password, message
		 */
		GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
		Long no = dao.insert( vo );
		vo = dao.get( no );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo );
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		response.setContentType( "application/json; charset=utf-8" );
		response.getWriter().print( jsonObject );		
	}

}
