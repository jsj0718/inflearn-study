package study.datajpa.repository;

/**
 * 클래스 기반 Projection
 * 1. 생성자가 핵심 (생성자의 파라미터를 분석하여 매칭함) -> 실제 구현 객체 주입
 */
public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
