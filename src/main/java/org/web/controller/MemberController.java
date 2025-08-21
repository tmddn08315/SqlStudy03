package org.web.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.web.constraint.Role;
import org.web.dto.MemberDto;
import org.web.service.MemberService;
import org.web.service.impl.MemberServiceImpl;

import com.sun.net.httpserver.Request;

@WebServlet("*.member") // *.member로 끝나는 모든 요청 처리
public class MemberController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("POST 요청 처리");
        req.setCharacterEncoding("UTF-8");

        String path = req.getContextPath();
        String uri = req.getRequestURI();
        String basicURI = uri.substring(path.length());

        System.out.println("Basic URL: " + basicURI);

        MemberService memberService = new MemberServiceImpl();

        try {
            if ("/insert.member".equals(basicURI)) {
                // 회원가입 처리
                String userEmail = req.getParameter("userEmail");
                String userName = req.getParameter("userName");
                String ageParam = req.getParameter("age");

                int age = 0;
                if (ageParam != null && !ageParam.isEmpty()) {
                    age = Integer.parseInt(ageParam);
                }

                int rs = memberService.save(new MemberDto(null, userEmail, userName, age, null, null, null));

                if (rs != 1) throw new IllegalArgumentException("회원가입 실패!");

                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<html><head><title>회원가입 결과</title></head><body>");
                out.println("<h1>이름: " + userName + "</h1>");
                out.println("<h2>이메일: " + userEmail + "</h2>");
                out.println("<h3>나이: " + age + "</h3>");
                out.println("</body></html>");
                out.close();

            } else if ("/update.member".equals(basicURI)) {
                // 회원 정보 수정 처리
                String idParam = req.getParameter("memberId");
                String userEmail = req.getParameter("userEmail");
                String userName = req.getParameter("userName");
                String roleParam = req.getParameter("role");
                String ageParam = req.getParameter("age");

                // memberId 검증
                if (idParam == null || idParam.isEmpty()) {
                    throw new IllegalArgumentException("회원 ID가 필요합니다.");
                }

                Long memberId = null;
                try {
                    memberId = Long.parseLong(idParam);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("유효한 회원 ID를 입력해주세요.");
                }

                // 나이 처리
                int age = (ageParam != null && !ageParam.isEmpty()) ? Integer.parseInt(ageParam) : 0;

                // role 처리 (null이면 기본 USER 지정)
                Role role = Role.MEMBER;
                if (roleParam != null && !roleParam.isEmpty()) {
                    try {
                        role = Role.valueOf(roleParam.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        // 잘못된 role 값 들어오면 기본 USER로 처리
                        role = Role.MEMBER;
                    }
                }

                // DB에 존재 여부 확인
                MemberDto existing = memberService.findById(memberId);
                if (existing == null) {
                    throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
                }

                // DTO 생성 후 업데이트
                MemberDto dto = new MemberDto(memberId, userEmail, userName, age, role, null, null);
                int rs = memberService.updateSave(dto);

                resp.setContentType("text/html; charset=UTF-8");
                try (PrintWriter out = resp.getWriter()) {
                    out.println("<html><head><title>회원수정 결과</title></head><body>");
                    if (rs == 1) {
                        out.println("<h1>회원 정보 수정 성공!</h1>");
                    } else {
                        out.println("<h1>회원 정보 수정 실패!</h1>");
                    }
                    out.println("</body></html>");
                }
            

            } else if ("/delete.member".equals(basicURI)) {
                // 회원 삭제 처리
                String idParam = req.getParameter("memberId");
                if (idParam == null || idParam.isEmpty()) {
                    throw new IllegalArgumentException("회원 ID가 필요합니다.");
                }

                Long memberId = Long.parseLong(idParam);
                int rs = memberService.deleteById(memberId);

                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<html><head><title>회원삭제 결과</title></head><body>");
                if (rs == 1) {
                    out.println("<h1>회원 삭제 성공!</h1>");
                } else {
                    out.println("<h1>회원 삭제 실패!</h1>");
                }
                out.println("</body></html>");
                out.close();

            } else if ("/select.member".equals(basicURI)) {
                // 전체 회원 조회
                List<MemberDto> memberDtos = memberService.findAll();

                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<html><head><title>회원목록</title><meta charset='utf-8'></head><body>");
                out.println("<h1>회원목록조회</h1>");
                out.println("<table border='1'><tr>");
                out.println("<th>아이디</th><th>userEmail</th><th>userName</th><th>age</th><th>role</th><th>createTime</th><th>updateTime</th>");
                out.println("</tr>");
                for (MemberDto memberDto : memberDtos) {
                    out.println("<tr>");
                    out.println("<td>" + memberDto.getMemberId() + "</td>");
                    out.println("<td>" + memberDto.getUserEmail() + "</td>");
                    out.println("<td>" + memberDto.getUserName() + "</td>");
                    out.println("<td>" + memberDto.getAge() + "</td>");
                    out.println("<td>" + memberDto.getRole() + "</td>");
                    out.println("<td>" + memberDto.getCreateTime() + "</td>");
                    out.println("<td>" + memberDto.getUpdateTime() + "</td>");
                    out.println("<td><a href='" + req.getContextPath() + "/detail.member?memberId=" + memberDto.getMemberId() + "'>보기</a></td>");
                    out.println("</tr>");
                }
                out.println("</table></body></html>");
                out.close();

            } else {
                System.out.println("알 수 없는 명령: " + basicURI);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>오류</title></head><body>");
            out.println("<h1>오류 발생: " + e.getMessage() + "</h1>");
            out.println("</body></html>");
            out.close();
        }
    }
}