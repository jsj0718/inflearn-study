package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // 메세지 Body 부분을 바이트 코드로 얻음
        // 바이트 코드를 String로 변환 시 Spring의 유틸리티를 사용
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 항상 바이트 -> 문자로 변환 시 인코딩 정보 필요 (반대도 동일)

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("OK");
    }
}
