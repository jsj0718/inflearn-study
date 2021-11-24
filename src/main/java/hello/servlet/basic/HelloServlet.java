package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // request.getParameter()로 Query Parameter를 쉽게 조회 가능
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // response message의 content type과 encoding을 설정 (http message header 부분)
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // http message body 부분에 데이터가 실림
        response.getWriter().write("hello, " + username);
    }
}
