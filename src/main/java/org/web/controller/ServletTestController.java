package org.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test.do")
public class ServletTestController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	
    @Override
    public void init() throws ServletException {
        // 서블릿이 처음 메모리에 로드될 때 실행
        System.out.println("ServletTestController init() 호출됨");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // doGet/doPost 실행 전에 공통 처리 가능
        System.out.println("ServletTestController service() 호출됨");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        System.out.println("GET 요청 처리");

        // 요청 받을때
        req.setCharacterEncoding("UTF-8");

        // 응답 데이터 인코딩
        resp.setContentType("text/html; charset=UTF-8");

        // 파라미터 받기 (예: ?userName=홍길동)
        String userName = req.getParameter("userName");

        // 출력 스트림
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>제목</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>여름: " + userName + "</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        System.out.println("POST 요청 처리");

        // 요청 받을때
        req.setCharacterEncoding("UTF-8");

        // 응답 데이터 인코딩
        resp.setContentType("text/html; charset=UTF-8");

        // 파라미터 받기 (예: ?userName=홍길동)
        String userName = req.getParameter("userName");
        String userAge = req.getParameter("userAge");

        // 출력 스트림
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>제목</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>이름: " + userName + "</h1>");
        out.println("<h1>나이: " + userAge + "</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void destroy() {
        // 서블릿이 메모리에서 내려갈 때 실행
        System.out.println("ServletTestController destroy() 호출됨");
    }
}