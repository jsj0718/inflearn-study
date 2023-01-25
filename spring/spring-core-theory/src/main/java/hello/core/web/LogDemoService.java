package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

//    private final ObjectProvider<MyLogger> myLoggerObjectProvider; // 방법1. Provider로 DL하여 HTTP 요청 시 주입하는 방법
    private final MyLogger myLogger; // 방법2. proxy를 사용하는 방법

    public void logic(String userId) {
//        MyLogger myLogger = myLoggerObjectProvider.getObject(); // 방법1
        myLogger.log("service id: " + userId);
    }

}
