package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    /**
     * 스프링에서 프록시를 생성하여 객체 주입
     * Close Projection : 정해진 필드만 가져옴
     * Open Projection : SpEL 문법을 통해 문자 조합
     */
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
