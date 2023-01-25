package hello.servlet.web.servlet;

import hello.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "memberFormServlet", urlPatterns = "/servlet/members/new-form")
public class MemberFormServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("    <meta charset='UTF-8'>");
        writer.println("    <title>Title</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("    <div>");
        writer.println("        <form action='/servlet/members/save' method='post'>");
        writer.println("            username : <input type='text' name='username'> <br>");
        writer.println("            age : <input type='text' name='age'> <br>");
        writer.println("            <button type='submit'>전송</button>");
        writer.println("        </form>");
        writer.println("    </div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
