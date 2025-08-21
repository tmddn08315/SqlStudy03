package org.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("*.do")
public class ServletController2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GET");
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST 요청 처리");

		// 요청 받을때
		request.setCharacterEncoding("UTF-8");

		// 응답 데이터 인코딩
		response.setContentType("text/html; charset=UTF-8");

		// 파라미터 받기 (예: ?userName=홍길동)
		String userName = request.getParameter("userName");

		// 출력 스트림
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>제목</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>이름: " + userName + "</h1>");
		out.println("</body>");
		out.println("</html>");

		out.close();

	}

}
