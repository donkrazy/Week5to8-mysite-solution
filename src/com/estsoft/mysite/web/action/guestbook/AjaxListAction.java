package com.estsoft.mysite.web.action.guestbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;
import com.estsoft.web.action.Action;

import net.sf.json.JSONObject;

public class AjaxListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter( "p" );
		
		GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
		List<GuestbookVo> list = dao.getList( Integer.parseInt( page ) );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", list );
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		response.setContentType( "application/json; charset=utf-8" );
		response.getWriter().print( jsonObject );
	}

}
