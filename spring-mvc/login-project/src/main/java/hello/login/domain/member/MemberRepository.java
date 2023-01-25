package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(sequence, member);
        return member;
    }

    public Member findById(Long memberId) {
        return store.get(memberId);
    }

    public Optional<Member> findByLoginId(String loginId) {
/*        List<Member> members = findAll();
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
*/
        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long memberId, Member memberParam) {
        Member member = findById(memberId);
        member.setLoginId(memberParam.getLoginId());
        member.setPassword(memberParam.getPassword());
        member.setName(memberParam.getName());
    }

    public void clear() {
        store.clear();
    }
}
