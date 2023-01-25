package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider; // 방법1. Provider로 DL하여 HTTP 요청 시 주입하는 방법
    private final MyLogger myLogger; // 방법2. proxy를 사용하는 방법

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
//        MyLogger myLogger = myLoggerObjectProvider.getObject(); // 방법1
        String requestURL = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);
        myLogger.log("Controller Test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }
}
