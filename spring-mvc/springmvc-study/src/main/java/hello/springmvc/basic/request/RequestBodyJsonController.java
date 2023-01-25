package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("requestBodyJsonV1 messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); // messageBody 내용(JSON)을 HelloData 타입으로 변환
        log.info("requestBodyJsonV1 HelloData={}", helloData);

        response.getWriter().write("OK");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("requestBodyJsonV2 messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("requestBodyJsonV2 HelloData={}", helloData);

        return "OK";
    }

    // ObjectMapper 사용 없이 바로 Http 요청 메세지 바디의 Json 데이터를 객체에 바인딩 가능
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("requestBodyJsonV3 HelloData={}", helloData);

        return "OK";
    }

    // HttpEntity<Generic>를 통해서 JSON 조회 가능
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) throws IOException {
        HelloData helloData = data.getBody(); // data에는 body뿐만 아니라 header 정보도 포함하기 때문에 body만 따로 조회해야 함
        log.info("requestBodyJsonV4 HelloData={}", helloData);

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
        log.info("requestBodyJsonV5 HelloData={}", helloData);
        return helloData;
    }
}
