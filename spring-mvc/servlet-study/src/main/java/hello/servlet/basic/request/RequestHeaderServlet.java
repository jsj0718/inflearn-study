package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
    }

    // Start Line의 모든 정보
    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- Request-Line - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); // HTTP Method
        System.out.println("request.getProtocol() = " + request.getProtocol()); // HTTP Protocol
        System.out.println("request.getScheme() = " + request.getScheme()); // HTTP
        System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header
        System.out.println("request.getRequestURI() = " + request.getRequestURI()); // /request-test
        System.out.println("request.getQueryString() = " + request.getQueryString()); //username=hi
        System.out.println("request.isSecure() = " + request.isSecure()); // https 사용 유무
        System.out.println("--- Request-Line - end ---");
        System.out.println();
    }

    // Header 모든 정보
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");

        // 과거 방식
        Enumeration<String> headerNames = request.getHeaderNames(); // HTTP의 모든 Header 정보 조회
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " : " + headerName);
        }

/*
        // 최근 방식 (java 11부터 지원)
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println("headerName = " + headerName));

 */

        // Header 정보 중 원하는 정보만 조회하고 싶은 경우
        System.out.println("request.getHeader(\"host\") = " + request.getHeader("host"));

        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    // Header 편의 기능 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 기능 조회 start ---");
        System.out.println("--- Header 편의 기능 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); // localhost
        System.out.println("request.getServerName() = " + request.getServerPort()); // 8080
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        Enumeration<Locale> locales = request.getLocales();
        while (locales.hasMoreElements()) {
            Locale locale = locales.nextElement();
            System.out.println("locale = " + locale); // ko_KR, ko, en_US, en (Accept 우선순위 순으로 나옴)
        }
        System.out.println("request.getLocale() = " + request.getLocale()); // ko_KR (가장 우선순위가 높은 것)
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println("cookie.getValue() = " + cookie.getValue());
            }
        }
        System.out.println();

        // message body와 관련
        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("--- Header 편의 기능 조회 end ---");
        System.out.println();
    }

    // 기타 부가적인 정보 조회 (Network Connection을 통해 알 수 있는 정보)
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");

        // 요청이 온 것에 대한 정보
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
        System.out.println("request.getRemotePort() = " + request.getRemotePort());
        
        // 나의 서버에 대한 정보
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName());
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
        System.out.println("request.getLocalPort() = " + request.getLocalPort());

        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }

}
