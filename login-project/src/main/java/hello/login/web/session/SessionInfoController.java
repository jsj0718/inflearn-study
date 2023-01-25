package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다";
        }

        // 보관했던 세션 데이터 출력
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String sessionName = sessionNames.nextElement();
            log.info("session name={}, value={}", sessionName, session.getAttribute(sessionName));
        }

        log.info("sessionId={}", session.getId()); // JSessionID
        log.info("getMaxInactiveInteval={}", session.getMaxInactiveInterval()); // 유효시간 (defalut 30분)
        log.info("creationTime={}", new Date(session.getCreationTime())); // 생성시간
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); // 유저가 마지막에 접근한 시간 (중요)
        log.info("isNew={}", session.isNew()); // 새로 생성한지 여부

        return "세션 출력";
    }
}
