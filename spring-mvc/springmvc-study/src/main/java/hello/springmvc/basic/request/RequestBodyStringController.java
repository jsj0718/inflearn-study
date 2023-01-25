package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트 코드를 문자로 변경하려면 인코딩 필요

        log.info("requestBodyStringV1 messageBody={}", messageBody);

        response.getWriter().write("OK");
    }

    // InputStream(Reader) : Http 요청 메세지 바디 내용 직접 조회
    // OutputStream(Writer) : Http 요청 메세지 바디 내용 직접 결과 출력
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("requestBodyStringV2 messageBody={}", messageBody);

        responseWriter.write("OK");
    }

    // 메세지 컨버터 이용 (header, body 내용 조회 가능)
    // Spring이 바디에 있는 내용을 HttpEntity의 설정된 타입으로 변경하여 대신 실행
    // 반환 타입이 HttpEntity<>이면 Http의 Spec화된 내용을 그대로 반환
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();

        log.info("requestBodyStringV3 messageBody={}", messageBody);

        return new HttpEntity<>("OK");
    }

    // HttpEntity를 상속받은 RequestEntity, ResponseEntity를 사용할 수 있다.
    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV4(RequestEntity<String> httpEntity) throws IOException { // RequestEntity<>()를 쓰면 url 등을 추가할 수 있다.
        String messageBody = httpEntity.getBody();

        log.info("requestBodyStringV4 messageBody={}", messageBody);

        return new ResponseEntity<String>("ok", HttpStatus.CREATED); // ResponseEntity<>()를 쓰면 상태코드를 넣을 수 있다.
    }

    /**
     * @RequestBody : Http 요청 메세지의 바디 부분을 문자, 객체로 변환하여 매개변수로 가져온다.
     * @ResponseBody : 반환된 값을 바로 Http 응답 메세지의 바디에 직접 넣어서 보낸다.
     */
    @ResponseBody
    @PostMapping("/request-body-string-v5")
    public String requestBodyStringV5(@RequestBody String messageBody) {
        log.info("requestBodyStringV5 messageBody={}", messageBody);

        return "ok";
    }
}
