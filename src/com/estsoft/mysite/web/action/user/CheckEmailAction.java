package com.estsoft.mysite.web.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.web.action.Action;

import net.sf.json.JSONObject;

public class CheckEmailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter( "email" );
		UserDao dao = new UserDao( new MySQLWebDBConnection() );
		UserVo vo = dao.get(email);
		
		// protocol
		/*
		 *       { 
		 *       	result:   "success" or "fail"
		 *       	message:  실패한 경우에만  어떤 fail이 났는지 메세지
		 *          data: 통신 데이터  
		 *       }
		 *       
		 *        { result: "fail", message:에러메서지 }  -> 이메일을 사용할 수 있는 경우
		 *        
		 *       통신이 성공한 경우
		 *       { result: "success", data:true }  -> 이메일을 사용할 수 있는 경우
		 *       { result: "success", data:false }  -> 이메일을 사용할 수 없는 경우
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo == null );
//		map.put( "isExist", vo != null );
		
		response.setContentType( "application/json; charset=utf-8" );
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = JSONObject.fromObject( map );
		out.println( jsonObject.toString() );
	}
}
