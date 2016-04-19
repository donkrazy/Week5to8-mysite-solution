package com.estsoft.mysite.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/life")
public class LifeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map map = null;
	public void init(ServletConfig config) throws ServletException {
		System.out.println( "LifeServlet:init() called" );
	}
	public void destroy() {
		System.out.println( "LifeServlet:destory() called" );
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "LifeServlet:service() called" );
		super.service( request, response );
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "LifeServlet:doGet() called" );
		synchronized( map ) {
			
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "LifeServlet:doPost() called" );
		doGet(request, response);
	}

}
