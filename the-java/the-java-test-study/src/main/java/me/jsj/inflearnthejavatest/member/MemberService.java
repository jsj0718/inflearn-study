package me.jsj.inflearnthejavatest.member;

import me.jsj.inflearnthejavatest.domain.Member;
import me.jsj.inflearnthejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
