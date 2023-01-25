package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheSubject implements Subject {

    private Subject target; //실제 호출해야하는 객체
    private String cacheValue;

    public CacheSubject(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            cacheValue = target.operation(); //캐시가 없는 경우 target 호출하여 값을 가져옴
        }
        return cacheValue; //캐시가 있는 경우 바로 반환
    }
}
