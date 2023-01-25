package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j // 로그 생성 방법 2. lombok 사용
@RestController
public class LogTestController {
    // 로그 생성 방법 1. Logger 객체 생성 후 현재 클래스를 매개변수로 넘김 (2가지 방식 존재)
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class);
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace("trace log="+ name);

        // log의 level
        log.trace("trace log={}", name);
        log.debug("debug log={}", name); // 개발 서버에 보기 위한 용도
        log.info("info log={}", name); // 중요한 정보
        log.warn("warn log={}", name); // 경고
        log.error("error log={}", name); // 에러 (알람으로 보거나, 별도의 파일에 남길 수 있음)

        return "ok";
    }
}
