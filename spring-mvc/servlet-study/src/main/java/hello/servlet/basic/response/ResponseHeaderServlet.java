package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 200, 직접 코드를 입력하는 것보다 상수 입력이 더 좋다. (매직 넘버는 가독성을 떨어뜨린다.)
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400

        // [response-header]
//        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 무효화
        response.setHeader("Pragma", "no-cache"); // http 1.1 이전 버전 캐시 무효화
        response.setHeader("my-header", "hello"); // 임의의 header 생성 가능

        // [Header의 편의 메서드]
        content(response);

        // [cookie 편의 메서드]
        cookie(response);

        // [redirect 편의 메서드]
        redirect(response);

        // [message body]
        PrintWriter writer = response.getWriter();
        writer.println("OK");
    }

    private void content(HttpServletResponse response) {
        // Content-Type: text/plain;charset=utf-8
        // Content-Length: 2

        // 방법 1
        // response.setHeader("Content-Type", "text/plain;charset=utf-8");

        // 방법2
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
//        response.setContentLength(2); // 생략 시 자동 생성
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;

        // 방법 1
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");

        // 방법 2
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

        // 방법 1
        //response.setStatus(HttpServletResponse.SC_FOUND); // 302
        //response.setHeader("Location", "/basic/hello-form.html");

        // 방법 2
        response.sendRedirect("/basic/hello-form.html");
    }
}
